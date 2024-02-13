package vo;

public class RecipeCommentVo {
	private int commentID;
	private int stars;
	private int commentOrder;
	private String content;
	private String image;
	private String userID;
	private String nickname;
	private String profileImage;
	private String commentWritedate;
	
	public RecipeCommentVo(int commentID, int commentOrder, String userID, String nickname, String profileImage, String content, String image, int stars, String commentWritedate) {
		this.commentID = commentID;
		this.stars = stars;
		this.content = content;
		this.image = image;
		this.stars = stars;
		this.userID = userID;
		this.nickname = nickname;
		this.commentOrder = commentOrder;
		this.profileImage = profileImage;
		this.commentWritedate = commentWritedate;
	}
	public int getCommentID() {
		return commentID;
	}
	public int getCommentOrder() {
		return commentOrder;
	}
	public int getCommentStars() {
		return stars;
	}
	public int getStars() {
		return stars;
	}
	public String getContent() {
		return content;
	}
	public String getImage() {
		return image;
	}
	public String getUserID() {
		return userID;
	}
	public String getCommentWritedate() {
		return commentWritedate;
	}
	public String getNickname() {
		return nickname;
	}
	public String getConmmentContent() {
		return content;
	}
	public String getConmmentImage() {
		return image;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public String getCommentWriteDate() {
		return commentWritedate;
	}
}
