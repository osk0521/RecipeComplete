package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ChefDao;
import dto.ChefDto;

@WebServlet("/ChefTest")
public class ChefTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("ChefPageTest.jsp");
		
		// 쉐프리스트 받기위한 Dao생성
		ChefDao chefDao = new ChefDao();
		// 임시 변수 생성
		String loginId = (String)session.getAttribute("loginId");
		String searchWord = request.getParameter("searchWord");
		request.setAttribute("searchWord", searchWord);
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(request.getParameter("page"));
		} catch(NumberFormatException e) { }
		String term = request.getParameter("term");
		request.setAttribute("term", term);
		// 쉐프리스트의 페이지 수를 받기
		int lastPageNum = chefDao.getChefPageNum(searchWord);
		// 페이지네이션을 위한 페이지 수 설정
		int pageSet = (pageNum/10 - (pageNum%10==0 ? 1 : 0)) * 10;
		int startPageNum = pageSet + 1;
		int endPageNum = pageSet + 10;
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("lastPageNum", lastPageNum);
		request.setAttribute("pageNum", pageNum);
		// 쉐프리스트 받기위한 리스트 생성
		ArrayList<ChefDto> chefList = chefDao.getChefSortByRecipeQty(loginId, searchWord, pageNum, term); // 소식받기순위 쉐프리스트(1페이지당 10개씩)
		request.setAttribute("chefList", chefList);
		
		rd.forward(request, response);
	}
}
