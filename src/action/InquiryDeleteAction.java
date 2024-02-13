package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.CustomerServiceDao;

public class InquiryDeleteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 문의글 번호 받아오기
		int inquiryId = Integer.parseInt(request.getParameter("inquiry_id"));
		// 문의글 삭제를 위한 CustomerServiceDao객체 생성
		CustomerServiceDao csDao = new CustomerServiceDao();
		// 문의글 삭제. 정상삭제 : true
		boolean result = csDao.deleteInquiry(inquiryId);
		
		// JSON객체로 결과값 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("result", result);
		out.print(obj);
	}
}
