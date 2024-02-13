package dto;

public class ChefDto {
	private int rownum;
	private String chefUid;
	private String chefNickname;
	private String chefProfileImage;
	private int recipeQtyOfChef;
	private int totalRecipeLikesOfChef;
	private int totalRecipeHitsOfChef;
	private int followerQtyOfChef;
	private boolean checkFollow;
	
	public ChefDto(int rownum, String chefUid, String chefNickname, String chefProfileImage, int recipeQtyOfChef,
			int totalRecipeLikesOfChef, int totalRecipeHitsOfChef, int followerQtyOfChef, boolean checkFollow) {
		this.rownum = rownum;
		this.chefUid = chefUid;
		this.chefNickname = chefNickname;
		this.chefProfileImage = chefProfileImage;
		this.recipeQtyOfChef = recipeQtyOfChef;
		this.totalRecipeLikesOfChef = totalRecipeLikesOfChef;
		this.totalRecipeHitsOfChef = totalRecipeHitsOfChef;
		this.followerQtyOfChef = followerQtyOfChef;
		this.checkFollow = checkFollow;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public String getChefUid() {
		return chefUid;
	}

	public void setChefUid(String chefUid) {
		this.chefUid = chefUid;
	}

	public String getChefNickname() {
		return chefNickname;
	}

	public void setChefNickname(String chefNickname) {
		this.chefNickname = chefNickname;
	}

	public String getChefProfileImage() {
		return chefProfileImage;
	}

	public void setChefProfileImage(String chefProfileImage) {
		this.chefProfileImage = chefProfileImage;
	}

	public int getRecipeQtyOfChef() {
		return recipeQtyOfChef;
	}

	public void setRecipeQtyOfChef(int recipeQtyOfChef) {
		this.recipeQtyOfChef = recipeQtyOfChef;
	}

	public int getTotalRecipeLikesOfChef() {
		return totalRecipeLikesOfChef;
	}

	public void setTotalRecipeLikesOfChef(int totalRecipeLikesOfChef) {
		this.totalRecipeLikesOfChef = totalRecipeLikesOfChef;
	}

	public int getTotalRecipeHitsOfChef() {
		return totalRecipeHitsOfChef;
	}

	public void setTotalRecipeHitsOfChef(int totalRecipeHitsOfChef) {
		this.totalRecipeHitsOfChef = totalRecipeHitsOfChef;
	}

	public int getFollowerQtyOfChef() {
		return followerQtyOfChef;
	}

	public void setFollowerQtyOfChef(int followerQtyOfChef) {
		this.followerQtyOfChef = followerQtyOfChef;
	}

	public boolean isCheckFollow() {
		return checkFollow;
	}

	public void setCheckFollow(boolean checkFollow) {
		this.checkFollow = checkFollow;
	}
	
}
