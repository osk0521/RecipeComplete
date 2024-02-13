package vo;

public class RecipeLvVo {
	private int lv;
	private String levelName;
	
	public RecipeLvVo(int lv, String levelName) {
		this.lv = lv;
		this.levelName = levelName;
	}
	public int getLv() {
		return lv;
	}
	public String getLevelName() {
		return levelName;
	}
}
