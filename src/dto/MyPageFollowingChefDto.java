package dto;

import java.util.ArrayList;

public class MyPageFollowingChefDto {
	String followingChefUid;
	String followingChefNickname;
	String followingChefProfileImage;
	int recipeQtyOfFollowingChef;
	int recipeLikeQtyOfFollowingChef;
	int recipeHitQtyOfFollowingChef;
	ArrayList<RecentRecipeOfFollowingChefDto> recentRecipeListOfFollowingChef;
	ArrayList<RecentReceiveCommentOfFollowingChefDto> recentReceiveCommentListOfFollowingChef;
	int followerQtyOfFollowingChef;
	
	public MyPageFollowingChefDto(String followingChefUid, String followingChefNickname,
			String followingChefProfileImage, int recipeQtyOfFollowingChef, int recipeLikeQtyOfFollowingChef,
			int recipeHitQtyOfFollowingChef, ArrayList<RecentRecipeOfFollowingChefDto> recentRecipeListOfFollowingChef,
			ArrayList<RecentReceiveCommentOfFollowingChefDto> recentReceiveCommentListOfFollowingChef,
			int followerQtyOfFollowingChef) {
		this.followingChefUid = followingChefUid;
		this.followingChefNickname = followingChefNickname;
		this.followingChefProfileImage = followingChefProfileImage;
		this.recipeQtyOfFollowingChef = recipeQtyOfFollowingChef;
		this.recipeLikeQtyOfFollowingChef = recipeLikeQtyOfFollowingChef;
		this.recipeHitQtyOfFollowingChef = recipeHitQtyOfFollowingChef;
		this.recentRecipeListOfFollowingChef = recentRecipeListOfFollowingChef;
		this.recentReceiveCommentListOfFollowingChef = recentReceiveCommentListOfFollowingChef;
		this.followerQtyOfFollowingChef = followerQtyOfFollowingChef;
	}

	public String getFollowingChefUid() {
		return followingChefUid;
	}

	public void setFollowingChefUid(String followingChefUid) {
		this.followingChefUid = followingChefUid;
	}

	public String getFollowingChefNickname() {
		return followingChefNickname;
	}

	public void setFollowingChefNickname(String followingChefNickname) {
		this.followingChefNickname = followingChefNickname;
	}

	public String getFollowingChefProfileImage() {
		return followingChefProfileImage;
	}

	public void setFollowingChefProfileImage(String followingChefProfileImage) {
		this.followingChefProfileImage = followingChefProfileImage;
	}

	public int getRecipeQtyOfFollowingChef() {
		return recipeQtyOfFollowingChef;
	}

	public void setRecipeQtyOfFollowingChef(int recipeQtyOfFollowingChef) {
		this.recipeQtyOfFollowingChef = recipeQtyOfFollowingChef;
	}

	public int getRecipeLikeQtyOfFollowingChef() {
		return recipeLikeQtyOfFollowingChef;
	}

	public void setRecipeLikeQtyOfFollowingChef(int recipeLikeQtyOfFollowingChef) {
		this.recipeLikeQtyOfFollowingChef = recipeLikeQtyOfFollowingChef;
	}

	public int getRecipeHitQtyOfFollowingChef() {
		return recipeHitQtyOfFollowingChef;
	}

	public void setRecipeHitQtyOfFollowingChef(int recipeHitQtyOfFollowingChef) {
		this.recipeHitQtyOfFollowingChef = recipeHitQtyOfFollowingChef;
	}

	public ArrayList<RecentRecipeOfFollowingChefDto> getRecentRecipeListOfFollowingChef() {
		return recentRecipeListOfFollowingChef;
	}

	public void setRecentRecipeListOfFollowingChef(
			ArrayList<RecentRecipeOfFollowingChefDto> recentRecipeListOfFollowingChef) {
		this.recentRecipeListOfFollowingChef = recentRecipeListOfFollowingChef;
	}

	public ArrayList<RecentReceiveCommentOfFollowingChefDto> getRecentReceiveCommentListOfFollowingChef() {
		return recentReceiveCommentListOfFollowingChef;
	}

	public void setRecentReceiveCommentListOfFollowingChef(
			ArrayList<RecentReceiveCommentOfFollowingChefDto> recentReceiveCommentListOfFollowingChef) {
		this.recentReceiveCommentListOfFollowingChef = recentReceiveCommentListOfFollowingChef;
	}

	public int getFollowerQtyOfFollowingChef() {
		return followerQtyOfFollowingChef;
	}

	public void setFollowerQtyOfFollowingChef(int followerQtyOfFollowingChef) {
		this.followerQtyOfFollowingChef = followerQtyOfFollowingChef;
	}
}
