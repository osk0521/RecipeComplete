package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;
import common.ConnectionRecipe;
import vo.RecipeIngredientVo;
import vo.RecipeRefrigeratorVo;
import vo.RecipeRefrigeratorImgVo;

public class RecipeRefrigeratorDao {
	public String getIngredeNameByID(int ingredeID) {
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String insertRecipeStepstSQL =	"SELECT name FROM INGREDIENT_ID where ingredi_id = ?";
		String ingredeName = "";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(insertRecipeStepstSQL);
			pstmt.setInt(1, ingredeID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ingredeName = rs.getString("name");
			}
			pstmt.close(); 
			ConnectionRecipe.disConnectionRecipe();
		} catch (SQLException e) {
			System.out.println("[SQL Error : " + e.getMessage() + "]");
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
		return ingredeName;
	}
	public ArrayList<RecipeRefrigeratorImgVo> getIngredeImageByID(String userID) {
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RecipeRefrigeratorImgVo> retulst = new ArrayList<RecipeRefrigeratorImgVo>(); 
		String ingredeImage = "SELECT ii.name, ii.ingredi_id, ii.image FROM INGREDIENT_ID ii JOIN REFRIGERATOR re on(ii.ingredi_id = re.ingredi_id) WHERE re.U_ID = ?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(ingredeImage);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				retulst.add(new RecipeRefrigeratorImgVo (rs.getString("NAME"), rs.getString("IMAGE")));
			}
			pstmt.close(); 
			ConnectionRecipe.disConnectionRecipe();
			System.out.println(retulst);
		} catch (SQLException e) {
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
		return retulst;
	}
	public boolean deleteRefrigeratorSQL(String userID) {
		boolean ok = false;
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		String deleteRecipeSQL = "DELETE refrigerator WHERE U_Id = ?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(deleteRecipeSQL);
			pstmt.setString(1, userID);
			pstmt.executeUpdate();
			pstmt.close(); 
			ConnectionRecipe.disConnectionRecipe();
			ok = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;
	}
	public boolean insertRefrigerator(String loginId, int ingrediID) {
		boolean ok = false;
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		String insertRecipeSQL = "INSERT INTO REFRIGERATOR(U_ID, INGREDI_ID) VALUES(?, ?)";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(insertRecipeSQL);
			pstmt.setString(1, loginId);
			pstmt.setInt(2, ingrediID);
			pstmt.executeUpdate();
			pstmt.close(); 
			ConnectionRecipe.disConnectionRecipe();
			ok = true;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;
	}
	public ArrayList<RecipeRefrigeratorVo> searchRecipeRefrigerator(String userID) {
		ArrayList<RecipeRefrigeratorVo> result = new ArrayList<RecipeRefrigeratorVo>();
		ConnectionRecipe.connectionRecipe();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL1 = "WITH HaveIngrediList AS (\r\n" + 
				"          SELECT DISTINCT\r\n" + 
				"            r.thumbnail,\r\n" + 
				"            r.title,\r\n" + 
				"            ri.recipe_id,\r\n" + 
				"            RTRIM(\r\n" + 
				"              XMLAGG(XMLELEMENT(E, ri.ingredi_id || ', ')).EXTRACT('//text()'),\r\n" + 
				"              ', '\r\n" + 
				"            ) AS have_ingredi_list,\r\n" + 
				"            COUNT(ri.ingredi_id) AS ingredi_count\r\n" + 
				"          FROM\r\n" + 
				"            recipe r\r\n" + 
				"            JOIN recipe_ingredient ri ON (r.recipe_id = ri.recipe_id)\r\n" + 
				"          WHERE\r\n" + 
				"            (\r\n" + 
				"              ri.ingredi_id IN (\r\n" + 
				"                SELECT\r\n" + 
				"                  ingredi_id\r\n" + 
				"                FROM\r\n" + 
				"                  refrigerator\r\n" + 
				"                WHERE\r\n" + 
				"                  U_ID = ?\r\n" + 
				"              )\r\n" + 
				"              AND r.deletedpost != 1\r\n" + 
				"              AND r.deletedaccount != 1\r\n" + 
				"              AND r.complete = 1\r\n" + 
				"              AND r.magazine_id != 1 \r\n" + 
				"            )\r\n" + 
				"          GROUP BY\r\n" + 
				"            r.thumbnail,\r\n" + 
				"            r.title,\r\n" + 
				"            ri.recipe_id\r\n" + 
				"        ),\r\n" + 
				"        NotHaveIngrediList AS (\r\n" + 
				"          SELECT DISTINCT\r\n" + 
				"            r.thumbnail,\r\n" + 
				"            r.title,\r\n" + 
				"            ri.recipe_id,\r\n" + 
				"            RTRIM(\r\n" + 
				"              XMLAGG(XMLELEMENT(E, ri.ingredi_id || ', ')).EXTRACT('//text()'),\r\n" + 
				"              ', '\r\n" + 
				"            ) AS not_have_ingredi_list,\r\n" + 
				"            COUNT(ri.ingredi_id) AS ingredi_count\r\n" + 
				"          FROM\r\n" + 
				"            recipe r\r\n" + 
				"            JOIN recipe_ingredient ri ON r.recipe_id = ri.recipe_id\r\n" + 
				"          WHERE\r\n" + 
				"            (\r\n" + 
				"              ri.ingredi_id NOT IN (\r\n" + 
				"                SELECT\r\n" + 
				"                  ingredi_id\r\n" + 
				"                FROM\r\n" + 
				"                  refrigerator\r\n" + 
				"                WHERE\r\n" + 
				"                  U_ID = ?\r\n" + 
				"              )\r\n" + 
				"              AND r.deletedpost != 1\r\n" + 
				"              AND r.deletedaccount != 1\r\n" + 
				"              AND r.complete = 1\r\n" + 
				"              AND r.magazine_id != 1\r\n" + 
				"            )\r\n" + 
				"          GROUP BY\r\n" + 
				"            r.thumbnail,\r\n" + 
				"            r.title,\r\n" + 
				"            ri.recipe_id\r\n" + 
				"        )\r\n" + 
				"        SELECT\r\n" + 
				"          HIL.thumbnail,\r\n" + 
				"          HIL.title,\r\n" + 
				"          HIL.recipe_id,\r\n" + 
				"          HIL.have_ingredi_list,\r\n" + 
				"          NIL.not_have_ingredi_list\r\n" + 
				"        FROM\r\n" + 
				"          HaveIngrediList HIL\r\n" + 
				"          JOIN NotHaveIngrediList NIL ON HIL.recipe_id = NIL.recipe_id\r\n" + 
				"        ORDER BY\r\n" + 
				"          HIL.ingredi_count DESC,\r\n" + 
				"          NIL.ingredi_count ASC";//페이지네이션 없음
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(SQL1);
			pstmt.setString(1, userID);
			pstmt.setString(2, userID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String thumbnail = rs.getString("thumbnail");
				String title = rs.getString("title");
				int recipeID = rs.getInt("recipe_id");
				String[] haveIngrediIDlistInString = (rs.getString("have_ingredi_list")).split(", ");
				String[] notHaveIngrediIDlistInString = (rs.getString("not_have_ingredi_list")).split(", ");
				TreeSet<Integer> haveIngrediIDtree = new TreeSet<>(); //TreeSet으로 중복 제거
				TreeSet<Integer> notHaveIngrediIDtree = new TreeSet<>();
				
				for(String id : haveIngrediIDlistInString) {
					haveIngrediIDtree.add(Integer.parseInt(id));
				}
				for(String id : notHaveIngrediIDlistInString) {
					notHaveIngrediIDtree.add(Integer.parseInt(id));
				}
				ArrayList<String> arrForHave = new ArrayList<String>();
				ArrayList<String> arrForNot = new ArrayList<String>();
				ArrayList<Integer> haveIngrediIDlist = new ArrayList<Integer>(haveIngrediIDtree);
				ArrayList<Integer> notHaveIngrediIDlist = new ArrayList<Integer>(notHaveIngrediIDtree);
				for(int i : haveIngrediIDtree) {
					arrForHave.add(getIngredeNameByID(i));
				}
				for(int i : notHaveIngrediIDtree) {
					arrForNot.add(getIngredeNameByID(i));
				}
				result.add(new RecipeRefrigeratorVo(recipeID, thumbnail, title, arrForHave, haveIngrediIDlist, arrForNot, notHaveIngrediIDlist));
			}
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
		return result;
	}
	public ArrayList<RecipeIngredientVo> getIngredientInfo() {
		ArrayList<RecipeIngredientVo> ingredientInfo = new ArrayList<RecipeIngredientVo>();
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String SQL ="SELECT * FROM ingredient_id ORDER BY ingredi_id ASC";	
				pstmt = ConnectionRecipe.getConnection().prepareStatement(SQL);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					int ingredeID = rs.getInt("ingredi_id");
					String ingredeName = rs.getString("name");
					ingredientInfo.add(new RecipeIngredientVo(ingredeID, ingredeName));
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
		return ingredientInfo;	 
	}
}
