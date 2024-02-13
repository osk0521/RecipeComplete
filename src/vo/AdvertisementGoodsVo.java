package vo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;

public class AdvertisementGoodsVo {
	private int productId;
	private String productImage;
	private String productName;
	private String productCost;
	private int productScore;
	private int productCommentQty;
	
	public AdvertisementGoodsVo(int productId, String productImage, String productName, String productCost,
			int productScore, int productCommentQty) {
		this.productId = productId;
		this.productImage = productImage;
		this.productName = productName;
		this.productCost = productCost;
		this.productScore = productScore;
		this.productCommentQty = productCommentQty;
	}

	public int getProductId() {
		return productId;
	}

	public String getProductImage() {
		return productImage;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductCost() {
		return productCost;
	}

	public int getProductScore() {
		return productScore;
	}

	public int getProductCommentQty() {
		return productCommentQty;
	}
}
