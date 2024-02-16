# 자취생을 위한 레시피사이트 자취생을 부탁해

## 프로젝트 소개
나날이 비싸져가는 배달료!
나날이 가벼워지는 자취생의 지갑!

그들의 지갑을 지키기 위해 탄생하였다!

자취생을 부탁해 사이트는
자취생들이 가지고 있는 냉장고 재료를 기반으로
다양한 요리를 보여주는 사이트입니다.


## 팀원 역할 분담
### 이진우
- 로그인/회원가입/회원정보수정
    - 네이버, 카카오 소셜 로그인
    - 휴대폰, 이메일 인증
    - 네이버 캡챠 인증
- 마이페이지(레시피, 댓글, 쉐프, 팔로잉레시피, 레시피노트)
- 쉐프페이지
- 고객센터
    - 네이버 스마트에디터
- 최근 본 레시피
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
│  ├─action
│  ├─common
│  ├─controller
│  ├─dao
│  ├─dto
│  ├─servlet
│  ├─test
│  ├─vo
└─WebContent
    ├─html
    ├─Images
    │  └─event
    ├─js
    ├─Member_CSS
    ├─META-INF
    ├─Recipe_CSS
    │  └─Public
    ├─remaining_files
    ├─se2823
    │  ├─css
    │  ├─img
    │  │  └─ko_KR
    │  ├─js
    │  │  └─lib
    │  └─sample
    │      ├─js
    │      │  └─plugin
    │      ├─photo_uploader
    │      │  └─img
    │      └─viewer
    │          └─htmlpurifier
    │              └─standalone
    │                  └─HTMLPurifier
    │                      ├─ConfigSchema
    │                      │  ├─Builder
    │                      │  ├─Interchange
    │                      │  └─schema
    │                      ├─DefinitionCache
    │                      │  └─Serializer
    │                      │      └─HTML
    │                      ├─EntityLookup
    │                      ├─Filter
    │                      ├─Language
    │                      │  ├─classes
    │                      │  └─messages
    │                      ├─Lexer
    │                      └─Printer
    ├─Store_CSS
    └─WEB-INF
        └─lib
</pre>
## 페이지별 기능
### 이진우
#### 소셜 로그인
- 카카오 & 네이버 소셜 로그인을 할 수 있습니다.

