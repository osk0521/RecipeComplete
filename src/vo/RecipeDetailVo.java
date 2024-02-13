package vo;

public class RecipeDetailVo {
	private String thumbnail;
	private String video;
	private String title;
	private String introduce;
	private int serving;
	private int recipeID;
	private int time;
	private int lv;
	private int hits;
	private int likes;
	private String tag;
	private String userID;
	private String writernickname;
	private String writerprofileImage;
	private String writerIntroduce;
	private int commentCnt;
	private String recipeWriteDate;
	private String recipeUpdateDate;
	public RecipeDetailVo(int recipeID, String thumbnail, String video, String title, String introduce, String tag, int hits, int likes, int serving, int time, int lv, int commentCnt, String userID, String writernickname, String writerprofileImage, String writerIntroduce, String writeDate, String updateDate) {

		this.thumbnail = thumbnail;
		this.title = title;
		this.video = video;
		this.introduce = introduce;
		this.serving = serving;
		this.time = time;
		this.lv = lv;
		this.hits = hits;
		this.likes = likes;
		this.tag = tag;
		this.commentCnt = commentCnt;
		this.recipeID = recipeID;
		this.userID = userID;
		this.writernickname = writernickname;
		this.writerprofileImage = writerprofileImage;
		this.writerIntroduce = writerIntroduce;
		this.recipeWriteDate = writeDate;
		this.recipeUpdateDate = updateDate;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public String getTitle() {
		return title;
	}
	public String getVideo() {
		return video;
	}
	public String getIntroduce() {
		return introduce;
	}
	public int getRecipeID() {
		return recipeID;
	}
	public int getServing() {
		return serving;
	}
	public int getTime() {
		return time;
	}
	public int getLv() {
		return lv;
	}
	public int getHits() {
		return hits;
	}
	public int getLikes() {
		return likes;
	}
	public String getTag() {
		return tag;
	}
	public int getCommentCnt() {
		return commentCnt;
	}
	public String getUserID() {
		return userID;
	}
	public String getWriterNickname() {
		return writernickname;
	}
	public String getWriterProfileImage() {
		return writerprofileImage;
	}
	public String getWriterIntroduce() {
		return writerIntroduce;
	}
	public String getWriteDate() {
		return recipeWriteDate;
	}
	public String getUpdateDate() {
		return recipeUpdateDate;
	}
	
}
