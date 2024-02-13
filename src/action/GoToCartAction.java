package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import dao.CartDao;

import vo.StoreGoodsDetailsOption1Vo;

public class GoToCartAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ajax를 통해서 action으로 이동");
		String[] strArr1 = request.getParameterValues("optionList"); // 상품 상세 페이지에서 전달된 옵션명들 ArrayList로 받기
		String[] strArr2 = request.getParameterValues("qtyList"); // 상품 상세 페이지에서 전달된 옵션 수량들 ArrayList로 받기
		
		ArrayList<StoreGoodsDetailsOption1Vo> cartList = new ArrayList<StoreGoodsDetailsOption1Vo>(); // 카트 정보 담는 ArrayList 선언
		
		for(int i=0;i<strArr1.length;i++) {// ArrayList에 담긴 정보들 순차적으로 cartList에 담기
			String option = strArr1[i]; 
			int qty = Integer.parseInt(strArr2[i]); // String 옵션 수량 int로 형변환
			cartList.add(new StoreGoodsDetailsOption1Vo(option, qty)); // cartList에 담기
		};
		
		HttpSession session = request.getSession(); // 세선선언
		String logInId = (String)session.getAttribute("loginId"); // 로그인된 아이디로 초기화 
		int productId = Integer.parseInt(request.getParameter("productId")); // 상품 번호로 초기화
		
		CartDao.getCartdao().insertCartDB(logInId,productId,cartList); // 로그인 아이디, 상품 번호, cartList 파라미터로 장바구니 디비에 넣는 메서드
		
		
		response.setContentType("application/json");// ajax로 성공값 전달
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("test", "test");
		out.print(obj);
	}
}
