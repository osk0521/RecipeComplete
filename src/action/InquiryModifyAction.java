package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerServiceDao;

public class InquiryModifyAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 문의글 수정에 필요한 값들 받아오기
		int inquiryId = Integer.parseInt(request.getParameter("inquiry_id"));
		String inquiryTitle = request.getParameter("inquiry_title");
		String inquiryContent = request.getParameter("inquiry_content");
		// 문의글 업데이트
		boolean result = new CustomerServiceDao().modifyInquiry(inquiryId, inquiryTitle, inquiryContent);
		request.setAttribute("result", result);
		
		request.getRequestDispatcher("Recipe_Inquiry_Modify_Result.jsp").forward(request, response);
	}
}
