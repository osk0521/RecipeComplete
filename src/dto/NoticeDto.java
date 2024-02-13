package dto;

public class NoticeDto {
	private int noticeId;
	private String noticeTitle;
	private String noticeContent;
	private String noticeWritedate;
	
	public NoticeDto(int noticeId, String noticeTitle, String noticeContent, String noticeWritedate) {
		this.noticeId = noticeId;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeWritedate = noticeWritedate;
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeWritedate() {
		return noticeWritedate;
	}

	public void setNoticeWritedate(String noticeWritedate) {
		this.noticeWritedate = noticeWritedate;
	}
	
}
