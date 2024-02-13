package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyPageFollowingRecipeDao;
import dao.ProfileDao;
import dao.RecipeBoardDao;
import dao.StoreMainpageDao;
import dao.UserFollowDao;
import dto.MyPageFollowingRecipeDto;
import dto.ProfileDto;
import dto.RecentRecipeDto;
import dto.UserFollowerDto;
import dto.UserFollowingDto;
import vo.AdvertisementGoodsVo;

public class MyPageFollowingRecipeViewAction implements Action {
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
		
		// 로그인 중인 계정이 있다면 로그인 아이디 기반의 정보를 가져오기 위한 DAO, DTO객체 생성
		if(loginId != null) {
			UserFollowDao userFollowDao = new UserFollowDao(); // 유저 팔로우 메서드 관련 DAO
			ProfileDao profileDao = new ProfileDao(); // 유저 프로필 메서드 관련 DAO
			ArrayList<UserFollowerDto> myFollowerList = userFollowDao.getMemberFollowerById(loginId, 1); // 로그인한 유저의 팔로워 리스트 저장
			ArrayList<UserFollowingDto> myFollowingList = userFollowDao.getMemberFollowingById(loginId, 1); // 로그인한 유저의 팔로잉 리스트 저장
			ProfileDto myProfile = profileDao.getProfileById(loginId); // 로그인한 유저의 프로필 정보 저장
			request.setAttribute("myFollowerList", myFollowerList);
			request.setAttribute("myFollowingList", myFollowingList);
			request.setAttribute("myProfile", myProfile);
			
			// 로그인 계정의 팔로잉 레시피를 받아올 수 있는 DAO객체 생성
			MyPageFollowingRecipeDao followingRecipeDao = MyPageFollowingRecipeDao.getMyFollowingRecipeDao();
			// 로그인 계정의 팔로잉 레시피 수를 기반으로 페이지 수를 가져오기
			lastPageNum = followingRecipeDao.getFollowingRecipePageNum(loginId);
			// 로그인 계정의 팔로잉 레시피를 저장할 리스트 생성
			ArrayList<MyPageFollowingRecipeDto> myFollowingRecipeList = followingRecipeDao.getMyFollowingRecipe(loginId, pageNum);
			request.setAttribute("lastPageNum", lastPageNum);
			request.setAttribute("myFollowingRecipeList", myFollowingRecipeList);
			
			// 파라미터로 받은 페이지 수가 총 페이지 수 보다 클 때
			if(pageNum>lastPageNum && lastPageNum!=0) { 
				request.getRequestDispatcher("Controller?command=page_not_found").forward(request, response);
			} else {
				request.getRequestDispatcher("Recipe_MyPage_FollowingRecipe.jsp").forward(request, response);
			}
		} else { // 비로그인상태에서 요청받았을 때
			request.getRequestDispatcher("Controller?command=page_not_login").forward(request, response);
		}
	}
}
