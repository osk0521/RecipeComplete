package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.MemberDao;

public class KakaoRegistAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 카카오 로그인으로부터 회원가입에 필요한 정보 가져오기
		String kakaoId = request.getParameter("id");
		String kakaoPw = "kakaotest1"; // 카카오 계정 임시 비밀번호
		String kakaoName = request.getParameter("name");
		String kakaoEmail = request.getParameter("email");
		String kakaoProfileImage = request.getParameter("profile_image");
		
		// 닉네임이 중복된 닉네임인지 확인
		MemberDao mDao = new MemberDao();
		boolean checkNickname = mDao.checkNicknameDuplication(kakaoName);
		int cnt = 0; // 중복 닉네임 방지 숫자
		while(!checkNickname) {
			kakaoName += cnt;
			checkNickname = mDao.checkNicknameDuplication(kakaoName);
			cnt++;
		}
		
		// 가져온 정보를 토대로 회원가입 진행(생일, 전화번호는 임시로 생성)
		boolean result = mDao.memberRegist(kakaoId, kakaoPw, kakaoName, "01012341234", kakaoEmail, "2000-01-01", 1, 1, 1);
		
		// JSON객체로 결과 반환
		response.setContentType("aplication/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONObject obj = new JSONObject();
		obj.put("success", result);
		out.print(obj);
	}
}
