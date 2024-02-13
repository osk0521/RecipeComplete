package vo;

public class RecipeManagerCategoryVo {
	private String name;
	private String image;
	private int categoryId;
	public RecipeManagerCategoryVo(int categoryId, String name, String image){
		this.categoryId = categoryId;
		this.image = image;
		this.name = name;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public String getName() {
		return name;
	}
	public String getImage() {
		return image;
	}
}
