package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.MemberDao;

public class MemberCheckAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 카카오 혹은 네이버 로그인으로부터 받은 ID값
		String paramId = request.getParameter("id");
		
		// 해당 아이디값으로 가입된 회원이 있는지 확인
		boolean isOurUser = new MemberDao().isOurUser(paramId);
		
		// JSON타입으로 결과값 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONObject obj = new JSONObject();
		obj.put("idExists", isOurUser);
		out.print(obj);
	}
}
