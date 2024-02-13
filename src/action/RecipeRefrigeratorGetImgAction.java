package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.RecipeRefrigeratorDao;
import vo.RecipeIngredientVo;
import vo.RecipeRefrigeratorImgVo;
import vo.RecipeRefrigeratorVo;

public class RecipeRefrigeratorGetImgAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession();
		String loginId = request.getParameter("loginId");	
		int pageNum = 1;		
		/*
		  int pageNum = Integer.parseInt(request.getParameter("page_num"));
		 */		RecipeRefrigeratorDao refrigeratorDao = new RecipeRefrigeratorDao();
		ArrayList<RecipeRefrigeratorVo> resultArrayList = refrigeratorDao.searchRecipeRefrigerator(loginId);
		PrintWriter out = response.getWriter();
		JSONObject resultJSON = new JSONObject();
		
		ArrayList<RecipeRefrigeratorImgVo> ingredeImgArrayList = new ArrayList<RecipeRefrigeratorImgVo>();
	 	JSONArray jArray = new JSONArray();
	 	ingredeImgArrayList = refrigeratorDao.getIngredeImageByID(loginId);
 		for (int i = 0; i < resultArrayList.size(); i++) {
	 		JSONObject sObject = new JSONObject();
	 		sObject.put("ingredeImgArrayList", ingredeImgArrayList);
	 		jArray.add(sObject);
		}
 		//resultJSON.put("resultArrayList", jArray);
 		resultJSON.put("ingredeImgArrayList", ingredeImgArrayList);
		out.println(resultJSON);
		//request.setAttribute("result", result);
	}
}
