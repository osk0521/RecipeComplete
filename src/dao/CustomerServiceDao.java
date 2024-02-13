package dao;

import java.sql.*;
import java.util.ArrayList;

import common.ConnectionRecipe;
import dto.HelpDto;
import dto.InquiryAnswerDto;
import dto.InquiryDto;
import dto.NoticeDto;

public class CustomerServiceDao {
	public int getNoticePageNum() {
		// 공지사항의 페이지 숫자를 반환하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int noticeNum = 0; // 전체 공지사항의 숫자를 받을 변수
		
		try {
			String sql = 
					"SELECT " + 
					"    count(*) " + 
					"FROM " + 
					"    cs_recipe " + 
					"WHERE " + 
					"    type = 0 " + 
					"AND " + 
					"    complete = 1";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				noticeNum = rs.getInt("count(*)");
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
		
		if(noticeNum%5 == 0) {
			System.out.println("공지사항 페이지 수 : " + noticeNum/5);
			return noticeNum/5;
		}
		System.out.println("공지사항 페이지 수 : " + noticeNum/5 + 1);
		return noticeNum/5 + 1;
	}
	public ArrayList<NoticeDto> getNotice(int pageNum) {
		// 공지사항을 가져오는 메서드
		// 공지사항을 받아줄 리스트 생성
		ArrayList<NoticeDto> noticeList = new ArrayList<NoticeDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    t2.* " + 
					"FROM " + 
					"    (" + 
					"        SELECT " + 
					"            rownum rnum, " + 
					"            t1.* " + 
					"        FROM " + 
					"            (" + 
					"                SELECT " + 
					"                    cs_id notice_id, " + 
					"                    title notice_title, " + 
					"                    content notice_content, " + 
					"                    writedate notice_writedate " + 
					"                FROM " + 
					"                    cs_recipe " + 
					"                WHERE " + 
					"                    type = 0 " + 
					"                AND " + 
					"                    complete = 1 " + 
					"                ORDER BY " + 
					"                    writedate DESC " + 
					"            ) t1 " + 
					"    ) t2 " + 
					"WHERE " + 
					"    rnum >= ? " + 
					"AND " + 
					"    rnum <= ?";
			// 한 페이지당 5개씩
			int endNum = pageNum * 5;
			int startNum = endNum - 4;
			
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int noticeId = rs.getInt("notice_id");
				String noticeTitle = rs.getString("notice_title");
				String noticeContent = rs.getString("notice_content");
				String noticeWritedate = rs.getString("notice_writedate");
				
				noticeList.add(new NoticeDto(noticeId, noticeTitle, noticeContent, noticeWritedate));
				System.out.println("공지ID : " + noticeId + " , 제목 : " + noticeTitle + " , 내용 : " + noticeContent + " , 작성날짜 : " + noticeWritedate);
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
		
		return noticeList;
	}
	public ArrayList<HelpDto> getHelp() {
		// 도움말을 가져오는 메서드
		// 도움말을 받아줄 리스트 생성
		ArrayList<HelpDto> helpList = new ArrayList<HelpDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    cs_id help_id, " + 
					"    title help_title, " + 
					"    content help_content, " + 
					"    writedate help_writedate " + 
					"FROM " + 
					"    cs_recipe " + 
					"WHERE " + 
					"    type = 1 " + 
					"AND " + 
					"    complete = 1 " + 
					"ORDER BY " + 
					"    writedate DESC";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int helpId = rs.getInt("help_id");
				String helpTitle = rs.getString("help_title");
				String helpContent = rs.getString("help_content");
				String helpWritedate = rs.getString("help_writedate");
				
				helpList.add(new HelpDto(helpId, helpTitle, helpContent, helpWritedate));
				System.out.println("도움말ID + " + helpId + " , 제목 : " + helpTitle + " , 내용 : " + helpContent + " , 작성날짜 " + helpWritedate);
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
		
		return helpList;
	}
	public ArrayList<InquiryDto> getInquiry(String uId) {
		// 인수로 받은 유저가 작성한 문의사항을 가져오는 메서드
		// 문의사항에 답변이 되어있다면 답변 게시글도 같이 가져온다.
		// 문의사항을 받을 리스트 생성
		ArrayList<InquiryDto> inquiryList = new ArrayList<InquiryDto>();
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    cs_id inquiry_id, " + 
					"    title inquiry_title, " + 
					"    content inquiry_content, " + 
					"    u_id inquiry_writer, " + 
					"    writedate inquiry_writedate " + 
					"FROM " + 
					"    cs_recipe " + 
					"WHERE " + 
					"    u_id = ? " + 
					"AND " + 
					"    type = 2 " + 
					"AND " + 
					"    complete = 1 " + 
					"ORDER BY " + 
					"    writedate DESC";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, uId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int inquiryId = rs.getInt("inquiry_id");
				String inquiryTitle = rs.getString("inquiry_title");
				String inquiryContent = rs.getString("inquiry_content");
				String inquiryWriter = rs.getString("inquiry_writer");
				String inquiryWritedate = rs.getString("inquiry_writedate");
				InquiryAnswerDto inquiryAnswer = null;
				// 문의게시글에 대한 답변게시글을 뽑기 위한 2차 쿼리문
				try {
					String sql2 = 
							"SELECT " + 
							"    cs_id inquiry_id, " + 
							"    content inquiry_answer_content, " + 
							"    writedate inquiry_answer_writedate " + 
							"FROM " + 
							"    cs_recipe_answer " + 
							"WHERE " + 
							"    cs_id = ?";
					pstmt2 = ConnectionRecipe.getConnection().prepareStatement(sql2);
					pstmt2.setInt(1, inquiryId);
					
					rs2 = pstmt2.executeQuery();
					
					if(rs2.next()) {
						String inquiryAnswerContent = rs2.getString("inquiry_answer_content");
						String inquiryAnswerWritedate = rs2.getString("inquiry_answer_writedate");
						
						inquiryAnswer = new InquiryAnswerDto(inquiryId, inquiryAnswerContent, inquiryAnswerWritedate);
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
				} // 2차 try-catch 종료
				
				inquiryList.add(new InquiryDto(inquiryId, inquiryTitle, inquiryContent, inquiryWriter, inquiryWritedate, inquiryAnswer));
				System.out.println("문의사항ID : " + inquiryId + " , 제목 : " + inquiryTitle + " , 내용 : " + inquiryContent + " , 작성자 : " + inquiryWriter + " , 작성날짜 " + inquiryWritedate);
				// 만약 해당 문의게시글에 답변이 있다면 서버 콘솔창에서 출력하기
				if(inquiryAnswer != null) {
					System.out.println("답변게시글 내용 : " + inquiryAnswer.getInquiryAnswerContent() + " , 답변게시글 작성날짜 : " + inquiryAnswer.getInquiryAnswerWritedate());
				}
			} // while(rs.next()) 종료
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
	 	} // 1차 try-catch 종료
		
		return inquiryList;
	}
	public InquiryDto getInquiryDetail(int inquiryId) {
		// 문의글 수정을 위해 인수로 받은 문의글 번호에 해당하는 제목과 내용을 가져오는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		InquiryDto inquiryObj = null;
		
		try {
			String sql = 
					"SELECT " + 
					"    title, " + 
					"    content " + 
					"FROM " + 
					"    cs_recipe " + 
					"WHERE " + 
					"    cs_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, inquiryId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String inquiryTitle = rs.getString("title");
				String inquiryContent = rs.getString("content");
				
				inquiryObj = new InquiryDto(inquiryId, inquiryTitle, inquiryContent, null, null, null);
				System.out.println(inquiryId + "번의 제목 : " + inquiryTitle + ", 내용 : " + inquiryContent);
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
		
		return inquiryObj;
	}
	public boolean writeInquiry(String title, String uId, String content) {
		// 인수로 받은 유저가 문의글을 작성하는 메서드.
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int result = -1; // 정상적으로 작성될 때 1
		
		try {
			String sql = 
					"INSERT INTO " + 
					"    cs_recipe(cs_id, title, u_id, writedate, content, type, complete) " + 
					"VALUES " + 
					"    (SEQ_CS_RECIPE.nextval, ?, ?, sysdate, ?, 2, 1)";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, uId);
			pstmt.setString(3, content);
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
	public boolean modifyInquiry(int inquiryId, String inquiryTitle, String inquiryContent) {
		// 인수로 받은 문의글 번호의 문의글 제목, 내용을 수정하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int result = -1; // 문의글 정상 수정되었다면 1
		
		try {
			String sql = 
					"UPDATE cs_recipe SET title = ?, content = ? WHERE cs_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setString(1, inquiryTitle);
			pstmt.setString(2, inquiryContent);
			pstmt.setInt(3, inquiryId);
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
	public boolean deleteInquiry(int inquiryId) {
		// 인수로 받은 inquiyId의 게시글을 삭제하는 메서드
		// JDBC 객체 생성
		ConnectionRecipe.connectionRecipe();
		PreparedStatement pstmt = null;
		int result = -1; // 삭제 결과값. 정상 삭제될 시 1
		try {
			String sql = "DELETE FROM cs_recipe WHERE cs_id = ?";
			pstmt = ConnectionRecipe.getConnection().prepareStatement(sql);
			pstmt.setInt(1, inquiryId);
			result = pstmt.executeUpdate();
			System.out.println(inquiryId + "번 문의글 삭제");
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
