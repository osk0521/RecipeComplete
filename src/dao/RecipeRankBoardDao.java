package dao;

import java.sql.*;
import java.util.ArrayList;

import common.ConnectionRecipe;
import vo.RecipeRankVo;
import vo.RecipeUserRankVo;
public class RecipeRankBoardDao {
	
	public ArrayList<RecipeRankVo> getTop100Recipe(String ptype) {
		ArrayList<RecipeRankVo> top100RecipeList = new ArrayList<RecipeRankVo>();

		ConnectionRecipe.connectionRecipe();
		String day="rhd.hit_date < TRUNC(SYSDATE) - INTERVAL '0' DAY "; // 일간 
		String week="rhd.hit_date < TRUNC(SYSDATE) - INTERVAL '7' DAY "; // 주간 
		String month="rhd.hit_date < TRUNC(SYSDATE) - INTERVAL '1' MONTH "; // 월간 
		String period = null;
		switch (ptype) {
		case "d":
			period = day;
			break;
		case "w":
			period = week;
			break;
		case "M":
			period = month;
		default:
			period = day;
			break;
		}
		try {
			String sqlForRecipe =
					"SELECT t2.* \r\n" + 
					"FROM (SELECT rownum rnum, t.* \r\n" + 
					"FROM (SELECT r.THUMBNAIL AS thumbnail, r.recipe_id AS recipe_id, r.title AS title, m.U_ID as UserID, m.nickname AS writer, m.PROFILE_IMAGE as PROFILE, count(rhd.hit_date) as HITCOUNT \r\n" + 
					"FROM RECIPE r join RECIPE_HIT_DATE rhd on (r.recipe_id = rhd.recipe_id) \r\n" + 
					"join MEMBER m on (r.u_id = m.u_id) \r\n" + 
					"where (r.magazine_id = 0 AND r.DELETEDPOST != 1 AND r.DELETEDACCOUNT != 1 AND r.complete = 1 AND ("+period+")) GROUP BY r.recipe_id, r.THUMBNAIL, r.title, r.star, m.nickname, \r\n" + 
					"m.PROFILE_IMAGE, m.U_ID \r\n" + 
					"ORDER BY count(rhd.hit_date) DESC, r.star DESC) t)t2 \r\n" + 
					"WHERE (t2.rnum >= 1 AND t2.rnum<= 100)";
			PreparedStatement pstmt = ConnectionRecipe.getConnection().prepareStatement(sqlForRecipe);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String userID = rs.getString("UserID");
				int recipeID = rs.getInt("RECIPE_ID");
				String thumbnail = rs.getString("THUMBNAIL");
				String title = rs.getString("TITLE");
				int hits = rs.getInt("HITCOUNT");
				String nickname = rs.getString("writer");
				String profileImage = rs.getString("PROFILE");
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
					
				top100RecipeList.add(new RecipeRankVo(recipeID, thumbnail, title, hits, commentCnt,userID, nickname, profileImage));
				
				}
				rs.close();
				pstmt.close();
				ConnectionRecipe.disConnectionRecipe();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		return top100RecipeList;
	}
	
	public ArrayList<RecipeUserRankVo> getTop100User(String ptype) {
		ArrayList<RecipeUserRankVo> top100UserList = new ArrayList<RecipeUserRankVo>();

		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String period = null;
		String day="mf.CREATE_DATE < TRUNC(SYSDATE) - INTERVAL '0' DAY "; // 일간 
		String week="mf.CREATE_DATE < TRUNC(SYSDATE) - INTERVAL '7' DAY "; // 주간 
		String month="mf.CREATE_DATE < TRUNC(SYSDATE) - INTERVAL '1' MONTH "; // 월간
		switch (ptype) {
		case "d":
			period = day;
			break;
		case "w":
			period = week;
			break;
		case "m":
			period = month;
		default:
			period = day;
			break;
		}
		try {
			String sqlForUser =
					"SELECT t2.* FROM\r\n" + 
					"(SELECT rownum rnum, t.* FROM\r\n" + 
					"(SELECT m.U_ID as UserID, m.PROFILE_IMAGE AS PROFILE, m.NICKNAME AS NICKNAME\r\n" + 
					"FROM MEMBER m\r\n" + 
					"join MEMBER_FOLLOW mf on (m.U_ID = mf.U_ID)\r\n" + 
					"where ("+period+")"+
					"group by m.PROFILE_IMAGE, m.NICKNAME, m.U_ID\r\n" + 
					"order by count(mf.u_id_followtarget) desc ) t)t2\r\n" + 
					"WHERE (t2.rnum >= 1 AND t2.rnum <= 100)";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sqlForUser);
			rs = pstmt.executeQuery();
			while(rs.next()) { 
				String userID = rs.getString("UserID");
				String nickname = rs.getString("NICKNAME");
				String profile = rs.getString("PROFILE");
				top100UserList.add(new RecipeUserRankVo(userID, nickname, profile));
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
		
		return top100UserList;
	}
	
	public ArrayList<String> getTop100Searchword(String ptype) {
		ArrayList<String> top100Searchword = new ArrayList<String>();

		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String period = null;
		String Day="SRCH_DATE < TRUNC(SYSDATE) - INTERVAL '0' DAY "; // 일간 
		String Week="SRCH_DATE < TRUNC(SYSDATE) - INTERVAL '7' DAY "; // 주간 
		String Month="SRCH_DATE < TRUNC(SYSDATE) - INTERVAL '1' MONTH "; // 월간
		switch (ptype) {
		case "d":
			period = Day;
			break;
		case "w":
			period = Week;
			break;
		case "m":
			period = Month;
		default:
			period = Day;
			break;
		}
		
		try {
			String sqlForSearchWord = 
					"SELECT t2.* FROM \r\n" + 
					"(SELECT rownum rnum, t.* FROM \r\n" + 
					"(SELECT searchword \r\n" + 
					"FROM Ranking_SearchWord \r\n" + 
					"WHERE ("+period+")"+
					"GROUP BY searchword \r\n" + 
					"ORDER BY count(searchword) DESC) t)t2 \r\n" + 
					"WHERE (t2.rnum >= 1 AND t2.rnum <= 100)";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sqlForSearchWord);
			rs = pstmt.executeQuery();
			while(rs.next()) { 
				String searchWord = rs.getString("searchword");
				top100Searchword.add(searchWord);
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
		return top100Searchword;
		}
	}