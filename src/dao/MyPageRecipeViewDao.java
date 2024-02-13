package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.ConnectionRecipe;
import dto.MyPageCompleteRecipeViewDto;
import dto.MyPageInCompleteRecipeViewDto;

/*
// 정렬 방식에 따른 sql문 조립
			switch(sortType) {
			case 1 : sql += " ORDER BY r.writedate DESC"; break; // 최신순
			case 2 : sql += " ORDER BY r.hits DESC"; break; // 조회순
			case 3 : sql += " ORDER BY r.likes DESC"; break; // 좋아요순
			default : sql += " ORDER BY r.writedate DESC"; // 만약 잘못된 값을 받아오면 default값인 최신순으로 정렬
			}
*/
/*
레시피 정렬은 로그인 계정 본인의 마이페이지를 바라볼 때 최신순, 조회순, 좋아요순의 정렬을 선택할 수 있고,
타인 계정의 마이페이지는 최신순으로만 정렬되어 있다.
*/
public class MyPageRecipeViewDao {
	public String makeSearchWord(String inputWord) {
		// 입력받은 검색어를 LIKE구문에 적용하기 위한 전처리 작업
		String searchWord = "%" + inputWord + "%";
		return searchWord;
	}
	public int getCompleteRecipePageNum(String uId, String searchWord) {
		// 인수로 받은 유저의 공개중인 레시피의 페이지 수를 계산(1페이지당 12개)
		int pageNum = 0;
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(searchWord == null) { // 검색어가 없을 때
			try {
				String sql = 
						"SELECT " + 
						"    count(*) " + 
						"FROM " + 
						"    recipe " + 
						"WHERE " + 
						"	 complete = 1 " +
						"AND " +
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
			
			if(pageNum%12 == 0) {
				return pageNum/12;
			}
			return pageNum/12 + 1;
		} else { // 검색어가 있을 때
			searchWord = makeSearchWord(searchWord); // 검색어 전처리
			
			try {
				String sql = 
						"SELECT " + 
						"    count(*) " + 
						"FROM " + 
						"    recipe " + 
						"WHERE " + 
						"	 complete = 1 " +
						"AND " + 
						"	 title LIKE ? " +
						"AND " +
						"    u_id = ?";
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setString(2, uId);
				
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
			
			if(pageNum%12 == 0) {
				return pageNum/12;
			}
			return pageNum/12 + 1;
		}
		
	}
	public int getInCompleteRecipePageNum(String uId, String searchWord) {
		// 인수로 받은 유저의 작성중인 레시피의 페이지 수를 계산(1페이지당 12개)
		int pageNum = 0;
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(searchWord == null) { // 검색어가 없을 때
			try {
				String sql = 
						"SELECT " + 
						"    count(*) " + 
						"FROM " + 
						"    recipe " + 
						"WHERE " + 
						"	 complete = 0 " +
						"AND " +
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
			
			if(pageNum%12 == 0) {
				return pageNum/12;
			}
			return pageNum/12 + 1;
		} else { // 검색어가 있을 때
			searchWord = makeSearchWord(searchWord); // 검색어 전처리
			
			try {
				String sql = 
						"SELECT " + 
						"    count(*) " + 
						"FROM " + 
						"    recipe " + 
						"WHERE " + 
						"	 complete = 0 " +
						"AND " + 
						"	 title LIKE ? " + 
						"AND " +
						"    u_id = ?";
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setString(2, uId);
				
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
			
			if(pageNum%12 == 0) {
				return pageNum/12;
			}
			return pageNum/12 + 1;
		}
		
	}
	public ArrayList<MyPageCompleteRecipeViewDto> getCompleteRecipeByIdSortByWritedate(String uId, String searchWord, int pageNum) {
		// 작성 완료된 레시피. 유저의 ID, 검색어를 인수로 받아와 해당하는 레시피를 등록 최신순으로 보여준다.
		// 레시피를 저장할 리스트 객체 생성
		ArrayList<MyPageCompleteRecipeViewDto> recipeList = new ArrayList<MyPageCompleteRecipeViewDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(searchWord == null) { // 검색어가 없을 때
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
						"                    r.thumbnail thumbnail, " + 
						"                    r.title title, " + 
						"                    m.nickname nickname " + 
						"                FROM " + 
						"                    recipe r, " + 
						"                    member m " + 
						"                WHERE " + 
						"                    r.u_id = m.u_id " + 
						"                AND " + 
						"                    r.complete = 1 " + 
						"                AND " + 
						"                    r.u_id = ? " + // 마이페이지 유저 아이디
						"                ORDER BY " + 
						"                    r.writedate DESC " + 
						"            ) t1 " + 
						"    ) t2 " + 
						"WHERE " + 
						"    rnum >= ? " + 
						"AND " + 
						"	 rnum <= ?";
				// 한 페이지에 12개씩 보여줄 것(1~12, 13~24, 25~36, ...)
				int endNum = pageNum * 12; 
				int startNum = endNum - 11;
				
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setInt(2, startNum);
				pstmt.setInt(3, endNum);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int recipeId = rs.getInt("recipe_id"); // 레시피 id
					String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
					String recipeTitle = rs.getString("title"); // 레시피 제목
					String recipeWriterNickname = rs.getString("nickname"); // 작성자 닉네임
					recipeList.add(new MyPageCompleteRecipeViewDto(recipeId, recipeThumbnail, recipeTitle, recipeWriterNickname));
					System.out.println("레시피ID : " + recipeId + " , 썸네일 : " + recipeThumbnail + " , 제목 : " + recipeTitle + " , 작성자 : " + recipeWriterNickname);
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
			
			return recipeList;
		} else { // 검색어가 있을 때
			searchWord = makeSearchWord(searchWord); // 검색어 전처리
			
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
						"                    r.thumbnail thumbnail, " + 
						"                    r.title title, " + 
						"                    m.nickname nickname " + 
						"                FROM " + 
						"                    recipe r, " + 
						"                    member m " + 
						"                WHERE " + 
						"                    r.u_id = m.u_id " + 
						"                AND " + 
						"                    r.complete = 1 " + 
						"                AND " + 
						"                    r.u_id = ? " + 
						"                AND " + 
						"                    r.title LIKE ? " + 
						"                ORDER BY " + 
						"                    r.writedate DESC " + 
						"            ) t1 " + 
						"    ) t2 " + 
						"WHERE " + 
						"    rnum >= ? " + 
						"AND " + 
						"	 rnum <= ?";
				// 한 페이지에 12개씩 보여줄 것(1~12, 13~24, 25~36, ...)
				int endNum = pageNum * 12; 
				int startNum = endNum - 11;
				
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, startNum);
				pstmt.setInt(4, endNum);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int recipeId = rs.getInt("recipe_id"); // 레시피 id
					String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
					String recipeTitle = rs.getString("title"); // 레시피 제목
					String recipeWriterNickname = rs.getString("nickname"); // 작성자 닉네임
					recipeList.add(new MyPageCompleteRecipeViewDto(recipeId, recipeThumbnail, recipeTitle, recipeWriterNickname));
					System.out.println("레시피ID : " + recipeId + " , 썸네일 : " + recipeThumbnail + " , 제목 : " + recipeTitle + " , 작성자 : " + recipeWriterNickname);
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
			
			return recipeList;
		}
	}
	public ArrayList<MyPageCompleteRecipeViewDto> getCompleteRecipeByIdSortByHit(String uId, String searchWord, int pageNum) {
		// 작성 완료된 레시피. 유저의 ID, 검색어를 인수로 받아와 해당하는 레시피를 조회순으로 보여준다.
		// 레시피를 저장할 리스트 객체 생성
		ArrayList<MyPageCompleteRecipeViewDto> recipeList = new ArrayList<MyPageCompleteRecipeViewDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(searchWord == null) { // 검색어가 있을 때
			try {
				String sql =
						"SELECT t2.* " + 
						"FROM " + 
						"    ( " + 
						"        SELECT " + 
						"            rownum rnum, " + 
						"            t1.* " + 
						"        FROM " + 
						"            ( " + 
						"                SELECT " + 
						"                    r2.recipe_id_view recipe_id, " + 
						"                    r1.thumbnail thumbnail, " + 
						"                    r1.title title, " + 
						"                    m.nickname nickname, " + 
						"                    r2.recipe_likes_view recipe_likes " + 
						"                FROM " + 
						"                    recipe r1, " + 
						"                    member m, " + 
						"                    ( " + 
						"                        SELECT " + 
						"                            r.recipe_id recipe_id_view, " + 
						"                            count(rl.recipe_id) recipe_likes_view " + 
						"                        FROM " + 
						"                            recipe r, " + 
						"                            recipe_like rl " + 
						"                        WHERE " + 
						"                            r.recipe_id = rl.recipe_id(+) " + // outer join : 조회수가 0인 레시피의 ID도 조회해야함.(레시피 테이블엔 있지만 레시피 조회날짜 테이블엔 없는 레시피 ID도 조회해야함) 
						"                        GROUP BY " + 
						"                            r.recipe_id " + 
						"                        ORDER BY " + 
						"                            count(rl.recipe_id) DESC " + 
						"                    ) r2 " + 
						"                WHERE " + 
						"                    r1.u_id = m.u_id " + 
						"                AND " + 
						"                    r1.recipe_id = r2.recipe_id_view " + 
						"                AND " + 
						"                    r1.complete = 1 " + 
						"                AND " + 
						"                    r1.u_id = ? " + 
						"                ORDER BY " + 
						"                    r2.recipe_likes_view DESC " + 
						"            ) t1 " + 
						"    ) t2 " + 
						"WHERE " + 
						"    rnum >= ? " + 
						"AND " + 
						"	 rnum <= ?";
				// 한 페이지에 12개씩 보여줄 것(1~12, 13~24, 25~36, ...)
				int endNum = pageNum * 12; 
				int startNum = endNum - 11;
				
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setInt(2, startNum);
				pstmt.setInt(3, endNum);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int recipeId = rs.getInt("recipe_id"); // 레시피 id
					String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
					String recipeTitle = rs.getString("title"); // 레시피 제목
					String recipeWriterNickname = rs.getString("nickname"); // 작성자 닉네임
					recipeList.add(new MyPageCompleteRecipeViewDto(recipeId, recipeThumbnail, recipeTitle, recipeWriterNickname));
					System.out.println("레시피ID : " + recipeId + " , 썸네일 : " + recipeThumbnail + " , 제목 : " + recipeTitle + " , 작성자 : " + recipeWriterNickname);
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
			
			return recipeList;
		} else { // 검색어가 없을 때
			searchWord = makeSearchWord(searchWord); // 검색어 전처리
			
			try {
				String sql =
						"SELECT t2.* " + 
						"FROM " + 
						"    ( " + 
						"        SELECT " + 
						"            rownum rnum, " + 
						"            t1.* " + 
						"        FROM " + 
						"            ( " + 
						"                SELECT " + 
						"                    r2.recipe_id_view recipe_id, " + 
						"                    r1.thumbnail thumbnail, " + 
						"                    r1.title title, " + 
						"                    m.nickname nickname, " + 
						"                    r2.recipe_likes_view recipe_likes " + 
						"                FROM " + 
						"                    recipe r1, " + 
						"                    member m, " + 
						"                    ( " + 
						"                        SELECT " + 
						"                            r.recipe_id recipe_id_view, " + 
						"                            count(rl.recipe_id) recipe_likes_view " + 
						"                        FROM " + 
						"                            recipe r, " + 
						"                            recipe_like rl " + 
						"                        WHERE " + 
						"                            r.recipe_id = rl.recipe_id(+) " + // outer join : 조회수가 0인 레시피의 ID도 조회해야함.(레시피 테이블엔 있지만 레시피 조회날짜 테이블엔 없는 레시피 ID도 조회해야함) 
						"                        GROUP BY " + 
						"                            r.recipe_id " + 
						"                        ORDER BY " + 
						"                            count(rl.recipe_id) DESC " + 
						"                    ) r2 " + 
						"                WHERE " + 
						"                    r1.u_id = m.u_id " + 
						"                AND " + 
						"                    r1.recipe_id = r2.recipe_id_view " + 
						"                AND " + 
						"                    r1.complete = 1 " + 
						"                AND " + 
						"                    r1.u_id = ? " + 
						"                AND " + 
						"                    r1.title LIKE ? " + 
						"                ORDER BY " + 
						"                    r2.recipe_likes_view DESC " + 
						"            ) t1 " + 
						"    ) t2 " + 
						"WHERE " + 
						"    rnum >= ? " + 
						"AND " + 
						"	 rnum <= ?";
				// 한 페이지에 12개씩 보여줄 것(1~12, 13~24, 25~36, ...)
				int endNum = pageNum * 12; 
				int startNum = endNum - 11;
				
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, startNum);
				pstmt.setInt(4, endNum);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int recipeId = rs.getInt("recipe_id"); // 레시피 id
					String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
					String recipeTitle = rs.getString("title"); // 레시피 제목
					String recipeWriterNickname = rs.getString("nickname"); // 작성자 닉네임
					recipeList.add(new MyPageCompleteRecipeViewDto(recipeId, recipeThumbnail, recipeTitle, recipeWriterNickname));
					System.out.println("레시피ID : " + recipeId + " , 썸네일 : " + recipeThumbnail + " , 제목 : " + recipeTitle + " , 작성자 : " + recipeWriterNickname);
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
			
			return recipeList;
		}
		
	}
	public ArrayList<MyPageCompleteRecipeViewDto> getCompleteRecipeByIdSortByLike(String uId, String searchWord, int pageNum) {
		// 작성 완료된 레시피. 유저의 ID, 검색어를 인수로 받아와 해당하는 레시피를 좋아요순으로 보여준다.
		// 레시피를 저장할 리스트 객체 생성
		ArrayList<MyPageCompleteRecipeViewDto> recipeList = new ArrayList<MyPageCompleteRecipeViewDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if(searchWord == null) { // 검색어가 없을 때
			try {
				String sql = 
						"SELECT t2.* " + 
						"FROM " + 
						"    ( " + 
						"        SELECT " + 
						"            rownum rnum, " + 
						"            t1.* " + 
						"        FROM " + 
						"            ( " + 
						"                SELECT " + 
						"                    r2.recipe_id_view recipe_id, " + 
						"                    r1.thumbnail thumbnail, " + 
						"                    r1.title title, " + 
						"                    m.nickname nickname, " + 
						"                    r2.recipe_likes_view recipe_likes " + 
						"                FROM " + 
						"                    recipe r1, " + 
						"                    member m, " + 
						"                    ( " + 
						"                        SELECT " + 
						"                            r.recipe_id recipe_id_view, " + 
						"                            count(rl.recipe_id) recipe_likes_view " + 
						"                        FROM " + 
						"                            recipe r, " + 
						"                            recipe_like rl " + 
						"                        WHERE " + 
						"                            r.recipe_id = rl.recipe_id(+) " + // outer join : 좋아요 수가 0인 레시피의 ID도 조회해야함.(레시피 테이블엔 있지만 레시피 좋아요 테이블엔 없는 레시피 ID도 조회해야함) 
						"                        GROUP BY " + 
						"                            r.recipe_id " + 
						"                        ORDER BY " + 
						"                            count(rl.recipe_id) DESC " + 
						"                    ) r2 " + 
						"                WHERE " + 
						"                    r1.u_id = m.u_id " + 
						"                AND " + 
						"                    r1.recipe_id = r2.recipe_id_view " + 
						"                AND " + 
						"                    r1.complete = 1 " + 
						"                AND " + 
						"                    r1.u_id = ? " + 
						"                ORDER BY " + 
						"                    r2.recipe_likes_view DESC " + 
						"            ) t1 " + 
						"    ) t2 " + 
						"WHERE " + 
						"    rnum >= ? " + 
						"AND " + 
						"	 rnum <= ?";
				// 한 페이지에 12개씩 보여줄 것(1~12, 13~24, 25~36, ...)
				int endNum = pageNum * 12; 
				int startNum = endNum - 11;
				
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setInt(2, startNum);
				pstmt.setInt(3, endNum);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int recipeId = rs.getInt("recipe_id"); // 레시피 id
					String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
					String recipeTitle = rs.getString("title"); // 레시피 제목
					String recipeWriterNickname = rs.getString("nickname"); // 작성자 닉네임
					recipeList.add(new MyPageCompleteRecipeViewDto(recipeId, recipeThumbnail, recipeTitle, recipeWriterNickname));
					System.out.println("레시피ID : " + recipeId + " , 썸네일 : " + recipeThumbnail + " , 제목 : " + recipeTitle + " , 작성자 : " + recipeWriterNickname);
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
			
			return recipeList;
		} else { // 검색어가 있을 때
			searchWord = makeSearchWord(searchWord); // 검색어 전처리
			
			try {
				String sql = 
						"SELECT t2.* " + 
						"FROM " + 
						"    ( " + 
						"        SELECT " + 
						"            rownum rnum, " + 
						"            t1.* " + 
						"        FROM " + 
						"            ( " + 
						"                SELECT " + 
						"                    r2.recipe_id_view recipe_id, " + 
						"                    r1.thumbnail thumbnail, " + 
						"                    r1.title title, " + 
						"                    m.nickname nickname, " + 
						"                    r2.recipe_likes_view recipe_likes " + 
						"                FROM " + 
						"                    recipe r1, " + 
						"                    member m, " + 
						"                    ( " + 
						"                        SELECT " + 
						"                            r.recipe_id recipe_id_view, " + 
						"                            count(rl.recipe_id) recipe_likes_view " + 
						"                        FROM " + 
						"                            recipe r, " + 
						"                            recipe_like rl " + 
						"                        WHERE " + 
						"                            r.recipe_id = rl.recipe_id(+) " + // outer join : 좋아요 수가 0인 레시피의 ID도 조회해야함.(레시피 테이블엔 있지만 레시피 좋아요 테이블엔 없는 레시피 ID도 조회해야함) 
						"                        GROUP BY " + 
						"                            r.recipe_id " + 
						"                        ORDER BY " + 
						"                            count(rl.recipe_id) DESC " + 
						"                    ) r2 " + 
						"                WHERE " + 
						"                    r1.u_id = m.u_id " + 
						"                AND " + 
						"                    r1.recipe_id = r2.recipe_id_view " + 
						"                AND " + 
						"                    r1.complete = 1 " + 
						"                AND " + 
						"                    r1.u_id = ? " + 
						"                AND " + 
						"                    r1.title LIKE ? " + 
						"                ORDER BY " + 
						"                    r2.recipe_likes_view DESC " + 
						"            ) t1 " + 
						"    ) t2 " + 
						"WHERE " + 
						"    rnum >= ? " + 
						"AND " + 
						"	 rnum <= ?";
				// 한 페이지에 12개씩 보여줄 것(1~12, 13~24, 25~36, ...)
				int endNum = pageNum * 12; 
				int startNum = endNum - 11;
				
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, startNum);
				pstmt.setInt(4, endNum);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int recipeId = rs.getInt("recipe_id"); // 레시피 id
					String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
					String recipeTitle = rs.getString("title"); // 레시피 제목
					String recipeWriterNickname = rs.getString("nickname"); // 작성자 닉네임
					recipeList.add(new MyPageCompleteRecipeViewDto(recipeId, recipeThumbnail, recipeTitle, recipeWriterNickname));
					System.out.println("레시피ID : " + recipeId + " , 썸네일 : " + recipeThumbnail + " , 제목 : " + recipeTitle + " , 작성자 : " + recipeWriterNickname);
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
			
			return recipeList;
		}
	}
	public ArrayList<MyPageInCompleteRecipeViewDto> getInCompleteRecipeSortByWritedate(String uId, String searchWord, int pageNum) {
		// 작성중인 레시피. 작성 순서(수정 순서) 내림차순 정렬이고 본인 계정의 마이페이지에서 본인이 작성중인 레시피만 확인 가능하다.
		// 레시피를 저장할 리스트 객체 생성
		ArrayList<MyPageInCompleteRecipeViewDto> recipeList = new ArrayList<MyPageInCompleteRecipeViewDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if(searchWord == null) { // 검색어가 없을 때
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
						"                    recipe_id, " + 
						"                    thumbnail, " + 
						"                    title " + 
						"                FROM " + 
						"                    recipe " + 
						"                WHERE " + 
						"                    complete = 0 " + 
						"                AND " + 
						"                    u_id = ? " + 
						"                ORDER BY " + 
						"                    update_date DESC " + 
						"            ) t1 " + 
						"    ) t2 " + 
						"WHERE " + 
						"    rnum >= ? " + 
						"AND " + 
						"    rnum <= ?";
				// 한 페이지에 12개씩 보여줄 것(1~12, 13~24, 25~36, ...)
				int endNum = pageNum * 12; 
				int startNum = endNum - 11;
				
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setInt(2, startNum);
				pstmt.setInt(3, endNum);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int recipeId = rs.getInt("recipe_id"); // 레시피 id
					String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
					String recipeTitle = rs.getString("title"); // 레시피 제목
					
					recipeList.add(new MyPageInCompleteRecipeViewDto(recipeId, recipeThumbnail, recipeTitle));
					System.out.println("recipeId : " + recipeId + ", thumbnail : " + recipeThumbnail + ", title : " + recipeTitle);
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
		} else { // 검색어가 있을 때
			searchWord = makeSearchWord(searchWord); // 검색어 전처리
			
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
						"                    recipe_id, " + 
						"                    thumbnail, " + 
						"                    title " + 
						"                FROM " + 
						"                    recipe " + 
						"                WHERE " + 
						"                    complete = 0 " + 
						"                AND " + 
						"                    u_id = ? " + 
						"                AND " + 
						"                    title LIKE ? " + 
						"                ORDER BY " + 
						"                    update_date DESC " + 
						"            ) t1 " + 
						"    ) t2 " + 
						"WHERE " + 
						"    rnum >= ? " + 
						"AND " + 
						"    rnum <= ?";
				// 한 페이지에 12개씩 보여줄 것(1~12, 13~24, 25~36, ...)
				int endNum = pageNum * 12; 
				int startNum = endNum - 11;
				
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, startNum);
				pstmt.setInt(4, endNum);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int recipeId = rs.getInt("recipe_id"); // 레시피 id
					String recipeThumbnail = rs.getString("thumbnail"); // 레시피 썸네일
					String recipeTitle = rs.getString("title"); // 레시피 제목
					
					recipeList.add(new MyPageInCompleteRecipeViewDto(recipeId, recipeThumbnail, recipeTitle));
					System.out.println("recipeId : " + recipeId + ", thumbnail : " + recipeThumbnail + ", title : " + recipeTitle);
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
		}
		return recipeList;
	}
}
