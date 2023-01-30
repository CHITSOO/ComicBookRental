# ComicBookRental
Java Toy Project
만화책 대여 관리 시스템 

## Goal

만화책을 관리하고 등록된 고객에거 대여/반납하는 기능을 개발한다.

## Requirements

- 만화책 조회/등록/수정/삭제/대여/반납 기능 구현
- 고객 조회/등록/수정/삭제 기능 구현
- 대여/반납 : 
  - 만화책이 이미 대여 중인지 확인 > 대여 가능할 경우 만화책ID, 고객 정보 입력 > 등록된 고객이 없을 경우 고객 등록 진행 후 대여
  - 고객이 대여 건이 있는 경우 반납 처리 후 대여가 될 수 있다록 한다
  - 반납의 경우 만화책ID만 입력 받아 반납 처리 후 대여
  - 별도의 대여 금액을 받지 않는다.

- 객체지향적으로 클래스를 설계하고 개발한다.

- UI는 콘솔을 통해 메뉴번호를 입력받아 애플리케이션을 제어한다.
- 각 메뉴 이동시마다 0.이전메뉴를 제공하여 이전 UI로 이동해야 한다.

```
<메뉴 선택>
1.만화책 조회/등록/수정/삭제
2.만화책 대여
3.만화책 반납
4.고객 조회/등록/수정/삭제
0.프로그램 종료
====================
메뉴입력 : 1
```

```
<메뉴 선택>
1.만화책 등록
2.만화책 수정
3.만화책 삭제
4.만화책 조회
0.이전 메뉴
====================
메뉴입력 : 1
```

```
<메뉴 선택>
1.고객 등록
2.고객 수정
3.고객 삭제
4.고객 조회
0.이전 메뉴
====================
메뉴입력 : 1
```


- 데이터 저장은 Database 대신 Collection 객체에 저장하여 관리한다.
- 만화책, 대여정보, 고객정보 등 도메인 모델은 DTO로 설계한다.
- 모델정보
  - 만화책 정보 : 만화책ID(중복 혀용 안함), 만화책명, 작가명
  - 고객 정보 : 고객ID(중복 허용 안함), 고객명
  - 대여 정보 : 대여ID(중복 허용 안함), 대여 만화책 정보

- 각 메뉴의 기능을 수행할 때마다 데이터가 없는 상황에 대해 예외처리를 해야 한다.
  - 구체적으로 "XXX가 존재하지 않습니다." "XXX 만화책 정보가 없습니다." "XXX 대여 정보가 없습니다." "XXX 대여된 만화책이 반납되지 않았습니다" 형식의 오류 메시지를 보여준다.
  
  - 패키지 구조는 아래와 같다
    - app :  Main Apllication(Menu 처리 및 View 처리용)
    - comm : 공통코드와 Utility
    - dto : 데이터 저장 클래스
    - factory :  객체 생성 팩토리 클래스
    - controller : 클라이언트 요청/응답 처리 컨트롤 및 비즈니스 처리
    - repository : 데이터 처리 관련 클래스 (조회/저장/수정/삭제)


## Configuration Settings
1. intellij 실행 > New Project > 항목 입력 > Create 버튼 클릭
  - Name : ComicBookRental
  - Language : Java
  - Build System : Gradle
  - Advanced Setting
    - GroupId : org.comicbookret
    - ArtifactId : ComicBookRental 
![image](https://user-images.githubusercontent.com/33537820/215393294-c151ad97-ad15-4f14-b455-eeccaace376f.png)


2. Lombok 라이브러리 설치를 위해 dependency 설정
 - Build.gradle 파일을 더블 클릭 > dependencies(의존성) 영역 아래에 추가
 ```
    compileOnly 'org.projectlombok:lombok:1.18.22'
    annotationProcessor 'org.projectlombok:lombok:1.18.22'
    implementation 'org.slf4j:slf4j-api:1.7.25'
 ```
 - 우측 상단에 코끼리 모양(Load gradle change)을 클릭하면 변경 내용이 반영되면서 라이브러리 다운
 
3. Lombok 플러그인 설치 및 Enable annotation proccesing 설정
  - File > Settings > Plugins > lombok 검색 > 선택 후 install
  - File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors > Enable annotation processing 체크박스 선택 > OK 버튼 클릭
  
4. [선택] encoding x-windows-949 에러 발생시
  - File > Settings > Editor > Code Style > File Encodings >
    - Global Encoing : UTF-8
    - Project Encoding : UTF-8
    - Default encoding for properties files: UTF-8
  - Help > Edit Custom VM Options...에 -Dfile.encoding=UTF-8 추가
  - File > Settings > Build, Execution, Deployment > Build Tools > Gradle > 
    - Build and run using : IntelliJ IDEA
![image](https://user-images.githubusercontent.com/33537820/215395117-a3fba531-842b-4ef4-a116-6343fe9179b7.png)
![image](https://user-images.githubusercontent.com/33537820/215395364-c9324e53-6beb-4036-96da-b8852c887381.png)
