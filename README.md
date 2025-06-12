# 🍯 꿀재료
필요한 만큼 식재료 판매 앱
소량 포장된 식재료를 조합해 필요한 만큼만 구매하고, 불필요한 쓰레기를 줄이기 위해 고안한 앱입니다.
맛있는 요리와 환경을 위한 작은 실천까지 고려하였습니다.


## 👀 UI
![image](https://github.com/user-attachments/assets/8f4f009e-5950-44d1-a356-778fac0d4ab0)
![image](https://github.com/user-attachments/assets/72bf0cc7-341b-470e-a4ff-b689387cddeb)
![image](https://github.com/user-attachments/assets/991733c8-6e95-4024-a69d-bd5c307d912d)
![image](https://github.com/user-attachments/assets/0279b1d4-a1a5-49bd-8572-cc21147139e4)


## ⚒️ 기술 스택
- Kotlin
- Jetpack Compose
- Coil
- Room (로컬 캐시)
- DataStore (Preference)
- Firebase FireStore API (데이터 저장 서버)
- Naver Map API (위치 정보 표시)
- Kakao Map API (주소 검색)


## 🏗️ 아키텍처 설계
본 프로젝트는 Clean Architecture 원칙에 따라 구성되어 있으며, 계층 간 책임 분리와 테스트 용이성을 최우선으로 고려하였습니다.

### 1. 설계 철학
- **단방향 흐름**  
  `View → ViewModel → UseCase → Repository → DataSource`  
  데이터는 항상 한 방향으로 흐르며, 각 계층은 하위 계층의 내부 구현을 알지 못합니다.

- **관심사 분리 (SoC)**  
  각 계층은 다음과 같은 역할만을 수행합니다:

  | Layer   | 역할                      |
  |---------|---------------------------|
  | UI      | 화면 표현 및 사용자 상호작용 |
  | Domain  | 비즈니스 로직, 정책 결정     |
  | Data    | 실제 데이터 제공 (API, DB 등) |

- **의존성 역전 (DIP)**  
  Domain 계층은 Data 계층의 구체 구현이 아닌 추상 인터페이스에만 의존합니다.  
  의존성은 수동으로 생성자에 명시적으로 주입합니다 (Manual DI).


### 2. 계층별 구조 및 구성요소
#### UI Layer
- **책임**: 사용자 이벤트 수신 및 ViewModel 상태 구독
- **구성**
    - `Screen`: Jetpack Compose 기반 UI
    - `ViewModel`: 상태(State) 관리 및 이벤트 처리
    - `UiState`: 화면 상태 표현 (sealed class)

#### Domain Layer
- **책임**: 앱의 핵심 로직을 담당하며 외부에 독립적
- **구성**
    - `UseCase`: 단일 기능 단위의 비즈니스 로직
    - `Repository Interface`: 추상화된 데이터 접근 인터페이스
    - `Entity`: 순수 데이터 모델

#### Data Layer
- **책임**: 외부 데이터 소스로부터 실제 데이터 수집 및 가공
- **구성**
    - `RepositoryImpl`: Domain의 Repository를 구현
    - `RemoteDataSource`: 외부 API 호출
    - `LocalDataSource`: Room 기반 로컬 저장소
    - `DTO`: 외부 응답 Entity

### 3. 의존성 흐름
UI → Domain → Data  
UI는 Domain만 알며, Domain은 Data 계층에 추상적으로만 의존합니다.  
수동 DI 방식을 사용하여 앱 초기 구동 시 모든 의존성을 명시적으로 연결합니다.

## 🎸 기타
- [HONEY_VER1](https://github.com/k-ye0415/Honey-ver1) 를 Jetpack Compose 와 Android App Architecture 를 적용하여 리펙토링 진행하였습니다.
- [요기요](https://www.yogiyo.co.kr/mobile/#/) 앱을 참고하여 만들었습니다.
