package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.ConnectionRecipe;
import vo.RelatedGoodsVo;
import vo.StoreGoodsDetailsOption1Vo;
import vo.StoreGoodsDetailsVo;

public class StoreGoodsDetailsDao {
	private static StoreGoodsDetailsDao GDD = new StoreGoodsDetailsDao();
	private StoreGoodsDetailsDao() {};
	public static StoreGoodsDetailsDao getInstance() {return GDD;}
	
	public StoreGoodsDetailsVo getDetailInfoById(int product_id) {
		// 상품 ID로  상품 상세정보들을 가져오는 메서드
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StoreGoodsDetailsVo detailvo = null;
		
		String sql = "SELECT\r\n" + 
				"p.product_id,p.name,pi.image, p.simple_info, c.name as category, p.sale_per, p.first_cost, p.sell_cost, p.deli_char, p.reserves, p.score, p.video, p.info, p.related_info,s.business_name,s.email,s.report_num,s.workplace,s.phone,s.company_reg_num  \r\n" + 
				"FROM  \r\n" + 
				"product p, product_image pi ,seller s, category2 c\r\n" + 
				"WHERE\r\n" + 
				"p.product_id=? AND pi.product_id = p.product_id AND pi.image_order = 1 AND p.seller = s.u_id AND p.category2 = c.category2_code";
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, product_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int productId = rs.getInt("product_id");
				String name = rs.getNString("name");
				String img = rs.getNString("image");
				String simpleInfo = rs.getNString("simple_info");
				String category = rs.getNString("category");
				int salePer = rs.getInt("sale_per");
				int firstCost = rs.getInt("first_cost");
				int sellCost = rs.getInt("sell_cost");
				int deliChar = rs.getInt("deli_char");
				int reserves = (int)(rs.getDouble("reserves")*sellCost);
				double score = rs.getDouble("score"); 
				String video = rs.getNString("video");
				String goodsInfo = rs.getNString("info");
				String relateInfo = rs.getNString("related_info");
				String sName = rs.getNString("business_name");
				String sEmail = rs.getNString("email");
				String sReportNum = rs.getNString("report_num");
				String sWorkplace = rs.getNString("workplace");
				String sPhone = rs.getNString("phone");
				String sCompanyNum = rs.getNString("company_reg_num");
				
				
				detailvo = new StoreGoodsDetailsVo(productId, name, img, simpleInfo, category, salePer, firstCost, sellCost, deliChar, reserves, score, video, goodsInfo, relateInfo, sName, sEmail, sReportNum, sWorkplace, sPhone, sCompanyNum);
				
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
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		return detailvo;
	}
	public ArrayList<String> getImgById(int product_id) {
		// 상품 ID로 상품 이미지 가져오는 메서드
		ConnectionRecipe.connectionRecipe();
		ArrayList<String> goodsImgs = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT\r\n" + 
				"image\r\n" + 
				"FROM\r\n" + 
				"product_image \r\n" + 
				"WHERE\r\n" + 
				"product_id=?";
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, product_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String img = rs.getNString("image");
				goodsImgs.add(img);
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
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		return goodsImgs;
	}
	public ArrayList<StoreGoodsDetailsOption1Vo> getOptionById(int product_id) {
		// 상품 번호로 상품 옵션들 찾는 메서드
		ConnectionRecipe.connectionRecipe();
		ArrayList<StoreGoodsDetailsOption1Vo> goodsOptions = new ArrayList<StoreGoodsDetailsOption1Vo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT\r\n" + 
				"*\r\n" + 
				"FROM\r\n" + 
				"product_op1\r\n" + 
				"WHERE\r\n" + 
				"product_id=?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, product_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String op1Num  = rs.getNString("op1_num");
				String content = rs.getNString("content");
				int price = rs.getInt("price");
				int qty = rs.getInt("qty");
				goodsOptions.add(new StoreGoodsDetailsOption1Vo(op1Num, content, price, qty));
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
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		return goodsOptions;
	}
	public ArrayList<String> getDetailImgs(int product_id) {
		// 상품 번호로 상품 이미지들 찾는 메서드
		ConnectionRecipe.connectionRecipe();
		ArrayList<String> goodsDetailImgs = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT\r\n" + 
				"image\r\n" + 
				"FROM\r\n" + 
				"product_detail_image\r\n" + 
				"WHERE\r\n" + 
				"product_id=?";
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, product_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String detailImg = rs.getNString(1);
				goodsDetailImgs.add(detailImg);
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
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		return goodsDetailImgs;
	}
	public ArrayList<RelatedGoodsVo> getRelatedGoods(int product_id) {
		//상품 번호로 같은 카테고리 상품들 ArrayList로 받아내는 메서드
		ConnectionRecipe.connectionRecipe();
		ArrayList<RelatedGoodsVo> relatedGoods = new ArrayList<RelatedGoodsVo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT\r\n" + 
				"p.product_id , p.name , pi.image, p.sale_per, p.first_cost , p.score ,p.hits\r\n" + 
				"FROM\r\n" + 
				"product p, product_image pi\r\n" + 
				"WHERE\r\n" + 
				"p.category2 IN\r\n" + 
				"(SELECT \r\n" + 
				"category2\r\n" + 
				"FROM \r\n" + 
				"product\r\n" + 
				"WHERE\r\n" + 
				"product_id = ?) \r\n" + 
				"AND p.product_id = pi.product_id AND pi.image_order = 1";
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, product_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int productId = rs.getInt("product_id");
				String name = rs.getNString("name");
				String image = rs.getNString("image");
				int salePer = rs.getInt("sale_per");
				int firstCost = rs.getInt("first_cost");
				double score = rs.getDouble("score");
				int hits = rs.getInt("hits");
				relatedGoods.add(new RelatedGoodsVo(image, productId, name, salePer, firstCost, score, hits));
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
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		return relatedGoods;
	}
	public ArrayList<String> getGoodsTag(int product_id) {
		// 상품 번호로 상품 태크 찾는 메서드
		ConnectionRecipe.connectionRecipe();
		ArrayList<String> goodsTags = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT\r\n" + 
				"tag\r\n" + 
				"FROM\r\n" + 
				"product_tag\r\n" + 
				"WHERE\r\n" + 
				"product_id = ?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, product_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String tag = rs.getNString(1);
				goodsTags.add(tag);
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
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		return goodsTags;
	}
	
	
	
}
