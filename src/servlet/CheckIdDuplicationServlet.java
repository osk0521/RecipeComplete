package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.MemberDao;

@WebServlet("/CheckIdDuplication")
public class CheckIdDuplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		System.out.println("중복체크할 id : " + id);
		
		MemberDao mDao = new MemberDao(); // id중복을 확인하기 위한 Dao객체 생성
		boolean result = mDao.checkIdDuplication(id); // 중복 확인 결과. true:중복아님, false:중복
		
		// json타입 데이터를 전달하기 위한 객체 생성
		response.setContentType("application/json"); // MIME타입.
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("result", result);
		out.println(obj);
	}
}
