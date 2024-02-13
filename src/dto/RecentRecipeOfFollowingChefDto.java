package dto;

public class RecentRecipeOfFollowingChefDto {
	int recipeId;
	String thumbnail;
	String updateDate;
	
	public RecentRecipeOfFollowingChefDto(int recipeId, String thumbnail, String updateDate) {
		this.recipeId = recipeId;
		this.thumbnail = thumbnail;
		this.updateDate = updateDate;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
}
