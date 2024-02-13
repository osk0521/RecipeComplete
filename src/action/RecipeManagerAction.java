package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProfileDao;
import dao.RecipeManagerDao;
import dto.ProfileDto;
import vo.RecipeManagerCategoryVo;

public class RecipeManagerAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		int recipeID = Integer.parseInt(request.getParameter("recipeID"));
		// 세션에 로그인 아이디가 있다면 가져오기
		String loginId = (String)session.getAttribute("loginId"); 

		if(loginId != null) {
			ProfileDao profileDao = new ProfileDao(); // 유저 프로필 메서드 관련 DAO
			ProfileDto myProfile = profileDao.getProfileById(loginId); // 로그인한 유저의 프로필 정보 저장
			request.setAttribute("myProfile", myProfile);
		}
		int id = Integer.parseInt(request.getParameter("id"));
		String image = request.getParameter("image");
		String name = request.getParameter("name");
		String table = request.getParameter("table");
		switch (table) {
		case "w"://name
			table = "what";
			break;
		case "k"://recent
			table = "kind";
			break;
		case "s"://hit
			table = "situation";
			break;
		}
		ArrayList<RecipeManagerCategoryVo> newData = new ArrayList<RecipeManagerCategoryVo>();
		newData.add(new RecipeManagerCategoryVo(id, name, image));
		RecipeManagerDao rmDao = new RecipeManagerDao();
		rmDao.insertNewCategory(table, newData);
		request.getRequestDispatcher("Recipe_Manager.jsp").forward(request, response);

	}

}
