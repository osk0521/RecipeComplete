package dto;

public class UserFollowingDto {
	private String followingUid;
	private String followingNickname;
	private String followingProfileImage;
	private boolean isFollowing; // true : 내가 이 사람을 팔로잉함 false : 내가 이 사람을 팔로잉하지 않음
	
	public UserFollowingDto(String followingUid, String followingNickname, String followingProfileImage, boolean isFollowing) {
		this.followingUid = followingUid;
		this.followingNickname = followingNickname;
		this.followingProfileImage = followingProfileImage;
		this.isFollowing = isFollowing;
	}

	public String getFollowingUid() {
		return followingUid;
	}

	public void setFollowingUid(String followingUid) {
		this.followingUid = followingUid;
	}

	public String getFollowingNickname() {
		return followingNickname;
	}

	public void setFollowingNickname(String followingNickname) {
		this.followingNickname = followingNickname;
	}

	public String getFollowingProfileImage() {
		return followingProfileImage;
	}

	public void setFollowingProfileImage(String followingProfileImage) {
		this.followingProfileImage = followingProfileImage;
	}
	
	public boolean getIsFollowing() {
		return isFollowing;
	}
	
	public void setIsFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
	
}
