package vo;

public class RelatedGoodsVo {
	private String image;
	private int productId;
	private String name;
	private int salePer;
	private int firstCost;
	private double score;
	private int hits;
	
	public RelatedGoodsVo(String image, int productId, String name, int salePer, int firstCost, double score,
			int hits) {
		
		this.image = image;
		this.productId = productId;
		this.name = name;
		this.salePer = salePer;
		this.firstCost = firstCost;
		this.score = score;
		this.hits = hits;
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

	public int getFirstCost() {
		return firstCost;
	}

	public double getScore() {
		return score;
	}

	public int getHits() {
		return hits;
	}
	
	
	
	
}
