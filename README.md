## 소개

2021 Summer Google Developers Group(GDG) 해커톤에 참여해서 작성된 프로젝트입니다.

### 모두의 GYM

구매한 헬스장 이용권을 다른 사람에게 양도할 수 있고,
다른 지역에서 저렴하게 운동하고 싶을 때, 다른 사람의 이용권을 양도 받아서 사용할 수 있습니다.
헬스장, PT, GX, 요가, 필라테스 등 여러 이용권들을 공유하는 플랫폼입니다.

-----

기존의 [저장소](https://github.com/GDG2021hackathon/modoogym-backend-spring) 에서 발생한 문제를 개선했습니다.

- 요청에 대한 응답에는 철저하게 DTO를 사용해서 연쇄 참조 문제를 해결했습니다.
- Controller - Service - Repository 를 명확하게 분리해서 요구사항 변화에 유연하게 대처할 수 있습니다.

<br>

## 프로젝트 정보

### Project Management Tool

- Gradle project 6.9

### Framework

- Spring Boot 2.4.8

### Packaging

- Jar

### Dependencies

- Spring Web: Apache Tomcat, Spring MVC etc..
- Spring Data JPA: ORM
- Lombok
- JJWT: Java JWT util
- P6SPY: SQL Query Parameter tracker

### Swagger API Document

[gdg-hackathon-modoogym-api/1.0.0](https://app.swaggerhub.com/apis/JinseongHwang/gdg-hackathon-modoogym-api/1.0.0)