package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.ConnectionRecipe;
import vo.StoreGoodsDetailsOption1Vo;
import vo.CartDetailVo;

public class CartDao {
	 static CartDao odao = new CartDao();
	 CartDao() {}
	public static CartDao getCartdao() {
		return odao;
	}
	public void insertCartDB(String logInId, int productId, ArrayList<StoreGoodsDetailsOption1Vo> cartList) {
		// 아이디, 상품번호, cartList ArrayList(옵션명, 수량) 파라미터로 받아서 cart table에 INSERT문 실행하는 메서드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "MERGE INTO cart\r\n" + 
				"USING dual\r\n" + 
				"ON (u_id = ? AND op1_num = ?)\r\n" + 
				"WHEN MATCHED THEN\r\n" + 
				"UPDATE SET op1_qty = op1_qty+?\r\n" + 
				"WHEN NOT MATCHED THEN\r\n" + 
				"INSERT(u_id,product_id,op1_num,op1_qty,op2_num,op2_qty,order_avail)\r\n" + 
				"VALUES(?, ?,?,?,null,null,1)";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			for(StoreGoodsDetailsOption1Vo vo : cartList) { // ArrayList에 있는 vo값들 for문으로 search, productID와 logInId는 INSERT문에 공통으로 들어감 
				String option = vo.getOp1Num();
				int qty = vo.getQty();
				
				pstmt.setString(1, logInId);
				pstmt.setString(2, option);
				
				pstmt.setInt(3, qty);
				
				pstmt.setString(4, logInId);
				pstmt.setInt(5, productId);
				pstmt.setNString(6, option);
				pstmt.setInt(7, qty);
				
				rs = pstmt.executeQuery();
			};
				
		
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
	}
	public ArrayList<CartDetailVo> getCartListById(String loginId) {
		//로그인 아이디 파라미터로 받아서 cartDB SELECT문 실행하는 메서드
		
		ArrayList<CartDetailVo> cartList = new ArrayList<CartDetailVo>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT\r\n" + 
				"c.product_id , p.name ,c.op1_num ,op1.content , pi.image , op1.price, c.op1_qty,p.reserves ,p.deli_char\r\n" + 
				"FROM \r\n" + 
				"cart c, product p , product_image pi , product_op1 op1\r\n" + 
				"WHERE\r\n" + 
				"c.u_id = ? AND c.product_id = p.product_id AND pi.product_id = c.product_id AND pi.image_order =1 AND c.op1_num = op1.op1_num";
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, loginId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				 int productId = rs.getInt("product_id");
				 String name = rs.getNString("name");
				 String optionNum = rs.getNString("op1_num");
				 String optionContent = rs.getNString("content");
				 String image = rs.getNString("image");
				 int price = rs.getInt("price")*rs.getInt("op1_qty");
				 int qty = rs.getInt("op1_qty");
				 int reserves = (int)(rs.getDouble("reserves")*price);
				 int deliChar = rs.getInt("deli_char");
				 
				 cartList.add(new CartDetailVo(productId, name, optionNum, optionContent, image, price, qty, reserves, deliChar));
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
		
		return cartList;
	}
	public void deleteCartDB(String[] cartDeleteList, String loginId) {
		// cartDeleteList(옵션명 들어있는 배열), 로그인 아이디 파라미터로 cartDB DELETE 실행하는 메서드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="DELETE FROM cart\r\n" + 
				"WHERE\r\n" + 
				"u_id = ? AND op1_num = ?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			for(String option : cartDeleteList) { // for문으로 배열에 저장된 option값들 search, id는 공통으로 들어감
				pstmt.setString(1, loginId);
				pstmt.setString(2, option);
				rs = pstmt.executeQuery();
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
	}
	public void updateCartDB(String loginId, String optionQty, String optionNum, int productId) {
		// 로그인 아이디, 옵션 수량, 옵션 명, 상품 번호 파라미터로 받아 카트 정보 수정하는 메서드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "MERGE INTO cart\r\n" + 
				"USING dual\r\n" + 
				"ON (u_id = ? AND op1_num = ?)\r\n" + 
				"WHEN MATCHED THEN\r\n" + 
				"UPDATE SET op1_qty = op1_qty + ?\r\n" + 
				"WHEN NOT MATCHED THEN\r\n" + 
				"INSERT(u_id,product_id,op1_num,op1_qty,op2_num,op2_qty,order_avail)\r\n" + 
				"VALUES(?, ?,?,?,null,null,1)";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setNString(1, loginId);
			pstmt.setNString(2, optionNum);
			pstmt.setNString(3, optionQty);
			pstmt.setNString(4, loginId);
			pstmt.setInt(5, productId);
			pstmt.setNString(6, optionNum);
			pstmt.setNString(7, optionQty);
			rs = pstmt.executeQuery();
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
	}
	
}
