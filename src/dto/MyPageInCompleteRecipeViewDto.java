package dto;

public class MyPageInCompleteRecipeViewDto {
	private int recipeId;
	private String recipeThumbnail;
	private String recipeTitle;
	
	public MyPageInCompleteRecipeViewDto(int recipeId, String recipeThumbnail, String recipeTitle) {
		this.recipeId = recipeId;
		this.recipeThumbnail = recipeThumbnail;
		this.recipeTitle = recipeTitle;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeThumbnail() {
		return recipeThumbnail;
	}

	public void setRecipeThumbnail(String recipeThumbnail) {
		this.recipeThumbnail = recipeThumbnail;
	}

	public String getRecipeTitle() {
		return recipeTitle;
	}

	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}
	
}
