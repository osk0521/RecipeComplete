package dao;

import java.sql.*;
import java.util.ArrayList;

import common.ConnectionRecipe;
import dto.MyPageFollowingChefDto;
import dto.RecentReceiveCommentOfFollowingChefDto;
import dto.RecentRecipeOfFollowingChefDto;

public class MyPageFollowingChefDao {
	public String makeSearchWord(String inputWord) {
		// 입력받은 검색어를 LIKE구문에 적용하기 위한 전처리 작업
		String searchWord = "%" + inputWord + "%";
		return searchWord;
	}
	public boolean checkFollowing(String uId, String otherUid) {
		// 인수로 받은 유저1(로그인 유저)가 인수로 받은 유저2(타인)를 팔로잉중인지 파악하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    * " + 
					"FROM " + 
					"    member_follow " + 
					"WHERE " + 
					"    u_id = ? " + 
					"AND " + 
					"    u_id_followtarget = ? ";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setString(2, otherUid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
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
		return false;
	}
	public int getFollowingChefPageNum(String uId, String searchWord) {
		// 인수로 받은 유저의 팔로잉 쉐프의 페이지 수를 검색어를 적용하여 계산(1페이지당 5개)
		int pageNum = 0;
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(searchWord == null) { // 검색어가 없다면
			try {
				String sql = 
						"SELECT DISTINCT " + 
						"    mf.u_id_followtarget " + 
						"FROM " + 
						"    member_follow mf, " + 
						"    recipe r " + 
						"WHERE " + 
						"    mf.u_id_followtarget = r.u_id " + 
						"AND " + 
						"    mf.u_id = ?";
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pageNum++;
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
			
			if(pageNum%5 == 0) {
				return pageNum/5;
			}
			return pageNum/5 + 1;
		} else { // 검색어가 있다면
			searchWord = makeSearchWord(searchWord); // 검색어 전처리
			
			try {
				String sql = 
						"SELECT DISTINCT " + 
						"    t1.* " + 
						"FROM " + 
						"    (    " + 
						"        SELECT " + 
						"            mf.u_id_followtarget followtarget, " + 
						"            m.nickname nickname" + 
						"        FROM " + 
						"            member m, " + 
						"            member_follow mf " + 
						"        WHERE " + 
						"            m.u_id = mf.u_id_followtarget " + 
						"        AND " + 
						"            mf.u_id = ? " + 
						"        AND " + 
						"            m.nickname LIKE ? " + 
						"    ) t1, " + 
						"    recipe r " + 
						"WHERE " + 
						"    t1.followtarget = r.u_id";
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setString(2, searchWord);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pageNum++;
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
			
			if(pageNum%5 == 0) {
				return pageNum/5;
			}
			return pageNum/5 + 1;
		}
	}
	public ArrayList<MyPageFollowingChefDto> getContentsOfFollowingChef(ResultSet rs) {
		// 선행 SQL결과를 담은 rs객체를 받아서 2차적으로 필요한 정보를 SELECT하는 메서드
		// JDBC 객체 생성
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		// MyPageFollowingChefDTO 객체를 담아둘 리스트 생성
		ArrayList<MyPageFollowingChefDto> myPageFollowingChefList = new ArrayList<MyPageFollowingChefDto>();
		
		try {
			while(rs.next()) {
				// MyPageFollowingChefDTO 객체를 생성하기 위한 변수 초기화
				String followingChefUid = null;
				String followingChefNickname = null;
				String followingChefProfileImage = null;
				int recipeQtyOfFollowingChef = 0;
				int recipeLikeQtyOfFollowingChef = 0;
				int recipeHitQtyOfFollowingChef = 0;
				ArrayList<RecentRecipeOfFollowingChefDto> recentRecipeListOfFollowingChef = new ArrayList<RecentRecipeOfFollowingChefDto>();
				ArrayList<RecentReceiveCommentOfFollowingChefDto> recentReceiveCommentListOfFollowingChef = new ArrayList<RecentReceiveCommentOfFollowingChefDto>();
				int followerQtyOfFollowingChef = 0;
				
				followingChefUid = rs.getString("u_id"); // 인수로 받은 유저가 팔로잉하는 유저의 아이디
				followingChefNickname = rs.getString("nickname"); // followingChef의 닉네임
				followingChefProfileImage = rs.getString("profile_image"); // followingChef의 프로필 사진
				System.out.println("팔로잉 유저ID : " + followingChefUid + " , 닉네임 : " + followingChefNickname + " , 프로필사진 : " + followingChefProfileImage);
				
				try {
					String sql2 = 
							"SELECT " + 
							"    count(r.recipe_id) recipeQty " + 
							"FROM " + 
							"    member m, " + 
							"    recipe r " + 
							"WHERE " + 
							"    m.u_id = r.u_id " + 
							"AND " + 
							"    r.complete = 1 " + 
							"AND " + 
							"    r.u_id = ?";
					pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
					pstmt2.setString(1, followingChefUid);
					
					rs2 = pstmt2.executeQuery();
					
					if(rs2.next()) {
						recipeQtyOfFollowingChef = rs2.getInt("recipeQty"); // followingChef의 공개중인 레시피 개수
						System.out.println("레시피 개수 : " + recipeQtyOfFollowingChef);
					}
				} catch(SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if(rs2 != null) rs2.close();
						if(pstmt2 != null) pstmt2.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				} // 첫 번째 2중쿼리문 종료
				
				try {
					String sql2 = 
							"SELECT " + 
							"    count(rl.recipe_id) recipeLikeQty, " + 
							"    count(rhd.recipe_id) recipeHitQty " + 
							"FROM " + 
							"    recipe r, " + 
							"    recipe_like rl, " + 
							"    recipe_hit_date rhd " + 
							"WHERE " + 
							"    r.recipe_id = rl.recipe_id(+) " + // 레시피 좋아요 부분은 null값이어도 레시피 조회수는 존재할 수 있다.
							"AND " + 
							"    r.recipe_id = rhd.recipe_id " + // 반면 레시피 조회수가 null인데 레시피 좋아요가 존재할 수는 없다.
							"AND " + 
							"    r.complete = 1 " + 
							"AND " + 
							"    r.u_id = ?";
					pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
					pstmt2.setString(1, followingChefUid);
					
					rs2 = pstmt2.executeQuery();
					
					if(rs2.next()) {
						recipeLikeQtyOfFollowingChef = rs2.getInt("recipeLikeQty");
						recipeHitQtyOfFollowingChef = rs2.getInt("recipeHitQty");
						System.out.println("레시피 좋아요 수 : " + recipeLikeQtyOfFollowingChef);
						System.out.println("레시피 조회수 : " + recipeHitQtyOfFollowingChef);
					}
				} catch(SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if(rs2 != null) rs2.close();
						if(pstmt2 != null) pstmt2.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				} // 두 번째 2중쿼리문 종료
				
				try {
					// followingChef의 최근 레시피 최대 4개
					String sql2 = 
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
							"                    recipe_id recipe_id, " + 
							"                    thumbnail thumbnail, " + 
							"                    update_date update_date " + 
							"                FROM " + 
							"                    recipe " + 
							"                WHERE " + 
							"				 	 complete = 1 " + 
							"				 AND " + 	
							"                    u_id = ? " + 
							"                ORDER BY " + 
							"                    update_date DESC " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= 1 " + 
							"AND " + 
							"    rnum <= 4";
					pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
					pstmt2.setString(1, followingChefUid);
					
					rs2 = pstmt2.executeQuery();
					
					System.out.println("-최근레시피 4개-");
					while(rs2.next()) {
						int recipeId = rs2.getInt("recipe_id"); // followingChef의 최근 레시피의 ID
						String thumbnail = rs2.getString("thumbnail"); // followingChef의 최근 레시피의 썸네일
						String updateDate = rs2.getString("update_date"); // followingChef의 최근 레시피의 업데이트 날짜(혹은 등록 날짜)
						System.out.println("레시피ID : " + recipeId + " , 썸네일 : " + thumbnail + " , 최근 수정날짜 : " + updateDate);
						
						recentRecipeListOfFollowingChef.add(new RecentRecipeOfFollowingChefDto(recipeId, thumbnail, updateDate));
					}
				} catch(SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if(rs2 != null) rs2.close();
						if(pstmt2 != null) pstmt2.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				} // 세 번째 2중쿼리문 종료
				
				try {
					// followingChef의 레시피에 달린 최근 댓글 2개
					String sql2 = 
							"SELECT " + 
							"    t2.* " + 
							"FROM " + 
							"    ( " + 
							"        SELECT " + 
							"            rownum rnum, " + 
							"            m.nickname writer_nickname, " + 
							"            t1.* " + 
							"        FROM " + 
							"            ( " + 
							"                SELECT " + 
							"                    rc.u_id user_id, " + 
							"                    rc.content content " + 
							"                FROM " + 
							"                    member m, " + 
							"                    recipe_comments rc, " + 
							"                    recipe r " + 
							"                WHERE " + 
							"                    m.u_id = r.u_id " + 
							"                AND " + 
							"                    rc.recipe_id = r.recipe_id  " + 
							"                AND " + 
							"                    r.u_id = ? " + 
							"                ORDER BY " + 
							"                    rc.writedate DESC " + 
							"            ) t1, " + 
							"            member m " + 
							"        WHERE " + 
							"            m.u_id = t1.user_id " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= 1 " + 
							"AND " + 
							"    rnum <= 2";
					pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
					pstmt2.setString(1, followingChefUid);
					
					rs2 = pstmt2.executeQuery();
					
					System.out.println("-최근에 받은 댓글 2개-");
					while(rs2.next()) {
						String commentWriterUid = rs2.getString("user_id");
						String commentWriterNickname = rs2.getString("writer_nickname");
						String commentContent = rs2.getString("content");
						System.out.println("댓글쓴사람ID : " + commentWriterUid + " , 댓글쓴사람닉네임 : " + commentWriterNickname +"  , 댓글내용 : " + commentContent);
						
						recentReceiveCommentListOfFollowingChef.add(new RecentReceiveCommentOfFollowingChefDto(commentWriterUid, commentWriterNickname, commentContent));
					}
				} catch(SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if(rs2 != null) rs2.close();
						if(pstmt2 != null) pstmt2.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				} // 네 번째 2중쿼리문 종료
				
				try {
					String sql2 = 
							"SELECT " + 
							"    count(*) " + 
							"FROM " + 
							"    member_follow " + 
							"WHERE " + 
							"    u_id_followtarget = ?";
					pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
					pstmt2.setString(1, followingChefUid);
					
					rs2 = pstmt2.executeQuery();
					
					if(rs2.next()) {
						followerQtyOfFollowingChef = rs2.getInt("count(*)"); // followingChef의 팔로워 수
						System.out.println("소식받는 수 : " + followerQtyOfFollowingChef);
					}
				} catch(SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if(rs2 != null) rs2.close();
						if(pstmt2 != null) pstmt2.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				} // 다섯 번째 2중쿼리문 종료
				
				myPageFollowingChefList.add(new MyPageFollowingChefDto(followingChefUid, followingChefNickname, followingChefProfileImage, recipeQtyOfFollowingChef, recipeLikeQtyOfFollowingChef, recipeHitQtyOfFollowingChef, recentRecipeListOfFollowingChef, recentReceiveCommentListOfFollowingChef, followerQtyOfFollowingChef));
				System.out.println("팔로잉쉐프 객체 하나 생성됨");
			} // while(rs.next()) 끝지점
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return myPageFollowingChefList;
	}
	// 인수로 받은 유저가 팔로잉중인 쉐프의 활동 내역을 가져오는 메서드.
	public ArrayList<MyPageFollowingChefDto> getActivityOfFollowingChef(String uId, int pageNum, String searchWord) {
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// MyPageFollowingChefDTO 객체를 담아둘 리스트 초기화
		ArrayList<MyPageFollowingChefDto> myPageFollowingChefList = null;
		
		if(searchWord == null) { // 검색어가 없을 때
			try {
				// 팔로잉 중인 유저 아이디를 최근 활동 순서 내림차순으로 정렬하여 페이징 처리까지 마침
				String sql = 
						"SELECT " + 
						"    t3.* " + 
						"FROM " + 
						"    ( " + 
						"        SELECT " + 
						"            rownum rnum, " + 
						"            t2.* " + 
						"        FROM " + 
						"            ( " + 
						"                SELECT " + 
						"                    m.nickname nickname, " + 
						"                    m.profile_image profile_image, " + 
						"                    t1.* " + 
						"                FROM " + 
						"                    ( " + 
						"                        SELECT " + 
						"                            mf.u_id_followtarget u_id, " + 
						"                            max(r.update_date) latest_update_date " + 
						"                        FROM " + 
						"                            recipe r, " + 
						"                            member_follow mf " + 
						"                        WHERE " + 
						"                            r.u_id = mf.u_id_followtarget " + 
						"                        AND " + 
						"                            r.complete = 1 " + 
						"                        AND " + 
						"                            mf.u_id = ? " + // 로그인 유저 아이디 or 타인 계정 아이디
						"                        GROUP BY " + 
						"                            mf.u_id_followtarget " + 
						"                    ) t1, " + 
						"                    member m " + 
						"                WHERE " + 
						"                    m.u_id = t1.u_id " + 
						"                ORDER BY " + 
						"                    t1.latest_update_date DESC " + 
						"            ) t2 " + 
						"    ) t3 " + 
						"WHERE " + 
						"    rnum >= ? " + 
						"AND " + 
						"    rnum <= ?";
				// 페이징 처리를 위한 수식과 수를 담은 변수
				int endNum = pageNum * 5;
				int startNum = endNum - 4;
				
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setInt(2, startNum);
				pstmt.setInt(3, endNum);
				
				rs = pstmt.executeQuery();
				
				myPageFollowingChefList = getContentsOfFollowingChef(rs);
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
			} // 메인쿼리문 종료
			
			return myPageFollowingChefList;
		} else { // 검색어가 있을 때
			searchWord = makeSearchWord(searchWord); // 검색어 전처리
			
			try {
				// 팔로잉 중인 유저 아이디를 최근 활동 순서 내림차순으로 정렬하여 페이징 처리까지 마침
				String sql = 
						"SELECT " + 
						"    t3.* " + 
						"FROM " + 
						"    ( " + 
						"        SELECT " + 
						"            rownum rnum, " + 
						"            t2.* " + 
						"        FROM " + 
						"            ( " + 
						"                SELECT " + 
						"                    m.nickname nickname, " + 
						"                    m.profile_image profile_image, " + 
						"                    t1.* " + 
						"                FROM " + 
						"                    ( " + 
						"                        SELECT " + 
						"                            mf.u_id_followtarget u_id, " + 
						"                            max(r.update_date) latest_update_date " + 
						"                        FROM " + 
						"                            recipe r, " + 
						"                            member_follow mf " + 
						"                        WHERE " + 
						"                            r.u_id = mf.u_id_followtarget " + 
						"                        AND " + 
						"                            r.complete = 1 " + 
						"                        AND " + 
						"                            mf.u_id = ? " + // 로그인 유저 아이디 or 타인 계정 아이디 
						"                        GROUP BY " + 
						"                            mf.u_id_followtarget " + 
						"                    ) t1, " + 
						"                    member m " + 
						"                WHERE " + 
						"                    m.u_id = t1.u_id " + 
						"                AND " + 
						"                    m.nickname LIKE ? " + 
						"                ORDER BY " + 
						"                    t1.latest_update_date DESC " + 
						"            ) t2 " + 
						"    ) t3 " + 
						"WHERE " + 
						"    rnum >= ? " + 
						"AND " + 
						"    rnum <= ?";
				// 페이징 처리를 위한 수식과 수를 담은 변수
				int endNum = pageNum * 5;
				int startNum = endNum - 4;
				
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, startNum);
				pstmt.setInt(4, endNum);
				
				rs = pstmt.executeQuery();
				
				myPageFollowingChefList = getContentsOfFollowingChef(rs);
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
			} // 메인쿼리문 종료
			
			return myPageFollowingChefList;
		}
	}
}
