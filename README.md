# 🏠 Meomureum (머무름)

> **머무름**은 단기 연속 거주를 위한 솔루션을 제공하는 플랫폼입니다.  
> 2주~한 달 단위로 지속적으로 머물 수 있는 방을 제공하며, 월세 기반의 간편한 결제 시스템을 지원합니다.

---

## 🛠️ 프로젝트 구조

demo ├── config │ ├── AppConfig.java │ ├── SecurityConfig.java │ └── SwaggerConfig.java ├── controller │ ├── ConsultController.java │ ├── ContentController.java │ ├── HomeController.java │ ├── KakaoApiController.java │ ├── LoginController.java │ ├── OAuth2Controller.java │ ├── RoomApiController.java │ └── UserController.java ├── dto │ ├── OAuth2Attribute.java │ ├── RoomRequest.java │ └── SessionUser.java ├── entity │ ├── Address.java │ ├── Contents.java │ ├── Role.java │ ├── Room.java │ └── User.java ├── repository │ ├── AddressRepository.java │ └── ContentsRepository.java ├── service │ ├── CustomOAuth2UserService.java │ ├── CustomUserDetailsService.java │ ├── UserService.java │ ├── ContentService.java │ ├── KakaoApiService.java │ ├── LoginService.java │ └── RoomService.java ├── resources │ ├── static │ │ ├── css │ │ │ ├── content/styles.css │ │ │ └── home/styles.css │ │ └── images │ │ ├── home-instr2.png │ │ └── home-instr3.png │ └── templates │ ├── apt-room.html │ ├── coliving-room.html │ ├── consult.html │ ├── content-form.html │ ├── content-list.html │ ├── deal-room.html │ └── discount-room.html

yaml
복사
편집

---

## ✨ 주요 기능

### 🔑 사용자 인증
- **OAuth2.0**를 활용한 소셜 로그인 (카카오, 구글, 네이버 등 지원).
- **Spring Security**를 기반으로 한 사용자 인증 및 권한 관리.

### 🏠 방 정보 제공
- 지역 및 조건별 필터를 활용한 방 검색.
- 단기 거주 계약 정보를 제공.

### 📄 콘텐츠 관리
- 방과 관련된 리뷰 및 계약 정보 작성 가능.
- 관리자가 콘텐츠를 등록/수정/삭제할 수 있는 기능.

### 🛠️ 기술 스택
- **백엔드**: Spring Boot, Spring Security, JPA
- **프론트엔드**: Thymeleaf, HTML5, CSS3
- **데이터베이스**: MySQL
- **외부 API**: Kakao API, OAuth 2.0

---

## 🚀 설치 및 실행 방법

### 1. 프로젝트 클론
```bash
git clone https://github.com/username/meomureum.git
cd meomureum
`
----
#



📂 API 설계
메서드	URL	설명	인증 필요
GET	/rooms	방 목록 조회	❌
GET	/rooms/{id}	특정 방 상세 조회	❌
POST	/rooms	방 등록	✅
POST	/login/oauth2/code	OAuth 2.0 로그인 처리	❌

📸 스크린샷
메인 페이지

🧑‍💻 기여 방법
이 프로젝트를 포크합니다.
새로운 브랜치를 생성합니다. (feature/새로운기능)
변경 사항을 커밋합니다. (git commit -m "Add 새로운 기능")
푸시 후 Pull Request를 생성합니다.

