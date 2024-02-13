package vo;

public class RecipeMagazineItemVo {
	private String title;
	private String thumbnail;
	private int recipeID;
	public RecipeMagazineItemVo(String title, String thumbnail, int recipeID) {
		this.title = title;
		this.thumbnail = thumbnail;
		this.recipeID = recipeID;
	}
	public int getRecipeID() {
		return recipeID;
	}
	public String getTitle() {
		return title;
	}
	public String getThumbnail() {
		return thumbnail;
	}

}