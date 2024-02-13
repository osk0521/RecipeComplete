package vo;

public class RecipeProcessVo {
	private int processID;
	private String process; 
	private String material;
	private String cookEquipment;
	private String fire;
	private String tip;
	private String image;
	private String lastimage;
	private String lastTipCaution;
	
	public RecipeProcessVo(int processID, String process, String material, String cookEquipment, String fire, String tip, String image, String lastimage, String lastTipCaution) {
		this.processID = processID;
		this.process = process;
		this.material = material;
		this.cookEquipment = cookEquipment;
		this.fire = fire;
		this.tip = tip;
		this.image = image;
		this.lastimage = lastimage;
		this.lastTipCaution = lastTipCaution;
	}

	public String getProcess() {
		return process;
	}

	public String getMaterial() {
		return material;
	}

	public String getCookEquipment() {
		return cookEquipment;
	}

	public String getFire() {
		return fire;
	}

	public String getTip() {
		return tip;
	}

	public String getImage() {
		return image;
	}

	public int getProcessID() {
		return processID;
	}

	public String getLastimage() {
		return lastimage;
	}

	public String getLastTipCaution() {
		return lastTipCaution;
	}
}

