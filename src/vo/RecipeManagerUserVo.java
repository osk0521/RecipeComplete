package vo;

public class RecipeManagerUserVo {
	private String userID;
	private String nickname;
	private int phoneNum;
	private String email;
	private String profileIMG;
	private boolean adult;
	private boolean seller;
	private boolean manager;
	
	public RecipeManagerUserVo(String userID, String nickname, String profileIMG, int phoneNum, String email, boolean seller, boolean manager, boolean isAdult) {
		
		this.userID = userID;
		this.nickname = nickname;
		this.profileIMG = profileIMG;
		this.phoneNum = phoneNum;
		this.email = email;
		this.seller = seller;
		this.manager = manager;
		this.adult = isAdult;
	}
	public String getProfileIMG() {
		return profileIMG;
	}
	public String getUserID() {
		return userID;
	}
	public String getNickname() {
		return nickname;
	}
	public int getPhoneNum() {
		return phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public boolean isAdult() {
		return adult;
	}
	public boolean isSeller() {
		return seller;
	}
	public boolean isManager() {
		return manager;
	}
}
