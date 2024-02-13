package dto;

public class InquiryAnswerDto {
	int inquiryId;
	String inquiryAnswerContent;
	String inquiryAnswerWritedate;
	
	public InquiryAnswerDto(int inquiryId, String inquiryAnswerContent, String inquiryAnswerWritedate) {
		this.inquiryId = inquiryId;
		this.inquiryAnswerContent = inquiryAnswerContent;
		this.inquiryAnswerWritedate = inquiryAnswerWritedate;
	}

	public int getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(int inquiryId) {
		this.inquiryId = inquiryId;
	}

	public String getInquiryAnswerContent() {
		return inquiryAnswerContent;
	}

	public void setInquiryAnswerContent(String inquiryAnswerContent) {
		this.inquiryAnswerContent = inquiryAnswerContent;
	}

	public String getInquiryAnswerWritedate() {
		return inquiryAnswerWritedate;
	}

	public void setInquiryAnswerWritedate(String inquiryAnswerWritedate) {
		this.inquiryAnswerWritedate = inquiryAnswerWritedate;
	}
}
