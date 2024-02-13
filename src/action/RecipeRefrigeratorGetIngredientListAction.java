package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RecipeBoardDao;
import dao.RecipeRefrigeratorDao;
import vo.RecipeDetailVo;
import vo.RecipeIngredientVo;

public class RecipeRefrigeratorGetIngredientListAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RecipeRefrigeratorDao refrigeratorDao = new RecipeRefrigeratorDao();
		ArrayList<RecipeIngredientVo> ingredientInfoList = refrigeratorDao.getIngredientInfo();
		request.setAttribute("ingredientInfoList", ingredientInfoList);
		RequestDispatcher rd = request.getRequestDispatcher("Recipe_Refrigerator.jsp");
		rd.forward(request, response);
	}
}
