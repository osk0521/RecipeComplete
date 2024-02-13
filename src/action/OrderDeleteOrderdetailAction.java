package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.OrderDao;

public class OrderDeleteOrderdetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller에서 OrderDeleteOrderdetailAction으로 이동");
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		OrderDao.getorderDao().deleteOrderlist(orderId);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("msg", "주문실패");
		out.print(obj);
	}

}
