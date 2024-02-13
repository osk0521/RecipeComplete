package vo;

public class StoreMainpageGoodVo {
	private String image;
	private int productId;
	private String name;
	private int salePer;
	private int sellCost;
	private int deliveryCharge;
	private int hotDeal;
	private double score;
	private int repleValue;
	private int orderValue;
	
	public StoreMainpageGoodVo(String image, int productId, String name, int salePer, int sellCost, int deliveryCharge,
			int hotDeal, double score, int repleValue, int orderValue) {
		this.image = image;
		this.productId = productId;
		this.name = name;
		this.salePer = salePer;
		this.sellCost = sellCost;
		this.deliveryCharge = deliveryCharge;
		this.hotDeal = hotDeal;
		this.score = score;
		this.repleValue = repleValue;
		this.orderValue = orderValue;
	}

	public String getImage() {
		return image;
	}

	public int getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public int getSalePer() {
		return salePer;
	}

	public int getSellCost() {
		return sellCost;
	}

	public int getDeliveryCharge() {
		return deliveryCharge;
	}

	public int getHotDeal() {
		return hotDeal;
	}

	public double getScore() {
		return score;
	}

	public int getRepleValue() {
		return repleValue;
	}

	public int getOrderValue() {
		return orderValue;
	}
	
	
	
}
