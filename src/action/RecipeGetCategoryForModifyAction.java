package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RecipeBoardDao;
import dto.RecentRecipeDto;
import vo.RecipeManagerCategoryVo;

public class RecipeGetCategoryForModifyAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<RecipeManagerCategoryVo> CategoryList_W = RecipeBoardDao.getCategoryList("What");
		ArrayList<RecipeManagerCategoryVo> CategoryList_K = RecipeBoardDao.getCategoryList("Kind");
		ArrayList<RecipeManagerCategoryVo> CategoryList_S = RecipeBoardDao.getCategoryList("Situation");
		
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
		
		request.setAttribute("CategoryList_W", CategoryList_W);
		request.setAttribute("CategoryList_K", CategoryList_K);
		request.setAttribute("CategoryList_S", CategoryList_S);
		request.getRequestDispatcher("Recipe_ModifyPage.jsp").forward(request, response);
	}
}
