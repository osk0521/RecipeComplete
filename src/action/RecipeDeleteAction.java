package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RecipeBoardDao;

public class RecipeDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int recipeID = Integer.parseInt(request.getParameter("recipeID"));
		RecipeBoardDao.deleteRecipeAndDetails(recipeID);
		request.getRequestDispatcher("Controller?command=main_page").forward(request, response);
	}
}
