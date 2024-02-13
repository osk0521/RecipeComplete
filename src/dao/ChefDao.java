package dao;

import java.sql.*;
import java.util.ArrayList;

import common.ConnectionRecipe;
import dto.ChefDto;

public class ChefDao {
	public String makeSearchWord(String inputWord) {
		// 입력받은 검색어를 LIKE구문에 적용하기 위한 전처리 작업
		String searchWord = "%" + inputWord + "%";
		return searchWord;
	}
	public int getChefPageNum(String searchWord) {
		// 쉐프 페이지 수를 계산하여 반환하는 메서드(한 페이지당 10개씩)
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int pageNum = 0; // 쉐프(멤버)수를 받아와서 수식에 맞게 변환한 후 반환
		
		try {
			if(searchWord == null) { // 검색어가 없을 때
				String sql = 
						"SELECT " + 
						"    count(*) " + 
						"FROM " + 
						"    member";
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					pageNum = rs.getInt("count(*)");
				}
			} else { // 검색어가 있을 때
				searchWord = makeSearchWord(searchWord); // 검색어 전처리
				
				String sql = 
						"SELECT " + 
						"    count(*) " + 
						"FROM " + 
						"    member " + 
						"WHERE " + 
						"    nickname LIKE ?";
				pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
				pstmt.setString(1, searchWord);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					pageNum = rs.getInt("count(*)");
				}
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
		
		if(pageNum%10 == 0) {
			System.out.println("페이지 수 : " + (pageNum/10));
			return pageNum / 10;
		}
		System.out.println("페이지 수 : " + (pageNum/10 + 1));
		return pageNum/10 + 1;
	}
	public ArrayList<ChefDto> getChefSortByFollower(String loginId, String searchWord, int pageNum, String term) {
		// 쉐프 목록을 총 팔로워 수 순위에 맞게 가져오는 메서드
		// 로그인 아이디가 있을 경우, 로그인 유저가 목록으로 나오는 쉐프를 팔로우했는지 여부도 같이 가져옴
		ArrayList<ChefDto> chefList = new ArrayList<ChefDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			if(searchWord == null) { // 검색어가 없을 때
				if(term == null || term.equals("all")) { // 날짜정렬 기준이 누적인 경우
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
							"                    count(mf.u_id_followtarget) cnt_follower, " + 
							"                    m.u_id u_id " + 
							"                FROM " + 
							"                    member m, " + 
							"                    member_follow mf " + 
							"                WHERE " + 
							"                    m.u_id = mf.u_id_followtarget(+) " + // 팔로워가 없는 사람도 뽑아내야함
							"                GROUP BY " + 
							"                    m.u_id " + 
							"                ORDER BY " + 
							"                    cnt_follower DESC " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setInt(1, startNum);
					pstmt.setInt(2, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int followerQtyOfChef = rs.getInt("cnt_follower"); // 쉐프의 팔로워 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
								System.out.println("순위 : " + rownum + " , 아이디 : " + chefUid + " , 닉네임 : " + chefNickname + " , 프로필사진 : " + chefProfileImage + " , 레시피개수 : " + recipeQtyOfChef + " , 레시피좋아요수 : " + totalRecipeLikesOfChef + " , 레시피조회수 : " + totalRecipeHitsOfChef + " , 팔로워수 : " + followerQtyOfChef + " , 나랑팔로잉함? " + checkFollow);
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				} else if(term.equals("today")) { // 날짜정렬 기준이 오늘인 경우
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
							"                    m.u_id u_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            count(*) " + 
							"                        FROM " + 
							"                            member_follow " + 
							"                        WHERE " + 
							"                            to_char(create_date, 'YYYYMMDD') = sysdate " + 
							"                        AND " + 
							"                            u_id_followtarget = m.u_id " + 
							"                    ) cnt_follower " + 
							"                FROM " + 
							"                    member m, " + 
							"                    member_follow mf " + 
							"                WHERE " + 
							"                    m.u_id = mf.u_id_followtarget(+) " + 
							"                GROUP BY " + 
							"                    m.u_id " + 
							"                ORDER BY " + 
							"                    cnt_follower DESC " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setInt(1, startNum);
					pstmt.setInt(2, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int followerQtyOfChef = rs.getInt("cnt_follower"); // 쉐프의 팔로워 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            to_char(writedate, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"        AND " + 
									"            to_char(rl.like_date, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"        AND " + 
									"            to_char(rhd.hit_date, 'YYYYMMDD') = sysdate" + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				}
			} else { // 검색어가 있을 때
				searchWord = makeSearchWord(searchWord);
				
				if(term == null || term.equals("all")) { // 날짜정렬 기준이 누적인 경우
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
							"                    count(mf.u_id_followtarget) cnt_follower, " + 
							"                    m.u_id u_id " + 
							"                FROM " + 
							"                    member m, " + 
							"                    member_follow mf " + 
							"                WHERE " + 
							"                    m.u_id = mf.u_id_followtarget(+) " + // 팔로워가 없는 사람도 뽑아내야함
							"				 AND " + 
							"					 m.nickname LIKE ? " + 
							"                GROUP BY " + 
							"                    m.u_id " + 
							"                ORDER BY " + 
							"                    cnt_follower DESC " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setString(1, searchWord);
					pstmt.setInt(2, startNum);
					pstmt.setInt(3, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int followerQtyOfChef = rs.getInt("cnt_follower"); // 쉐프의 팔로워 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				} else if(term.equals("today")) { // 날짜정렬 기준이 오늘인 경우
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
							"                    m.u_id u_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            count(*) " + 
							"                        FROM " + 
							"                            member_follow " + 
							"                        WHERE " + 
							"                            to_char(create_date, 'YYYYMMDD') = sysdate " + 
							"                        AND " + 
							"                            u_id_followtarget = m.u_id " + 
							"                    ) cnt_follower " + 
							"                FROM " + 
							"                    member m, " + 
							"                    member_follow mf " + 
							"                WHERE " + 
							"                    m.u_id = mf.u_id_followtarget(+) " + 
							"				 AND " + 
							"					 m.nickname LIKE ? " + 
							"                GROUP BY " + 
							"                    m.u_id " + 
							"                ORDER BY " + 
							"                    cnt_follower DESC " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setString(1, searchWord);
					pstmt.setInt(2, startNum);
					pstmt.setInt(3, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int followerQtyOfChef = rs.getInt("cnt_follower"); // 쉐프의 팔로워 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				}
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
		
		return chefList;
	}
	public ArrayList<ChefDto> getChefSortByRecipeHits(String loginId, String searchWord, int pageNum, String term) {
		// 쉐프 목록을 레시피 총 조회수 순위에 맞게 가져오는 메서드
		// 로그인 아이디가 있을 경우, 로그인 유저가 목록으로 나오는 쉐프를 팔로우했는지 여부도 같이 가져옴
		ArrayList<ChefDto> chefList = new ArrayList<ChefDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			if(searchWord == null) { // 검색어가 없을 때
				if(term == null || term.equals("all")) { // 날짜정렬 기준이 누적인 경우
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
							"                    user_id.u_id u_id, " + 
							"                    NVL(hits.cnt_recipe_hit, 0) recipe_hits " + 
							"                FROM " + 
							"                    ( " + 
							"                        SELECT DISTINCT " + 
							"                            m.u_id u_id " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            member m " + 
							"                        WHERE " + 
							"                            r.u_id(+) = m.u_id " + // 레시피 등록하지 않은 유저도 전부 조회하기
							"                    ) user_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            r.u_id u_id, " + 
							"                            count(rhd.recipe_id) cnt_recipe_hit " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            recipe_hit_date rhd " + 
							"                        WHERE " + 
							"                            r.recipe_id = rhd.recipe_id " + 
							"                        AND " + 
							"                            r.complete = 1 " + 
							"                        GROUP BY " + 
							"                            r.u_id " + 
							"                        ORDER BY " + 
							"                            cnt_recipe_hit DESC " + 
							"                    ) hits " + 
							"                WHERE " + 
							"                    user_id.u_id = hits.u_id(+) " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setInt(1, startNum);
					pstmt.setInt(2, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int totalRecipeHitsOfChef = rs.getInt("recipe_hits"); // 쉐프의 레시피 조회수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
								System.out.println("순위 : " + rownum + " , 아이디 : " + chefUid + " , 닉네임 : " + chefNickname + " , 프로필사진 : " + chefProfileImage + " , 레시피개수 : " + recipeQtyOfChef + " , 레시피좋아요수 : " + totalRecipeLikesOfChef + " , 레시피조회수 : " + totalRecipeHitsOfChef + " , 팔로워수 : " + followerQtyOfChef + " , 나랑팔로잉함? " + checkFollow);
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				} else if(term.equals("today")) { // 날짜정렬 기준이 오늘인 경우
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
							"                    user_id.u_id u_id, " + 
							"                    NVL(hits.cnt_recipe_hit, 0) recipe_hits " + 
							"                FROM " + 
							"                    ( " + 
							"                        SELECT DISTINCT " + 
							"                            m.u_id u_id " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            member m " + 
							"                        WHERE " + 
							"                            r.u_id(+) = m.u_id " + 
							"                    ) user_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            r.u_id u_id, " + 
							"                            count(rhd.recipe_id) cnt_recipe_hit " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            recipe_hit_date rhd " + 
							"                        WHERE " + 
							"                            r.recipe_id = rhd.recipe_id " + 
							"                        AND " + 
							"                            r.complete = 1 " + 
							"                        AND " + 
							"                            to_char(rhd.hit_date, 'YYYYMMDD') = sysdate " + 
							"                        GROUP BY " + 
							"                            r.u_id " + 
							"                        ORDER BY " + 
							"                            cnt_recipe_hit DESC " + 
							"                    ) hits " + 
							"                WHERE " + 
							"                    user_id.u_id = hits.u_id(+) " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setInt(1, startNum);
					pstmt.setInt(2, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int totalRecipeHitsOfChef = rs.getInt("recipe_hits"); // 쉐프의 레시피 조회수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            to_char(writedate, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"        AND " + 
									"            to_char(rl.like_date, 'YYYYMMDD') = sysdate   " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"        AND " + 
									"            to_char(mf.create_date, 'YYYYMMDD') = sysdate " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ?" + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
								System.out.println("순위 : " + rownum + " , 아이디 : " + chefUid + " , 닉네임 : " + chefNickname + " , 프로필사진 : " + chefProfileImage + " , 레시피개수 : " + recipeQtyOfChef + " , 레시피좋아요수 : " + totalRecipeLikesOfChef + " , 레시피조회수 : " + totalRecipeHitsOfChef + " , 팔로워수 : " + followerQtyOfChef + " , 나랑팔로잉함? " + checkFollow);
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				}
			} else { // 검색어가 있을 때
				searchWord = makeSearchWord(searchWord);
				
				if(term == null || term.equals("all")) { // 날짜정렬 기준이 누적인 경우
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
							"                    user_id.u_id u_id, " + 
							"                    NVL(hits.cnt_recipe_hit, 0) recipe_hits " + 
							"                FROM " + 
							"                    ( " + 
							"                        SELECT DISTINCT " + 
							"                            m.u_id u_id " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            member m " + 
							"                        WHERE " + 
							"                            r.u_id(+) = m.u_id " + 
							"                        AND " + 
							"                            m.nickname LIKE ? " + 
							"                    ) user_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            r.u_id u_id, " + 
							"                            count(rhd.recipe_id) cnt_recipe_hit " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            recipe_hit_date rhd " + 
							"                        WHERE " + 
							"                            r.recipe_id = rhd.recipe_id " + 
							"                        AND " + 
							"                            r.complete = 1 " + 
							"                        GROUP BY " + 
							"                            r.u_id " + 
							"                        ORDER BY " + 
							"                            cnt_recipe_hit DESC " + 
							"                    ) hits " + 
							"                WHERE " + 
							"                    user_id.u_id = hits.u_id(+) " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setString(1, searchWord);
					pstmt.setInt(2, startNum);
					pstmt.setInt(3, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int totalRecipeHitsOfChef = rs.getInt("recipe_hits"); // 쉐프의 레시피 조회수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
								System.out.println("순위 : " + rownum + " , 아이디 : " + chefUid + " , 닉네임 : " + chefNickname + " , 프로필사진 : " + chefProfileImage + " , 레시피개수 : " + recipeQtyOfChef + " , 레시피좋아요수 : " + totalRecipeLikesOfChef + " , 레시피조회수 : " + totalRecipeHitsOfChef + " , 팔로워수 : " + followerQtyOfChef + " , 나랑팔로잉함? " + checkFollow);
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				} else if(term.equals("today")) { // 날짜정렬 기준이 오늘인 경우
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
							"                    user_id.u_id u_id, " + 
							"                    NVL(hits.cnt_recipe_hit, 0) recipe_hits " + 
							"                FROM " + 
							"                    ( " + 
							"                        SELECT DISTINCT " + 
							"                            m.u_id u_id " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            member m " + 
							"                        WHERE " + 
							"                            r.u_id(+) = m.u_id " + 
							"                        AND " + 
							"                            m.nickname LIKE ? " + 
							"                    ) user_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            r.u_id u_id, " + 
							"                            count(rhd.recipe_id) cnt_recipe_hit " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            recipe_hit_date rhd " + 
							"                        WHERE " + 
							"                            r.recipe_id = rhd.recipe_id " + 
							"                        AND " + 
							"                            r.complete = 1 " + 
							"                        AND " + 
							"                            to_char(rhd.hit_date, 'YYYYMMDD') = sysdate " + 
							"                        GROUP BY " + 
							"                            r.u_id " + 
							"                        ORDER BY " + 
							"                            cnt_recipe_hit DESC " + 
							"                    ) hits " + 
							"                WHERE " + 
							"                    user_id.u_id = hits.u_id(+) " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setString(1, searchWord);
					pstmt.setInt(2, startNum);
					pstmt.setInt(3, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int totalRecipeHitsOfChef = rs.getInt("recipe_hits"); // 쉐프의 레시피 조회수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            to_char(writedate, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"        AND " + 
									"            to_char(rl.like_date, 'YYYYMMDD') = sysdate   " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"        AND " + 
									"            to_char(mf.create_date, 'YYYYMMDD') = sysdate " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ?" + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
								System.out.println("순위 : " + rownum + " , 아이디 : " + chefUid + " , 닉네임 : " + chefNickname + " , 프로필사진 : " + chefProfileImage + " , 레시피개수 : " + recipeQtyOfChef + " , 레시피좋아요수 : " + totalRecipeLikesOfChef + " , 레시피조회수 : " + totalRecipeHitsOfChef + " , 팔로워수 : " + followerQtyOfChef + " , 나랑팔로잉함? " + checkFollow);
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				}
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
		
		return chefList;
	}
	public ArrayList<ChefDto> getChefSortByRecipeLikes(String loginId, String searchWord, int pageNum, String term) {
		// 쉐프 목록을 레시피 총 좋아요 수 순위에 맞게 가져오는 메서드
		// 로그인 아이디가 있을 경우, 로그인 유저가 목록으로 나오는 쉐프를 팔로우했는지 여부도 같이 가져옴
		ArrayList<ChefDto> chefList = new ArrayList<ChefDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			if(searchWord == null) { // 검색어가 없을 때
				if(term == null || term.equals("all")) { // 날짜정렬 기준이 누적인 경우
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
							"                    user_id.u_id u_id, " + 
							"                    NVL(likes.cnt_recipe_like, 0) recipe_likes " + 
							"                FROM " + 
							"                    ( " + 
							"                        SELECT DISTINCT " + 
							"                            m.u_id u_id " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            member m " + 
							"                        WHERE " + 
							"                            r.u_id(+) = m.u_id " + 
							"                    ) user_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            r.u_id u_id, " + 
							"                            count(rl.recipe_id) cnt_recipe_like " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            recipe_like rl " + 
							"                        WHERE " + 
							"                            r.recipe_id = rl.recipe_id " + 
							"                        AND " + 
							"                            r.complete = 1 " + 
							"                        GROUP BY " + 
							"                            r.u_id " + 
							"                        ORDER BY " + 
							"                            cnt_recipe_like DESC " + 
							"                    ) likes " + 
							"                WHERE " + 
							"                    user_id.u_id = likes.u_id(+) " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setInt(1, startNum);
					pstmt.setInt(2, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int totalRecipeLikesOfChef = rs.getInt("recipe_likes"); // 쉐프의 레시피 총 좋아요 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ?" + 
									"        AND " + 
									"            u_id_followtarget = ?" + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
								System.out.println("순위 : " + rownum + " , 아이디 : " + chefUid + " , 닉네임 : " + chefNickname + " , 프로필사진 : " + chefProfileImage + " , 레시피개수 : " + recipeQtyOfChef + " , 레시피좋아요수 : " + totalRecipeLikesOfChef + " , 레시피조회수 : " + totalRecipeHitsOfChef + " , 팔로워수 : " + followerQtyOfChef + " , 나랑팔로잉함? " + checkFollow);
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				} else if(term.equals("today")) { // 날짜정렬 기준이 오늘인 경우
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
							"                    user_id.u_id u_id, " + 
							"                    NVL(likes.cnt_recipe_like, 0) recipe_likes " + 
							"                FROM " + 
							"                    ( " + 
							"                        SELECT DISTINCT " + 
							"                            m.u_id u_id " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            member m " + 
							"                        WHERE " + 
							"                            r.u_id(+) = m.u_id " + 
							"                    ) user_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            r.u_id u_id, " + 
							"                            count(rl.recipe_id) cnt_recipe_like " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            recipe_like rl " + 
							"                        WHERE " + 
							"                            r.recipe_id = rl.recipe_id " + 
							"                        AND " + 
							"                            r.complete = 1 " + 
							"                        AND " + 
							"                            to_char(rl.like_date, 'YYYYMMDD') = sysdate " + 
							"                        GROUP BY " + 
							"                            r.u_id " + 
							"                        ORDER BY " + 
							"                            cnt_recipe_like DESC " + 
							"                    ) likes " + 
							"                WHERE " + 
							"                    user_id.u_id = likes.u_id(+) " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setInt(1, startNum);
					pstmt.setInt(2, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int totalRecipeLikesOfChef = rs.getInt("recipe_likes"); // 쉐프의 레시피 총 좋아요 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            to_char(writedate, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"        AND " + 
									"            to_char(rhd.hit_date, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"        AND " + 
									"            to_char(mf.create_date, 'YYYYMMDD') = sysdate " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ?" + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
								System.out.println("순위 : " + rownum + " , 아이디 : " + chefUid + " , 닉네임 : " + chefNickname + " , 프로필사진 : " + chefProfileImage + " , 레시피개수 : " + recipeQtyOfChef + " , 레시피좋아요수 : " + totalRecipeLikesOfChef + " , 레시피조회수 : " + totalRecipeHitsOfChef + " , 팔로워수 : " + followerQtyOfChef + " , 나랑팔로잉함? " + checkFollow);
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				}
			} else { // 검색어가 있을 때
				searchWord = makeSearchWord(searchWord);
				
				if(term == null || term.equals("all")) { // 날짜정렬 기준이 누적인 경우
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
							"                    user_id.u_id u_id, " + 
							"                    NVL(likes.cnt_recipe_like, 0) recipe_likes " + 
							"                FROM " + 
							"                    ( " + 
							"                        SELECT DISTINCT " + 
							"                            m.u_id u_id " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            member m " + 
							"                        WHERE " + 
							"                            r.u_id(+) = m.u_id " + 
							"                        AND " + 
							"                            m.nickname LIKE ? " + 
							"                    ) user_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            r.u_id u_id, " + 
							"                            count(rl.recipe_id) cnt_recipe_like " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            recipe_like rl " + 
							"                        WHERE " + 
							"                            r.recipe_id = rl.recipe_id " + 
							"                        AND " + 
							"                            r.complete = 1 " + 
							"                        GROUP BY " + 
							"                            r.u_id " + 
							"                        ORDER BY " + 
							"                            cnt_recipe_like DESC " + 
							"                    ) likes " + 
							"                WHERE " + 
							"                    user_id.u_id = likes.u_id(+) " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setString(1, searchWord);
					pstmt.setInt(2, startNum);
					pstmt.setInt(3, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int totalRecipeLikesOfChef = rs.getInt("recipe_likes"); // 쉐프의 레시피 총 좋아요 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ?" + 
									"        AND " + 
									"            u_id_followtarget = ?" + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
								System.out.println("순위 : " + rownum + " , 아이디 : " + chefUid + " , 닉네임 : " + chefNickname + " , 프로필사진 : " + chefProfileImage + " , 레시피개수 : " + recipeQtyOfChef + " , 레시피좋아요수 : " + totalRecipeLikesOfChef + " , 레시피조회수 : " + totalRecipeHitsOfChef + " , 팔로워수 : " + followerQtyOfChef + " , 나랑팔로잉함? " + checkFollow);
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				} else if(term.equals("today")) { // 날짜정렬 기준이 오늘인 경우
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
							"                    user_id.u_id u_id, " + 
							"                    NVL(likes.cnt_recipe_like, 0) recipe_likes " + 
							"                FROM " + 
							"                    ( " + 
							"                        SELECT DISTINCT " + 
							"                            m.u_id u_id " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            member m " + 
							"                        WHERE " + 
							"                            r.u_id(+) = m.u_id " + 
							"                        AND " + 
							"                            m.nickname LIKE ? " + 
							"                    ) user_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            r.u_id u_id, " + 
							"                            count(rl.recipe_id) cnt_recipe_like " + 
							"                        FROM " + 
							"                            recipe r, " + 
							"                            recipe_like rl " + 
							"                        WHERE " + 
							"                            r.recipe_id = rl.recipe_id " + 
							"                        AND " + 
							"                            r.complete = 1 " + 
							"                        AND " + 
							"                            to_char(rl.like_date, 'YYYYMMDD') = sysdate " + 
							"                        GROUP BY " + 
							"                            r.u_id " + 
							"                        ORDER BY " + 
							"                            cnt_recipe_like DESC " + 
							"                    ) likes " + 
							"                WHERE " + 
							"                    user_id.u_id = likes.u_id(+) " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setString(1, searchWord);
					pstmt.setInt(2, startNum);
					pstmt.setInt(3, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int totalRecipeLikesOfChef = rs.getInt("recipe_likes"); // 쉐프의 레시피 총 좋아요 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"        AND " + 
									"            to_char(writedate, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"        AND " + 
									"            to_char(rhd.hit_date, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"        AND " + 
									"            to_char(mf.create_date, 'YYYYMMDD') = sysdate " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ?" + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int recipeQtyOfChef = rs2.getInt("recipe_qty"); // 쉐프의 레시피 수
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
								System.out.println("순위 : " + rownum + " , 아이디 : " + chefUid + " , 닉네임 : " + chefNickname + " , 프로필사진 : " + chefProfileImage + " , 레시피개수 : " + recipeQtyOfChef + " , 레시피좋아요수 : " + totalRecipeLikesOfChef + " , 레시피조회수 : " + totalRecipeHitsOfChef + " , 팔로워수 : " + followerQtyOfChef + " , 나랑팔로잉함? " + checkFollow);
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				}
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
		
		return chefList;
	}
	public ArrayList<ChefDto> getChefSortByRecipeQty(String loginId, String searchWord, int pageNum, String term) {
		// 쉐프 목록을 레시피 개수 순위에 맞게 가져오는 메서드
		// 로그인 아이디가 있을 경우, 로그인 유저가 목록으로 나오는 쉐프를 팔로우했는지 여부도 같이 가져옴
		ArrayList<ChefDto> chefList = new ArrayList<ChefDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			if(searchWord == null) { // 검색어가 없을 때
				if(term == null || term.equals("all")) { // 날짜정렬 기준이 누적인 경우
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
							"                    m.u_id u_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            count(recipe_id) " + 
							"                        FROM " + 
							"                            recipe " + 
							"                        WHERE " + 
							"                            complete = 1 " + 
							"                        AND " + 
							"                            m.u_id = u_id(+) " + 
							"                    ) cnt_recipe " + 
							"                FROM " + 
							"                    member m, " + 
							"                    recipe r " + 
							"                WHERE " + 
							"                    m.u_id = r.u_id(+) " + 
							"                GROUP BY " + 
							"                    m.u_id " + 
							"                ORDER BY " + 
							"                    cnt_recipe DESC " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setInt(1, startNum);
					pstmt.setInt(2, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int recipeQtyOfChef = rs.getInt("cnt_recipe"); // 쉐프의 레시피 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ?" + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
								System.out.println("순위 : " + rownum + " , 아이디 : " + chefUid + " , 닉네임 : " + chefNickname + " , 프로필사진 : " + chefProfileImage + " , 레시피개수 : " + recipeQtyOfChef + " , 레시피좋아요수 : " + totalRecipeLikesOfChef + " , 레시피조회수 : " + totalRecipeHitsOfChef + " , 팔로워수 : " + followerQtyOfChef + " , 나랑팔로잉함? " + checkFollow);
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				} else if(term.equals("today")) { // 날짜정렬 기준이 오늘인 경우
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
							"                    m.u_id u_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            count(recipe_id) " + 
							"                        FROM " + 
							"                            recipe " + 
							"                        WHERE " + 
							"                            to_char(writedate, 'YYYYMMDD') = sysdate " + 
							"                        AND " + 
							"                            complete = 1 " + 
							"                        AND " + 
							"                            m.u_id = u_id(+) " + 
							"                    ) cnt_recipe " + 
							"                FROM " + 
							"                    member m, " + 
							"                    recipe r " + 
							"                WHERE " + 
							"                    m.u_id = r.u_id(+) " + 
							"                GROUP BY " + 
							"                    m.u_id " + 
							"                ORDER BY " + 
							"                    cnt_recipe DESC " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setInt(1, startNum);
					pstmt.setInt(2, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int recipeQtyOfChef = rs.getInt("cnt_recipe"); // 쉐프의 레시피 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"        AND " + 
									"            to_char(rl.like_date, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"        AND " + 
									"            to_char(rhd.hit_date, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"        AND " + 
									"            to_char(mf.create_date, 'YYYYMMDD') = sysdate " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ?" + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				}
			} else { // 검색어가 있을 때
				searchWord = makeSearchWord(searchWord);
				
				if(term == null || term.equals("all")) { // 날짜정렬 기준이 누적인 경우
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
							"                    m.u_id u_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            count(recipe_id) " + 
							"                        FROM " + 
							"                            recipe " + 
							"                        WHERE " + 
							"                            complete = 1 " + 
							"                        AND " + 
							"                            m.u_id = u_id(+) " + 
							"                    ) cnt_recipe " + 
							"                FROM " + 
							"                    member m, " + 
							"                    recipe r " + 
							"                WHERE " + 
							"                    m.u_id = r.u_id(+) " + 
							"                AND " + 
							"                    m.nickname LIKE ? " + 
							"                GROUP BY " + 
							"                    m.u_id " + 
							"                ORDER BY " + 
							"                    cnt_recipe DESC " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setString(1, searchWord);
					pstmt.setInt(2, startNum);
					pstmt.setInt(3, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int recipeQtyOfChef = rs.getInt("cnt_recipe"); // 쉐프의 레시피 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ?" + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				} else if(term.equals("today")) { // 날짜정렬 기준이 오늘인 경우
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
							"                    m.u_id u_id, " + 
							"                    ( " + 
							"                        SELECT " + 
							"                            count(recipe_id) " + 
							"                        FROM " + 
							"                            recipe " + 
							"                        WHERE " + 
							"                            to_char(writedate, 'YYYYMMDD') = sysdate " + 
							"                        AND " + 
							"                            complete = 1 " + 
							"                        AND " + 
							"                            m.u_id = u_id(+) " + 
							"                    ) cnt_recipe " + 
							"                FROM " + 
							"                    member m, " + 
							"                    recipe r " + 
							"                WHERE " + 
							"                    m.u_id = r.u_id(+) " + 
							"                AND " + 
							"                    m.nickname LIKE ? " + 
							"                GROUP BY " + 
							"                    m.u_id " + 
							"                ORDER BY " + 
							"                    cnt_recipe DESC " + 
							"            ) t1 " + 
							"    ) t2 " + 
							"WHERE " + 
							"    rnum >= ? " + 
							"AND " + 
							"    rnum <= ?";
					// 1페이지 당 10개씩
					int endNum = pageNum * 10;
					int startNum = endNum - 9;
					
					pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
					pstmt.setString(1, searchWord);
					pstmt.setInt(2, startNum);
					pstmt.setInt(3, endNum);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						// ChefDto 객체 생성에 필요한 변수 선언
						int rownum = rs.getInt("rnum"); // 순위
						String chefUid = rs.getString("u_id"); // 쉐프 유저 아이디
						int recipeQtyOfChef = rs.getInt("cnt_recipe"); // 쉐프의 레시피 수
						
						try {
							String sql2 = 
									"SELECT " + 
									"    ( " + 
									"        SELECT " + 
									"            nickname " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) nickname, " + 
									"    ( " + 
									"        SELECT " + 
									"            profile_image " + 
									"        FROM " + 
									"            member " + 
									"        WHERE " + 
									"            u_id = ? " + 
									"    ) profile_image, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_like rl, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rl.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"        AND " + 
									"            to_char(rl.like_date, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_like_qty, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            recipe_hit_date rhd, " + 
									"            recipe r " + 
									"        WHERE " + 
									"            r.recipe_id = rhd.recipe_id " + 
									"        AND " + 
									"            r.u_id = ? " + 
									"        AND " + 
									"            to_char(rhd.hit_date, 'YYYYMMDD') = sysdate " + 
									"    ) recipe_hits, " + 
									"    ( " + 
									"        SELECT " + 
									"            count(*) " + 
									"        FROM " + 
									"            member m, " + 
									"            member_follow mf  " + 
									"        WHERE " + 
									"            m.u_id = mf.u_id_followtarget  " + 
									"        AND " + 
									"            mf.u_id_followtarget = ? " + 
									"        AND " + 
									"            to_char(mf.create_date, 'YYYYMMDD') = sysdate " + 
									"    ) cnt_follower, " + 
									"    ( " + 
									"        SELECT " + 
									"            create_date " + 
									"        FROM " + 
									"            member_follow " + 
									"        WHERE " + 
									"            u_id = ?" + 
									"        AND " + 
									"            u_id_followtarget = ? " + 
									"    ) check_follow " + 
									"FROM " + 
									"    dual";
							pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
							pstmt2.setString(1, chefUid);
							pstmt2.setString(2, chefUid);
							pstmt2.setString(3, chefUid);
							pstmt2.setString(4, chefUid);
							pstmt2.setString(5, chefUid);
							pstmt2.setString(6, loginId);
							pstmt2.setString(7, chefUid);
							
							rs2 = pstmt2.executeQuery();
							
							if(rs2.next()) {
								String chefNickname = rs2.getString("nickname"); // 쉐프 닉네임
								String chefProfileImage = rs2.getString("profile_image"); // 쉐프 프로필 이미지
								int followerQtyOfChef = rs2.getInt("cnt_follower"); // 쉐프의 팔로워 수
								int totalRecipeLikesOfChef = rs2.getInt("recipe_like_qty"); // 쉐프의 레시피 총 좋아요 수
								int totalRecipeHitsOfChef = rs2.getInt("recipe_hits"); // 쉐프의 레시피 조회수
								boolean checkFollow = false; // 로그인 유저와 쉐프와의 팔로잉 관계
								if(rs2.getString("check_follow") != null) {
									checkFollow = true; 
								}
								
								chefList.add(new ChefDto(rownum, chefUid, chefNickname, chefProfileImage, recipeQtyOfChef, totalRecipeLikesOfChef, totalRecipeHitsOfChef, followerQtyOfChef, checkFollow));
							}
						} catch(SQLException e) {
							e.printStackTrace();
						} catch(NullPointerException e) {
							e.printStackTrace();
						} finally {
							try {
								if(rs2 != null) rs2.close();
								if(pstmt2 != null) pstmt2.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
						} // 이중쿼리문 종료
					} // while(rs.next()) 종료
				}
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
		
		return chefList;
	}
}