package dto;

public class MyPageFollowingRecipeDto {
	private int recipeId;
	private String recipeTitle;
	private String recipeThumbnail;
	private String writerNickname;
	
	public MyPageFollowingRecipeDto(int recipeId, String recipeTitle, String recipeThumbnail, String writerNickname) {
		this.recipeId = recipeId;
		this.recipeTitle = recipeTitle;
		this.recipeThumbnail = recipeThumbnail;
		this.writerNickname = writerNickname;
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

	public String getWriterNickname() {
		return writerNickname;
	}

	public void setWriterNickname(String writerNickname) {
		this.writerNickname = writerNickname;
	}
	
}
