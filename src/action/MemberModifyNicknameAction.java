package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.MemberDao;

public class MemberModifyNicknameAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 유저 아이디 받아오기
		String loginId = (String)request.getSession().getAttribute("loginId");
		// 변경하고자 하는 닉네임
		String nickname = request.getParameter("nickname");
		// 닉네임 변경
		boolean result = new MemberDao().modifyNickname(loginId, nickname);
		
		// JSON객체로 결과값 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("result", result);
		out.print(obj);
	}
}
