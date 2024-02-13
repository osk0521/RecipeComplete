package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.ConnectionRecipe;
import vo.AdvertisementGoodsVo;
import vo.StoreMainpageGoodVo;

public class StoreMainpageDao {
	
	public ArrayList<String> Showevent(){
		// 메인페이지 이벤트 url 받는 메서드
		ConnectionRecipe.connectionRecipe();
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		ArrayList<String> event = new ArrayList<>();
		String sql = "SELECT image FROM product_event";
		
		try {
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				rs = pstmt.executeQuery(sql);
			while(rs.next()) {
				String event_url = rs.getNString(1);
				event.add(event_url);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return event;
	}
	
	public ArrayList<StoreMainpageGoodVo> Getbestdealinfo() {
		// 베스트 상품 정보 ArrayList로 받는 메서드
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<StoreMainpageGoodVo> BestDealGoodsInfo = new ArrayList<>();
		
		String sql = "SELECT pi.image,p.product_id, p.name, p.sale_per, p.sell_cost, p.deli_char, p.hot_deal,p.score ,p.hits, ov.주문량 \r\n" + 
				"FROM product p, product_image pi, replevalue_view rv, ordervalue_view ov\r\n" + 
				"WHERE p.product_id = pi.product_id AND p.product_id = rv.product_id AND p.product_id = ov.product_id AND pi.image_order =1 AND p.best =1";
		
		try {
			
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String image = rs.getNString("IMAGE");
				int productId = rs.getInt("product_id");
				String name = rs.getNString("name");
				int salePer = rs.getInt("sale_per");
				int sellCost = rs.getInt("sell_cost");
				int deliveryCharge = rs.getInt("deli_char");
				int hotDeal = rs.getInt("hot_deal");
				double score = rs.getDouble("score");
				int repleValue = rs.getInt("hits");
				int orderValue = rs.getInt("주문량");
				
				BestDealGoodsInfo.add(new StoreMainpageGoodVo(image, productId, name, salePer, sellCost, deliveryCharge, hotDeal, score, repleValue, orderValue));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return BestDealGoodsInfo;
	}
	
	
	public ArrayList<StoreMainpageGoodVo> GetHotdealinfo() {
		// 핫딜 상품 정보 ArrayList 받는 메서드
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<StoreMainpageGoodVo> HotDealGoodsInfo = new ArrayList<>();
		
		String sql = "SELECT pi.image,p.product_id ,p.name, p.sale_per, p.sell_cost, p.deli_char, p.hot_deal,p.score ,p.hits, ov.주문량\r\n" + 
				"FROM product p, product_image pi, replevalue_view rv, ordervalue_view ov \r\n" + 
				"WHERE p.product_id = pi.product_id AND p.product_id = rv.product_id AND p.product_id = ov.product_id AND pi.image_order =1 AND p.hot_deal =1";
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String Image = rs.getNString("IMAGE");
				int product_id = rs.getInt("product_id");
				String Name = rs.getNString("name");
				int SalePer = rs.getInt("sale_per");
				int SellCost = rs.getInt("sell_cost");
				int DeliChar = rs.getInt("deli_char");
				int HotDeal = rs.getInt("hot_deal");
				double Score = rs.getDouble("score");
				int reple = rs.getInt("hits");
				int order = rs.getInt("주문량");
				
				HotDealGoodsInfo.add(new StoreMainpageGoodVo(Image,product_id,Name,SalePer,SellCost,DeliChar,HotDeal,Score,reple,order));
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return HotDealGoodsInfo;
	}

	public ArrayList<StoreMainpageGoodVo> Getrecentdealinfo() {
		//신상 상품  정보 ArrayList 받는 메서드
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<StoreMainpageGoodVo> RecentdealinfoServlet = new ArrayList<>();
		
		
		String sql = "SELECT pi.image, p.product_id, p.name, p.sale_per, p.sell_cost, p.deli_char, p.hot_deal,p.score ,p.hits, ov.주문량 \r\n" + 
				"FROM product p, product_image pi, replevalue_view rv, ordervalue_view ov\r\n" + 
				"WHERE p.product_id = pi.product_id AND p.product_id = rv.product_id AND p.product_id = ov.product_id AND pi.image_order =1 AND p.update_date > sysdate-120";
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String Image = rs.getNString("IMAGE");
				int product_id = rs.getInt("product_id");
				String Name = rs.getNString("name");
				int SalePer = rs.getInt("sale_per");
				int SellCost = rs.getInt("sell_cost");
				int DeliChar = rs.getInt("deli_char");
				int HotDeal = rs.getInt("hot_deal");
				double Score = rs.getDouble("score");
				int reple = rs.getInt("hits");
				int order = rs.getInt("주문량");
				
				RecentdealinfoServlet.add(new StoreMainpageGoodVo(Image,product_id,Name,SalePer,SellCost,DeliChar,HotDeal,Score,reple,order));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return RecentdealinfoServlet;
	}
	public String setAmount(String productCost) {
		if(productCost == null) {
			return productCost;
		} else if(productCost.equals("")) {
			return productCost;
		}
		productCost = productCost.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");

		return productCost;
	}
	public ArrayList<AdvertisementGoodsVo> getAdvertisementGoodsList() {
		// 레시피 마이페이지 or 공지사항에서 우측 하단에 나타나는 상품베스트 5종을 가져오는 메서드
		// 베스트 상품을 담을 리스트 생성
		ArrayList<AdvertisementGoodsVo> advertisementGoodsList = new ArrayList<AdvertisementGoodsVo>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    t2.* " + 
					"FROM " + 
					"    ( SELECT " + 
					"        rownum rnum, " + 
					"        t1.* " + 
					"    FROM " + 
					"        ( SELECT " + 
					"            p.product_id product_id, " + 
					"            pi.image product_image, " + 
					"            p.name product_name, " + 
					"            p.sell_cost product_cost, " + 
					"            p.score product_score, " + 
					"            p.hits product_comment_qty " + 
					"        FROM " + 
					"            product p, " + 
					"            product_image pi, " + 
					"            replevalue_view rv, " + 
					"            ordervalue_view ov " + 
					"        WHERE " + 
					"            p.product_id = pi.product_id " + 
					"        AND " + 
					"            p.product_id = rv.product_id " + 
					"        AND " + 
					"            p.product_id = ov.product_id " + 
					"        AND " + 
					"            pi.image_order = 1 " + 
					"        AND " + 
					"            p.best = 1 ) t1 ) t2 " + 
					"WHERE " + 
					"    rnum >= 1 " + 
					"AND " + 
					"    rnum <= 5";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int productId = rs.getInt("product_id");
				String productImage = rs.getString("product_image");
				String productName = rs.getString("product_name");
				String productCost = setAmount(rs.getInt("product_cost") + "");
				int productScore = (int)rs.getDouble("product_score");
				int productCommentQty = rs.getInt("product_comment_qty");
				
				advertisementGoodsList.add(new AdvertisementGoodsVo(productId, productImage, productName, productCost, productScore, productCommentQty));
				System.out.println("상품ID : " + productId + " , 상품이미지 : " + productImage + " , 상품명 : " + productName 
								+ " , 상품가격 : " + productCost + " , 상품별점 : " + productScore + " , 상품댓글 : " + productCommentQty + "개");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return advertisementGoodsList;
	}
}
