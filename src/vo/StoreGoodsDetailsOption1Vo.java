package vo;

public class StoreGoodsDetailsOption1Vo {
	private String op1Num;
	private String optionContent;
	private int price;
	private int qty;
	
	public StoreGoodsDetailsOption1Vo(String op1Num2, String content, int price, int qty) {
		this.op1Num = op1Num2;
		this.optionContent = content;
		this.price = price;
		this.qty = qty;
	}
	
	public StoreGoodsDetailsOption1Vo(String op1num , int qty) {
		this.op1Num = op1num;
		this.qty = qty;
		this.price = 0;
		this.optionContent = null;
	}
	
	public String getOp1Num() {
		return op1Num;
	}

	public String getoptionContent() {
		return optionContent;
	}

	public int getPrice() {
		return price;
	}

	public int getQty() {
		return qty;
	}
	
	
}
