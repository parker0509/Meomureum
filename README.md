# 🏠 Meomureum (머무름)
http://www.meomureum.com/
## 프로젝트 개요
“Meomureum (머무름)”은 재개발 지역이나 잠시 머무를 곳이 필요한 분들, 그리고 여행 중 장기 숙박을 원하지만 1달 이상 거주하기 어려운 상황과 1주 2주만 살 수 있게 유용한 단기 연속 거주 플랫폼입니다.

### 주요 기능
연속적인 단기 거주 제공: 1주에서 한 달까지 지속적으로 머물 수 있는 방을 제공 
간편한 월세 결제 시스템: 직관적인 결제 시스템으로 간편한 월세 결제 지원
### 이용 대상
재개발 지역 또는 장기적으로 머물 공간이 필요한 분들
여행 중 잠시 동안 연속적으로 머물 수 있는 숙소가 필요한 분들

## 개발 기간 24.12.14 ~ ~ing
(Dec 16, 2024 ~ ing)


## 🛠️ 프로젝트 구조

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


### 커뮤니티 게시판 : 사용자들끼리 소통할 수 있고 정보를 얻고 방을 구할 수 있는 게시판

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

#### Swagger
- Swagger를 이용하여 문서화 작업을 진행했습니다.

<img src="https://github.com/user-attachments/assets/a733b357-1969-4ee3-8dcf-dcd6fa414182" width="200" height="100"/>




---
## 📸 스크린샷
### 메인 페이지

- **홈**
<img src="https://github.com/user-attachments/assets/616e4e96-6290-4ff4-abcb-691bb7cdb124" width="200" height="100"/>
<img src="https://github.com/user-attachments/assets/21d8722b-c504-4d2a-a399-40b21a328147" width="200" height="100"/>

- **로그인**
<img src="https://github.com/user-attachments/assets/21d8722b-c504-4d2a-a399-40b21a328147" width="200" height="100"/>


<< 로그인 실패 >>


<img src="https://github.com/user-attachments/assets/74752eb8-7175-4971-a879-6e09b0b8dede" width="200" height="100"/>


- **맵**
<img src="https://github.com/user-attachments/assets/5d0ae10d-f5b1-44d1-b59f-d375b5331ae1" width="200" height="100"/>


- **마커**
<img src="https://github.com/user-attachments/assets/cfbb87e8-9428-4fdc-b636-4eab372fe1c2" width="200" height="100"/>

---
## 🌟 담당 업무

### 1인 개발: 기획부터 백엔드, 프론트엔드, API 설계, DB 설계, 배포까지 전 과정 담당

### OAuth2.0 인증: 카카오, 구글, 네이버 소셜 로그인 구현

### 카카오 맵 API: 지도와 주소 검색 기능 연동

### API 설계: Swagger를 활용한 API 문서화

## 💡 성과

### 사용자 중심의 직관적인 UI/UX 제공

### 단기 연속 거주라는 새로운 숙박 시장을 위한 실용적 플랫폼 구축

### 소셜 로그인과 간편 결제 기능으로 사용자 편의성 극대화

## 🔗 프로젝트 링크

GitHub: https://github.com/parker0509/meomureum

Notion: 프로젝트 상세 문서화 (요청 시 제공 가능)
---

## 📅 기능 추가 Notion 사용
- 계속 필요한 기능 추가/수정 중
<img src="https://github.com/user-attachments/assets/d16c9315-385a-4dc9-80cb-2cadaa758208" width="400" height="200"/>

---

📧 연락처: coldwatergk@gmail.com📂 GitHub: https://github.com/parker0509

---
## 🚀 시스템 아키텍처

![qw](https://github.com/user-attachments/assets/435a40be-4875-4f07-aadc-c268de83c947)


