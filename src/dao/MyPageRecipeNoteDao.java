package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.ConnectionRecipe;
import dto.MyPageRecipeNoteDto;

/*
레시피노트 관련 메서드
레시피노트는 한 유저가 하나의 레시피에 한 개씩만 작성할 수 있다.
*/
public class MyPageRecipeNoteDao {
	// 싱글톤 패턴 적용
	private static MyPageRecipeNoteDao myRecipeNoteDao = new MyPageRecipeNoteDao();
	private MyPageRecipeNoteDao() { }
	public static MyPageRecipeNoteDao getMyRecipeNoteDao() {
		return myRecipeNoteDao;
	}
	public int getRecipeNotePageNum(String uId) {
		// 인수로 받은 유저의 레시피 노트의 페이지 수를 계산(1페이지당 4개)
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
					"    recipe_note " + 
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
		
		if(pageNum%4 == 0) {
			return pageNum/4;
		}
		return pageNum/4 + 1;
	}
	public ArrayList<MyPageRecipeNoteDto> getMyRecipeNote(String uId, int pageNum) {
		// 인수로 받은 유저의 레시피 노트 전부를 가져오는 메서드(마이페이지에서 사용)
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 인수로 받은 유저의 레시피 노트를 저장할 리스트 생성
		ArrayList<MyPageRecipeNoteDto> myRecipeNoteList = new ArrayList<MyPageRecipeNoteDto>();
		
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
					"                    r.title recipe_title, " + 
					"                    r.thumbnail recipe_thumbnail, " + 
					"                    rn.content note_content, " + 
					"                    rn.writedate note_writedate " + 
					"                FROM " + 
					"                    recipe r, " + 
					"                    recipe_note rn " + 
					"                WHERE " + 
					"                    r.recipe_id = rn.recipe_id " + 
					"                AND " + 
					"                    rn.u_id = ? " + // 인수로 받은 유저 아아디
					"                ORDER BY " + 
					"                    note_writedate DESC " + 
					"            ) t1 " + 
					"    ) t2 " + 
					"WHERE " + 
					"    rnum >= ? " + 
					"AND " + 
					"    rnum <= ?";
			// 한 페이지에 4개씩 보여줄 것
			int endNum = pageNum * 4;
			int startNum = endNum - 3;
			
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setInt(2, startNum);
			pstmt.setInt(3, endNum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int recipeId = rs.getInt("recipe_id"); // 레시피 노트가 적힌 레시피 번호
				String recipeTitle = rs.getString("recipe_title"); // 위의 레시피의 제목
				String recipeThumbnail = rs.getString("recipe_thumbnail"); // 위의 레시피의 썸네일
				String noteContent = rs.getString("note_content"); // 레시피 노트 내용
				String noteWritedate = rs.getString("note_writedate"); // 레시피 노트 작성날짜
				
				myRecipeNoteList.add(new MyPageRecipeNoteDto(recipeId, recipeTitle, recipeThumbnail, noteContent, noteWritedate));
				System.out.println("레시피ID : " + recipeId + " , 레시피제목 : " + recipeTitle + " , 레시피썸네일 : " + recipeThumbnail + " , 노트내용 : " + noteContent + " , 작성날짜 : " + noteWritedate);
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
		
		return myRecipeNoteList;
	}
	public String getMyRecipeNoteByRecipeId(String uId, int recipeId) {
		// 인수로 받은 로그인 유저가 인수로 받은 레시피ID에 작성한 레시피 노트를 가져오는 메서드(레시피 상세페이지에서 사용)
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 레시피 노트 내용을 받을 변수 생성
		String noteContent = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    rn.content content " + 
					"FROM " + 
					"    recipe r, " + 
					"    recipe_note rn " + 
					"WHERE " + 
					"    r.recipe_id = rn.recipe_id " +
					"AND " + 
					"	 r.recipe_id = ? " + 
					"AND " + 
					"    rn.u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeId);
			pstmt.setString(2, uId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				noteContent = rs.getString("content"); // 레시피 노트 내용
				System.out.println(uId + "가 작성한 " + recipeId + "번의 노트 내용 : " + noteContent);
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
			
		return noteContent;
	}
	public boolean deleteRecipeNote(int recipeId, String uId) {
		// 인수로 받은 유저의 인수로 받은 레시피ID에 작성된 노트를 삭제하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		
		try {
			String sql = 
					"DELETE FROM " + 
					"    recipe_note " + 
					"WHERE " + 
					"    recipe_id = ? " +
					"AND " + 
					"	 u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeId); // 레시피 ID
			pstmt.setString(2, uId); // 유저 ID
			pstmt.executeUpdate();
			System.out.println(uId + "의 " + recipeId + "번 레시피노트 삭제 완료.");
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
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
	public boolean modifyRecipeNote(int recipeId, String uId, String noteContent) {
		// 인수로 받은 유저의 인수로 받은 레시피ID에 작성된 노트 내용을 인수로 받은 noteContent로 업데이트하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		
		try {
			String sql = 
					"UPDATE " + 
					"    recipe_note " + 
					"SET " + 
					"    content = ? " + 
					"WHERE " + 
					"    recipe_id = ? " + 
					"AND " + 
					"	 u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, noteContent); // 업데이트할 노트 내용
			pstmt.setInt(2, recipeId); // 레시피 ID
			pstmt.setString(3, uId); // 유저 ID
			pstmt.executeUpdate();
			System.out.println(uId + "의 " + recipeId + "번 레시피노트 업데이트 완료");
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
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
	public boolean writeRecipeNote(int recipeId, String uId, String noteContent, String noteWritedate) {
		// 인수로 받은 유저가 인수로 받은 레시피 번호에 해당하는 레시피에 노트를 작성하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		
		try {
			String sql = 
					"INSERT INTO " + 
					"    recipe_note " + 
					"    ( " + 
					"        recipe_id, " + 
					"        u_id, " + 
					"        content, " + 
					"        writedate " + 
					"    ) " + 
					"VALUES " + 
					"    ( " + 
					"        ?, " + 
					"        ?, " + 
					"        ?, " + 
					"        sysdate " + 
					"    )";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, recipeId); // 해당 레시피의 ID
			pstmt.setString(2, uId); // 로그인 유저 아이디
			pstmt.setString(3, noteContent); // 레시피 노트 내용
			pstmt.executeUpdate();
			System.out.println(recipeId + "번 레시피의 레시피노트 작성 완료");
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
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
}
