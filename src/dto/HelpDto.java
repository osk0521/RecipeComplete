package dto;

public class HelpDto {
	private int helpId;
	private String helpTitle;
	private String helpContent;
	private String helpWritedate;
	
	public HelpDto(int helpId, String helpTitle, String helpContent, String helpWritedate) {
		this.helpId = helpId;
		this.helpTitle = helpTitle;
		this.helpContent = helpContent;
		this.helpWritedate = helpWritedate;
	}

	public int getHelpId() {
		return helpId;
	}

	public void setHelpId(int helpId) {
		this.helpId = helpId;
	}

	public String getHelpTitle() {
		return helpTitle;
	}

	public void setHelpTitle(String helpTitle) {
		this.helpTitle = helpTitle;
	}

	public String getHelpContent() {
		return helpContent;
	}

	public void setHelpContent(String helpContent) {
		this.helpContent = helpContent;
	}

	public String getHelpWritedate() {
		return helpWritedate;
	}

	public void setHelpWritedate(String helpWritedate) {
		this.helpWritedate = helpWritedate;
	}
	
}
