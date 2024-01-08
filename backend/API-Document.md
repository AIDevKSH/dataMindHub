# API Document

# User
## 개요
사용자 정보를 생성, 조회, 수정, 삭제하는 기능을 제공합니다.
<br/><br/>
## 엔드포인트

### @ 사용자 생성 (회원가입) - POST /users
필수로 포함해야 하는 정보(form 태그 내 name)<br/>
  &emsp;email (로그인 시 ID 용도)<br/>
  &emsp;password<br/><br/>
선택<br/>
  &emsp;username<br/>
  &emsp;birthday<br/>
  &emsp;nickname<br/>
  &emsp;phone
<br/><br/>
### @ 로그인 - POST /login
필수<br/>
  &emsp;email<br/>
  &emsp;password<br/>
### @ 로그아웃 - POST /logout

### @ 사용자 조회

### @ 사용자 수정

### @ 사용자 삭제

<br/>

# Post
## 개요
게시글을 생성, 조회, 수정, 삭제하는 기능을 제공합니다.
<br/><br/>
## 엔드포인트

### @ 게시글 생성

### @ 게시글 조회 (목록 보기) - GET /

### @ 게시글 수정

### @ 게시글 삭제
