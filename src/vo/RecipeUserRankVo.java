package vo;

public class RecipeUserRankVo {
	private String nickname;
	private String uID;
	private String profile;
	public RecipeUserRankVo(String uID, String nickname, String profile) {
		this.uID = uID;
		this.nickname = nickname;
		this.profile = profile;
	}
	
	public String getUserID() {
		return uID;
	}
	public String getNickname() {
		return nickname;
	}
	public String getProfile() {
		return profile;
	}
}
