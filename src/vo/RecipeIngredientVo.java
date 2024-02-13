package vo;

public class RecipeIngredientVo {
	private int ingredieID;
	private String ingredieName;
	public RecipeIngredientVo(int ingredieID, String ingredieName) {
		this.ingredieName = ingredieName;
		this.ingredieID = ingredieID;
	}
	public int getIngredieID() {
		return ingredieID;
	}
	public String getingredieName() {
		return ingredieName;
	}

}
