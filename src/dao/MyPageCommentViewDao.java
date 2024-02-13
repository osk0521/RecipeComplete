package dao;

import java.sql.*;
import java.util.ArrayList;

import common.ConnectionRecipe;
import dto.MyPageCommentViewDto;

public class MyPageCommentViewDao {
	// 인수로 받은 유저의 레시피에 작성된 댓글을 1페이지당 9개의 댓글로 묶어 페이지수를 반환
	public int getReceiveCommentPageNum(String uId) {
		int pageNum = 0;
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    count(*) " + 
					"FROM " + 
					"    recipe_comments rc, " + 
					"    recipe r " + 
					"WHERE " + 
					"    rc.recipe_id = r.recipe_id " + 
					"AND " + 
					"    r.u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pageNum = rs.getInt("count(*)");
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
		
		if(pageNum%9 == 0) {
			return pageNum/9;
		}
		return pageNum/9 + 1;
	}
	// 인수로 받은 유저가 작성한 댓글을 1페이지당 9개의 댓글로 묶어 페이지수를 반환
	public int getWriteCommentPageNum(String uId) {
		int pageNum = 0;
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    count(*) " + 
					"FROM " + 
					"    recipe_comments " + 
					"WHERE " + 
					"    u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pageNum = rs.getInt("count(*)");
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
		
		if(pageNum%9 == 0) {
			return pageNum/9;
		}
		return pageNum/9 + 1;
	}
	// 인수로 받은 유저가 작성한 댓글을 작성날짜 기준 내림차순으로 가져옴
	public ArrayList<MyPageCommentViewDto> getMyCommentSortByWritedate(String uId, int pageNum) {
		ArrayList<MyPageCommentViewDto> myCommentList = new ArrayList<MyPageCommentViewDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    t2.* " + 
					"FROM " + 
					"    ( " + 
					"        SELECT " + 
					"            rownum rnum, " + 
					"            t1.* " + 
					"        FROM " + 
					"            ( " + 
					"                SELECT " + 
					"                    r.recipe_id recipe_id, " + 
					"                    rc.comment_id comment_id, " + 
					"                    r.thumbnail thumbnail, " + 
					"                    rc.content content, " + 
					"                    m.nickname nickname, " + 
					"                    NVL(rc.star, 0) star, " + 
					"                    rc.writedate writedate " + 
					"                FROM " + 
					"                    recipe r, recipe_comments rc, member m " + 
					"                WHERE " + 
					"                    r.recipe_id = rc.recipe_id " + 
					"                AND " + 
					"                    rc.u_id = m.u_id " + 
					"                AND " + 
					"                    rc.u_id = ? " + 
					"                ORDER BY " + 
					"                    rc.writedate DESC " + 
					"            ) t1 " + 
					"    ) t2 " + 
					"WHERE " + 
					"    rnum >= ? " + 
					"AND " + 
					"    rnum <= ?";
			// 한 페이지당 9개씩 보여줄 것(1~9, 10~18, 19~27, ...)
			int endNum = pageNum * 9;
			int startNum = endNum - 8;
			
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setInt(2, startNum);
			pstmt.setInt(3, endNum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int recipeId = rs.getInt("recipe_id"); // 레시피 ID
				int commentId = rs.getInt("comment_id"); // 댓글 ID
				String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
				String commentContent = rs.getString("content"); // 댓글 내용
				String commentWriterNickname = rs.getString("nickname"); // 댓글 작성자 닉네임
				int commentStar = rs.getInt("star"); // 댓글 별점
				String commentWritedate = rs.getString("writedate"); // 댓글 작성날짜
				System.out.println("레시피ID : " + recipeId + " , 댓글ID : " + commentId + " , 레시피썸네일 : " + recipeThumbnail + " , 댓글내용 : " + commentContent + " , 댓글작성자 : " + commentWriterNickname + " , 댓글별점 : " + commentStar + " , 작성날짜 : " + commentWritedate);
				myCommentList.add(new MyPageCommentViewDto(recipeId, commentId, recipeThumbnail, commentContent, commentWriterNickname, commentStar, commentWritedate));
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
		
		return myCommentList;
	}
	// 인수로 받은 유저의 레시피에 작성된 댓글을 작성날짜 기준 내림차순으로 가져옴
	public ArrayList<MyPageCommentViewDto> getReceiveCommentSortByWritedate(String uId, int pageNum) {
		ArrayList<MyPageCommentViewDto> receiveCommentList = new ArrayList<MyPageCommentViewDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    t2.* " + 
					"FROM " + 
					"    ( " + 
					"        SELECT " + 
					"            rownum rnum, " + 
					"            t1.* " + 
					"        FROM " + 
					"            ( " + 
					"                SELECT " + 
					"                    r.recipe_id recipe_id, " + 
					"                    rc.comment_id comment_id, " + 
					"                    r.thumbnail thumbnail, " + 
					"                    rc.content content, " + 
					"                    m.nickname nickname, " + 
					"                    NVL(rc.star, 0) star, " + 
					"                    rc.writedate writedate " + 
					"                FROM " + 
					"                    recipe r, recipe_comments rc, member m " + 
					"                WHERE " + 
					"                    r.recipe_id = rc.recipe_id " + 
					"                AND " + 
					"                    rc.u_id = m.u_id " + 
					"                AND " + 
					"                    r.u_id = ? " + 
					"                ORDER BY " + 
					"                    rc.writedate DESC " + 
					"            ) t1 " + 
					"    ) t2 " + 
					"WHERE " + 
					"    rnum >= ? " + 
					"AND " + 
					"    rnum <= ?";
			// 한 페이지당 9개씩 보여줄 것(1~9, 10~18, 19~27, ...)
			int endNum = pageNum * 9;
			int startNum = endNum - 8;
			
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setInt(2, startNum);
			pstmt.setInt(3, endNum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int recipeId = rs.getInt("recipe_id"); // 레시피 ID
				int commentId = rs.getInt("comment_id"); // 댓글 ID
				String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
				String commentContent = rs.getString("content"); // 댓글 내용
				String commentWriterNickname = rs.getString("nickname"); // 댓글 작성자 닉네임
				int commentStar = rs.getInt("star"); // 댓글 별점
				String commentWritedate = rs.getString("writedate"); // 댓글 작성날짜
				System.out.println("레시피ID : " + recipeId + " , 댓글ID : " + commentId + " , 레시피썸네일 : " + recipeThumbnail + " , 댓글내용 : " + commentContent + " , 댓글작성자 : " + commentWriterNickname + " , 댓글별점 : " + commentStar + " , 작성날짜 : " + commentWritedate);
				receiveCommentList.add(new MyPageCommentViewDto(recipeId, commentId, recipeThumbnail, commentContent, commentWriterNickname, commentStar, commentWritedate));
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
		
		return receiveCommentList;
	}
}
