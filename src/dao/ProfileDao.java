package dao;

import java.sql.*;

import common.ConnectionRecipe;
import dto.ProfileDto;

public class ProfileDao {
	public ProfileDto getProfileById(String uId) {
		// 인수로 받은 유저의 프로필 표시에 필요한 정보들을 가져오는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ProfileDTO 객체 초기화에 사용될 변수 생성
		String profileImage = null; // 인수로 받은 유저의 프로필 사진
		String nickname = null; // 인수로 받은 유저의 닉네임
		String selfIntroduce = null; // 인수로 받은 유저의 자기소개
		int totalHits = 0; // 인수로 받은 유저가 공개중인 레시피의 총 조회수
		int follower = 0; // 인수로 받은 유저의 팔로워 수
		int following = 0; // 인수로 받은 유저의 팔로잉 수
		
		// member테이블에서 필요한 정보 조회
		try {
			String sql = "SELECT profile_image, nickname, self_introduce FROM member WHERE u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				profileImage = rs.getString("profile_image");
				nickname = rs.getString("nickname");
				selfIntroduce = rs.getString("self_introduce");
				
				System.out.println("프로필사진 : " + profileImage);
				System.out.println("닉네임 : " + nickname);
				System.out.println("자기소개 : " + selfIntroduce);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		// recipe테이블과 recipe_hit_date테이블 join하여 필요한 정보 조회
		try {
			String sql =  "SELECT count(*) "
						+ "FROM recipe r, recipe_hit_date rhd "
						+ "WHERE r.recipe_id = rhd.recipe_id "
						+ "AND r.u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalHits = rs.getInt("count(*)"); // 기존의 조회수와 새로 발생한 조회수를 누적 덧셈
				System.out.println("총 조회수 : " + totalHits);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		// member_follow테이블에서 필요한 정보 조회
		// 인수로 받은 유저의 팔로워 수
		try {
			String sql = "SELECT count(*) FROM member_follow WHERE u_id_followtarget = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				follower = rs.getInt("count(*)");
				System.out.println("팔로워 : " + follower);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		// 인수로 받은 유저의 팔로잉 수
		try {
			String sql = "SELECT count(*) FROM member_follow WHERE u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				following = rs.getInt("count(*)");
				System.out.println("팔로잉 : " + following);
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
		
		// ProfileDTO 객체 생성
		ProfileDto profileDto = new ProfileDto(profileImage, nickname, selfIntroduce, totalHits, follower, following);
		return profileDto;
	}
	public boolean modifyProfile(String uId, String profileImage, String selfIntroduce) {
		// 프로필 이미지와 자기소개를 변경하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int result = -1; // 제대로 수정되었다면 1
		try {
			String sql = 
					"UPDATE " + 
					"    member " + 
					"SET " + 
					"    profile_image = ?, " + 
					"    self_introduce = ? " + 
					"WHERE " + 
					"    u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, profileImage);
			pstmt.setString(2, selfIntroduce);
			pstmt.setString(3, uId);
			result = pstmt.executeUpdate();
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
		
		return result == 1;
	}
}
