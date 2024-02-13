package vo;

public class RecipeMagazineVo {
	private int magazineID;
	private String mName;
	private String mImage;
	public RecipeMagazineVo(int magazineID, String mName, String mImage) {
		this.magazineID = magazineID;
		this.mName = mName;
		this.mImage = mImage;
	}
	public int getMID() {
		return magazineID;
	}
	public String getMName() {
		return mName;
	}
	public String getMImage() {
		return mImage;
	}
}