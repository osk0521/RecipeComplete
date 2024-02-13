package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import common.ConnectionRecipe;
import dto.RecentRecipeDto;
import vo.RecipeLvVo;
import vo.RecipeCommentVo;
import vo.RecipeDetailVo;
import vo.RecipeIngrediVo;
import vo.RecipeManagerCategoryVo;
import vo.RecipeProcessVo;
import vo.RecipeVo;

public class RecipeBoardDao {

	
	private static int getCommentCnt(int recipeID) {
		ConnectionRecipe.connectionRecipe();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int commentCnt=0;
		try {
			String commentSQL = "SELECT nvl(count(COMMENT_ID),0) AS comment_cnt FROM RECIPE_COMMENTS where ( recipe_ID = ? AND COMMENT_ORDER = 1 AND DELETEDACCOUNT = 0 AND DELETEDCOMMENT = 0)";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(commentSQL);
			pstmt.setInt(1, recipeID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				commentCnt = rs.getInt("comment_cnt");
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
		return commentCnt;
	}
	public static int getNewCommentID(){
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int newCommentID =0;
		try {
			String newCommentIDSQL = "SELECT seq_recipe_comments_cno.NEXTVAL as new_comment_ID FROM Dual";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(newCommentIDSQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				newCommentID = rs.getInt("new_comment_ID");
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
		return newCommentID;	
	}
	public static int [] getNewCommentGroupAndOrder(int recipeID, int commentTo) {
		int [] newCommentNums = new int[2];
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "";
			if(commentTo == 0) {//댓글 일때 새 코멘트 그룹 받아오기
				sql = "SELECT nvl(MAX(comment_group),0)+1 FROM RECIPE_COMMENTS where recipe_id = ?";
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setInt(1, recipeID);
			} else { //대댓글일때 소속된 코멘트 그룹 & 새 댓글 순서 받아오기
				sql = "SELECT nvl(MAX(comment_order),0)+1 as new_CommentOrder, comment_group\r\n" + 
						"FROM RECIPE_COMMENTS\r\n" + 
						"WHERE\r\n" + 
						"    (recipe_id = ? AND comment_group = \r\n" + 
						"        (\r\n" + 
						"        SELECT\r\n" + 
						"        comment_group\r\n" + 
						"        FROM recipe_comments\r\n" + 
						"        WHERE (recipe_id = ? AND comment_id = ?)\r\n" + 
						"        )\r\n" + 
						"    ) group by comment_group";
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setInt(1, recipeID);
				pstmt.setInt(2, recipeID);
				pstmt.setInt(3, commentTo);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(commentTo == 0) {
					newCommentNums[0] = (rs.getInt(1));
					newCommentNums[1] = 1;
				} else {
					newCommentNums[0] = (rs.getInt(1));
					newCommentNums[1] = (rs.getInt(2));
				}
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
		return newCommentNums;
	}
	
	public int getNormalRecipeTotalCnt() { //매거진 소속이 아닌 레시피 총 수
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(recipe_id) AS cnt FROM RECIPE where MAGAZINE_ID = 0";
		int cnt = 0;
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt += rs.getInt("cnt");
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
		return cnt;
	}
	
	public int getNormalRecipeTotalCnt(String searchWord) {
		ConnectionRecipe.connectionRecipe();
		String sql="*";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if(searchWord!=null) {
			sql = "	 SELECT \r\n" + 
					"		count(r.recipe_id) AS cnt, r.recipe_id, m.nickname\r\n" + 
					"	FROM\r\n" + 
					"		RECIPE r join MEMBER m on (r.u_id = m.u_id) \r\n" + 
					"	WHERE\r\n" + 
					"		(r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1 AND INSTR(r.title, ?) > 0) group by m.nickname, r.recipe_id\r\n" + 
					"UNION \r\n" + 
					"	SELECT \r\n" + 
					"		count(r.recipe_id) AS cnt, r.recipe_id, m.nickname\r\n" + 
					"	FROM\r\n" + 
					"		RECIPE r join MEMBER m on (r.u_id = m.u_id) \r\n" + 
					"	WHERE\r\n" + 
					"		(r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1 AND INSTR(m.nickname, ?) > 0) group by m.nickname, r.recipe_id";
				}
		int cnt = 0;
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			if(rs.next()) {
				cnt += rs.getInt("cnt");
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
		return cnt;
	}
	
	public int getNormalRecipeLastPageNum() {
		int cnt = getNormalRecipeTotalCnt();
		if(cnt%16==0) {
			return cnt/16;
		}
		return cnt/16 + 1;//4
	}
	public String getTitleByID(int recipeID) {//상세보기 페이지용
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String rTitle = "";
		try {
			String sql = "SELECT title from RECIPE where recipe_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				rTitle = rs.getString(1);
			}
		} catch (Exception e) {
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
		return rTitle;
	}

	public static ArrayList<RecipeManagerCategoryVo> getCategoryList(String table){
		String name;
		String image;
		int categoryId;
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RecipeManagerCategoryVo> getCategoryList = new ArrayList<RecipeManagerCategoryVo>();
		try {
			String commentSQL =  "SELECT * FROM "+table+" ORDER BY "+table+"_id";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(commentSQL);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				categoryId = rs.getInt(table+"_id");
				name = rs.getString("name");
				image = rs.getString("image");
				getCategoryList.add(new RecipeManagerCategoryVo(categoryId, name, image));
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
		return getCategoryList;
	}
	
	public static ArrayList<RecipeLvVo> getLvList() {
		ArrayList<RecipeLvVo> getLvList = new ArrayList<RecipeLvVo>();
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM RECIPE_LV ORDER BY lv ASC";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			int lv;
			String lvName;
			rs = pstmt.executeQuery();
			while(rs.next()) {
				lv = rs.getInt("lv");
				lvName = rs.getString("level_name");
				getLvList.add(new RecipeLvVo(lv, lvName));
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
		return getLvList;
	}
	public int getSearchedRecipeCnt(String searchWord, int what, int kind, int situation, int minTime, int maxTime, int lv) {
		ConnectionRecipe.connectionRecipe();
		int cnt = getNormalRecipeTotalCnt();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String addSQL = "";
		if(what!=0) {
			addSQL +=" AND r.what = "+what+" \r\n";
		}
		if(kind!=0) {
			addSQL +=" AND r.kind = "+kind+" \r\n";
		}
		if(situation!=0) {
			addSQL +=" AND r.situation = "+situation+"\r\n";
		}
		if(minTime!=0) {
			addSQL +=" AND r.time >= "+minTime+"\r\n";
		}
		if(maxTime!=0) {
			addSQL +=" AND r.time <= "+maxTime+" \r\n";
		}
		if(lv!=100) {
			addSQL +=" AND r.lv <= "+lv+"  \r\n";
		}
		
		String SQL ="";
		if(searchWord==null) {//검색어 없을 때
			SQL =
					"SELECT count(*)\r\n" + 
					"	FROM\r\n" + 
					"		(SELECT rownum rnum, t.* FROM\r\n" + 
					"(SELECT\r\n" + 
					"	recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate, count(rhd.hit_date) AS hitcount\r\n" + 
					"FROM\r\n" + 
					"	(\r\n" + 
					"		SELECT\r\n" + 
					"			r.thumbnail AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, r.writedate as writedate, m.nickname AS writer, r.u_id as user_id, m.profile_image as profile\r\n" + 
					"		FROM\r\n" + 
					"			RECIPE r join MEMBER m on (r.u_id = m.u_id)\r\n" + 
					"		WHERE\r\n" + 
					"			(r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1"+ addSQL+")\r\n" + 
					"	) recipe_info, recipe_hit_date rhd\r\n" + 
					"	WHERE\r\n" + 
					"		recipe_info.recipe_id = rhd.recipe_id\r\n" + 
					"GROUP BY\r\n" + 
					"	recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate\r\n" + 
					"	UNION\r\n" + 
					"	SELECT\r\n" + 
					"		recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate, count(rhd.hit_date) AS hitcount\r\n" + 
					"	FROM\r\n" + 
					"	(\r\n" + 
					"		SELECT\r\n" + 
					"			r.thumbnail AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, r.writedate as writedate, m.nickname AS writer, r.u_id as user_id, m.profile_image as profile\r\n" + 
					"		FROM\r\n" + 
					"			RECIPE r join MEMBER m on (r.u_id = m.u_id)\r\n" + 
					"		WHERE\r\n" + 
					"		(r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1"+ addSQL+")\r\n" + 
					"	) recipe_info, recipe_hit_date rhd\r\n" + 
					"	WHERE\r\n" + 
					"		recipe_info.recipe_id = rhd.recipe_id\r\n" + 
					"	GROUP BY\r\n" + 
					"		recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate\r\n" + 
					"    ) t\r\n" + 
					")t2\r\n" ;
			}
		if(searchWord!=null) {//검색어 있을 때
			SQL =
				"SELECT count(*) \r\n" + 
				"	FROM\r\n" + 
				"		(SELECT rownum rnum, t.* FROM\r\n" + 
				"(SELECT\r\n" + 
				"	recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate, count(rhd.hit_date) AS hitcount\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			r.thumbnail AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, r.writedate as writedate, m.nickname AS writer, r.u_id as user_id, m.profile_image as profile\r\n" + 
				"		FROM\r\n" + 
				"			RECIPE r join MEMBER m on (r.u_id = m.u_id)\r\n" + 
				"		WHERE\r\n" + 
				"			(r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1 "+ addSQL+" AND INSTR ( m.nickname, '"+searchWord+"') > 0)\r\n" + 
				"	) recipe_info, recipe_hit_date rhd\r\n" + 
				"	WHERE\r\n" + 
				"		recipe_info.recipe_id = rhd.recipe_id\r\n" + 
				"GROUP BY\r\n" + 
				"	recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate\r\n" + 
				"	UNION\r\n" + 
				"	SELECT\r\n" + 
				"		recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate, count(rhd.hit_date) AS hitcount\r\n" + 
				"	FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			r.thumbnail AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, r.writedate as writedate, m.nickname AS writer, r.u_id as user_id, m.profile_image as profile\r\n" + 
				"		FROM\r\n" + 
				"			RECIPE r join MEMBER m on (r.u_id = m.u_id)\r\n" + 
				"		WHERE\r\n" + 
				"		(r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1 "+ addSQL+" AND INSTR (r.title, '"+searchWord+"') > 0)\r\n" + 
				"	) recipe_info, recipe_hit_date rhd\r\n" + 
				"	WHERE\r\n" + 
				"		recipe_info.recipe_id = rhd.recipe_id\r\n" + 
				"	GROUP BY\r\n" + 
				"		recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate\r\n" + 
				"    ) t\r\n" + 
				")t2\r\n" ;
			}
		try {
		pstmt = ConnectionRecipe.getConnection().prepareStatement(SQL);
		rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
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
		return cnt;
	}
	public ArrayList<RecipeVo> getSearchedRecipeList(String searchWord, int what, int kind, int situation, int minTime, int maxTime, int lv, int pageNum, String order) {
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RecipeVo> recipeList = new ArrayList<RecipeVo>();
		int endRnum = pageNum * 16;//각 페이지에서 마지막 게시글 수 
		int startRnum = endRnum - 15;
		String orderBy = "*";
		switch (order) {
		case "n"://name
			orderBy = "title asc";
			break;
		case "r"://recent
			orderBy = "writedate DESC";
			break;
		case "h"://hit
			orderBy = "hitcount DESC, title ASC";
			break;
		default:
			orderBy = "hitcount DESC, title ASC";
			break;
		}
		String addSQL = "";
		if(what!=0) {
			addSQL +=" AND r.what = "+what+" \r\n";
		}
		if(kind!=0) {
			addSQL +=" AND r.kind = "+kind+" \r\n";
		}
		if(situation!=0) {
			addSQL +=" AND r.situation = "+situation+"\r\n";
		}
		if(minTime!=0) {
			addSQL +=" AND r.time >= "+minTime+"\r\n";
		}
		if(maxTime!=0) {
			addSQL +=" AND r.time <= "+maxTime+" \r\n";
		}
		if(lv!=100) {
			addSQL +=" AND r.lv <= "+lv+"  \r\n";
		}
		
		String SQL ="";
		if(searchWord==null) {
			SQL =
					"SELECT t2.*\r\n" + 
					"	FROM\r\n" + 
					"		(SELECT rownum rnum, t.* FROM\r\n" + 
					"(SELECT\r\n" + 
					"	recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate, count(rhd.hit_date) AS hitcount\r\n" + 
					"FROM\r\n" + 
					"	(\r\n" + 
					"		SELECT\r\n" + 
					"			r.thumbnail AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, r.writedate as writedate, m.nickname AS writer, r.u_id as user_id, m.profile_image as profile\r\n" + 
					"		FROM\r\n" + 
					"			RECIPE r join MEMBER m on (r.u_id = m.u_id)\r\n" + 
					"		WHERE\r\n" + 
					"			(r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1"+ addSQL+")\r\n" + 
					"	) recipe_info, recipe_hit_date rhd\r\n" + 
					"	WHERE\r\n" + 
					"		recipe_info.recipe_id = rhd.recipe_id\r\n" + 
					"GROUP BY\r\n" + 
					"	recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate\r\n" + 
					"	UNION\r\n" + 
					"	SELECT\r\n" + 
					"		recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate, count(rhd.hit_date) AS hitcount\r\n" + 
					"	FROM\r\n" + 
					"	(\r\n" + 
					"		SELECT\r\n" + 
					"			r.thumbnail AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, r.writedate as writedate, m.nickname AS writer, r.u_id as user_id, m.profile_image as profile\r\n" + 
					"		FROM\r\n" + 
					"			RECIPE r join MEMBER m on (r.u_id = m.u_id)\r\n" + 
					"		WHERE\r\n" + 
					"		(r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1"+ addSQL+")\r\n" + 
					"	) recipe_info, recipe_hit_date rhd\r\n" + 
					"	WHERE\r\n" + 
					"		recipe_info.recipe_id = rhd.recipe_id\r\n" + 
					"	GROUP BY\r\n" + 
					"		recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate\r\n" + 
					"	ORDER BY "+orderBy+
					"    ) t\r\n" + 
					")t2\r\n" + 
					"    WHERE (t2.rnum >= ? AND t2.rnum <= ?)";
		}
		if(searchWord!=null) {
			SQL =
				"SELECT t2.*\r\n" + 
				"	FROM\r\n" + 
				"		(SELECT rownum rnum, t.* FROM\r\n" + 
				"(SELECT\r\n" + 
				"	recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate, count(rhd.hit_date) AS hitcount\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			r.thumbnail AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, r.writedate as writedate, m.nickname AS writer, r.u_id as user_id, m.profile_image as profile\r\n" + 
				"		FROM\r\n" + 
				"			RECIPE r join MEMBER m on (r.u_id = m.u_id)\r\n" + 
				"		WHERE\r\n" + 
				"			(r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1 "+ addSQL+" AND INSTR ( m.nickname, '"+searchWord+"') > 0)\r\n" + 
				"	) recipe_info, recipe_hit_date rhd\r\n" + 
				"	WHERE\r\n" + 
				"		recipe_info.recipe_id = rhd.recipe_id\r\n" + 
				"GROUP BY\r\n" + 
				"	recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate\r\n" + 
				"	UNION\r\n" + 
				"	SELECT\r\n" + 
				"		recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate, count(rhd.hit_date) AS hitcount\r\n" + 
				"	FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT\r\n" + 
				"			r.thumbnail AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, r.writedate as writedate, m.nickname AS writer, r.u_id as user_id, m.profile_image as profile\r\n" + 
				"		FROM\r\n" + 
				"			RECIPE r join MEMBER m on (r.u_id = m.u_id)\r\n" + 
				"		WHERE\r\n" + 
				"		(r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1 "+ addSQL+" AND INSTR (r.title, '"+searchWord+"') > 0)\r\n" + 
				"	) recipe_info, recipe_hit_date rhd\r\n" + 
				"	WHERE\r\n" + 
				"		recipe_info.recipe_id = rhd.recipe_id\r\n" + 
				"	GROUP BY\r\n" + 
				"		recipe_info.recipe_id, recipe_info.thumbnail, recipe_info.title, recipe_info.writer, recipe_info.user_id, recipe_info.profile, recipe_info.writedate\r\n" + 
				"	ORDER BY "+orderBy+
				"    ) t\r\n" + 
				")t2\r\n" + 
				"    WHERE (t2.rnum >= ? AND t2.rnum <= ?)";
		}
		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(SQL);
				pstmt.setInt(1, startRnum);
				pstmt.setInt(2, endRnum);
			//}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int recipeID = rs.getInt("RECIPE_ID");
				String recipeThumbnail = rs.getString("THUMBNAIL");
				String recipeTitle = rs.getString("TITLE");
				int recipeHits = rs.getInt("HITCOUNT");
				String writerUserID = rs.getString("user_id");
				String writerNickname = rs.getString("writer");
				String writerProfileImage = rs.getString("profile");
				int recipeCommentCnt = 0;
				recipeCommentCnt = getCommentCnt(recipeID);
				recipeList.add(new RecipeVo(recipeID, recipeThumbnail, recipeTitle, recipeHits, recipeCommentCnt, writerUserID, writerNickname, writerProfileImage));
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
		return recipeList;
	}
	public static int[] getRecipeCategoryByID(int recipeID) { 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int recipeCategory[] = new int[3];
		String sql = "SELECT\r\n" + 
				"    what,\r\n" + 
				"    kind,\r\n" + 
				"    situation\r\n" + 
				"FROM RECIPE WHERE recipe_id = ?";
		ConnectionRecipe.connectionRecipe();
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				recipeCategory[0] = rs.getInt("what");
				recipeCategory[1] = rs.getInt("kind");
				recipeCategory[2] = rs.getInt("situation");
			}
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
		return	recipeCategory;
	}
	public static String[] getCategoryNameBy(int recipeID) {
		String recipeCategoryName[] = new String[3];
		String sqlW = "SELECT\r\n" + 
				"    name as what_name\r\n" + 
				"FROM What where what_id =\r\n" + 
				"(SELECT \r\n" + 
				"    what\r\n" + 
				"    FROM RECIPE\r\n" + 
				"    WHERE recipe_id = ?)";
		String sqlK = "SELECT\r\n" + 
				"    name as kind_name\r\n" + 
				"FROM KIND where kind_id =\r\n" + 
				"(\r\n" + 
				"SELECT\r\n" + 
				"    kind\r\n" + 
				"    FROM RECIPE\r\n" + 
				"    WHERE recipe_id = ?)";
		
		String sqlS = "SELECT\r\n" + 
				"    name as situation_name\r\n" + 
				"FROM SITUATION where situation_id =\r\n" + 
				"(\r\n" + 
				"SELECT\r\n" + 
				"    situation\r\n" + 
				"FROM RECIPE WHERE recipe_id = ?)";
		ConnectionRecipe.connectionRecipe();
		try {
			PreparedStatement pstmt1 = ConnectionRecipe.getConnection().prepareStatement(sqlW);
			pstmt1.setInt(1, recipeID);
			PreparedStatement pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sqlK);
			pstmt2.setInt(1, recipeID);
			PreparedStatement pstmt3 = ConnectionRecipe.getConnection().prepareStatement(sqlS);
			pstmt3.setInt(1, recipeID);
			ResultSet rs1 = pstmt1.executeQuery();
			ResultSet rs2 = pstmt2.executeQuery();
			ResultSet rs3 = pstmt3.executeQuery();
			if (rs1.next()) {
				recipeCategoryName[0] = rs1.getString("what_name");
			}
			if (rs2.next()) {
				recipeCategoryName[1] = rs2.getString("kind_name");
			}
			if (rs3.next()) {
				recipeCategoryName[2] = rs3.getString("situation_name");
			}
			rs3.close();
			rs2.close();
			rs1.close();
			pstmt3.close();
			pstmt2.close();
			pstmt1.close();
			ConnectionRecipe.disConnectionRecipe();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recipeCategoryName;
	}
	public static RecipeDetailVo getRecipeDetailVoByID(int recipeID) {
		ConnectionRecipe.connectionRecipe();
		RecipeDetailVo vo = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String tag = "없음";
		String sql = "SELECT  \r\n" + 
				"r.THUMBNAIL AS thumbnail, r.video, r.TITLE, r.INTRODUCE, r.TAG,  \r\n" + 
				"nvl(r.SERVING, 0) as Serving , nvl(r.TIME,0) as Time, r.LV, \r\n" + 
				"to_date(r.writedate, 'YYYY-MM-DD') as recipe_writedate,  \r\n" + 
				"to_date(r.UPDATE_DATE, 'YYYY-MM-DD') as recipe_update_date,  \r\n" + 
				"m.u_id as userID,  \r\n" + 
				"m.NICKNAME as writer,  \r\n" + 
				"m.PROFILE_IMAGE as PROFILE, m.SELF_INTRODUCE,  \r\n" + 
				"count(rhd.hit_date) as HITCOUNT  \r\n" + 
				"FROM RECIPE r  \r\n" + 
				"JOIN RECIPE_HIT_DATE rhd on r.recipe_id = rhd.recipe_id  \r\n" + 
				"JOIN MEMBER m on r.u_id = m.u_id  \r\n" + 
				"WHERE (r.recipe_id = ?) \r\n" + 
				"GROUP BY to_date(r.writedate, 'YYYY-MM-DD'), r.writedate, 'YYYY-MM-DD', r.video, r.writedate, \r\n" + 
				"'YYYY-MM-DD', to_date(r.UPDATE_DATE, 'YYYY-MM-DD'), r.UPDATE_DATE, 'YYYY-MM-DD', r.UPDATE_DATE, \r\n" + 
				"'YYYY-MM-DD', r.THUMBNAIL, r.TITLE, r.INTRODUCE, r.tag, \r\n" + 
				"nvl(r.SERVING, 0), r.SERVING, 0, r.SERVING, 0, \r\n" + 
				"nvl(r.TIME,0), r.TIME, 0, r.TIME, 0, \r\n" + 
				"r.LV, m.NICKNAME, m.PROFILE_IMAGE, m.SELF_INTRODUCE, m.u_id";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String recipeThumbnail = rs.getString("THUMBNAIL");
				String recipeTitle = rs.getString("TITLE");
				String recipeIntroduce = rs.getString("INTRODUCE");
				String recipeVideo = rs.getString("video");
				int recipeHits = rs.getInt("HITCOUNT");
				StringBuilder sb = new StringBuilder();
				if(rs.getString("TAG")!=null) {
					tag = rs.getString("TAG");
					String[] tags = tag.split(" /// ");
					for(int k=0; k<tags.length; k++) {
						if(k < tags.length-1) {
							sb.append(tags[k]+" ");
						} else {
							sb.append(tags[k]);
						}
					}
					tag = sb.toString();
					tag = tag.trim();
				}
				String writerUserID = rs.getString("userID");
				String writerNickname = rs.getString("writer");
				String writerProfileImage = rs.getString("PROFILE");
				String writerIntroduce = rs.getString("SELF_INTRODUCE");
				String recipeWriteDate = rs.getString("recipe_writedate");
				String wd[] = recipeWriteDate.split(" ");
				System.out.println(wd[0].length());
				if(wd[0].length() == 7) {
					recipeWriteDate = "200"+wd[0];
				} else {
					recipeWriteDate = "20"+wd[0];
				}
				String recipeUpdateDate = rs.getString("recipe_update_date");
				String ud[] = recipeUpdateDate.split(" ");
				if(ud[0].length() == 7) {
					recipeUpdateDate = "200"+ud[0];
				} else {
					recipeUpdateDate = "20"+ud[0];
				}
				int recipeServing = rs.getInt("Serving");
				int recipeTime = rs.getInt("Time");
				int recipeLv = rs.getInt("Lv");
				int recipeCommentCnt = 0;
				recipeCommentCnt = getCommentCnt(recipeID);
				int likeCnt = 0;
				try {
					String likeSQL = "SELECT count(u_id) as likes from RECIPE_LIKE where recipe_id = ?";
					PreparedStatement pstmt2 = ConnectionRecipe.getConnection().prepareStatement(likeSQL);
					pstmt2.setInt(1, recipeID);
					ResultSet rs2 = pstmt2.executeQuery();
					if(rs2.next()) {
						likeCnt = rs2.getInt("likes");
					}
					rs2.close();
					pstmt2.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				vo = new RecipeDetailVo(recipeID, recipeThumbnail, recipeVideo, recipeTitle, recipeIntroduce, tag, recipeHits, likeCnt, recipeServing, recipeTime, recipeLv, recipeCommentCnt, writerUserID, writerNickname, writerProfileImage, writerIntroduce, recipeWriteDate, recipeUpdateDate);
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
		return vo;
	}
	public static String getLevelName(int recipeID) {
		String sql ="SELECT LEVEL_NAME as lv_name FROM recipe_lv WHERE lv = (SELECT LV FROM RECIPE WHERE (recipe_id = ?))";
		String level_name ="";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				level_name = rs.getString("lv_name");
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
		return level_name;
	}
	public static boolean addRecipeNote(int recipeID, String userID, String content) {
		PreparedStatement pstmt = null;
		boolean ok = false;
		String sql ="INSERT INTO recipe_note(recipe_id, u_id, content, writedate) VALUES (?, ?, ?, to_date(sysdate, 'YYYY-MM-DD'))";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeID);
			pstmt.setString(2, userID);
			pstmt.setString(3, content);
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
	public static ArrayList<RecipeIngrediVo> getRecipeIngrediVoByID(int recipeID) {
		ConnectionRecipe.connectionRecipe();
		ArrayList<RecipeIngrediVo> recipeIngrediList = new ArrayList<RecipeIngrediVo>();
		String sql = "SELECT * FROM RECIPE_INGREDIENT WHERE (RECIPE_ID = ?) ORDER BY BUNDLE_NUM";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeID);
			rs = pstmt.executeQuery();
			String qty = "";
			while(rs.next()) {
				int bundleNum = rs.getInt("BUNDLE_Num");
				String bundleName = rs.getString("BUNDLE_NAME");
				String ingrediName = rs.getString("NAME");
				if(rs.getString("QTY") != null) {
					qty = rs.getString("QTY");
				}
				recipeIngrediList.add(new RecipeIngrediVo(bundleNum, bundleName, ingrediName, qty));
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
		return recipeIngrediList;
	}

	
	public static ArrayList<RecipeProcessVo> getRecipeProcessVoByID(int recipe_id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RecipeProcessVo> recipeProcessList = new ArrayList<RecipeProcessVo>();
		ConnectionRecipe.connectionRecipe();
		String sql = "SELECT * FROM COOKING_ORDER WHERE (recipe_id = ? and process_id < 10000) ORDER BY process_ID";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipe_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int processID = rs.getInt("PROCESS_ID");
				String process = rs.getString("PROCESS");
				String material = rs.getString("MATERIAL");
				String cookEquipment = rs.getString("COOK_EQUIPMENT");
				String fire = rs.getString("FIRE");
				String tip = rs.getString("TIP");
				String image = rs.getString("IMAGE");
				recipeProcessList.add(new RecipeProcessVo(processID, process, material, cookEquipment, fire, tip, image, null, null));
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
		return recipeProcessList;
	}
	public static RecipeProcessVo getRecipeLastProcessVoByID(int recipe_id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecipeProcessVo vo = null;
		ConnectionRecipe.connectionRecipe();
		String sql = "SELECT TIP_CAUTION as LAST_TIP_CAUTION, NVL(IMAGE, '') as LAST_IMAGE FROM COOKING_ORDER WHERE recipe_id = ? and process_id = 10000";
		String lastTipCaution = "없음";
		String lastImg = "없음";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipe_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("LAST_TIP_CAUTION") != null) {
					lastTipCaution = rs.getString("LAST_TIP_CAUTION");
					lastTipCaution = lastTipCaution.trim();
				}
				if(rs.getString("LAST_IMAGE") != null) {
					lastImg = rs.getString("LAST_IMAGE");
				}
				vo = new RecipeProcessVo(10000, null, null, null, null, null, null, lastImg, lastTipCaution);
			}
			rs.close();
			pstmt.close();
			ConnectionRecipe.disConnectionRecipe();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		 catch(NullPointerException e) {
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
		return vo;
	}
	public static ArrayList<RecipeCommentVo> getRecipeCommentVoByID(int recipeID) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RecipeCommentVo> recipeCommentList = new ArrayList<RecipeCommentVo>();
		ConnectionRecipe.connectionRecipe();
		String sql = "SELECT \r\n" + 
				"   rc.COMMENT_ID,\r\n" + 
				"   rc.COMMENT_ORDER as comment_order, \r\n" + 
				"   rc.comment_id,\r\n" + 
				"   m.nickname AS writer, \r\n" + 
				"   m.PROFILE_IMAGE as PROFILE,\r\n" + 
				"    m.u_id,\r\n" + 
				"   rc.content, \r\n" + 
				"   nvl(rc.star, 0) as stars, \r\n" + 
				"   rc.comment_order, \r\n" + 
				"   rc.image, \r\n" + 
				"   to_date(rc.writedate, 'YYYY-MM-DD HH:MI') as comment_writedate \r\n" + 
				"				FROM \r\n" + 
				"   Recipe_Comments rc join MEMBER m on (rc.u_id = m.u_id) \r\n" + 
				"				WHERE \r\n" + 
				"   (rc.recipe_id = ?) \r\n" + 
				"				ORDER BY \r\n" + 
				"   rc.comment_group desc, \r\n" + 
				"   rc.comment_order asc, \r\n" + 
				"   rc.writedate ASC";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int commentID = rs.getInt("comment_id");
				String nickname = rs.getString("writer");
				String profileImg = rs.getString("PROFILE");
				String content = rs.getString("content");
				String userID = rs.getString("u_id");
				int stars = rs.getInt("stars");
				int commentOrder = rs.getInt("comment_order");
				String image = rs.getString("image");
				String commentWritedate = rs.getString("comment_writedate");
				String wd[] = commentWritedate.split(" ");
				commentWritedate = "20"+wd[0];
				recipeCommentList.add(new RecipeCommentVo(commentID, commentOrder, userID, nickname, profileImg, content, image, stars, commentWritedate));
			}
			rs.close();
			pstmt.close();
			ConnectionRecipe.disConnectionRecipe();
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
		return recipeCommentList;
	}
	
	public static String getRecipeNoteByID(String userID, int recipeID) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ConnectionRecipe.connectionRecipe();
		String sql = "SELECT * FROM RECIPE_NOTE WHERE u_id = ? AND recipe_id = ?";
		String recipeNote = null;
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setInt(2, recipeID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				recipeNote = rs.getString("content");
			}
		} catch (SQLException e) {
			System.out.println("insertRecipeInfo에서 에러! [SQL Error : " + e.getMessage() + "]");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		return recipeNote;
	}
	
	public static int getNewRecipeID() {
		int recipeID = 0;
		ConnectionRecipe.connectionRecipe();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String newRecipeIDSQL = "select SEQ_Recipe_RECIPE_ID.nextval FROM DUAL";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(newRecipeIDSQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				recipeID = rs.getInt("nextval");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return recipeID;
	}

	public void insertRecipeInfo(int recipeID, String u_id, String title, String introduce, int what, int kind, int situation, int serving, int time, int lv, String thumbnail, String video, String tag, int complete) {
		
		String insertRecipeSQL ="INSERT INTO RECIPE\r\n" + 
				"(recipe_id,\r\n" + 
				"u_id,\r\n" + 
				"title,\r\n" + 
				"introduce,\r\n" + 
				"what,\r\n" + 
				"kind,\r\n" + 
				"situation,\r\n" + 
				"serving,\r\n" + 
				"time,\r\n" + 
				"lv,\r\n" + 
				"thumbnail,\r\n" + 
				"video,\r\n" + 
				"tag,\r\n" + 
				"writedate,\r\n" + 
				"update_date,\r\n" + 
				"complete,\r\n" + 
				"deletedaccount,\r\n" + 
				"deletedpost)\r\n" + 
				"VALUES \r\n" + 
				"	(?, \r\n" + 
				"    ?, --2 u_id\r\n" + 
				"    ?, --3 title\r\n" + 
				"    ?, --4 introduce, \r\n" + 
				"    ?,--5 what\r\n" + 
				"    ?, -- 6 kind, \r\n" + 
				"    ?, -- 7 situation, \r\n" + 
				"    ?, -- 8 serving, \r\n" + 
				"    ?, -- 9 time, \r\n" + 
				"    ?, -- 10 lv, --\r\n" + 
				"    ?,--11 thumbnail, \r\n" + 
				"    ?,  -- 12 video, \r\n" + 
				"    ?, -- 13 tag, \r\n" + 
				"    to_date(sysdate, 'YYYY-MM-DD'), \r\n" + 
				"    to_date(sysdate, 'YYYY-MM-DD'), \r\n" + 
				"    ?,   -- 14 complete, \r\n" + 
				"    0, \r\n" + 
				"    0" + 
				")";
		PreparedStatement pstmt = null;
		ConnectionRecipe.connectionRecipe();
		try {
			pstmt =	ConnectionRecipe.getConnection().prepareStatement(insertRecipeSQL);
			pstmt.setInt(1, recipeID);
			pstmt.setString(2, u_id);
			pstmt.setString(3, title);
			pstmt.setString(4, introduce);
			pstmt.setInt(5, what);
			pstmt.setInt(6, kind);
			pstmt.setInt(7, situation);
			pstmt.setInt(8, serving);
			pstmt.setInt(9, time);
			pstmt.setInt(10, lv);
			pstmt.setString(11, thumbnail);
			pstmt.setString(12, video);
			pstmt.setString(13, tag);
			pstmt.setInt(14, complete);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insertRecipeIngredi (int recipeID, ArrayList<RecipeIngrediVo> ingrediList) {
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		String insertRecipeIngredentSQL = 
				"INSERT INTO RECIPE_INGREDIENT\r\n" + 
				"(RECIPE_ID ,\r\n" + 
				"BUNDLE_NUM,\r\n" + 
				"BUNDLE_NAME, \r\n" + 
				"NAME, \r\n" + 
				"Qty, \r\n" + 
				"INGREDI_ID)\r\n" + 
				"VALUES (\r\n" + 
				"?, \r\n" + 
				"?, \r\n" + 
				"?, \r\n" + 
				"?, \r\n" + 
				"?, \r\n" + 
				"(SELECT ii.INGREDI_ID FROM Ingredient_replace_name irn join Ingredient_ID ii on (irn.REPLACE_NAME = ii.name) where irn.NAME like ?)\r\n" + 
				")";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(insertRecipeIngredentSQL);
			for(int i=0;i<ingrediList.size();i++) {
				RecipeIngrediVo ingredi = ingrediList.get(i);
				pstmt.setInt(1, recipeID);
				pstmt.setInt(2, ingredi.getBundleNum());
				pstmt.setString(3, ingredi.getBundleName());
				pstmt.setString(4, ingredi.getIngrediName());
				pstmt.setString(5, ingredi.getQTY());
				pstmt.setString(6, "%"+ingredi.getIngrediName()+"%");
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("insertRecipeIngredi에서 에러! [SQL Error : " + e.getMessage() + "]");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insertRecipeSteps (int recipeID, ArrayList<RecipeProcessVo> stepList) {
		ConnectionRecipe.connectionRecipe();

		PreparedStatement pstmt = null;		
		String insertRecipeStepstSQL =
				"INSERT INTO Cooking_Order\r\n" + 
				"(RECIPE_ID, PROCESS_ID, PROCESS, MATERIAL, COOK_EQUIPMENT, FIRE, TIP, TIP_CAUTION, IMAGE) VALUES\r\n" + 
				"(?, -- RECIPE_ID\r\n" + 
				"?, --PROCESS_ID\r\n" + 
				"?, --PROCESS\r\n" + 
				"?, --MATERIAL\r\n" + 
				"?, --COOK_EQUIPMENT\r\n" + 
				"?, --FIRE\r\n" + 
				"?, --TIP\r\n" + 
				"?, --TIP_CAUTION\r\n" + 
				"? --IMAGE\r\n" + 
				")";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(insertRecipeStepstSQL);
			for(int i=0;i<stepList.size();i++) {
				RecipeProcessVo step = stepList.get(i);
					pstmt.setInt(1, recipeID);
					pstmt.setInt(2, step.getProcessID());
					pstmt.setString(3, step.getProcess());
					pstmt.setString(4, step.getMaterial());
					pstmt.setString(5, step.getCookEquipment());
					pstmt.setString(6, step.getFire());
					pstmt.setString(7, step.getTip());
					pstmt.setString(8, step.getLastTipCaution());
					pstmt.setString(9, step.getImage());
					pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("insertRecipeSteps에서 에러![SQL Error : " + e.getMessage() + "]");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void updateRecipeInfo(int recipeID, String u_id, String title, String introduce, int what, int kind, int situation, int serving, int time, int lv, String thumbnail, String video, String tag, int complete) {
		
		String updateRecipeSQL =
				"UPDATE RECIPE\r\n" + 
				"    SET\r\n" + 
				"        title = ?,-- 1 title\r\n" + 
				"        introduce = ?, -- 2 introduce\r\n" + 
				"        what = ?, -- 3 what\r\n" + 
				"        kind = ?, -- 4 kind\r\n" + 
				"        situation = ?, -- 5 situation\r\n" + 
				"        serving = ?, -- 6 serving\r\n" + 
				"        time = ?, -- 7 time\r\n" + 
				"        lv = ?, -- 8 lv\r\n" + 
				"        thumbnail = ?, -- 9 thumbnail\r\n" + 
				"        video = ?, -- 10 video\r\n" + 
				"        tag = '?', -- 11 tag\r\n" + 
				"        update_date = to_date('sysdate', 'YYYY-MM-DD'),\r\n" + 
				"        complete = ?, -- 12 complete\r\n" + 
				"    WHERE recipe_id = ?";

		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(updateRecipeSQL);
			pstmt.setString(1, title);
			pstmt.setString(2, introduce);
			pstmt.setInt(3, what);
			pstmt.setInt(4, kind);
			pstmt.setInt(5, situation);
			pstmt.setInt(6, serving);
			pstmt.setInt(7, time);
			pstmt.setInt(8, lv);
			pstmt.setString(9, thumbnail);
			pstmt.setString(10, video);
			pstmt.setString(11, tag);
			pstmt.setInt(12, complete);
			pstmt.setInt(13, recipeID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[SQL Error : " + e.getMessage() + "]");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateRecipeIngredi (int recipeID, ArrayList<RecipeIngrediVo> ingrediList) {
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;		
		String updateRecipeIngrediSQL =
				"UPDATE RECIPE_INGREDIENT\r\n" + 
				"    SET\r\n" + 
				"        BUNDLE_NUM = ?,-- 1 BUNDLE_NUM\r\n" + 
				"        BUNDLE_NAME = ?, -- 2 BUNDLE_NAME\r\n" + 
				"        NAME = ?, -- 3 NAME\r\n" + 
				"        Qty = ?, -- 4 Qty\r\n" + 
				"        INGREDE_ID = ?, -- 5 INGREDE_ID\r\n" + 
				"    WHERE recipe_id = ?";
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(updateRecipeIngrediSQL);
			for(int i=0;i<ingrediList.size();i++) {
				RecipeIngrediVo ingredi = ingrediList.get(i);
				
				pstmt.setInt(1, recipeID);
				pstmt.setInt(2, ingredi.getBundleNum());
				pstmt.setString(3, ingredi.getBundleName());
				pstmt.setString(4, ingredi.getIngrediName());
				pstmt.setString(5, ingredi.getIngrediName());
				pstmt.setString(6, ingredi.getQTY());
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("[SQL Error : " + e.getMessage() + "]");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateRecipeStepList (int recipeID, ArrayList<RecipeProcessVo> stepList) {
		ConnectionRecipe.connectionRecipe();
		String updateRecipeStepSQL =
				"INSERT INTO Cooking_Order\r\n" + 
						"(RECIPE_ID, PROCESS_ID, PROCESS, MATERIAL, COOK_EQUIPMENT, FIRE, TIP, TIP_CAUTION, IMAGE) VALUES\r\n" + 
						"(?, -- RECIPE_ID\r\n" + 
						"?, --PROCESS_ID\r\n" + 
						"?, --PROCESS\r\n" + 
						"?, --MATERIAL\r\n" + 
						"?, --COOK_EQUIPMENT\r\n" + 
						"?, --FIRE\r\n" + 
						"?, --TIP\r\n" + 
						"?, --TIP_CAUTION\r\n" + 
						"? --IMAGE\r\n" + 
						")";
		PreparedStatement pstmt = null;		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(updateRecipeStepSQL);
			for(int i=0;i<stepList.size();i++) {
				RecipeProcessVo step = stepList.get(i);
				pstmt.setInt(1, recipeID);
				pstmt.setInt(2, step.getProcessID());
				pstmt.setString(3, step.getProcess());
				pstmt.setString(4, step.getMaterial());
				pstmt.setString(5, step.getCookEquipment());
				pstmt.setString(6, step.getFire());
				pstmt.setString(7, step.getTip());
				pstmt.setString(8, step.getLastTipCaution());
				pstmt.setString(9, step.getImage());
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("[SQL Error : " + e.getMessage() + "]");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void deleteRecipeAndDetails (int recipeID) {
		ConnectionRecipe.connectionRecipe();
		String deleteRecipeSQL = "DELETE FROM RECIPE WHERE recipe_id = ?";
		PreparedStatement pstmt = null;		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(deleteRecipeSQL);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static boolean deleteRecipeCommentVoByID (int commentID) {
		ConnectionRecipe.connectionRecipe();
		String deleteCommentSQL = "DELETE FROM RECIPE_COMMENTS WHERE COMMENT_ID = ?";	
		PreparedStatement pstmt = null;		
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(deleteCommentSQL);
			pstmt.setInt(1, commentID);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static void addRecipeHitByID (int commentID) {
		ConnectionRecipe.connectionRecipe();
		String SQL = "INSERT INTO Recipe_HIT_DATE(RECIPE_ID, HIT_DATE) VALUES (?, to_date(sysdate, 'YYYY-MM-DD'))";	
		PreparedStatement pstmt = null;
		try {
			pstmt = ConnectionRecipe.getConnection().prepareStatement(SQL);
			pstmt.setInt(1, commentID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(ConnectionRecipe.getConnection() != null) ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public RecentRecipeDto getRecentRecipe(int recipeId) {
		// 최근 본 레시피의 리스트를 가져오기 위한 메서드
		// 레시피ID로 이루어진 배열을 받고 해당 ID의 사진, 제목을 Dto 가진 Dto를 생성 후 리스트에 담는다.
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecentRecipeDto recentRecipe = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    title, " + 
					"    thumbnail " + 
					"FROM " + 
					"    recipe " + 
					"WHERE " + 
					"    recipe_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String recipeTitle = rs.getString("title");
				String recipeThumbnail = rs.getString("thumbnail");
				
				recentRecipe = new RecentRecipeDto(recipeId, recipeTitle, recipeThumbnail);
				System.out.println("최근 본 레시피 ID : " + recipeId + ", 제목 : " + recipeTitle + ", 썸네일 : " + recipeThumbnail);
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
		return recentRecipe;
	}
}
