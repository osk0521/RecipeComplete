package dao;

import java.sql.*;

import common.ConnectionRecipe;

public class RecipeCommentDao extends ConnectionRecipe {
	public static int getNewCommentID() {
		int commentID = 0;
		ConnectionRecipe.connectionRecipe();
		String newRecipeIDSQL = "SELECT SEQ_RECIPE_COMMENTS_CNO.nextval FROM DUAL";
		try {
			PreparedStatement pstmt = ConnectionRecipe.getConnection().prepareStatement(newRecipeIDSQL);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				commentID = rs.getInt("nextval");
			}
			rs.close();
			pstmt.close();
			ConnectionRecipe.disConnectionRecipe();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return commentID;
	}
	public static boolean insertRecipeComment(int recipe_id, String u_id, int comment_group, int comment_order, String content, String image) {
		// 쿼리문 정상작동 여부(정상 : true, 비정상 : false)
		boolean complete = false;
		int newCommentID = getNewCommentID();
		// 쿼리문 실행
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "INSERT INTO Recipe_Comments"
					+ "(COMMENT_ID, RECIPE_ID , U_ID, COMMENT_GROUP, COMMENT_ORDER, WRITEDATE, CONTENT, IMAGE, STAR, DELETEDACCOUNT, DELETEDCOMMENT)"
					+ "VALUES (?, ?, ?, ?, ?, to_date(sysdate, 'YYYY-MM-DD HH24:MI'), ?, ?, null, 0, 0)";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, newCommentID);
			pstmt.setInt(2, recipe_id);
			pstmt.setString(3, u_id);
			pstmt.setInt(4, comment_group);
			pstmt.setInt(5, comment_order);
			pstmt.setString(6, content);
			pstmt.setString(7, image);
			pstmt.executeUpdate();
			complete = true;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		return complete;
	}
}
