# Carlpion

전기차 공유 예약 서비스와 커뮤니티 기능을 통합한 웹 플랫폼입니다.  
사용자 간의 정보 교류와 차량 예약을 하나의 시스템에서 지원하여 편의성과 접근성을 높이고,  
친환경적 이동 수단의 접근성을 강화해 지속 가능한 모빌리티 생태계 조성을 목표로 합니다.

## 배포 주소

아래 주소에서 서비스를 바로 체험해보실 수 있습니다.  
👉 [https://carlpion.store](https://carlpion.store)  

## 주요 기능

- **회원 관리**
  - 회원가입, 로그인 (Spring Security + JWT)
  - 구글 소셜 로그인
  - 아이디/비밀번호 찾기
  - 회원탈퇴, 비밀번호/프로필/닉네임/이름 수정
  - 게시글/포인트 내역 조회

- **차량 관리 및 예약**
  - 차량 모델/운용차량(위치) 관리
  - 예약 가능 차량 및 위치 조회
  - 차량 렌트(결제), 사용자 예약 정보 관리

- **커뮤니티**
  - 게시판 조회/작성/수정/삭제
  - 댓글 기능

## 사용 기술

- **Frontend:** React, HTML, CSS, Axios
- **Backend:** Spring Boot, Spring Security
- **Database:** Oracle, MyBatis, JDBC
- **DevOps:** GCP, Docker, Firebase Hosting/Storage
- **Version Control:** Git, GitHub
- **Tools:** STS4, VS Code, Postman, DBeaver
- **Collaboration:** Notion, Figma, Slack
- **External API:** PortOne(결제), Kakao Maps(지도), Google OAuth(로그인), 서울시 공공데이터 포털(공영주차장 정보)

## 프로젝트 목표

- 직관적이고 반응성 높은 UI/UX 설계 (React 기반)
- JWT 인증 방식 도입으로 보안성 및 유지보수성 강화
- 전기차 중심 공유 시스템을 통해 친환경적 이동 수단 확산
- 커뮤니티 및 정보 교류 활성화를 통한 사용자 편의성 극대화
