package dto;

public class ProfileDto {
	private String profileImage;
	private String nickname;
	private String selfIntroduce;
	private int totalHits;
	private int follower;
	private int following;
	
	public ProfileDto(String profileImage, String nickname, String selfIntroduce, int totalHits, int follower, int following) {
		this.profileImage = profileImage;
		this.nickname = nickname;
		this.selfIntroduce = selfIntroduce;
		this.totalHits = totalHits;
		this.follower = follower;
		this.following = following;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSelfIntroduce() {
		return selfIntroduce;
	}

	public void setSelfIntroduce(String selfIntroduce) {
		this.selfIntroduce = selfIntroduce;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public int getFollower() {
		return follower;
	}

	public void setFollower(int follower) {
		this.follower = follower;
	}

	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}
}
