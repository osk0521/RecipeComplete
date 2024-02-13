package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import dao.CartDao;

public class CartDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ajax를 통해서 delete action으로 이동");
		String[] cartDeleteList = request.getParameterValues("optionList"); // String 배열 전달된 옵션 배열로 초기화
		
		HttpSession session = request.getSession(); // 세션 선언
		String loginId = (String)session.getAttribute("loginId");
		
		CartDao.getCartdao().deleteCartDB(cartDeleteList,loginId);
		//옵션배열, 아이디 파라미터로 받아서 cart table DELETE문 실행
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("msg","삭제 완료되었습니다"); // 삭제 성공시 메시지 보내기
		out.print(obj);
	}

}
