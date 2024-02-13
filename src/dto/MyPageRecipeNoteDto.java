package dto;

public class MyPageRecipeNoteDto {
	private int recipeId;
	private String recipeTitle;
	private String recipeThumbnail;
	private String noteContent;
	private String noteWritedate;
	
	public MyPageRecipeNoteDto(int recipeId, String recipeTitle, String recipeThumbnail, String noteContent,
			String noteWritedate) {
		this.recipeId = recipeId;
		this.recipeTitle = recipeTitle;
		this.recipeThumbnail = recipeThumbnail;
		this.noteContent = noteContent;
		this.noteWritedate = noteWritedate;
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

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public String getNoteWritedate() {
		return noteWritedate;
	}

	public void setNoteWritedate(String noteWritedate) {
		this.noteWritedate = noteWritedate;
	}
	
}
