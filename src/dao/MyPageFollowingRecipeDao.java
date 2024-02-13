package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import common.ConnectionRecipe;
import dto.MyPageFollowingRecipeDto;

public class MyPageFollowingRecipeDao {
	// 싱글톤 패턴 적용
	private static MyPageFollowingRecipeDao myFollowingRecipeDao = new MyPageFollowingRecipeDao();
	private MyPageFollowingRecipeDao() { }
	public static MyPageFollowingRecipeDao getMyFollowingRecipeDao() {
		return myFollowingRecipeDao;
	}
	public int getFollowingRecipePageNum(String uId) {
		// 인수로 받은 유저의 팔로잉 레시피의 페이지 수를 계산(1페이지에 8개씩)
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int pageNum = 0; // 팔로잉레시피의 개체 수를 담아둘 변수
		
		try {
			String sql = 
					"SELECT " + 
					"    count(*) " + 
					"FROM " + 
					"    recipe_like " + 
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
		
		if(pageNum%8 == 0) {
			return pageNum/8;
		}
		return pageNum/8 + 1;
	}
	public ArrayList<MyPageFollowingRecipeDto> getMyFollowingRecipe(String uId, int pageNum) {
		// 인수로 받은 유저가 팔로잉중인 레시피를 가져오는 메서드
		// 팔로잉 레시피를 저장할 리스트 생성
		ArrayList<MyPageFollowingRecipeDto> myFollowingRecipeList = new ArrayList<MyPageFollowingRecipeDto>();
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
					"                    r.title title, " + 
					"                    r.thumbnail thumbnail, " + 
					"                    m.nickname nickname " + 
					"                FROM " + 
					"                    recipe r, " + 
					"                    recipe_like rl, " + 
					"                    member m " + 
					"                WHERE " + 
					"                    r.recipe_id = rl.recipe_id " + 
					"                AND " + 
					"                    r.u_id = m.u_id " + 
					"                AND " + 
					"                    rl.u_id = ? " + 
					"                ORDER BY " + 
					"                    rl.like_date DESC " + 
					"            ) t1 " + 
					"    ) t2 " + 
					"WHERE " + 
					"    rnum >= ? " + 
					"AND " + 
					"    rnum <= ?";
			// 한 페이지당 8개씩
			int endNum = pageNum * 8;
			int startNum = endNum - 7;
			
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setInt(2, startNum);
			pstmt.setInt(3, endNum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int recipeId = rs.getInt("recipe_id"); // 레시피ID
				String recipeTitle = rs.getString("title"); // 레시피 제목
				String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
				String writerNickname = rs.getString("nickname"); // 작성자 닉네임
				
				myFollowingRecipeList.add(new MyPageFollowingRecipeDto(recipeId, recipeTitle, recipeThumbnail, writerNickname));
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
		
		return myFollowingRecipeList;
	}
	public boolean deleteFollowingRecipe(String uId, int recipeId) {
		// 인수로 받은 유저가 인수로 받은 레시피ID의 팔로우를 해제하는 메서드
		// 복수의 레시피ID가 들어올 수 있기 때문에 중복값이 없는 Set자료구조로 받아옴
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		
		try {
			String sql = 
					"DELETE FROM " + 
					"    recipe_like " + 
					"WHERE " + 
					"    u_id = ? " + 
					"AND " + 
					"    recipe_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setInt(2, recipeId);
			pstmt.executeUpdate();
			System.out.println(uId + "님이 " + recipeId + "번 레시피의 팔로우를 해제했습니다.");
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
	public boolean addFollowingRecipe(String uId, int recipeId) {
		// 인수로 받은 유저가 인수로 받은 레시피ID의 레시피를 팔로우하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		
		try {
			String sql = 
					"INSERT INTO " + 
					"    recipe_like " + 
					"    ( " + 
					"      u_id, " + 
					"      recipe_id, " + 
					"      like_date " + 
					"    ) " + 
					"VALUES " + 
					"    ( " + 
					"        ?, " + 
					"        ?, " + 
					"        sysdate " + 
					"    );";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setInt(2, recipeId);
			pstmt.executeUpdate();
			System.out.println(uId + "님이 " + recipeId + "번 레시피를 팔로우했습니다.");
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
