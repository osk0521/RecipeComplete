# 자취생을 위한 레시피사이트 자취생을 부탁해

## 프로젝트 소개
나날이 비싸져가는 배달료!
나날이 가벼워지는 자취생의 지갑!

그들의 지갑을 지키기 위해 탄생하였다!

자취생을 부탁해 사이트는
자취생들이 가지고 있는 냉장고 재료를 기반으로
다양한 요리를 보여주는 사이트입니다.
자취생을 부탁해 API 명세서(https://nine-megaraptor-493.notion.site/9fa27de839484993af5b1f81e4a78bb4?v=477ade06ef864b4fa1aa027fadda2b41)

## 팀원 역할 분담
### 이진우
- 로그인/회원가입/회원정보수정
    - 네이버, 카카오 소셜 로그인
    - 휴대폰, 이메일 인증
    - 네이버 캡챠 인증
- 마이페이지
- 쉐프페이지
- 고객센터
    - 네이버 스마트에디터
-최근 본 레시피
### 오상경
- 메인페이지
- 레시피 / 검색어 / 쉐프(유저) 랭킹
- 레시피 분류/검색
- 냉장고
    - 카카오 지도
- 레시피 상세페이지/ 댓글 등록
- 레시피 등록
- 매거진
### 김하람
- 쇼핑몰 메인페이지
- 상품 상세페이지
- 장바구니
- 주문서 작성페이지
  - 결제
  - 우편번호 검색
## 개발 기간
- 전체 개발 기간 : 2023.10.09 ~ 2024.01.31
- UI 구현 : 2023.10.09 ~ 2023.12.29
- 기능 구현 : 2024.01.02 ~ 2024.01.31
## 개발 환경
- Front-end : HTML,CSS,JS,jQuery,AJAX
- Back-end : Apache Tomcat, JSP,JDBC,ORACLE
- IDE : Eclipse
## 프로젝트 구조
<pre>
├─src
  ├─action
  ├─common
  ├─controller
  ├─dao
  ├─dto
  ├─servlet
  ├─test
  ├─vo
</pre>
## 페이지별 기능
공통: 레시피 썸네일 클릭시 해당 레시피 상세페이지로 이동합니다.
시연 영상(https://www.canva.com/design/DAF82TK7_vA/ypTQf6kEBpeABW9iucfyOw/edit?utm_content=DAF82TK7_vA&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)
### 메인 페이지
  - 화살표 클릭 혹은 드레그시 slick을 이용하여 다른 상품들을 보여줍니다.
    ![메인페이지_화면이동
### 랭킹 페이지 
  - 레시피 / 검색어 / 쉐프(유저)를 [일간 / 주간 / 월간]을 기준으로 순위를 적용한 랭킹페이지를 보여줍니다.
### 레시피 분류 / 검색
- 분류 카테고리 클릭 시 해당 카테고리에 소속된 레시피 목록을 보여줍니다.
- 이름순 / 최신순 / 조회수 순으로 정렬
### 상세페이지
- 레시피 게시글의 썸네일, 레시피 제목, 레시피 소개, 재료, 조리순서, 작성자 소개, 댓글 순으로 보여줍니다. 
- 조리순서는 3가지 버전이 있고 사진이 큰 버전, 사진이 없는 버전, 사진과 글을 한번에 볼 수 있는 버전이 있습니다.
### 냉장고(로그인 필요)
- 재료 클릭시 ‘사용자가 보유 중인 재료’가 포함된 레시피 목록을 출력합니다.
- 제목 클릭시 해당 레시피 상세페이지로 이동합니다.
- 요리에 필요한 재료 중 '사용자가 이미 갖고 있는 재료'와 '부족한 재료'를 분리해서 출력, '부족한 재료' 클릭시 카카오 지도로 근처 대형마트 위치 표시합니다.
### 매거진
 - 테마에 맞는 특수 레시피들의 목록을 보여줍니다.
 글쓰기(로그인 필요)
 - 레시피 제목(필수), 소개와 레시피 카테고리(필수), 재료와 조리순서를 작성할 수 있습니다.
 - 페이지 하단에는 '저장하기'(미완성 상태,  마이페이지에서 본인만 볼 수 있는 상태로 추후 수정이 가능합니다.),  '저장 후 공개하기'(완성 상태,  타 유저에게 공개되며 추후에 수정이 가능합니다.),  '취소하기'(메인페이지로 이동합니다.) 버튼이 있습니다.
