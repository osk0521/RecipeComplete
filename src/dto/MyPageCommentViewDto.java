package dto;

public class MyPageCommentViewDto {
	private int recipeId;
	private int commentId;
	private String recipeThumbnail;
	private String commentContent;
	private String commentWriterNickname;
	private int commentStar;
	private String commentWritedate;
	
	public MyPageCommentViewDto(int recipeId, int commentId, String recipeThumbnail, String commentContent, String commentWriterNickname,
			int commentStar, String commentWritedate) {
		this.recipeId = recipeId;
		this.commentId = commentId;
		this.recipeThumbnail = recipeThumbnail;
		this.commentContent = commentContent;
		this.commentWriterNickname = commentWriterNickname;
		this.commentStar = commentStar;
		this.commentWritedate = commentWritedate;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getRecipeThumbnail() {
		return recipeThumbnail;
	}

	public void setRecipeThumbnail(String recipeThumbnail) {
		this.recipeThumbnail = recipeThumbnail;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentWriterNickname() {
		return commentWriterNickname;
	}

	public void setCommentWriterNickname(String commentWriterNickname) {
		this.commentWriterNickname = commentWriterNickname;
	}

	public int getCommentStar() {
		return commentStar;
	}

	public void setCommentStar(int commentStar) {
		this.commentStar = commentStar;
	}

	public String getCommentWritedate() {
		return commentWritedate;
	}

	public void setCommentWritedate(String commentWritedate) {
		this.commentWritedate = commentWritedate;
	}
	
}
