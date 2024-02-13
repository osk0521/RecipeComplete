package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.tribes.util.Arrays;
import org.json.simple.JSONObject;

import dao.OrderDao;

public class OrderInsertOrderListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller에서 orderInsertOrderListAction으로 이동"); // orderlist DB에 인서트 하기 위한 배열 선언
		String[] productIdList1 = request.getParameterValues("productIdList"); // 상품 가격 비교를 위한 배열 선언
		String[] productIdList2 = request.getParameterValues("productIdList"); // 상품 id 배열, 중복값 제외
		String[] optionList = request.getParameterValues("optionList"); // 상품 옵션 배열
		String[] qtyList = request.getParameterValues("qtyList"); // 상품 갯수 배열
		int orderId = Integer.parseInt(request.getParameter("orderId")); // 주문번호
		int total = Integer.parseInt(request.getParameter("total"));
		String loginId = (String) request.getSession().getAttribute("loginId"); // 로그인 아이디
		
		HashSet<Integer> productIdset = new HashSet<>(); // 상품id 중복값 걸러내기위해 hashset 사용
		for(String s : productIdList2) {
			productIdset.add(Integer.parseInt(s));
		}
		
		Integer[] productIdArray = productIdset.toArray(new Integer[0]); // hashSet에서 배열로 다시 전환, 메서드 사용을 위해
		
		System.out.println(orderId);
		System.out.println(Arrays.toString(productIdArray));
		System.out.println(Arrays.toString(optionList));
		System.out.println(Arrays.toString(qtyList));
		System.out.println("총합은 "+total);
		
		
		
		for(int i=0;i<qtyList.length;i++) { // 배열 차례대로 dao메서드 실행 
		int productId = Integer.parseInt(productIdList1[i]);
		String option = optionList[i]; 
		int qty = Integer.parseInt(qtyList[i]);
		OrderDao.getorderDao().insertOrderList(orderId,loginId,productId,option,qty);} // 주문 정보 입력하는 메서드  
		
		int DBOptionsAmount = OrderDao.getorderDao().selectOptionSum(orderId); // db상에 옵션값들 총합 구하는 메서드
		
		int totalDBdelivery = 0;
		for(int i : productIdArray) {
			int DBDelivery = OrderDao.getorderDao().selectProduct(i); // db상 상품 배송비 총합 구하는 메서드
			totalDBdelivery += DBDelivery;
		}
		
		boolean check = OrderDao.getorderDao().compareTotalFromDB(total,DBOptionsAmount+totalDBdelivery); // 유저로부터 받은 총 가격과 DB상의 가격을 비교하는 메서드, 다르면 false 맞으면 true
		System.out.println(check);
		 response.setContentType("application/json");
		 response.setCharacterEncoding("UTF-8"); 
		 PrintWriter out = response.getWriter(); 
		 JSONObject obj = new JSONObject(); 
		 obj.put("msg",check);// 화면에 메시지 전달 out.print(obj);
		 out.print(obj);
	
	}
		
} 
	


