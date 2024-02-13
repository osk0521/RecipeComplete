package vo;

public class RecipeManagerMagazineVo {
	private String name;
	private String image;
	private int magazineId;
	RecipeManagerMagazineVo(int magazineId,String name, String image){
		this.magazineId = magazineId;
		this.image = image;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public String getImage() {
		return image;
	}
	public int getMagazineId() {
		return magazineId;
	}

}