![네이버 소셜 로그인](https://github.com/ljwoo1005/Recipe/assets/130548652/561b5e17-5dd0-41b0-bcb3-0944ae1227c4)

![카카오 소셜 로그인](https://github.com/ljwoo1005/Recipe/assets/130548652/23fa8104-0245-4a77-a32f-394172303b2c)
#### 회원가입
- 휴대폰 & 이메일 & 네이버 캡챠 인증을 진행합니다.
  
![휴대폰 인증](https://github.com/ljwoo1005/Recipe/assets/130548652/18180561-a3b5-4ac4-b4cf-92842bc8965c)

![이메일 인증](https://github.com/ljwoo1005/Recipe/assets/130548652/7665e9bb-50ec-4efd-9c5d-4c0383bba70e)

![네이버 캡챠](https://github.com/ljwoo1005/Recipe/assets/130548652/b34a90e0-ab35-434a-98c9-d1cb7bca4096)
#### 회원정보 수정
- 비밀번호와 닉네임을 변경합니다.
  
![비밀번호 변경](https://github.com/ljwoo1005/Recipe/assets/130548652/56b9e940-ac56-4457-aa3d-088fd7f51e41)

![닉네임 변경](https://github.com/ljwoo1005/Recipe/assets/130548652/e3a2488b-1ef1-4e54-b07b-88c391840562)
#### 마이페이지
- 마이페이지 레시피 / 댓글 / 쉐프 페이지 입니다.
- 유저가 작성한 레시피와 댓글, 유저가 팔로잉중인 쉐프의 활동내역을 보여줍니다.
![마이페이지 레시피 댓글 쉐프](https://github.com/ljwoo1005/Recipe/assets/130548652/420ffbce-7455-4ace-8e0c-5c234c646333)
- 마이페이지 팔로잉레시피 페이지 입니다.
- 유저가 좋아요를 누른 레시피를 모아서 보여줍니다.
- 이 화면에서 레시피 팔로잉을 삭제할 수 있습니다.
![마이페이지 팔로잉레시피](https://github.com/ljwoo1005/Recipe/assets/130548652/ec82a09d-54d5-4707-a4fb-f611cd7082fb)
- 마이페이지 레시피노트 페이지 입니다.
- 유저가 레시피에 작성한 레시피노트를 모아서 보여줍니다.
- 이 화면에서 레시피노트를 수정 & 삭제할 수 있습니다.
![마이페이지 레시피노트](https://github.com/ljwoo1005/Recipe/assets/130548652/061d2927-3f6a-45ac-9533-b66295ec60ff)
- 마이페이지 팔로워 & 팔로잉창을 무한스크롤로 만들었습니다.
- ajax를 이용하여 무한스크롤 기능을 구현하였습니다.
![마이페이지 무한스크롤](https://github.com/ljwoo1005/Recipe/assets/130548652/5fd45217-2ce9-42b5-ae38-f83afb4b67d8)
- 마이페이지 프로필 변경 입니다.
- 자기소개와 프로필 이미지를 변경할 수 있습니다.
![마이페이지 프로필 변경](https://github.com/ljwoo1005/Recipe/assets/130548652/4722dbf5-9520-4341-a45b-3adfa460e375)
#### 고객센터
- 공지사항 / 도움말 / 문의사항 페이지 입니다.
- 사이트의 공지사항과 도움말, 로그인 중인 유저가 작성한 문의사항을 보여줍니다.
![고객센터](https://github.com/ljwoo1005/Recipe/assets/130548652/4b69ac00-5f2e-4a41-8cd7-0c88f45716de)
- 문의사항 게시글 작성 입니다.
- 네이버 스마트에디터를 사용하여 게시글 작성을 유연하게 할 수 있습니다.
![문의사항 글쓰기](https://github.com/ljwoo1005/Recipe/assets/130548652/5352b3c5-7e94-431e-a539-8cd5c3cc3a4f)
- 문의사항 게시글 수정 & 삭제 입니다.
- 아직 답변받지 않은 문의사항에 대하여 게시글의 수정과 삭제가 가능합니다.
![문의사항 수정](https://github.com/ljwoo1005/Recipe/assets/130548652/8b2aa1aa-e1fe-46b5-9398-8a8e170c648d)
![문의사항 삭제](https://github.com/ljwoo1005/Recipe/assets/130548652/1acd6ec7-08fc-43b4-bdee-388248fa7cd0)
#### 최근 본 레시피
- 최근 본 레시피를 모아서 보여줍니다.
- 레시피 상세페이지를 볼 때마다 쿠키에 해당 레시피의 정보를 저장하여 모든 페이지에서 해당 레시피를 모아서 보여줍니다.
- slick을 이용하여 슬라이드 기능을 추가하였습니다.
![최근 본 레시피](https://github.com/ljwoo1005/Recipe/assets/130548652/d8cdad4f-5342-4893-a71c-1b2170d0a8ae)

### 오상경
#### 레시피 메인
- 화살표 클릭 혹은 드레그시 slick을 이용하여 다른 상품들을 보여줍니다.
#### 랭킹 페이지 
- 레시피 / 검색어 / 쉐프(유저)를 [일간 / 주간 / 월간]을 기준으로 순위를 적용한 랭킹페이지를 보여줍니다.
#### 레시피 분류 / 검색
- 분류 카테고리 클릭 시 해당 카테고리에 소속된 레시피 목록을 보여줍니다.
- 이름순 / 최신순 / 조회수 순으로 정렬
#### 상세페이지
- 레시피 게시글의 썸네일, 레시피 제목, 레시피 소개, 재료, 조리순서, 작성자 소개, 댓글 순으로 보여줍니다. 
- 조리순서는 3가지 버전이 있고 사진이 큰 버전, 사진이 없는 버전, 사진과 글을 한번에 볼 수 있는 버전이 있습니다.
#### 냉장고(로그인 필요)
- 재료 클릭시 ‘사용자가 보유 중인 재료’가 포함된 레시피 목록을 출력합니다.
- 제목 클릭시 해당 레시피 상세페이지로 이동합니다.
- 요리에 필요한 재료 중 '사용자가 이미 갖고 있는 재료'와 '부족한 재료'를 분리해서 출력, '부족한 재료' 클릭시 카카오 지도로 근처 대형마트 위치 표시합니다.
#### 매거진
- 테마에 맞는 특수 레시피들의 목록을 보여줍니다.
 글쓰기(로그인 필요)
- 레시피 제목(필수), 소개와 레시피 카테고리(필수), 재료와 조리순서를 작성할 수 있습니다.
- 페이지 하단에는 '저장하기'(미완성 상태,  마이페이지에서 본인만 볼 수 있는 상태로 추후 수정이 가능합니다.),  '저장 후 공개하기'(완성 상태,  타 유저에게 공개되며 추후에 수정이 가능합니다.),  '취소하기'(메인페이지로 이동합니다.) 버튼이 있습니다.

시연 영상(https://www.canva.com/design/DAF82TK7_vA/ypTQf6kEBpeABW9iucfyOw/edit?utm_content=DAF82TK7_vA&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

### 김하람
#### 스토어 메인
- 화살표 클릭시 slick을 이용하여 다른 상품들을 보여줍니다.
    ![메인페이지_slick gif](https://github.com/ram9611/recipe-store-project/assets/66862342/3ae5d01f-8793-48e0-92fa-9a1e214da2cc)
- 상품 클릭시 해당 상품 상세페이지로 이동합니다.
    ![메인페이지_화면이동 gif](https://github.com/ram9611/recipe-store-project/assets/66862342/16517e08-8536-4efd-89ca-9e1b24a982de)
#### 상품 상세
  - 상품 옵션 선택 후 장바구니 버튼 클릭시 상품 옵션들과 함께 장바구니 페이지로 이동합니다. 선택한 옵션이 없다면 알림이 발생합니다.
    ![상품상세페이지_화면이동 gif](https://github.com/ram9611/recipe-store-project/assets/66862342/f19d9aed-ff15-49b0-919e-08bbd8a61f60)
#### 장바구니
- 상품 옵션 선택하고 상품 삭제 클릭시 해당 옵션이 삭제됩니다. 선택한 옵션이 없다면 알림이 발생합니다.
    ![장바구니_상품삭제 gif](https://github.com/ram9611/recipe-store-project/assets/66862342/627a5cff-47b5-44ad-a15e-645f79ab2513)
- 상품 옵션 선택하고 선택 구매 클릭시 해당 옵션들과 함께 주문서 페이지로 이동합니다. 선택한 옵션이 없다면 알림이 발생합니다.
   ![장바구니_선택구매](https://github.com/ram9611/recipe-store-project/assets/66862342/0fd4808d-b7c7-4e56-bea8-e26efc7c85c1)
- 전체 구매 클릭시 장바구니에 전체 옵션들과 함께 주문서 페이지로 이동합니다.
    ![장바구니_전체구매 gif](https://github.com/ram9611/recipe-store-project/assets/66862342/5bf7d880-ae2b-413d-9285-6956490f7008)
#### 주문서 작성
 - 기본 배송지 선택시 유저가 등록한 기본 배송지로 입력값이 채워집니다. 직접입력 선택시 입력값들은 빈칸으로 보여줍니다.
  ![주문서_기본배송지 직접입력 gif](https://github.com/ram9611/recipe-store-project/assets/66862342/38713ef5-c4dd-4c84-acca-cd90c6022a2d)
- 배송지 선택 클릭시 해당 배송지 정보로 입력값이 채워집니다.
  ![주문서_배송지선택 gif](https://github.com/ram9611/recipe-store-project/assets/66862342/ebd57170-04ae-4ca0-aa93-68aacc9c6b16)
- 배송지 수정 클릭시 해당 배송지 정보 수정 가능합니다.
  ![주문서_배송지수정 gif](https://github.com/ram9611/recipe-store-project/assets/66862342/7d50f7b8-c918-4b33-945b-a98e8b20447d)
- 새 배송지 입력 클릭시 유저의 새로운 배송지 등록이 가능합니다.
  ![주문서_새배송지입력 gif](https://github.com/ram9611/recipe-store-project/assets/66862342/47869b63-e84f-4c06-9465-34d10f3e4d79)
- 우편번호 클릭시 우편번호 창이 생성되며, 주소 선택시 해당 주소 정보로 입력값이 채워집니다.
  ![주문서_우편번호검색](https://github.com/ram9611/recipe-store-project/assets/66862342/949eb847-7c9d-421c-b5fc-3d26b3dc089a)
- 결제정보가 임의로 훼손된다면 결제는 진행되지 않으며 알림 발생 후 메인 페이지로 이동합니다.
  ![주문서_결제실패 gif](https://github.com/ram9611/recipe-store-project/assets/66862342/9b2e8865-8151-43e9-acbe-1fc5b104e774)
- 결제하기 클릭시 카카오 결제창으로 결제 진행합니다.
 ![주문서_결제 gif](https://github.com/ram9611/recipe-store-project/assets/66862342/52763044-adf5-4b4c-bb7d-14a0765c7dc3)
