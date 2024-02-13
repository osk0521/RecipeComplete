package dto;

public class RecentRecipeDto {
	private int recipeId;
	private String recipeTitle;
	private String recipeThumbnail;
	
	public RecentRecipeDto(int recipeId, String recipeTitle, String recipeThumbnail) {
		this.recipeId = recipeId;
		this.recipeTitle = recipeTitle;
		this.recipeThumbnail = recipeThumbnail;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeTitle() {
		return recipeTitle;
	}

	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}

	public String getRecipeThumbnail() {
		return recipeThumbnail;
	}

	public void setRecipeThumbnail(String recipeThumbnail) {
		this.recipeThumbnail = recipeThumbnail;
	}
}
