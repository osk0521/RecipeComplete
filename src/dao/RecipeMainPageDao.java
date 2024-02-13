package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.ConnectionRecipe;
import vo.RecipeMainPageShoppingVo;
import vo.RecipeMagazineItemVo;
import vo.RecipeMainPageVo;
import vo.RecipeRankVo;
import vo.RecipeUserRankVo;
import vo.RecipeUserVo;

public class RecipeMainPageDao {
	public ArrayList<RecipeMainPageVo> getMainImgForSlide() {
		ArrayList<RecipeMainPageVo> mainImgList = new ArrayList<RecipeMainPageVo>();
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT IMAGE FROM MAINPAGE_IMAGE ORDER BY IMAGE_num";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String imgForSlide = rs.getString("IMAGE");
				mainImgList.add(new RecipeMainPageVo(imgForSlide));
			}
			rs.close();
			pstmt.close();
			ConnectionRecipe.getConnection().close();
		} catch(SQLException e) {
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
		return mainImgList;
	}
	public ArrayList<RecipeUserVo> getUserInfo() {
		ArrayList<RecipeUserVo> mainUserList = new ArrayList<RecipeUserVo>();
		ConnectionRecipe.connectionRecipe();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT\r\n" + 
					"  t2.*\r\n" + 
					"FROM\r\n" + 
					"  (\r\n" + 
					"    SELECT\r\n" + 
					"      rownum rnum, t1.*\r\n" + 
					"    FROM\r\n" + 
					"      (\r\n" + 
					"        SELECT m.NICKNAME, m.PROFILE_IMAGE\r\n" + 
					"        FROM Member m join MEMBER_FOLLOW mf on (m.u_id = mf.U_id)\r\n" + 
					"        group by m.NICKNAME, m.PROFILE_IMAGE, rownum\r\n" + 
					"        ORDER BY\r\n" + 
					"          count(mf.U_ID_FOLLOWTARGET) DESC,  m.NICKNAME\r\n" + 
					"      ) t \r\n" + 
					"    ) t2\r\n" + 
					"WHERE ( t2.rnum > 0 AND t2.rnum <= 30 )";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String nickName = rs.getString("NICKNAME");
				String profileImg = rs.getString("PROFILE_IMAGE");
				mainUserList.add(new RecipeUserVo(nickName, profileImg));
			}
			rs.close();
			pstmt.close();
			ConnectionRecipe.disConnectionRecipe();
		} catch(SQLException e) {
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
		return mainUserList;
	}
	String sqlForBest = "SELECT t2.* FROM\r\n" + 
			" (SELECT rownum rnum, t.* FROM \r\n" + 
			" (SELECT r.THUMBNAIL AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, m.nickname AS writer, m.PROFILE_IMAGE as PROFILE, count(rhd.hit_date) as HITCOUNT\r\n" + 
			" FROM RECIPE r join RECIPE_HIT_DATE rhd on (r.recipe_id = rhd.recipe_id)\r\n" + 
			" join MEMBER m on (r.u_id = m.u_id) where ( r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1)\r\n" + 
			" group by r.recipe_id, r.THUMBNAIL, r.title, r.star, m.nickname, m.PROFILE_IMAGE ORDER BY count(rhd.hit_date) DESC, r.star DESC) t)t2\r\n" + 
			" WHERE (t2.rnum >= 1 AND t2.rnum<= 10)";
	public ArrayList<RecipeRankVo> getTop10Recipe() {
		ArrayList<RecipeRankVo> top10RecipeList = new ArrayList<RecipeRankVo>();
		ConnectionRecipe.connectionRecipe();
		try {
			String sqlForRecipe =
					"SELECT t2.* \r\n" + 
					"FROM (SELECT rownum rnum, t.* \r\n" + 
					"FROM (SELECT r.THUMBNAIL AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, m.U_ID as userID, m.nickname AS writer, m.PROFILE_IMAGE as PROFILE, count(rhd.hit_date) as HITCOUNT \r\n" + 
					"FROM RECIPE r join RECIPE_HIT_DATE rhd on (r.recipe_id = rhd.recipe_id) \r\n" + 
					"join MEMBER m on (r.u_id = m.u_id) \r\n" + 
					"where (r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1)\r\n" + 
					"group by r.recipe_id, r.THUMBNAIL, r.title, r.star, m.nickname, m.PROFILE_IMAGE, m.U_ID \r\n" + 
					"ORDER BY count(rhd.hit_date) DESC, r.star DESC) t)t2 \r\n" + 
					"WHERE (t2.rnum >= 1 AND t2.rnum<= 10)";
			PreparedStatement pstmt = ConnectionRecipe.getConnection().prepareStatement(sqlForRecipe);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int recipeID = rs.getInt("RECIPE_ID");
				String thumbnail = rs.getString("THUMBNAIL");
				String title = rs.getString("TITLE");
				int hits = rs.getInt("HITCOUNT");
				String nickname = rs.getString("writer");
				String profileImage = rs.getString("PROFILE");
				String userID = rs.getString("PROFILE");
				int commentCnt = 0;
				
					try {
						String commentSQL =  "SELECT count(COMMENT_ID) AS comment_cnt FROM RECIPE_COMMENTS where ( recipe_ID = ? AND COMMENT_ORDER = 1 AND DELETEDACCOUNT = 0 AND DELETEDCOMMENT = 0)";
						PreparedStatement pstmt2 = ConnectionRecipe.getConnection().prepareStatement(commentSQL);
						pstmt2.setInt(1, recipeID);
						ResultSet rs2 = pstmt2.executeQuery();
						if(rs2.next()) {
							commentCnt = rs2.getInt("comment_cnt");
						}
						rs2.close();
						pstmt2.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
					
				top10RecipeList.add(new RecipeRankVo(recipeID, thumbnail, title, hits, commentCnt, userID, nickname, profileImage));
				}
					rs.close();
					pstmt.close();
					ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		return top10RecipeList;
	}
	public ArrayList<RecipeUserRankVo> getTop30User() {
		ArrayList<RecipeUserRankVo> top30UserList = new ArrayList<RecipeUserRankVo>();
		ConnectionRecipe.connectionRecipe();
		try {
			String sqlForUser =
					"SELECT t2.* FROM \r\n" + 
					"(SELECT rownum rnum, t.* FROM\r\n" + 
					"(SELECT m.U_ID as UserID, m.PROFILE_IMAGE AS PROFILE, m.NICKNAME AS NICKNAME\r\n" + 
					"FROM MEMBER m\r\n" + 
					"join MEMBER_FOLLOW mf on (m.U_ID = mf.U_ID)\r\n" + 
					"group by m.PROFILE_IMAGE, m.NICKNAME, m.U_ID\r\n" + 
					"order by count(mf.u_id_followtarget) desc ) t)t2\r\n" + 
					"WHERE (t2.rnum >= 1 AND t2.rnum <= 30)";
			PreparedStatement pstmt = ConnectionRecipe.getConnection().prepareStatement(sqlForUser);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 
				String userID = rs.getString("UserID");
				String nickname = rs.getString("NICKNAME");
				String profile = rs.getString("PROFILE");
				top30UserList.add(new RecipeUserRankVo(userID, nickname, profile));
			}
			rs.close();
			pstmt.close();
			ConnectionRecipe.disConnectionRecipe();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return top30UserList;
	}
	
	public ArrayList<RecipeMagazineItemVo> get20RecipeListByMID(int magazineID) {
		ArrayList<RecipeMagazineItemVo> magazineItemList = new ArrayList<RecipeMagazineItemVo>();

		ConnectionRecipe.connectionRecipe();
		String sql="SELECT t2.* FROM\r\n" + 
				"( SELECT rownum rnum, t.* FROM(\r\n" + 
				"SELECT r.THUMBNAIL AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, count(rhd.hit_date) as HITCOUNT\r\n" + 
				"FROM RECIPE r join RECIPE_HIT_DATE rhd on (r.recipe_id = rhd.recipe_id)\r\n" + 
				"where (r.magazine_id = ? AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1)\r\n" + 
				"group by r.recipe_id, r.THUMBNAIL, r.title\r\n" + 
				"ORDER BY count(rhd.hit_date) DESC)\r\n" + 
				"t)t2 WHERE (t2.rnum >= 1 AND t2.rnum<= 20)";
		try {
			PreparedStatement pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, magazineID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String title = rs.getString("TITLE");
				String thumbnail = rs.getString("THUMBNAIL");
				int recipeID = rs.getInt("recipe_id");
				magazineItemList.add(new RecipeMagazineItemVo(title, thumbnail, recipeID));
			}
			rs.close();
			pstmt.close();
			ConnectionRecipe.disConnectionRecipe();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return magazineItemList;
	}
	public ArrayList<RecipeMainPageShoppingVo> getshoppingBest() {
		ArrayList<RecipeMainPageShoppingVo> shoppingBest = new ArrayList<RecipeMainPageShoppingVo>();

		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="SELECT pi.product_id, pi.image, p.name,p.sell_cost, nvl(p.deli_char,0) as deli_char, count(p.hits) as hitcnt\r\n" + 
				"FROM product p, product_image pi\r\n" + 
				"WHERE p.product_id = pi.product_id AND pi.image_order =1 AND p.best =1\r\n" + 
				"group by pi.product_id, pi.image, p.name, p.sell_cost, p.deli_char";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String thumbnail = rs.getString("image");
				String title = rs.getString("name");
				int price = rs.getInt("sell_cost");
				int deliveryFee = rs.getInt("deli_char");
				int hitcnt = rs.getInt("hitcnt");
				int productId = rs.getInt("product_id");
				shoppingBest.add(new RecipeMainPageShoppingVo(productId, title, thumbnail, price, deliveryFee, hitcnt));
			}
			rs.close();
			pstmt.close();
			ConnectionRecipe.disConnectionRecipe();
		} catch(SQLException e) {
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
		return shoppingBest;
	}
	
}
