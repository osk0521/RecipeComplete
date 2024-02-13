package dto;

public class RecentReceiveCommentOfFollowingChefDto {
	String commentWriterUid;
	String commentWriterNickname;
	String commentContent;
	
	public RecentReceiveCommentOfFollowingChefDto(String commentWriterUid, String commentWriterNickname,
			String commentContent) {
		this.commentWriterUid = commentWriterUid;
		this.commentWriterNickname = commentWriterNickname;
		this.commentContent = commentContent;
	}

	public String getCommentWriterUid() {
		return commentWriterUid;
	}

	public void setCommentWriterUid(String commentWriterUid) {
		this.commentWriterUid = commentWriterUid;
	}

	public String getCommentWriterNickname() {
		return commentWriterNickname;
	}

	public void setCommentWriterNickname(String commentWriterNickname) {
		this.commentWriterNickname = commentWriterNickname;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
}
