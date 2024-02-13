package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;

@WebServlet("/ChefLogin")
public class ChefLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramId = request.getParameter("id");
		String paramPw = request.getParameter("pw");
		
		MemberDao mDao = new MemberDao();
		boolean result = mDao.login(paramId, paramPw);
		
		if(result) {
			HttpSession session = request.getSession();
			session.setAttribute("loginId", paramId);
			response.sendRedirect("ChefTest");
		} else {
			response.sendRedirect("ChefLogin.jsp");
		}
	}
}
