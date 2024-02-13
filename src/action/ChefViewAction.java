package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ChefDao;
import dao.MemberDao;
import dao.ProfileDao;
import dao.RecipeBoardDao;
import dao.StoreMainpageDao;
import dto.ChefDto;
import dto.ProfileDto;
import dto.RecentRecipeDto;
import vo.AdvertisementGoodsVo;

public class ChefViewAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션 객체 생성
		HttpSession session = request.getSession();
		// 세션에 로그인 아이디가 있다면 가져오기
		String loginId = (String)session.getAttribute("loginId");
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
		// sort1, sort2 속성값에 따라 정렬방식이 나뉨
		String sort1 = (String)request.getParameter("sort1");
		String sort2 = (String)request.getParameter("sort2");
		// searchWord(검색어) 쿼리스트링이 존재하면 해당 문자열이 포함된 레시피 제목 검색 가능
		String searchWord = (String)request.getParameter("searchWord");
		// 검색어에 따른 화면의 차이를 나타내기 위해 받아온 검색어를 다시 request에 저장
		request.setAttribute("searchWord", searchWord);
		// 쉐프 목록을 가져오기 위한 Dao와 List객체 생성
		ChefDao cDao = new ChefDao();
		ArrayList<ChefDto> chefList = null;
		// 현재 페이지 번호를 받아오기. 기본값은 1
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(request.getParameter("page"));
		} catch(NumberFormatException e) { }; // 잘못된 페이지값을 받을 경우 1로 고정
		// pagination을 위한 시작페이지, 끝페이지 설정
		int page = (pageNum/10 - (pageNum%10==0 ? 1 : 0)) * 10; // 시작, 끝페이지를 정하기 위한 수식
		int startPageNum = page + 1;
		int endPageNum = page + 10;
		int lastPageNum = cDao.getChefPageNum(searchWord);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("lastPageNum", lastPageNum);
		
		// 로그인되어있는 아이디가 있다면 페이지 header에 로그인 유저 정보를 표시하기 위해 로그인 유저 프로필 정보 뽑아오기
		if(loginId != null) {
			ProfileDto myProfile = new ProfileDao().getProfileById(loginId); // 로그인한 유저의 프로필 정보 저장
			request.setAttribute("myProfile", myProfile);
		}
		
		// 파라미터로 받은 페이지 수가 총 페이지 수 보다 클 때
		if(pageNum>lastPageNum && lastPageNum!=0) { 
			request.getRequestDispatcher("Controller?command=page_not_found").forward(request, response);
		} else {
			// 받아온 정렬방식에 맞게 쉐프 리스트 받아오기
			if( (sort1 == null || sort1.equals("follower")) && (sort2 == null || sort2.equals("all") || sort2.equals("today")) ) {
				chefList = cDao.getChefSortByFollower(loginId, searchWord, pageNum, sort2);
				request.setAttribute("chefList", chefList);
				request.getRequestDispatcher("Recipe_Chef.jsp").forward(request, response);
			} else if( sort1.equals("hits") && (sort2 == null || sort2.equals("all") || sort2.equals("today")) ) {
				chefList = cDao.getChefSortByRecipeHits(loginId, searchWord, pageNum, sort2);
				request.setAttribute("chefList", chefList);
				request.getRequestDispatcher("Recipe_Chef.jsp").forward(request, response);
			} else if( sort1.equals("likes") && (sort2 == null || sort2.equals("all") || sort2.equals("today")) ) {
				chefList = cDao.getChefSortByRecipeLikes(loginId, searchWord, pageNum, sort2);
				request.setAttribute("chefList", chefList);
				request.getRequestDispatcher("Recipe_Chef.jsp").forward(request, response);
			} else if( sort1.equals("recipe") && (sort2 == null || sort2.equals("all") || sort2.equals("today")) ) {
				chefList = cDao.getChefSortByRecipeQty(loginId, searchWord, pageNum, sort2);
				request.setAttribute("chefList", chefList);
				request.getRequestDispatcher("Recipe_Chef.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("Controller?command=page_not_found").forward(request, response);
			}
		}
	}
}
