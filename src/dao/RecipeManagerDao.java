package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.ConnectionRecipe;
import vo.RecipeManagerCategoryVo;
import vo.RecipeManagerUserVo;
import vo.RecipeManagerMagazineVo;

public class RecipeManagerDao {
	public static ArrayList<RecipeManagerUserVo> getUserList() {
		ArrayList<RecipeManagerUserVo> userListForManager = new ArrayList<RecipeManagerUserVo>();
		String sql = "SELECT \r\n" + 
				"    U_ID,\r\n" + 
				"    NICKNAME,\r\n" + 
				"    BIRTH,\r\n" + 
				"    PROFILE_IMAGE,\r\n" + 
				"    phone,\r\n" + 
				"    email,\r\n" + 
				"    ADMIN,\r\n" + 
				"    SELLER,\r\n" + 
				"        CASE \r\n" + 
				"        WHEN (TRUNC(MONTHS_BETWEEN(SYSDATE, BIRTH) / 12) >= 19)\r\n" + 
				"        THEN '성인' ELSE '미성년자'\r\n" + 
				"        END AS 성인여부\r\n" + 
				"    FROM MEMBER";
		try {
			PreparedStatement pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String userID = rs.getString("U_ID");
				String nickname = rs.getString("NICKNAME");
				int phoneNum = rs.getInt("phone");
				String email = rs.getString("email");
				String profileIMG = rs.getString("PROFILE_IMAGE");
				int manager = rs.getInt("ADMIN");//매니저
				int seller = rs.getInt("SELLER");//판매자
				String Adult = rs.getString("성인여부");
				boolean isAdult = false;
				boolean isManager = false;
				boolean isSeller = false;
				if(Adult.equals("성인")) {
					isAdult = true;
				}
				if(manager == 1) {
					isManager = true;
				}
				if(seller == 1) {
					isSeller = true;
				}
				userListForManager.add(new RecipeManagerUserVo(userID, nickname, profileIMG, phoneNum, email, isSeller, isManager, isAdult));
			}
			rs.close();
			pstmt.close();
			ConnectionRecipe.disConnectionRecipe();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return userListForManager;
	}
	
	public static ArrayList<RecipeManagerCategoryVo> getManagerCategory(String table) {
		String name;
		String image;
		int categoryId;
		ArrayList<RecipeManagerCategoryVo> getCategoryList = new ArrayList<RecipeManagerCategoryVo>();
		try {
			String commentSQL =  "SELECT * FROM "+table+" ORDER BY "+table+"_id";
			ConnectionRecipe.connectionRecipe();
			PreparedStatement pstmt = ConnectionRecipe.getConnection().prepareStatement(commentSQL);
			ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					categoryId = rs.getInt(table+"_id");
					name = rs.getString("name");
					image = rs.getString("image");
					
					getCategoryList.add(new RecipeManagerCategoryVo(categoryId, name, image));
				}
				rs.close();
				pstmt.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		return getCategoryList;
	}
	
	public void insertNewCategory(String table, ArrayList<RecipeManagerCategoryVo> newData) {
		try {
			String insertNewCategory =  "INSERT INTO ? ("+table+"_id, name, image) VALUES (SEQ_"+table+"_ID.nextval, '?', '?')";
			ConnectionRecipe.connectionRecipe();
			PreparedStatement pstmt = ConnectionRecipe.getConnection().prepareStatement(insertNewCategory);
			pstmt.setString(1, table);
			pstmt.setString(2, (newData.get(0).toString()));
			pstmt.setString(3, (newData.get(1).toString()));
			ResultSet rs = pstmt.executeQuery();
				rs.close();
				pstmt.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
	}
}
