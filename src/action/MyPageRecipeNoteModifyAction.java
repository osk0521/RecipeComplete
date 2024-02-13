package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyPageRecipeNoteDao;

public class MyPageRecipeNoteModifyAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션 객체 생성
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId"); // 로그인 계정 ID 가져오기
		int recipeId = Integer.parseInt(request.getParameter("modify_recipe_id")); // 삭제할 레시피 노트의 레시피ID
		String noteContent = request.getParameter("content");
		
		// 레시피 노트 수정을 위한 Dao객체 생성
		MyPageRecipeNoteDao recipeNoteDao = MyPageRecipeNoteDao.getMyRecipeNoteDao();
		// 레시피 노트 수정
		recipeNoteDao.modifyRecipeNote(recipeId, loginId, noteContent);
		request.getRequestDispatcher("Controller?command=mypage_recipenote_view").forward(request, response);
	}
}
