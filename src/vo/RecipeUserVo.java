package vo;

public class RecipeUserVo {
	private String nickname;
	private String profile;
	public RecipeUserVo(String nickname, String profile) {
		this.nickname = nickname;
		this.profile = profile;
	}
	public String getNickname() {
		return nickname;
	}
	public String getProfile() {
		return profile;
	}
}
