package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StoreGoodsDetailsDao;
import vo.RelatedGoodsVo;
import vo.StoreGoodsDetailsOption1Vo;
import vo.StoreGoodsDetailsVo;
import vo.StoreMainpageGoodVo;

public class StoreGoodsDetialAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int product_id = Integer.parseInt(request.getParameter("product_id")); // 클릭 이벤트시 전달되는 상품 번호 int 변수로 받기
		System.out.println(product_id);
		
		StoreGoodsDetailsDao detail1dao = StoreGoodsDetailsDao.getInstance();
		
		StoreGoodsDetailsVo goodsdetailvo = detail1dao.getDetailInfoById(product_id); // 상품 id를 이용하여 상품 상세 정보들을 가져오는 메서드
		request.setAttribute("goodsdetail", goodsdetailvo);
		
		ArrayList<String> goodsdetailImg = new ArrayList<String>();
		goodsdetailImg = detail1dao.getImgById(product_id); // 상품 id를 이용하여 상품 이미지 받아오는 메서드
		request.setAttribute("goodsimgs", goodsdetailImg);
		
		ArrayList<StoreGoodsDetailsOption1Vo> goodsOptions = new ArrayList<StoreGoodsDetailsOption1Vo>();
		goodsOptions = detail1dao.getOptionById(product_id); // 상품 id를 이용하여 상품 옵션 받아오기
		request.setAttribute("goodsOptions", goodsOptions);
		
		ArrayList<String> goodsDetailImages = new ArrayList<String>();
		goodsDetailImages = detail1dao.getDetailImgs(product_id); // 상품 id를 이용하여 상품 상세 이미지 받아오기
		request.setAttribute("goodsDetailImages", goodsDetailImages);
		
		ArrayList<RelatedGoodsVo> relatedGoods = new ArrayList<RelatedGoodsVo>();
		relatedGoods = detail1dao.getRelatedGoods(product_id); // 상품 id를 이용하여 상품 관련 리스트들 불러오기
		request.setAttribute("relatedGoods", relatedGoods);
		
		ArrayList<String> goodsTag = new ArrayList<String>();
		goodsTag = detail1dao.getGoodsTag(product_id);
		request.setAttribute("goodsTag", goodsTag);
		
		request.getRequestDispatcher("HRK_Store_Gooddetail.jsp").forward(request, response); // 상품 상세 정보 jsp로 이동

	}

}
