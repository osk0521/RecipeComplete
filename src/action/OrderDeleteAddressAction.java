package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.OrderDao;

public class OrderDeleteAddressAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller에서 orderDeleteAddressAction으로 이동");
		int addressId = Integer.parseInt(request.getParameter("addressId")); // 주소ID로 int 변수 초기화
		OrderDao.getorderDao().deleteAddress(addressId); // 주소아이디 파라미터로 주소 정보 삭제하는 메서드
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("msg", "삭제가 완료되었습니다"); // 완료시 메시지 전달
		out.print(obj);
	}

}
