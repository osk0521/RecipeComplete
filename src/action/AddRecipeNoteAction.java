package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProfileDao;
import dao.RecipeBoardDao;
import dto.ProfileDto;
import vo.RecipeCommentVo;
import vo.RecipeDetailVo;
import vo.RecipeIngrediVo;
import vo.RecipeProcessVo;

public class AddRecipeNoteAction implements Action {

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

		String content = (String)request.getParameter("content");
		
		boolean ok = RecipeBoardDao.addRecipeNote(recipeID, loginId, content);
		if(ok) {
			request.getRequestDispatcher("Controller?command=view_recipe_detail").forward(request, response);
		}
	}

}
