package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.CustomerServiceDao;

public class InquiryWriteSimpleAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginId = (String)request.getSession().getAttribute("loginId"); // 작성자의 아이디
		String inquiryTitle = "의견"; // 간단 의견제출에 사용될 임시 제목
		String inquiryContent = request.getParameter("inquiry_content"); // 간단 의견제출 내용
		
		// 문의글 작성을 위한 Dao객체 생성
		CustomerServiceDao csDao = new CustomerServiceDao();
		boolean result = csDao.writeInquiry(inquiryTitle, loginId, inquiryContent);
		
		// JSON객체로 결과 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("result", result);
		out.print(obj);
	}
}
