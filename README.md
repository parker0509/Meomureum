![image](https://github.com/user-attachments/assets/7cfb395e-0f23-4c3d-bbce-71e11775adef)![image](https://github.com/user-attachments/assets/06687b20-6f70-4ff9-94f6-84c57c88efd1)# 🏠 Meomureum (머무름)

## 프로젝트 개요
“Meomureum (머무름)”은 재개발 지역이나 잠시 머무를 곳이 필요한 분들, 그리고 여행 중 장기 숙박을 원하지만 (1주 이상) 거주하기 어려운 상황에서 유용한 단기 연속 거주 플랫폼입니다.

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


- **지도 표시**: 방의 위치를 카카오 맵을 통해 사용자에게 시각적으로 제공합니다.

<img src = "https://github.com/user-attachments/assets/54b48bf8-1e1b-416a-a6cc-de93a2b383cc" width="400" height="200"/>
<img src="https://github.com/user-attachments/assets/5d0ae10d-f5b1-44d1-b59f-d375b5331ae1" width="400" height="200"/>

<p></p>



- **주소 검색 기능**: 사용자가 입력한 주소를 바탕으로 정확한 위치를 지도에서 확인할 수 있습니다.

<img src="https://github.com/user-attachments/assets/b69dc788-a9a8-43bf-911c-2994a10fe97e" width="400" height="200"/>.




### 🛠️ 기술 스택
- **백엔드**: Spring Boot, Spring Security, JPA
- **프론트엔드**: Thymeleaf, HTML5, CSS3 , Javascript, Bootstrap
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

![image](https://github.com/user-attachments/assets/a733b357-1969-4ee3-8dcf-dcd6fa414182)
![image](https://github.com/user-attachments/assets/309fa4c5-3047-48a3-9150-0b370e789557)
![image](https://github.com/user-attachments/assets/be7378c4-0528-48f0-b540-2314710e1fed)
![image](https://github.com/user-attachments/assets/e20b64e5-37b6-41cc-8496-a3170257223c)


---
## 📸 스크린샷
### 메인 페이지

<img src="https://github.com/user-attachments/assets/616e4e96-6290-4ff4-abcb-691bb7cdb124" width="200" height="100"/>
<img src="https://github.com/user-attachments/assets/e4cd9e4f-486a-4062-a4d8-1e48e6244df8" width="200" height="100"/>
<img src="https://github.com/user-attachments/assets/21d8722b-c504-4d2a-a399-40b21a328147" width="200" height="100"/>
<img src="https://github.com/user-attachments/assets/5d0ae10d-f5b1-44d1-b59f-d375b5331ae1" width="200" height="100"/>



---


## 🚀 시스템 아키텍처

![qw](https://github.com/user-attachments/assets/435a40be-4875-4f07-aadc-c268de83c947)


