package dto;

public class InquiryDto {
	int inquiryId;
	String inquiryTitle;
	String inquiryContent;
	String inquiryWriter;
	String inquiryWritedate;
	InquiryAnswerDto inquiryAnswer;
	
	public InquiryDto(int inquiryId, String inquiryTitle, String inquiryContent, String inquiryWriter,
			String inquiryWritedate, InquiryAnswerDto inquiryAnswer) {
		this.inquiryId = inquiryId;
		this.inquiryTitle = inquiryTitle;
		this.inquiryContent = inquiryContent;
		this.inquiryWriter = inquiryWriter;
		this.inquiryWritedate = inquiryWritedate;
		this.inquiryAnswer = inquiryAnswer;
	}

	public int getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(int inquiryId) {
		this.inquiryId = inquiryId;
	}

	public String getInquiryTitle() {
		return inquiryTitle;
	}

	public void setInquiryTitle(String inquiryTitle) {
		this.inquiryTitle = inquiryTitle;
	}

	public String getInquiryContent() {
		return inquiryContent;
	}

	public void setInquiryContent(String inquiryContent) {
		this.inquiryContent = inquiryContent;
	}

	public String getInquiryWriter() {
		return inquiryWriter;
	}

	public void setInquiryWriter(String inquiryWriter) {
		this.inquiryWriter = inquiryWriter;
	}

	public String getInquiryWritedate() {
		return inquiryWritedate;
	}

	public void setInquiryWritedate(String inquiryWritedate) {
		this.inquiryWritedate = inquiryWritedate;
	}

	public InquiryAnswerDto getInquiryAnswer() {
		return inquiryAnswer;
	}

	public void setInquiryAnswer(InquiryAnswerDto inquiryAnswer) {
		this.inquiryAnswer = inquiryAnswer;
	}
}
