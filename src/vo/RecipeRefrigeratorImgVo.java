package vo;

public class RecipeRefrigeratorImgVo {
	private String ingredieName;
	private String ingredieImg;
	public RecipeRefrigeratorImgVo(String ingredieName, String ingredieImg) {
		this.ingredieImg = ingredieImg;
		this.ingredieName = ingredieName;
	}
	public String getIngredieName() {
		return ingredieName;
	}
	public String getingredieImg() {
		return ingredieImg;
	}

}
