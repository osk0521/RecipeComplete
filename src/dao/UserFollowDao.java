package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.ConnectionRecipe;
import dto.UserFollowerDto;
import dto.UserFollowingDto;

public class UserFollowDao {
	// 로그인한 유저가 상대방을 팔로잉하는 중인지를 체크함
	public boolean checkFollowing(String loginId, String otherId) {
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM member_follow WHERE u_id = ? AND u_id_followtarget = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, otherId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
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
		
		return false;
	}
	// 팔로워 : 나를 팔로우 하는사람 | 팔로잉 : 내가 팔로우 하는사람
	// 현재 로그인한 유저의 팔로워의 유저 ID, 닉네임, 프로필 사진과 해당 유저와의 관계를 가져옴
	public ArrayList<UserFollowerDto> getMemberFollowerById(String uId, int page) {
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 팔로워 정보를 저장할 ArrayList객체 생성
		ArrayList<UserFollowerDto> followerList = new ArrayList<UserFollowerDto>();
		
		try {
			String sql = 
					"SELECT " + 
					"    t2.* " + 
					"FROM " + 
					"    ( SELECT " + 
					"        rownum rnum, " + 
					"        t1.* " + 
					"    FROM " + 
					"        ( SELECT " + 
					"            m.u_id AS id, " + 
					"            m.nickname AS nickname, " + 
					"            m.profile_image AS profile_image " + 
					"        FROM " + 
					"            member_follow mf, " + 
					"            member m " + 
					"        WHERE " + 
					"            mf.u_id = m.u_id " + 
					"        AND " + 
					"            mf.u_id_followtarget = ? " + 
					"        ORDER BY " + 
					"            mf.create_date DESC ) t1 ) t2 " + 
					"WHERE " + 
					"    rnum >= ? " + 
					"AND " + 
					"    rnum <= ? ";
			// 한 페이지 당 20개씩 가져옴
			int endNum = page * 20;
			int startNum = endNum - 19;
			
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setInt(2, startNum);
			pstmt.setInt(3, endNum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String followerUid = rs.getString("id"); // 팔로워의 유저 id
				String followerNickname = rs.getString("nickname"); // 팔로워의 닉네임
				String followerProfileImage = rs.getString("profile_image"); // 팔로워의 프로필 사진
				boolean isFollowing = false; // 인수로 받은 유저가 팔로워 유저를 팔로잉하고 있는지의 여부. true:팔로잉함 false:팔로잉안함
				// 새로운 쿼리문 실행을 위한 JDBC객체 생성
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				try {
					String sql2 = "SELECT * FROM member_follow WHERE u_id = ? AND u_id_followtarget = ?";
					pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
					pstmt2.setString(1, uId);
					pstmt2.setString(2, followerUid);
					
					rs2 = pstmt2.executeQuery();
					
					if(rs2.next()) { // 인수로 받은 유저가 현재 조회한 팔로워를 팔로잉하고 있다면
						isFollowing = true;
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
				}
				
				followerList.add(new UserFollowerDto(followerUid, followerNickname, followerProfileImage, isFollowing));
				System.out.println("아이디 : " + followerUid + ", 닉네임 : " + followerNickname);
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
		
		return followerList;
	}
	// 현재 로그인한 유저의  팔로우중인 유저 ID, 닉네임, 프로필 사진과 해당 유저와의 관계를 가져옴
	public ArrayList<UserFollowingDto> getMemberFollowingById(String uId, int page) {
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 팔로잉 정보를 저장할 ArrayList객체 생성
		ArrayList<UserFollowingDto> followingList = new ArrayList<UserFollowingDto>();
		
		try {
			String sql = 
					"SELECT " + 
					"    t2.* " + 
					"FROM " + 
					"    ( SELECT " + 
					"        rownum rnum, " + 
					"        t1.* " + 
					"    FROM " + 
					"        ( SELECT " + 
					"            m.u_id AS id, " + 
					"            m.nickname AS nickname, " + 
					"            m.profile_image AS profile_image " + 
					"        FROM " + 
					"            member_follow mf, " + 
					"            member m " + 
					"        WHERE " + 
					"            mf.u_id_followtarget = m.u_id " + 
					"        AND " + 
					"            mf.u_id = ? " + 
					"        ORDER BY " + 
					"            mf.create_date DESC ) t1 ) t2 " + 
					"WHERE " + 
					"    rnum >= ? " + 
					"AND " + 
					"    rnum <= ? ";
			// 한 페이지 당 20개씩 가져옴
			int endNum = page * 20;
			int startNum = endNum - 19;
			
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setInt(2, startNum);
			pstmt.setInt(3, endNum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String followingUid = rs.getString("id"); // 팔로잉한 유저의 id
				String followingNickname = rs.getString("nickname"); // 팔로잉한 유저의 닉네임
				String followingProfileImage = rs.getString("profile_image"); // 팔로잉한 유저의 프로필사진
				boolean isFollowing = false; // 인수로 받은 유저가 팔로워 유저를 팔로잉하고 있는지의 여부. true:팔로잉함 false:팔로잉안함
				// 새로운 쿼리문 실행을 위한 JDBC객체 생성
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				try {
					String sql2 = "SELECT * FROM member_follow WHERE u_id = ? AND u_id_followtarget = ?";
					pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
					pstmt2.setString(1, uId);
					pstmt2.setString(2, followingUid);
					
					rs2 = pstmt2.executeQuery();
					
					if(rs2.next()) { // 인수로 받은 유저가 현재 조회한 팔로잉 유저를 팔로잉하고 있다면
						isFollowing = true;
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
				}
				
				followingList.add(new UserFollowingDto(followingUid, followingNickname, followingProfileImage, isFollowing));
				System.out.println("아이디 : " + followingUid + ", 닉네임 : " + followingNickname);
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
		
		return followingList;
	}
	// 인수로 받은 유저의 팔로워의 유저 ID, 닉네임, 프로필 사진을 가져오고 그 팔로워와 로그인 계정과의 관계를 가져옴
	public ArrayList<UserFollowerDto> getMemberFollowerById(String uId, String otherUid) {
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 팔로워 정보를 저장할 ArrayList객체 생성
		ArrayList<UserFollowerDto> followerList = new ArrayList<UserFollowerDto>();
		
		try {
			String sql = "SELECT m.u_id AS id, m.nickname AS nickname, m.profile_image AS profile_image "
					+ "FROM member_follow mf, member m "
					+ "WHERE mf.u_id = m.u_id "
					+ "AND mf.u_id_followtarget = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, otherUid);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String followerUid = rs.getString("id"); // 팔로워의 유저 id
				String followerNickname = rs.getString("nickname"); // 팔로워의 닉네임
				String followerProfileImage = rs.getString("profile_image"); // 팔로워의 프로필 사진
				boolean isFollowing = false; // 인수로 받은 유저가 팔로워 유저를 팔로잉하고 있는지의 여부. true:팔로잉함 false:팔로잉안함
				// 새로운 쿼리문 실행을 위한 JDBC객체 생성
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				try {
					String sql2 = "SELECT * FROM member_follow WHERE u_id = ? AND u_id_followtarget = ?";
					pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
					pstmt2.setString(1, uId);
					pstmt2.setString(2, followerUid);
					
					rs2 = pstmt2.executeQuery();
					
					if(rs2.next()) { // 인수로 받은 유저가 현재 조회한 팔로워를 팔로잉하고 있다면
						isFollowing = true;
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
				}
				
				followerList.add(new UserFollowerDto(followerUid, followerNickname, followerProfileImage, isFollowing));
				System.out.println("아이디 : " + followerUid + ", 닉네임 : " + followerNickname);
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
		
		return followerList;
	}
	// 인수로 받은 유저의  팔로우중인 유저 ID, 닉네임, 프로필 사진을 가져오고 그 팔로우중인 유저와 로그인 계정과의 관계를 가져옴
	public ArrayList<UserFollowingDto> getMemberFollowingById(String uId, String otherUid) {
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 팔로잉 정보를 저장할 ArrayList객체 생성
		ArrayList<UserFollowingDto> followingList = new ArrayList<UserFollowingDto>();
		
		try {
			String sql = "SELECT m.u_id AS id, m.nickname AS nickname, m.profile_image AS profile_image "
					+ "FROM member_follow mf, member m "
					+ "WHERE mf.u_id_followtarget = m.u_id "
					+ "AND mf.u_id = ?"; 
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, otherUid);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String followingUid = rs.getString("id"); // 팔로잉한 유저의 id
				String followingNickname = rs.getString("nickname"); // 팔로잉한 유저의 닉네임
				String followingProfileImage = rs.getString("profile_image"); // 팔로잉한 유저의 프로필사진
				boolean isFollowing = false; // 인수로 받은 유저가 팔로워 유저를 팔로잉하고 있는지의 여부. true:팔로잉함 false:팔로잉안함
				// 새로운 쿼리문 실행을 위한 JDBC객체 생성
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				try {
					String sql2 = "SELECT * FROM member_follow WHERE u_id = ? AND u_id_followtarget = ?";
					pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
					pstmt2.setString(1, uId);
					pstmt2.setString(2, followingUid);
					
					rs2 = pstmt2.executeQuery();
					
					if(rs2.next()) { // 인수로 받은 유저가 현재 조회한 팔로잉 유저를 팔로잉하고 있다면
						isFollowing = true;
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
				}
				
				followingList.add(new UserFollowingDto(followingUid, followingNickname, followingProfileImage, isFollowing));
				System.out.println("아이디 : " + followingUid + ", 닉네임 : " + followingNickname);
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
		
		return followingList;
	}
}
