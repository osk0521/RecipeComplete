 package vo;
public class RecipeIngrediVo {
	private int bundleNum;
	private String bundleName;
	private String ingrideName;
	private String qty;

	public RecipeIngrediVo(int bundleNum, String bundleName, String ingrideName, String qty) {
		this.bundleNum = bundleNum;
		this.bundleName = bundleName;
		this.ingrideName = ingrideName;
		this.qty = qty;
	}
	public int getBundleNum() {
		return bundleNum;
	}
	public String getBundleName() {
		return bundleName;
	}
	public String getIngrediName() {
		return ingrideName;
	}
	public String getQTY() {
		return qty;
	}
	
}
