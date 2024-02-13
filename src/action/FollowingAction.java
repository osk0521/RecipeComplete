package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.MemberDao;

public class FollowingAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 소식받기 버튼 눌렀을 때 db의 member_follow테이블에 데이터 insert 후 결과값을 return
		System.out.println("ajax의 요청에 의해  action으로 넘어옴");
		String loginId = request.getParameter("login_id");
		String yourUid = request.getParameter("your_uid");
		System.out.println("로그인 아이디 : " + loginId + ", 팔로잉 대상 아이디 : " + yourUid);
		
		// 팔로잉 결과를 알려줌. true:성공 false:실패
		boolean result = new MemberDao().memberFollowing(loginId, yourUid);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		out.print(obj);
	}
}
