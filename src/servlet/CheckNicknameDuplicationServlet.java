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

@WebServlet("/CheckNicknameDuplication")
public class CheckNicknameDuplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String nickname = request.getParameter("nickname");
		System.out.println("중복체크할 닉네임 : " + nickname);
		
		MemberDao mDao = new MemberDao(); // 닉네임 중복을 확인하기 위한 Dao객체 생성
		boolean result = mDao.checkNicknameDuplication(nickname); // 중복 확인 결과. true:중복아님, false:중복임
		
		// json타입 데이터를 전달하기 위한 객체 생성
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("result", result);
		out.println(obj);
	}
}
