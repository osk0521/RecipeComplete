package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;

public class RegistAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파라미터로 넘겨받은 회원가입 정보
		String regId, regPw, regNickname, regPhone, regEmail, regBirth, regAgree3;
		int regYear, regMonth, regDay;
		regId = request.getParameter("id");
		regPw = request.getParameter("pw");
		regNickname = request.getParameter("nickname");
		regPhone = request.getParameter("phone");
		regEmail = request.getParameter("email");
		regYear = Integer.valueOf(request.getParameter("year"));
		regMonth = Integer.valueOf(request.getParameter("month"));
		regDay = Integer.valueOf(request.getParameter("day"));
		regBirth = regYear + "-" + regMonth + "-" + regDay; // 생년월일 문자열
		// 파라미터로 넘겨받은 동의사항(선택동의사항은 null값이 존재할 수 있음) 0:비동의, 1:동의 
		int agree1 = 1; // 1번동의사항(필수이기 때문에 반드시 1)
		int agree2 = 1; // 2번동의사항(필수이기 때문에 반드시 1)
		int agree3 = 1; // 3번동의사항(선택이기 때문에 0 또는 1)
		regAgree3 = null; // 3번동의사항(선택동의사항)
		regAgree3 = request.getParameter("agree3");
		if(regAgree3 == null) { // 3번동의사항이 체크되지 않았을 때
			agree3 = 0;
		}
		// 회원가입 메서드를 사용하기 위해 MemberDAO객체 생성
		MemberDao memberDao = new MemberDao();
		// 쿼리문 정상작동 여부. true=정상 false=비정상
		boolean complete = memberDao.memberRegist(regId, regPw, regNickname, regPhone, regEmail, regBirth, agree1, agree2, agree3);
		// 휴대폰, 이메일 인증번호 세션에서 삭제
		HttpSession session = request.getSession();
		session.removeAttribute("phoneAuthenticationNumber");
		session.removeAttribute("emailAuthenticationNumber");
		
		request.setAttribute("complete", complete);
		request.getRequestDispatcher("Controller?command=regist_result").forward(request, response);
	}
}
