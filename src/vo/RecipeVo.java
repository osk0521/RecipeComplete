package vo;

public class RecipeVo {
	private int recipeID;
	private String thumbnail;
	private String title;
	private int hits;
	private int commentCnt;
	private String userID;
	private String nickname;
	private String profileImage;
	
	public RecipeVo(int recipeID, String thumbnail, String title, int hits, int commentCnt, String userID, String nickname, String profileImage) {
		this.recipeID = recipeID;
		this.thumbnail = thumbnail;
		this.title = title;
		this.hits = hits;
		this.commentCnt = commentCnt;
		this.userID = userID;
		this.nickname = nickname;
		this.profileImage = profileImage;
	}
	public int getRecipeID() {
		return recipeID;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public String getTitle() {
		return title;
	}
	public int getHits() {
		return hits;
	}
	public int getCommentCnt() {
		return commentCnt;
	}
	public String getUserID() {
		return userID;
	}
	public String getNickname() {
		return nickname;
	}
	public String getProfileImage() {
		return profileImage;
	}
}

