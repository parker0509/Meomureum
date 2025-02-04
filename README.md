# 🏠 Meomureum (머무름)

## 프로젝트 개요
“Meomureum (머무름)”은 재개발 지역이나 잠시 머무를 곳이 필요한 분들, 그리고 여행 중 장기 숙박을 원하지만 1주 이상 거주하기 어려운 상황에서 유용한 단기 연속 거주 플랫폼입니다.

### 주요 기능
연속적인 단기 거주 제공: 1주에서 한 달까지 지속적으로 머물 수 있는 방을 제공 
간편한 월세 결제 시스템: 직관적인 결제 시스템으로 간편한 월세 결제 지원
### 이용 대상
재개발 지역 또는 장기적으로 머물 공간이 필요한 분들
여행 중 잠시 동안 연속적으로 머물 수 있는 숙소가 필요한 분들

## 개발 기간 24.12.14 ~ ~ing
(Dec 16, 2024 ~ ing)


## 🛠️ 프로젝트 구조
<pre>
demo 
├── config 
│ ├── AppConfig.java 
│ ├── SecurityConfig.java
│ └── SwaggerConfig.java 
├── controller 
│ ├── ConsultController.java 
│ ├── ContentController.java 
│ ├── HomeController.java 
│ ├── KakaoApiController.java 
│ ├── LoginController.java 
│ ├── OAuth2Controller.java 
│ ├── RoomApiController.java 
│ └── UserController.java 
├── dto 
│ ├── OAuth2Attribute.java 
│ ├── RoomRequest.java 
│ └── SessionUser.java 
├── entity 
│ ├── Address.java 
│ ├── Contents.java 
│ ├── Role.java 
│ ├── Room.java 
│ └── User.java 
├── repository 
│ ├── AddressRepository.java 
│ └── ContentsRepository.java 
├── service 
│ ├── CustomOAuth2UserService.java 
│ ├── CustomUserDetailsService.java 
│ ├── UserService.java 
│ ├── ContentService.java 
│ ├── KakaoApiService.java 
│ ├── LoginService.java 
│ └── RoomService.java 

</pre>

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

### 🗺️ 카카오 맵 API 통합

`Meomureum`은 사용자에게 위치 기반 서비스를 제공합니다. 이를 위해 카카오 맵 API를 통합하여, 사용자가 방을 검색하고, 위치를 확인할 수 있도록 지원합니다. 카카오의 주소 검색 기능을 통해 정확한 위치 정보와 더불어, 방의 위치를 지도에서 직접 확인할 수 있습니다.

- **주소 검색 기능**: 사용자가 입력한 주소를 바탕으로 정확한 위치를 지도에서 확인할 수 있습니다.
- **지도 표시**: 방의 위치를 카카오 맵을 통해 사용자에게 시각적으로 제공합니다.

<img src = "https://github.com/user-attachments/assets/3c330dbb-e656-4167-af1f-2d21ea56095c" width="300" height="200"/>

<img src = "https://github.com/user-attachments/assets/6fc71361-7f84-45a3-8001-eb3efe12d4cb" width="300" height ="200"/>


### 🛠️ 기술 스택
- **백엔드**: Spring Boot, Spring Security, JPA
- **프론트엔드**: Thymeleaf, HTML5, CSS3 , Javascript
- **데이터베이스**: MySQL
- **외부 API**: Kakao API, OAuth 2.0

---

## 🚀 설치 및 실행 방법

### 1. 프로젝트 클론
```bash
git clone https://github.com/parker0509/meomureum.git
cd meomureum
```
----



## 📂 API 설계


| 메서드   | URL                           | 설명                          | 인증 필요 |
|----------|-------------------------------|-------------------------------|-----------|
| `GET`    | `/api/consult`                 | 상담 페이지 이동               | ❌        |
| `GET`    | `/search-address`             | 주소 검색                      | ❌        |
| `GET`    | `/api/room/new`                | 방 생성 폼                    | ✅        |
| `POST`   | `/api/room/new`                | 새로운 방 생성                | ✅        |
| `GET`    | `/api/room`                    | 모든 방 목록 조회             | ❌        |
| `GET`    | `/api/room/{id}`               | 방 세부 정보 조회             | ❌        |
| `GET`    | `/api/room/video`              | 비디오 방 보기                 | ❌        |
| `GET`    | `/api/room/share`              | 공유 방 보기                   | ❌        |
| `GET`    | `/api/room/search`             | 방 검색                        | ❌        |
| `GET`    | `/api/room/pet`                | 애완동물 허용 방 보기          | ❌        |
| `GET`    | `/api/room/oneroom`            | 원룸 보기                      | ❌        |
| `GET`    | `/api/room/officetel`          | 오피스텔 방 보기               | ❌        |
| `GET`    | `/api/room/map`                | 방 지도 보기                   | ❌        |
| `GET`    | `/api/room/guest`              | 게스트룸 보기                  | ❌        |
| `GET`    | `/api/room/filter`             | 방 유형별 필터링               | ❌        |
| `GET`    | `/api/room/discount`           | 할인 방 보기                   | ❌        |
| `GET`    | `/api/room/coliving`           | 코리빙 방 보기                 | ❌        |
| `GET`    | `/api/room/apt`                | 아파트 방 보기                 | ❌        |
| `GET`    | `/`                            | 방 목록 조회                   | ❌        |
| `GET`    | `/api/join`                    | 회원가입 페이지                | ❌        |
| `POST`   | `/api/join`                    | 회원가입                       | ❌        |
| `GET`    | `/testOAuth2`                  | OAuth2 로그인 페이지          | ❌        |
| `GET`    | `/oauth2/login`                | OAuth2 로그인                  | ❌        |
| `GET`    | `/HomePage`                    | HomePage로 이동                | ❌        |
| `GET`    | `/api/contents/new`            | 콘텐츠 작성 페이지            | ✅        |
| `POST`   | `/api/contents/new`            | 콘텐츠 저장                    | ✅        |
| `GET`    | `/api/contents`                | 모든 콘텐츠 목록 조회         | ❌        |
| `GET`    | `/api/contents/content-form`   | 콘텐츠 작성 폼 페이지         | ❌        |
| `GET`    | `/api/login`                   | 로그인 페이지 조회            | ❌        |
| `POST`   | `/login/oauth2/code`           | OAuth 2.0 로그인 처리          | ❌        |


---
## 📸 스크린샷
### 메인 페이지

<img src="https://github.com/user-attachments/assets/616e4e96-6290-4ff4-abcb-691bb7cdb124" width="200" height="100"/>
<img src="https://github.com/user-attachments/assets/e4cd9e4f-486a-4062-a4d8-1e48e6244df8" width="200" height="100"/>
<img src="https://github.com/user-attachments/assets/0d26cbb7-4abd-4ff4-ae27-ccfa1f2a5c5a" width="200" height="100"/>



---


## 🚀 시스템 아키텍처

![qw](https://github.com/user-attachments/assets/435a40be-4875-4f07-aadc-c268de83c947)


