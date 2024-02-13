package vo;

public class RecipeMainPageShoppingVo {
	private String title;
	private String thumbnail;
	private int price;
	private int deliveryFee;
	private int hitcnt;
	private int productId;
	public RecipeMainPageShoppingVo(int productId, String title, String thumbnail, int price, int deliveryFee, int hitcnt) {
		this.productId = productId;
		this.title = title;
		this.title = title;
		this.thumbnail = thumbnail;
		this.price = price;
		this.deliveryFee = deliveryFee;
		this.hitcnt = hitcnt;
	}
	public String getTitle() {
		return title;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public int getProductId() {
		return productId;
	}
	public int getPrice() {
		return price;
	}
	public int getDeliveryFee() {
		return deliveryFee;
	}
	public int getHitcnt() {
		return hitcnt;
	}
}
