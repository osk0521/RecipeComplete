package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import dao.MyPageCommentViewDao;
import dao.ProfileDao;
import dao.RecipeBoardDao;
import dao.StoreMainpageDao;
import dao.UserFollowDao;
import dto.MyPageCommentViewDto;
import dto.ProfileDto;
import dto.RecentRecipeDto;
import dto.UserFollowerDto;
import dto.UserFollowingDto;
import vo.AdvertisementGoodsVo;

public class MyPageCommentViewAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션 객체 생성
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId"); // 세션에 로그인 아이디가 있다면 가져오기
		// 하단 우측 베스트상품 5종 가져오기
		StoreMainpageDao storeDao = new StoreMainpageDao();
		ArrayList<AdvertisementGoodsVo> advertisementGoodsList = storeDao.getAdvertisementGoodsList();
		request.setAttribute("advertisementGoodsList", advertisementGoodsList);
		// 최근 본 레시피 가져오기
		Cookie[] cookies = request.getCookies();
		// 최근 본 레시피를 가진 쿠키만 저장하기 위한 리스트
		ArrayList<Cookie> recentRecipeCookies = new ArrayList<Cookie>();
		for(Cookie c : cookies) {
			if(c.getName().contains("recipeId")) {
				System.out.println("최근본레시피 아이디 : " + c.getValue());
				recentRecipeCookies.add(c);
			}
		}
		RecipeBoardDao rbDao = new RecipeBoardDao();
		ArrayList<RecentRecipeDto> recentRecipeList = new ArrayList<RecentRecipeDto>();
		if(recentRecipeCookies.size() != 0) {
			for(int i=recentRecipeCookies.size()-1; i>=0; i--) {
				System.out.println("최근본레시피 배열에 1개 삽입");
				int recentRecipeId = Integer.parseInt( recentRecipeCookies.get(i).getValue() );
				recentRecipeList.add(rbDao.getRecentRecipe(recentRecipeId));
			}
		}
		System.out.println("최근본레시피 개수 : " + recentRecipeCookies.size());
		request.setAttribute("recentRecipeList", recentRecipeList);
		// mode 쿼리스트링이 존재하면 로그인 계정 마이페이지 댓글에서 내가쓴 댓글, 받은 댓글 페이지로 이동 가능
		String mode = (String)request.getParameter("mode");
		// uId 쿼리스트링이 존재하면 마이페이지가 타인계정 페이지
		String paramUid = null;
		if(request.getParameter("uId") != null) {
			paramUid = request.getParameter("uId");
		}
		// 현재 페이지 번호를 받아오기. 기본값은 1
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(request.getParameter("page"));
		} catch(NumberFormatException e) { } // 잘못된 페이지값을 받을 경우 1로 고정
		// pagination을 위한 시작페이지, 끝페이지 설정
		int page = (pageNum/3 - (pageNum%3==0 ? 1 : 0)) * 3; // 시작, 끝페이지를 정하기 위한 수식
		int startPageNum = page + 1;
		int endPageNum = page + 3;
		int lastPageNum = 0;
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		
		if(paramUid == null || paramUid.equals("") || paramUid.equals(loginId)) { // 페이지 이동을 로그인 계정 마이페이지로
			if(loginId != null) { // 로그인 중인 계정이 있다면 로그인 아이디 기반의 정보를 가져오기 위한 DAO, DTO객체 생성
				UserFollowDao userFollowDao = new UserFollowDao(); // 유저 팔로우 메서드 관련 DAO
				ProfileDao profileDao = new ProfileDao(); // 유저 프로필 메서드 관련 DAO
				ArrayList<UserFollowerDto> myFollowerList = userFollowDao.getMemberFollowerById(loginId, 1); // 로그인한 유저의 팔로워 리스트 저장
				ArrayList<UserFollowingDto> myFollowingList = userFollowDao.getMemberFollowingById(loginId, 1); // 로그인한 유저의 팔로잉 리스트 저장
				ProfileDto myProfile = profileDao.getProfileById(loginId); // 로그인한 유저의 프로필 정보 저장
				request.setAttribute("myFollowerList", myFollowerList);
				request.setAttribute("myFollowingList", myFollowingList);
				request.setAttribute("myProfile", myProfile);
				
				// 로그인 계정의 댓글 정보를 가져오기 위한 DAO객체 생성
				MyPageCommentViewDao commentViewDao = new MyPageCommentViewDao();
				
				// mode 쿼리스트링이 send이면 로그인 계정이 작성한 것, receive면 로그인 계정이 받은 것
				if(mode == null || mode.equals("") || mode.equals("send")) {
					// 로그인 계정이 작성한 댓글의 개수에 기반한 페이지 수
					lastPageNum = commentViewDao.getWriteCommentPageNum(loginId);
					// 로그인 계정이 작성한 댓글 리스트
					ArrayList<MyPageCommentViewDto> myCommentList = commentViewDao.getMyCommentSortByWritedate(loginId, pageNum);
					request.setAttribute("lastPageNum", lastPageNum);
					request.setAttribute("myCommentList", myCommentList);
					
					// 파라미터로 받은 페이지 수가 총 페이지 수 보다 클 때
					if(pageNum>lastPageNum && lastPageNum!=0) { 
						request.getRequestDispatcher("Controller?command=page_not_found").forward(request, response);
					} else {
						// 로그인 계정 마이페이지의 댓글 페이지로 이동
						request.getRequestDispatcher("Recipe_MyPage_Comment_Write.jsp").forward(request, response);
					}
				} else if(mode.equals("receive")) {
					// 로그인 계정의 레시피에 작성된 댓글의 개수에 기반한 페이지 수
					lastPageNum = commentViewDao.getReceiveCommentPageNum(loginId);
					// 로그인 계정의 레시피에 작성된 댓글 리스트
					ArrayList<MyPageCommentViewDto> receiveCommentList = commentViewDao.getReceiveCommentSortByWritedate(loginId, pageNum);
					request.setAttribute("lastPageNum", lastPageNum);
					request.setAttribute("receiveCommentList", receiveCommentList);
					
					// 파라미터로 받은 페이지 수가 총 페이지 수 보다 클 때
					if(pageNum>lastPageNum && lastPageNum!=0) { 
						request.getRequestDispatcher("Controller?command=page_not_found").forward(request, response);
					} else {
						// 로그인 계정 마이페이지의 댓글 페이지로 이동
						request.getRequestDispatcher("Recipe_MyPage_Comment_Take.jsp").forward(request, response);
					}
				} else {
					// mode 파라미터를 잘못 받았을 경우
					request.getRequestDispatcher("Controller?command=page_not_found").forward(request, response);
				}
			} else {
				// 비로그인 상태에서 요청
				request.getRequestDispatcher("Controller?command=page_not_login").forward(request, response);
			}
		} else { // 페이지 이동을 타인 계정 마이페이지로
			if(new MemberDao().isOurUser(paramUid)) { // 파라미터로 받은 ID가 회원인 경우
				request.setAttribute("uId", paramUid); // 타인 유저아이디 저장
				// 타인 팔로워, 팔로잉 보여주는 곳에서 현재 로그인 계정의 정보가 필요함. 로그인되어있지 않다면 정보를 받아올 필요가 없음.
				if(loginId != null) {
					// 타인 계정 기반의 정보를 가져오기 위한 DAO, DTO객체 생성
					UserFollowDao userFollowDao = new UserFollowDao(); // 유저 팔로우 메서드 관련 DAO
					ProfileDao profileDao = new ProfileDao(); // 유저 프로필 메서드 관련 DAO
					ArrayList<UserFollowerDto> otherFollowerList = userFollowDao.getMemberFollowerById(loginId, paramUid); // 타인 계정의 팔로워 리스트 저장
					ArrayList<UserFollowingDto> otherFollowingList = userFollowDao.getMemberFollowingById(loginId, paramUid); // 타인 계정의 팔로잉 리스트 저장
					ProfileDto otherProfile = profileDao.getProfileById(paramUid); // 타인 계정의 프로필 정보 저장
					// 헤더의 검색창 우측 버튼에 로그인 유저를 표시하기 위한 DTO객체 생성
					ProfileDto myProfile = profileDao.getProfileById(loginId);
					// 로그인 유저가 타인계정을 팔로잉하는지의 여부.
					boolean checkFollowing = userFollowDao.checkFollowing(loginId, paramUid);
					
					request.setAttribute("otherFollowerList", otherFollowerList);
					request.setAttribute("otherFollowingList", otherFollowingList);
					request.setAttribute("otherProfile", otherProfile);
					request.setAttribute("myProfile", myProfile);
					request.setAttribute("checkFollowing", checkFollowing);
				}
				
				// 타인 계정이 작성한 댓글을 가져오기 위한 DAO, DTO객체 생성
				MyPageCommentViewDao commentViewDAO = new MyPageCommentViewDao();
				// 파라미터로 받은 타인 계정의 공개중 레시피의 개수에 기반한 페이지 수
				lastPageNum = commentViewDAO.getWriteCommentPageNum(paramUid);
				// 파라미터로 받은 타인 계정의 레시피 리스트 저장
				ArrayList<MyPageCommentViewDto> otherCommentList = commentViewDAO.getMyCommentSortByWritedate(paramUid, pageNum);
				request.setAttribute("lastPageNum", lastPageNum);
				request.setAttribute("otherCommentList", otherCommentList);
				
				// 파라미터로 받은 페이지 수가 총 페이지 수 보다 클 때
				if(pageNum>lastPageNum && lastPageNum!=0) { 
					request.getRequestDispatcher("Controller?command=page_not_found").forward(request, response);
				} else {
					// 타인 계정의 마이페이지(레시피)로 이동
					request.getRequestDispatcher("Recipe_MyPage_Comment_Others.jsp").forward(request, response);
				}
			} else { // 파라미터로 받은 ID가 회원이 아닌 경우
				// 예외 페이지로 넘김
				request.getRequestDispatcher("Controller?command=page_not_found").forward(request, response);
			}
		}
	}
}
