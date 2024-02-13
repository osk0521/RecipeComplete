package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.MemberDao;

public class NaverRegistAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 네이버 로그인으로부터 회원가입에 필요한 정보 가져오기
		String naverId = request.getParameter("id");
		String naverPw = "navertest1"; // 네이버 계정 임시 비밀번호
		String naverEmail = request.getParameter("email");
		String naverNickname = request.getParameter("nickname");
		String naverPhone = request.getParameter("phone").replaceAll("-", "");
		String naverBirth = request.getParameter("birth");
		
		// 닉네임이 중복된 닉네임인지 확인
		MemberDao mDao = new MemberDao();
		boolean checkNickname = mDao.checkNicknameDuplication(naverNickname);
		int cnt = 0; // 중복 닉네임 방지 숫자
		while(!checkNickname) {
			naverNickname += cnt;
			checkNickname = mDao.checkNicknameDuplication(naverNickname);
			cnt++;
		}
		
		// 가져온 정보를 토대로 회원가입 진행
		boolean result = mDao.memberRegist(naverId, naverPw, naverNickname, naverPhone, naverEmail, naverBirth, 1, 1, 1);
		
		// JSON객체로 결과 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("success", result);
		out.print(obj);
	}
}
