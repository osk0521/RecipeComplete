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
import vo.RecipeCommentVo;
import vo.RecipeDetailVo;
import vo.RecipeIngrediVo;
import vo.RecipeProcessVo;

public class RecipeDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 세션 객체 생성
		HttpSession session = request.getSession();
		int recipeID = Integer.parseInt(request.getParameter("recipeID"));
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

		RecipeDetailVo recipeDetailVo = RecipeBoardDao.getRecipeDetailVoByID(recipeID);
		RecipeBoardDao.addRecipeHitByID(recipeID);//조회수 1 추가
		ArrayList<RecipeIngrediVo> recipeIngrideList = RecipeBoardDao.getRecipeIngrediVoByID(recipeID);
		ArrayList<RecipeProcessVo> recipeProcessList = RecipeBoardDao.getRecipeProcessVoByID(recipeID);
		RecipeProcessVo recipeLastProcessVo = RecipeBoardDao.getRecipeLastProcessVoByID(recipeID);
		ArrayList<RecipeCommentVo> recipeCommentList = RecipeBoardDao.getRecipeCommentVoByID(recipeID);
		String recipeNote = null;
		if(loginId != null) {
			recipeNote = RecipeBoardDao.getRecipeNoteByID(loginId, recipeID);
		}
		request.setAttribute("recipeID", recipeID);
		request.setAttribute("recipeDetailVo", recipeDetailVo);
		request.setAttribute("recipeNote", recipeNote);
		request.setAttribute("recipeIngrideList", recipeIngrideList);
		request.setAttribute("recipeProcessList", recipeProcessList);
		request.setAttribute("recipeLastProcessVo", recipeLastProcessVo);   
		request.setAttribute("recipeCommentList", recipeCommentList);
		request.getRequestDispatcher("Recipe_View_Recipe.jsp").forward(request, response);
	}
}
