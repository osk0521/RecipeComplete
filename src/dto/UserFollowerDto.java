package dto;

public class UserFollowerDto {
	private String followerUid;
	private String followerNickname;
	private String followerProfileImage;
	private boolean isFollowing; // true : 내가 이 사람을 팔로잉함 false : 내가 이 사람을 팔로잉하지 않음
	
	public UserFollowerDto(String followerUid, String followerNickname, String followerProfileImage, boolean isFollowing) {
		this.followerUid = followerUid;
		this.followerNickname = followerNickname;
		this.followerProfileImage = followerProfileImage;
		this.isFollowing = isFollowing;
	}

	public String getFollowerUid() {
		return followerUid;
	}

	public void setFollowerUid(String followerUid) {
		this.followerUid = followerUid;
	}

	public String getFollowerNickname() {
		return followerNickname;
	}

	public void setFollowerNickname(String followerNickname) {
		this.followerNickname = followerNickname;
	}

	public String getFollowerProfileImage() {
		return followerProfileImage;
	}

	public void setFollowerProfileImage(String followerProfileImage) {
		this.followerProfileImage = followerProfileImage;
	}
	
	public boolean getIsFollowing() {
		return isFollowing;
	}
	
	public void setIsFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
}
