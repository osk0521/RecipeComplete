package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerServiceDao;
import dao.ProfileDao;
import dto.ProfileDto;

public class InquiryWriteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 아이디 받아옴
		String loginId = (String)request.getSession().getAttribute("loginId");
		// 문의글의 제목, 내용을 받아옴
		String inquiryTitle = request.getParameter("inquiry_title");
		String inquiryContent = request.getParameter("inquiry_content");
		
		// 문의글 작성을 위한 Dao객체 생성
		CustomerServiceDao csDao = new CustomerServiceDao();
		// 문의글 작성. 정상 작성 : true
		boolean result = csDao.writeInquiry(inquiryTitle, loginId, inquiryContent);
		request.setAttribute("result", result);
		request.getRequestDispatcher("Controller?command=inquiry_write_result").forward(request, response);
	}
}
