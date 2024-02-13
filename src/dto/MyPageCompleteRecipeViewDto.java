package dto;

public class MyPageCompleteRecipeViewDto {
	private int recipeId;
	private String recipeThumbnail;
	private String recipeTitle;
	private String recipeWriterNickname;
	
	public MyPageCompleteRecipeViewDto(int recipeId, String recipeThumbnail, String recipeTitle, String recipeWriterNickname) {
		this.recipeId = recipeId;
		this.recipeThumbnail = recipeThumbnail;
		this.recipeTitle = recipeTitle;
		this.recipeWriterNickname = recipeWriterNickname;
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

	public String getRecipeWriterNickname() {
		return recipeWriterNickname;
	}

	public void setRecipeWriterNickname(String recipeWriterNickname) {
		this.recipeWriterNickname = recipeWriterNickname;
	}
	
}
