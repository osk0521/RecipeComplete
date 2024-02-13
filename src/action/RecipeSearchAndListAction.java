package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProfileDao;
import dao.RecipeBoardDao;
import dto.ProfileDto;
import dto.RecentRecipeDto;
import vo.RecipeLvVo;
import vo.RecipeManagerCategoryVo;
import vo.RecipeVo;

public class RecipeSearchAndListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		/* int recipeID = Integer.parseInt(request.getParameter("recipeID")); */
		// 세션에 로그인 아이디가 있다면 가져오기
		String loginId = (String)session.getAttribute("loginId"); 

		if(loginId != null) {
			ProfileDao profileDao = new ProfileDao(); // 유저 프로필 메서드 관련 DAO
			ProfileDto myProfile = profileDao.getProfileById(loginId); // 로그인한 유저의 프로필 정보 저장
			request.setAttribute("myProfile", myProfile);
		}
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
		
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) { }
		String orderBy = (String)(request.getParameter("order_by"));
		if(orderBy == null) {
			orderBy = "h";
		}
		String searchWord=null;
		if(request.getParameter("sw")!=null) {
			searchWord= (String)(request.getParameter("sw"));
		}
		int what = 0;
		int kind = 0;
		int situation = 0;
		int minTime = 0;
		int maxTime = 0;
		int lv = 100;
		int p = (pageNum/5)*5 - (pageNum%5==0 ? 5: 0);
		int startPNum = p + 1;
		int endPNum = p + 5;
		RecipeBoardDao recipeBoardDao = new RecipeBoardDao();
		if(request.getParameter("s")!=null) {
			situation= Integer.parseInt((request.getParameter("s")));
		}
		if(request.getParameter("k")!=null) {
			kind= Integer.parseInt((request.getParameter("k")));
		}
		if(request.getParameter("w")!=null) {
			what= Integer.parseInt((request.getParameter("w")));
		}
		if(request.getParameter("lv")!=null) {
			lv= Integer.parseInt((request.getParameter("lv")));
		}
		if(request.getParameter("minTime")!=null) {
			minTime= Integer.parseInt((request.getParameter("minTime")));
		}
		if(request.getParameter("maxTime")!=null) {
			maxTime= Integer.parseInt((request.getParameter("maxTime")));
		}
		int searchedRecipeCount = recipeBoardDao.getSearchedRecipeCnt(searchWord, what, kind, situation, minTime, maxTime, lv);
		int lastPageNum = recipeBoardDao.getNormalRecipeLastPageNum();
		int i = (searchedRecipeCount%16==0 ? searchedRecipeCount/16 : (searchedRecipeCount/16)+1);
		if(i != lastPageNum) {
			lastPageNum = i;
		}
		//System.out.println("마지막 페이지 번호는 : "+recipeBoardDao.getLastPageNum());
		ArrayList<RecipeVo> searchedRecipeList = recipeBoardDao.getSearchedRecipeList(searchWord, what, kind, situation, minTime, maxTime, lv, pageNum, orderBy);
		ArrayList<RecipeManagerCategoryVo> getCategoryWhatList = recipeBoardDao.getCategoryList("WHAT");
		ArrayList<RecipeManagerCategoryVo> getCategoryKindList = recipeBoardDao.getCategoryList("KIND");
		ArrayList<RecipeManagerCategoryVo> getCategorySituation = recipeBoardDao.getCategoryList("SITUATION");
		ArrayList<RecipeLvVo> getLv = recipeBoardDao.getLvList();
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPNum", startPNum);
		request.setAttribute("endPNum", endPNum);
		request.setAttribute("lastPageNum", lastPageNum);
		request.setAttribute("searchedRecipeList", searchedRecipeList);
		request.setAttribute("searchedRecipeCount", searchedRecipeCount);
		request.setAttribute("getCategoryWhatList", getCategoryWhatList);
		request.setAttribute("getCategoryKindList", getCategoryKindList);
		request.setAttribute("getLv", getLv);
		request.setAttribute("getCategorySituation", getCategorySituation);
		//request.getRequestDispatcher("Search").forward(request, response);
		request.getRequestDispatcher("Recipe_Search_And_Classification.jsp").forward(request, response);

	}
}
