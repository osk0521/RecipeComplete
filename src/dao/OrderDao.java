package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import common.ConnectionRecipe;
import vo.OrderAddressVo;
import vo.PageVo;
import vo.CartDetailVo;

public class OrderDao {
	private static OrderDao odao = new OrderDao();
	private OrderDao() {};
	public static OrderDao getorderDao() {
		return odao;
	}
	
	public ArrayList<CartDetailVo> getSelectOrderListFromCartDB(String loginId, String[] optionNumList) {
		//cart창에 선택된 상품 옵션들과 로그인 정보를 이용하여  상품 정보 담긴 ArrayList 담아내는 메소드
		ArrayList<CartDetailVo> selectOrderList = new ArrayList<CartDetailVo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println(Arrays.toString(optionNumList));
		
		String sql = "SELECT \r\n" + 
				"c.product_id , p.name ,c.op1_num ,op1.content , pi.image , op1.price, c.op1_qty,p.reserves ,p.deli_char \r\n" + 
				"FROM  \r\n" + 
				"cart c, product p , product_image pi , product_op1 op1\r\n" + 
				"WHERE\r\n" + 
				"c.u_id = ? AND op1.op1_num = ? AND c.product_id = p.product_id AND pi.product_id = c.product_id AND pi.image_order =1 AND c.op1_num = op1.op1_num";
		
	
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			for(int i=0;i<optionNumList.length;i++) {
				String option = optionNumList[i];
				System.out.println(option);
				pstmt.setNString(1, loginId);
				pstmt.setNString(2, option);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					 int productId = rs.getInt("product_id");
					 System.out.println(productId);
					 String name = rs.getNString("name");
					 String optionNum = rs.getNString("op1_num");
					 String optionContent = rs.getNString("content");
					 String image = rs.getNString("image");
					 int price = rs.getInt("price")*rs.getInt("op1_qty");
					 int qty = rs.getInt("op1_qty");
					 int reserves = (int)(price*rs.getDouble("reserves"));
					 int deliChar = rs.getInt("deli_char");
					 selectOrderList.add(new CartDetailVo(productId, name, optionNum, optionContent, image, price, qty, reserves, deliChar));
				};
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
		
		return selectOrderList;
	}
	public OrderAddressVo getDefaultAddressById(String loginId) {
		// 로그인 아이디로 기본 배송지 vo 받는 메소드
		OrderAddressVo defaultAddress = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT \r\n" + 
				"d.address_id,d.receiver , d.zip_code , d.address1 , d.address2, d.phone_number\r\n" + 
				"FROM\r\n" + 
				"destination d\r\n" + 
				"WHERE\r\n" + 
				"d.u_id = ? AND d.default_destination=1";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setNString(1, loginId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int  addressId = rs.getInt("address_id");
				String reciever = rs.getNString("receiver");
				String zipCode = rs.getNString("zip_code");
				String address = rs.getNString("address1");
				String detailAddress = rs.getNString("address2");
				String phoneNumber = rs.getNString("phone_number");
				
				defaultAddress = new OrderAddressVo(addressId ,reciever, zipCode, address, detailAddress, phoneNumber, 1);
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
		
		return defaultAddress;
	}
	public void insertNewAddress(String loginId, String receiver, String zipCode, String address, String detailAddress,
			String phoneNumber, int defaultAddress) {
		// 아이디, 받으실분, 우편주소, 주소, 상세주소, 핸드폰번호, 기본배송지 DB에 인서트 메서드
		
		System.out.println(loginId);
		System.out.println(zipCode);
		System.out.println(address);
		System.out.println(detailAddress);
		System.out.println(phoneNumber);
		System.out.println(defaultAddress);
		
		System.out.println(loginId);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO\r\n" + 
				"destination (address_id, u_id, receiver, zip_code, address1, address2, phone_number, default_destination)\r\n" + 
				"VALUES\r\n" + 
				"(\r\n" + 
				"(SELECT NVL(max(address_id),0)+1 FROM destination ),\r\n" + 
				"?,\r\n" + 
				"?,\r\n" + 
				"?,\r\n" + 
				"?,\r\n" + 
				"?,\r\n" + 
				"?,\r\n" + 
				"?\r\n" + 
				")";
	
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setNString(1, loginId);
			pstmt.setNString(2, receiver);
			pstmt.setNString(3, zipCode);
			pstmt.setNString(4, address);
			pstmt.setNString(5, detailAddress);
			pstmt.setNString(6, phoneNumber);
			pstmt.setInt(7, defaultAddress);
			rs = pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public void resetDefaultAddress(String loginId) {
		// 신규로 작성된 주소 기본배송지 설정시 기존 기본 배송지 초기화 실행 메서드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="UPDATE \r\n" + 
				"    destination\r\n" + 
				"SET\r\n" + 
				"    default_destination = 0\r\n" + 
				"WHERE\r\n" + 
				"    default_destination = 1 AND u_id = ?";
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setNString(1, loginId);
			rs = pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}
	public ArrayList<OrderAddressVo> getUserAddressList(String loginId) { // 유저아이디로 유저 주소 배열 받기 메서드, 페이지당 3개 주소를 보여준다
	  
	  ArrayList<OrderAddressVo> userAddressList = new ArrayList<OrderAddressVo>();
	  PreparedStatement pstmt = null; ResultSet rs = null; 
	  String sql = "SELECT * FROM destination WHERE u_id = ? ORDER BY default_destination desc"; 
	  try { 
		  pstmt = ConnectionRecipe.getConnection().prepareStatement(sql); 
		  pstmt.setNString(1,loginId); 
		  rs = pstmt.executeQuery(); 
		  while(rs.next()) { 
			  int addressId = rs.getInt("address_id"); 
			  String receiver = rs.getString("receiver"); 
			  String zipCode = rs.getNString("zip_code"); 
			  String address = rs.getNString("address1"); 
			  String detailAddress = rs.getNString("address2");
			  String phoneNumber = rs.getNString("phone_number"); 
			  int defaultAddress = rs.getInt("default_destination"); 
			  userAddressList.add(new OrderAddressVo(addressId,receiver, zipCode, address, detailAddress,phoneNumber, defaultAddress)); 
			  } 
		  }catch(SQLException e) {
	  e.printStackTrace(); }
	  catch(NullPointerException e) 
	  { e.printStackTrace();}
	  finally { try { 
		 if(pstmt!=null) pstmt.close();
		 if(rs!=null) rs.close(); 
		  ConnectionRecipe.disConnectionRecipe(); } 
	  catch (SQLException e) {
	  e.printStackTrace(); } }
	  
	  return userAddressList; 
	  
	}

	public void updateExistingAddress(int proudctId, String loginId, String receiver, String zipCode, String address,
			String detailAddress, String phoneNumber, int defaultAddress) {
		// 기존 주소 업데이트 메서드
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="UPDATE \r\n" + 
				"destination\r\n" + 
				"SET\r\n" + 
				"default_destination = ?,\r\n" + 
				"receiver =?,\r\n" + 
				"zip_code=?,\r\n" + 
				"address1=?,\r\n" + 
				"address2=?,\r\n" + 
				"phone_number=?\r\n" + 
				"WHERE\r\n" + 
				"address_id = ? AND u_id = ?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, defaultAddress);
			pstmt.setNString(2, receiver);
			pstmt.setNString(3, zipCode);
			pstmt.setNString(4, address);
			pstmt.setNString(5, detailAddress);
			pstmt.setNString(6,phoneNumber);
			pstmt.setInt(7,proudctId);
			pstmt.setNString(8, loginId);
			rs = pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void deleteAddress(int addressId) {
		// 주소 아이디를 이용하여 주소 삭제 메서드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="DELETE FROM destination WHERE address_id = ?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, addressId);
			rs = pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public int getreservesById(String loginId) {
		// 사용자 적립금 찾는 메서드
		int reserves = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT \r\n" + 
				"reserves\r\n" + 
				"FROM \r\n" + 
				"member\r\n" + 
				"WHERE\r\n" + 
				"u_id = ?";
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setNString(1, loginId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reserves = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reserves;
	}
	public void insertOrderDetailDB(int orderId, String loginId, String receiver, String zipcode, String address,
			String detailAddress, String phoneNumber, String order, String orderPhoneNumber, String email, String memo,
			int reserves) {
		// 주문서 작성된 정보들 디비에 입력하는 메서드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO\r\n" + 
				"order_detail\r\n" + 
				"(order_id,U_ID,ORDER_DATE,PAYDATE,PAYBY,BANK,PAY_NAME,RESERVER,RECEIVER,RECEIVER_ZIP_CODE,RECEIVER_ADRESS1,RECEIVER_ADRESS2,RECEIVER_PHONE,ORDERER,ORDERER_CALL,ORDERER_PHONE,ORDERER_EMAIL,NOTE,STATUS_ID)\r\n" + 
				"VALUES\r\n" + 
				"(?,?,SYSDATE + 1,SYSDATE+4,0,6,'',0,?,?,?,?,?,?,'',?,?,?,1)";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, orderId);
			pstmt.setNString(2, loginId);
			pstmt.setNString(3, receiver);
			pstmt.setNString(4, zipcode);
			pstmt.setNString(5, address);
			pstmt.setNString(6, detailAddress);
			pstmt.setNString(7, phoneNumber);
			pstmt.setNString(8, order);
			pstmt.setNString(9, orderPhoneNumber);
			pstmt.setNString(10, email);
			pstmt.setNString(11, memo);
			rs = pstmt.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void insertOrderList(int orderId, String loginId, int productId, String option, int qty) {
		// 주문 정보 디비에 입력하는 메서드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO\r\n" + 
				"orderlist\r\n" + 
				"(ORDER_ID,U_ID,PRODUCT_ID,OP1_NUM,OP1_QTY,OP2_NUM,OP2_QTY,STATUS_ID,STATUS_DATE)\r\n" + 
				"VALUES\r\n" + 
				"(?,?,?,?,?,'','',1,SYSDATE + 1)";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, orderId);
			pstmt.setNString(2, loginId);
			pstmt.setInt(3,productId);
			pstmt.setNString(4, option);
			pstmt.setInt(5, qty);
			rs = pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public void deleteCartList(String loginId, String option) {
		// 구매 완료 후 장바구니 정보 삭제하는 메서드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM \r\n" + 
				"cart\r\n" + 
				"WHERE\r\n" + 
				"u_id=? AND op1_num = ?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setNString(1, loginId);
			pstmt.setNString(2, option);
			rs = pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public int selectOptionSum(int orderId) {
		// 주문번호를 파라미터로 주문한 옵션들의 DB상 가격들을 알아내는 메서드
		int result = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT\r\n" + 
					"sum(op1.price*ol.op1_qty) as amount\r\n" + 
					"FROM\r\n" + 
					"orderlist ol , product_op1 op1\r\n" + 
					"WHERE\r\n" + 
					"ol.order_id = ? AND\r\n" + 
					"op1.op1_num = ol.op1_num ";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, orderId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public int selectProduct(int i) {
		// 상품번호를 파라미터로 DB상 배송비를 알아보는 메서드
		int result = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT\r\n" + 
					"p.deli_char\r\n" + 
					"FROM\r\n" + 
					"product p\r\n" + 
					"where\r\n" + 
					"p.product_id = ?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, i);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public boolean compareTotalFromDB(int total, int i) {
		// 화면에서 받은 총 가격과 DB상의 가격을 비교하는 메서드, 다르면 0, 맞으면 1
		boolean check = false;
		if(total==i)
			check = true;
		return check;
	}
	public void deleteOrderlist(int orderId) {
		// 유저에게 받은 가격과 DB상 가격 비교 결과 달라 해당 구매id 정보 orderList DB delete 실시하는 메서드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM \r\n" + 
					"orderlist\r\n" + 
					"WHERE\r\n" + 
					"order_id = ?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, orderId);
			rs = pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	 
	

}
