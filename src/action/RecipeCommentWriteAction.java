package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import dao.RecipeBoardDao;
import dao.RecipeCommentDao;
public class RecipeCommentWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession();
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		int recipeID = Integer.parseInt(request.getParameter("recipeID"));
		int commentTo = Integer.parseInt(request.getParameter("commentTo")); //0
		
		String loginId = request.getParameter("loginId");
		String comment = request.getParameter("content");
		System.out.println(comment);
		
		String image = request.getParameter("image");
		int [] commentGroupOrOrder = RecipeBoardDao.getNewCommentGroupAndOrder(recipeID, commentTo);

		boolean result = false;
		if(commentTo == 0) {
			RecipeCommentDao.insertRecipeComment(recipeID, loginId, commentGroupOrOrder[0], 1, comment, image);
		} else {
			RecipeCommentDao.insertRecipeComment(recipeID, loginId, commentGroupOrOrder[0], commentGroupOrOrder[1], comment, image);
		}
		
		if(result) {
			obj.put("result", "success");
			System.out.println("댓글 등록 성공");
		} else {
			obj.put("result", "fail");
		}
		out.println(obj);
	}

}
