package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.OrderDao;

public class OrderDeleteCartListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller에서 orderDeleteCartListAction으로 이동");
		String[] optionList = request.getParameterValues("optionList"); // 장바구니 화면에서 전달된 옵션명 배열로 String 배열 초기화
		String loginId = (String)request.getSession().getAttribute("loginId"); // 로그인 아이디 받기
		for(String option : optionList) { // String 배열에서 option명 String으로 받아서 순차적으로 삭제 메서드 실행
			OrderDao.getorderDao().deleteCartList(loginId,option); // 로그인 아이디,옵션명 파라미터로 장바구니 DB 삭제 실행하는 메서드 
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("msg", "cart삭제완료"); // 화면에 메시지 전달
		out.print(obj);
	}

}
