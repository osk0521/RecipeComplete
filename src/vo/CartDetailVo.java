package vo;

public class CartDetailVo {
	private int productId;
	private String name;
	private String optionNum;
	private String optionContent;
	private String image;
	private int price;
	private int qty;
	private int reserves;
	private int deliveryCharge;
	
	
	public CartDetailVo(int productId, String name, String optionNum, String optionContent, String image, int price,
			int qty, int reserves, int deliChar) {
		
		this.productId = productId;
		this.name = name;
		this.optionNum = optionNum;
		this.optionContent = optionContent;
		this.image = image;
		this.price = price;
		this.qty = qty;
		this.reserves = reserves;
		this.deliveryCharge = deliChar;
	}


	public int getProductId() {
		return productId;
	}


	public String getName() {
		return name;
	}


	public String getOptionNum() {
		return optionNum;
	}


	public String getOptionContent() {
		return optionContent;
	}


	public String getImage() {
		return image;
	}


	public int getPrice() {
		return price;
	}


	public int getQty() {
		return qty;
	}


	public int getReserves() {
		return reserves;
	}


	public int getdeliveryCharge() {
		return deliveryCharge;
	}
	
	
	
}
