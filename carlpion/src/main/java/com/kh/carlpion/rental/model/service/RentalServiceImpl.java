package com.kh.carlpion.rental.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.stereotype.Service;

import com.kh.carlpion.admin.model.dao.RentCarMapper;
import com.kh.carlpion.admin.model.dto.RentCarDTO;
import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.auth.model.vo.CarlpionUserDetails;
import com.kh.carlpion.exception.exceptions.BadRequestException;
import com.kh.carlpion.exception.exceptions.CarNotFoundException;
import com.kh.carlpion.exception.exceptions.RentCarNotFoundException;
import com.kh.carlpion.payment.model.service.PortoneService;
import com.kh.carlpion.rental.model.dao.RentalMapper;
import com.kh.carlpion.rental.model.dto.PreparePaymentRequestDTO;
import com.kh.carlpion.rental.model.dto.RentCarPriceDTO;
import com.kh.carlpion.rental.model.dto.ReservationDTO;
import com.kh.carlpion.rental.model.dto.ReservationDetailDTO;
import com.kh.carlpion.rental.model.dto.ReservationHistoryDTO;
import com.kh.carlpion.rental.model.entity.Reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService{
	
	private final RentCarMapper rentCarMapper;
	private final RentalMapper rentalMapper;
	private final PortoneService portoneService;
	private final AuthService authService;


	@Override
	public List<RentCarDTO> searchRentCarList(ReservationDTO reservation) {
		
		List<RentCarDTO> rentCarList = null;
		
		if(reservation.getRentalDate() == null || reservation.getReturnDate() == null) {
			throw new BadRequestException("잘못된 요청입니다.");
		}
		
		rentCarList = rentCarMapper.getRentCarListByAddr(reservation);
		
		for(RentCarDTO rentCar : rentCarList) {
			rentCar.getCarModel().setImgURL("http://localhost/uploads/carModel/" + rentCar.getCarModel().getImgURL());
		}
		
		return rentCarList;
	}

	@Override
	public List<RentCarDTO> getRentalListByParkingId(ReservationDTO reservation) {
		
		List<RentCarDTO> rentCarList = null;
		
		rentCarList = rentCarMapper.getRentalListByParkingId(reservation);
		
		for(RentCarDTO rentCar : rentCarList) {
			rentCar.getCarModel().setImgURL("http://localhost/uploads/carModel/" + rentCar.getCarModel().getImgURL());
		}
		
		return rentCarList;
		
	}
	
	@Override
	public List<RentCarDTO> getRentalListByCarNo(int carNo) {
		
		List<RentCarDTO> rentCarList = null;
		
		rentCarList = rentCarMapper.getRentalListByCarNo(carNo);
		
		for(RentCarDTO rentCar : rentCarList) {
			rentCar.getCarModel().setImgURL("http://localhost/uploads/carModel/" + rentCar.getCarModel().getImgURL());
		}
		
		return rentCarList;
		
	}

	@Override
	public Map<String, Integer> preparePaymdent(PreparePaymentRequestDTO preparePaymentRequest) {
		
		long userNo = authService.getUserDetails().getUserNo();
		
		int check = rentalMapper.checkDuplicationReservationByUserNo(preparePaymentRequest ,userNo);
		
		if(check > 0) {
			throw new CarNotFoundException("사용자께서 이미 예약한 차량이 존재합니다.\n확인 후 다시 시도해 주세요");
		}
		
		int checkNum = rentalMapper.checkReservation(preparePaymentRequest.getMerchantUid());
		
		if(checkNum > 0) {
			throw new CarNotFoundException("이미 존재하는 예약 아이디입니다.");
		}
		
		int checkDuplication = rentalMapper.checkDuplicationReservation(preparePaymentRequest);
		
		if(checkDuplication > 0) {
			throw new CarNotFoundException("예약 불가능한 차량입니다.");
		}
		
		String checkVal = rentCarMapper.checkCarNo(preparePaymentRequest.getCarNo()); 
		
		if(checkVal == null) {
			throw new CarNotFoundException("차량이 존재하지 않습니다.");
		}
		
		RentCarPriceDTO rentCarPrice = rentalMapper.getRentCarPriceByCarNo(preparePaymentRequest.getCarNo());
		
		int totalPrice = calculate(preparePaymentRequest.getRentalDate(), 
								   preparePaymentRequest.getReturnDate(), 
				  				   rentCarPrice.getRentPrice(),
				  				   rentCarPrice.getHourPrice());
		
		Map<String, Integer> response = new HashMap<>();
        response.put("totalPrice", totalPrice);
        
		return response;
	}
	
	

	@Override
	public void completePayment(ReservationDetailDTO reservationDetail) {
		
		String accessToken = portoneService.getAccessToken();
        Map<String, Object> paymentData = portoneService.verifyPayment(reservationDetail.getImpUID(), accessToken);

        int paidAmount = (int) paymentData.get("amount");
        String status = (String) paymentData.get("status");

        RentCarPriceDTO rentCar = rentalMapper.getRentCarPriceByCarNo(reservationDetail.getCarNo());
		
        int expectedPrice = calculate(reservationDetail.getRentalDate(), 
        		   reservationDetail.getReturnDate(), 
        		   rentCar.getRentPrice(),
        		   rentCar.getHourPrice()); 
        
        if (paidAmount != expectedPrice || !"paid".equals(status)) {
            throw new RentCarNotFoundException("결제 검증 실패!!");
        }
    	
    	CarlpionUserDetails user = authService.getUserDetails();
        
    	Reservation reservation = Reservation.builder()
    											 .merchantUid(reservationDetail.getMerchantUid())
    										  	 .userNo(user.getUserNo())
    										  	 .carNo(reservationDetail.getCarNo())
    										  	 .parkingId(rentCar.getParkingId())
    										  	 .rentalDate(reservationDetail.getRentalDate() + ":00")
    										  	 .returnDate(reservationDetail.getReturnDate() + ":00")
    										  	 .totalPrice(expectedPrice)
    										  	 .status("결제 완료")
    										  	 .impUID(reservationDetail.getImpUID())
    										  	 .build();
        
        rentalMapper.saveReservation(reservation);
	}
	

	private int calculate(String rentalDate, String returnDate, int rentPrice, int hourPrice) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy/mm/dd HH");   
		
		Date frentalDate = null;
		Date freturnDate = null;
		try {
			frentalDate = dateFormat.parse(rentalDate);
			freturnDate = dateFormat.parse(returnDate);
		} catch (ParseException e) {
			throw new TypeMismatchDataAccessException("타입 문제 발생!");
		}
		
		long difference = freturnDate.getTime() - frentalDate.getTime();
		
		return (int)(difference / (1000 * 60 * 60)) * hourPrice + rentPrice;
	}

	@Override
	public ReservationHistoryDTO getPaymentHistory(String impUID) {
		
		int checkNum = rentalMapper.checkReservationByImpUID(impUID);
		
		if(checkNum < 1) {
			throw new CarNotFoundException("존재하지 않은 주문번호입니다.");
		}
		
		ReservationHistoryDTO reservation = rentalMapper.getPaymentHistory(impUID);
		
		return reservation;
	}

	@Override
	public ReservationHistoryDTO getReservation() {
		
		long userNo = authService.getUserDetails().getUserNo();
		
		ReservationHistoryDTO reservation = rentalMapper.getReservation(userNo);
		
		return reservation;
	}

	@Override
	public Map<String, Object> getRentHistory(int limit) {
		
		if(limit < 0) {
			throw new CarNotFoundException("잘못된 요청입니다.");
		}
		
		long userNo = authService.getUserDetails().getUserNo();
		
		RowBounds rowBounds = new RowBounds(0, limit);
		
		List<ReservationHistoryDTO> reservationList = rentalMapper.getRentHistory(rowBounds, userNo);
		
		int historyCount = rentalMapper.getRentHistoryCount(userNo);
		
		Map<String, Object> history = new HashMap();
		
		history.put("historyList", reservationList);
		history.put("historyCount", historyCount);
		
		return history;
	}

	@Override
	public List<ReservationHistoryDTO> getReservationList() {

		long userNo = authService.getUserDetails().getUserNo();
		
		List<ReservationHistoryDTO> reservationList = rentalMapper.getReservationAllList(userNo);
		
		return reservationList;
	}

	@Override
	public void deleteReservationByImpUID(String impUID) {
		
		long userNo = authService.getUserDetails().getUserNo();
		
		Long checkUserNo = rentalMapper.getUserNoByImpUID(impUID);
		
		if(checkUserNo == null) {
			throw new CarNotFoundException("존재하지 않는 주문번호 요청입니다.");
		}
		
		if(checkUserNo != userNo) {
			throw new CarNotFoundException("요청 권한이 없는 사용자 입니다.");
		}
		
		int checkTime = rentalMapper.checkDeleteTime(impUID);
		
		if(checkTime > 0) {
			throw new CarNotFoundException("반납완료 혹은 대여중인 차량은 예약취소가 불가합니다.");
		}
		
		rentalMapper.deleteReservationByImpUID(impUID);
	}

}
