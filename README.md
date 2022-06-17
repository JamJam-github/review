## homework

- Project: Gradle Project
- Spring Boot: 2.7.0
- Language: Java
- Java: 11
- Dependencies: Spring Web, MyBatis Framework, Lombok, MySQL Driver


### Tests
MYSQL 스키마를 생성해주세요.
- create database if not exists triple;

Application.properties 내 Username, Password를 확인해주세요.
- Username: root, Password: a123

애플리케이션을 실행해주세요.
- run ReviewApplication


### Architecture
Controller: APIController

APIs
- GET: /events/userId="3ede0ef2-92b7-4817-a5f3-0c575361f745"

- POST: /events/ body={
"type": "REVIEW",
"action": "ADD", /* "MOD", "DELETE" */
"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
"content": "좋아요!",
"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-
851d-4a50-bb07-9cc15cbdc332"],
"userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
"placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
