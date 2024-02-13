package dto;

public class MemberDto {
	private String uId;
	private String pw;
	private String nickname;
	private String phone;
	private String email;
	
	public MemberDto(String uId, String pw, String nickname, String phone, String email) {
		this.uId = uId;
		this.pw = pw;
		this.nickname = nickname;
		this.phone = phone;
		this.email = email;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
