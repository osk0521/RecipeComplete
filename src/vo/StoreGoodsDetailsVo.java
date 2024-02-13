package vo;

public class StoreGoodsDetailsVo {
	private int productId;
	private String name;
	private String img;
	private String simpleInfo;
	private String category;
	private int salePer;
	private int firstCost;
	private int sellCost;
	private int deliveryCharge;
	private int reserves;
	private double score;
	private String video;
	private String goodsinfo;
	private String relatedInfo;
	private String sName;
	private String sEmail;
	private String sReportNum;
	private String sWorkplace;
	private String sPhone;
	private String sCompanyNum;
	
	public StoreGoodsDetailsVo(int productId, String name, String img, String simpleInfo, String category, int salePer,
			int firstCost, int sellCost, int deliChar, int reserves, double score, String video, String goodsinfo,
			String relatedInfo, String sName, String sEmail, String sReportNum, String sWorkplace, String sPhone,
			String sCompanyNum) {
		
		this.productId = productId;
		this.name = name;
		this.img = img;
		this.simpleInfo = simpleInfo;
		this.category = category;
		this.salePer = salePer;
		this.firstCost = firstCost;
		this.sellCost = sellCost;
		this.deliveryCharge = deliChar;
		this.reserves = reserves;
		this.score = score;
		this.video = video;
		this.goodsinfo = goodsinfo;
		this.relatedInfo = relatedInfo;
		this.sName = sName;
		this.sEmail = sEmail;
		this.sReportNum = sReportNum;
		this.sWorkplace = sWorkplace;
		this.sPhone = sPhone;
		this.sCompanyNum = sCompanyNum;
	}

	public int getProuductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public String getImg() {
		return img;
	}

	public String getSimpleInfo() {
		return simpleInfo;
	}

	public String getCategory() {
		return category;
	}

	public int getSalePer() {
		return salePer;
	}

	public int getFirstCost() {
		return firstCost;
	}

	public int getSellCost() {
		return sellCost;
	}

	public int getdeliveryCharge() {
		return deliveryCharge;
	}

	public int getReserves() {
		return reserves;
	}

	public double getScore() {
		return score;
	}

	public String getVideo() {
		return video;
	}

	public String getGoodsinfo() {
		return goodsinfo;
	}

	public String getRelatedInfo() {
		return relatedInfo;
	}

	public String getsName() {
		return sName;
	}

	public String getsEmail() {
		return sEmail;
	}

	public String getsReportNum() {
		return sReportNum;
	}

	public String getsWorkplace() {
		return sWorkplace;
	}

	public String getsPhone() {
		return sPhone;
	}

	public String getsCompanyNum() {
		return sCompanyNum;
	}
	
	
	
	
}
