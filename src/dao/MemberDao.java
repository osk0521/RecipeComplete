package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.ConnectionRecipe;
import dto.MemberDto;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

public class MemberDao extends ConnectionRecipe {
	public boolean isOurUser(String uId) {
		// 인수로 받은 유저 아이디가 현시점에서 가입되어있는 유저인지 판단
		// 쿼리문 실행을 위한 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 쿼리문 실행
		try {
			String sql = "SELECT count(*) FROM member WHERE u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int userCheck = 0; // 0이면 회원 아님, 1이면 회원 맞음
				userCheck = rs.getInt("count(*)");
				
				if(userCheck == 1) {
					System.out.println(uId + "는 우리 아이가 맞습니다");
					return true;
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
		
		System.out.println(uId + "는 우리 아이가 아닙니다");
		return false;
	}
	public boolean login(String uId, String pw) {
		// 로그인 성공 여부(성공  : true, 실패 : false)
		// 쿼리문 실행을 위한 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 쿼리문 실행 - 아이디가 틀렸을 때와 패스워드가 틀렸을 때의 경우를 나누어야 함(추후에)
		try {
			String sql = "SELECT count(*) FROM member WHERE u_id = ? AND password = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				int resultNum = rs.getInt("count(*)");
				if(resultNum == 1) {
					return true; // 로그인 성공
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
		
		return false; // 로그인 실패
	}
	public boolean memberRegist(String uId, String pw, String nickname, String phone, String email, String birth, int agree1, int agree2, int agree3) {
		// 쿼리문 정상작동 여부(정상 : true, 비정상 : false)
		boolean complete = false;
		
		// 쿼리문 실행
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "INSERT INTO "
					+ "member(u_id, password, nickname, phone, email, agree1, agree2, agree3, birth) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, to_date(?, 'YYYY-MM-DD'))";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setString(2, pw);
			pstmt.setString(3, nickname);
			pstmt.setString(4, phone);
			pstmt.setString(5, email);
			pstmt.setInt(6, agree1);
			pstmt.setInt(7, agree2);
			pstmt.setInt(8, agree3);
			pstmt.setString(9, birth);
			pstmt.executeUpdate();
			complete = true;
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
		
		return complete;
	}
	public boolean checkIdDuplication(String id) {
		// 인수로 받은 ID가 DB에 저장되어있는 ID와 중복인지 체크하는 메서드
		// 중복이 아니면 true, 중복이면 false
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    count(*) " + 
					"FROM " + 
					"    member " + 
					"WHERE " + 
					"    u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int cnt = rs.getInt("count(*)"); // 아이디가 중복이라면 1, 아니면 0
				
				if(cnt==0) {
					System.out.println("id " + id + "은(는) 중복 아님");
					return true;
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
		
		System.out.println("id " + id + "은(는) 중복임");
		return false;
	}
	public boolean checkNicknameDuplication(String nickname) {
		// 인수로 받은 닉네임이 DB에 저장되어있는 닉네임과 중복인지 체크하는 메서드
		// 중복이 아니면 true, 중복이면 false
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    count(*) " + 
					"FROM " + 
					"    member " + 
					"WHERE " + 
					"    nickname = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, nickname);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int cnt = rs.getInt("count(*)"); // 닉네임이 중복이라면 1, 아니면 0
				
				if(cnt==0) {
					System.out.println("닉네임 " + nickname + "은(는) 중복 아님");
					return true;
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
		
		System.out.println("닉네임 " + nickname + "은(는) 중복임");
		return false;
	}
	public boolean memberFollowing(String loginId, String yourUid) {
		// 로그인 유저가 상대방의 유저ID를 받아 팔로잉하는 메서드.
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int ret = -1; // insert 후 결과를 저장할 변수
		
		try {
			String sql = 
					"INSERT INTO member_follow(u_id, u_id_followtarget, create_date) " + 
					"VALUES (?, ?, sysdate)";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, yourUid);
			ret = pstmt.executeUpdate();
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
		
		return ret==1;
	}
	public boolean memberUnFollowing(String loginId, String yourUid) {
		// 로그인 유저가 상대방의 유저ID를 받아 언팔로잉하는 메서드.
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int ret = -1; // delete 후 결과를 저장할 변수
		
		try {
			String sql = 
					"DELETE FROM member_follow " + 
					"WHERE u_id = ? AND u_id_followtarget = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, yourUid);
			ret = pstmt.executeUpdate();
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
		
		return ret==1;
	}
	public MemberDto getMemberInfo(String uId) {
		// 인수로 받은 유저의 회원정보를 가져오는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDto mDto = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    password, " + 
					"    nickname, " + 
					"    phone, " + 
					"    email " + 
					"FROM " + 
					"    member " + 
					"WHERE " + 
					"    u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String pw = rs.getString("password");
				String nickname = rs.getString("nickname");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				mDto = new MemberDto(uId, pw, nickname, phone, email);
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
		
		return mDto;
	}
	public boolean checkPassword(String uId, String pw) {
		// 인수로 받은 유저와 화면에서 건네받은 비밀번호가 유저의 비밀번호와 같은지 비교하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;  // 비밀번호가 일치하다면 1
		
		try {
			String sql = 
					"SELECT " + 
					"    count(*) " + 
					"FROM " + 
					"    member " + 
					"WHERE " + 
					"    u_id = ? " + 
					"AND " + 
					"    password = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("count(*)");
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
		
		return result == 1;
	}
	public boolean modifyPassword(String uId, String newPw) {
		// 인수로 받은 유저의 비밀번호를 변경하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int result = -1;  // 비밀번호가 정상적으로 변경됐다면 1
		
		try {
			String sql = 
					"UPDATE member SET password = ? WHERE u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, newPw);
			pstmt.setString(2, uId);
			result = pstmt.executeUpdate();
			System.out.println(uId + "의 비번을 " + newPw + "로 바꿈");
			System.out.println("결과값 : " + result);
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
	public boolean modifyNickname(String uId, String nickname) {
		// 인수로 받은 유저의 닉네임을 변경하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int result = -1;  // 닉네임이 정상적으로 변경됐다면 1
		
		try {
			String sql = 
					"UPDATE member SET nickname = ? WHERE u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, nickname);
			pstmt.setString(2, uId);
			result = pstmt.executeUpdate();
			System.out.println(uId + "의 닉네임을 " + nickname + "로 바꿈");
			System.out.println("결과값 : " + result);
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
	public boolean modifyEmail(String uId, String email) {
		// 인수로 받은 유저의 이메일을 변경하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int result = -1;  // 이메일이 정상적으로 변경됐다면 1
		
		try {
			String sql = 
					"UPDATE member SET email = ? WHERE u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, uId);
			result = pstmt.executeUpdate();
			System.out.println(uId + "의 이메일을 " + email + "로 바꿈");
			System.out.println("결과값 : " + result);
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
	public boolean modifyPhone(String uId, String phone) {
		// 인수로 받은 유저의 휴대폰 번호를 변경하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int result = -1;  // 이메일이 정상적으로 변경됐다면 1
		
		try {
			String sql = 
					"UPDATE member SET phone = ? WHERE u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, phone);
			pstmt.setString(2, uId);
			result = pstmt.executeUpdate();
			System.out.println(uId + "의 휴대폰 번호를 " + phone + "로 바꿈");
			System.out.println("결과값 : " + result);
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
	public boolean memberWithdrawal(String uId) {
		// 인수로 받은 유저를 회원탈퇴시키는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int result = -1;  // 유저가 정상적으로 삭제됐다면 1
		
		try {
			String sql = 
					"DELETE FROM member WHERE u_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			result = pstmt.executeUpdate();
			System.out.println(uId + "를 탈퇴시킴");
			System.out.println("결과값 : " + result);
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
	public int sendAuthenticationMessage(String toPhone, String msg) {
		// 회원가입 시 휴대폰 인증 절차를 위한 문자전송 메서드.
		// 인증이 필요한 휴대폰 번호와 인증번호를 포함안 메시지를 파라미터로 받고, 응답코드를 반환한다.
		DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("NCSROYZK0AZHV5PV", 
				"8YTEXXYHSPFJKKA6UHY01TO7ILNIQZVL", 
				"https://api.coolsms.co.kr");
				
		Message message = new Message();
		// 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
		message.setFrom("01092166247");
		// 수신번호
		message.setTo(toPhone);
		// 보낼 메시지
		message.setText(msg);
		// 문자 전송
		SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
		System.out.println("상태코드 : " + response.getStatusCode()); // 코드 2000 : 정상
		return Integer.parseInt(response.getStatusCode());
	}
}
