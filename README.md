# depromeet-9-6-backend(article-reminder)
- 디프만 9기 6조 백엔드 https://www.notion.so/depromeet/6-7508bdb9345c45a4a6836e446f35a7a9 
- 아이디어 회의 https://www.notion.so/depromeet/3-27-12320b2b806849d8a00d99240e5472d9
- 기술스택 회의 https://www.notion.so/depromeet/Backend-7bd3b3b7934c47c8b8373d4c92bc2aa6

사용 기술
---
- Spring-boot
- Java
- Junit
- Swagger
- mysql
- jpa
- Gradle Project
- H2 Database

요구 사항
---
- 소셜 로그인 Oauth2.0 카카오 로그인
- 링크 저장 
- 해시 태그 필터링 및 카테고리 기능
- 알람
- 아티클 완료시 레벨 설정
- 추천 아티클 개인화 아티클로 최근 관심있게 태그한 카테고리 위주로 앱 상단에 조회

개발 순서
---
- DB ERD
- 시퀀스 다이어그램
- API 설계
- 로그인(Oauth 2.0, Kakao 로그인api)
- 링크 저장
- 푸시 알림
- 리워드
- 추천 아티클

H2 Database
---
http://localhost:8080/h2-console/

Swagger
---
http://ec2-3-35-234-167.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui.html

UML
---
https://www.notion.so/depromeet/Sequence-Diagram-e7f80df08f5149ddbccd9b9af13e8f7e

ERD
---

![링줍ERD](https://user-images.githubusercontent.com/23554779/118814836-8863cf80-b8eb-11eb-9546-b2bf8842c867.png)
- 개별 링크 알 서버에서 관리하지 않는 것으로 결정되어 LinkAlarm 테이블 삭제 & User 테이블 내 사용자의 푸시 알람람 활성화 여부 컬럼 추가 : 20210515
- 시즌 뱃지와 포인트 뱃지 테이블 하나로 합침 (시즌 뱃지인지 포인트 뱃지인지 구분하도록 status를 둠) : 20210501

## Wiki
https://github.com/depromeet/depromeet-9-6-backend/wiki
