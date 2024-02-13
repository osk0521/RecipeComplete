package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.StoreGoodsDetailsDao;
import vo.StoreGoodsDetailsOption1Vo;

public class CartGetUpdateOptionAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int productId = Integer.parseInt(request.getParameter("productId")); // 장바구니화면에서 전달된 상품 번호로 int 변수 초기화
		System.out.println(productId);
		
		StoreGoodsDetailsDao detail1dao = StoreGoodsDetailsDao.getInstance(); // 상품 상세 dao 선언
		ArrayList<StoreGoodsDetailsOption1Vo> goodsOptions = new ArrayList<StoreGoodsDetailsOption1Vo>(); // 상품 상세 옵션 담는 ArrayList 선언
		goodsOptions = detail1dao.getOptionById(productId); // 상품번호를 파라미터로 상세 옵션 ArrayList 받는 메서드 실행 
		
		response.setContentType("application/json"); // ajax로 화면에 전달
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONArray array = new JSONArray();
		for(StoreGoodsDetailsOption1Vo vo : goodsOptions) { // JSONArray로 담기
			JSONObject obj = new JSONObject();
			obj.put("optionNum",vo.getOp1Num());
			obj.put("optionContent",vo.getoptionContent());
			obj.put("optionQty", vo.getQty());
			obj.put("optionPrice", vo.getPrice());
			array.add(obj);
		}
		
		out.print(array);
		
	}

}
