package vo;

public class OrderAddressVo {
	private int addressId;
	private String receiver;
	private String zipCode;
	private String address;
	private String detailAddress;
	private String phoneNumber;
	private int defaultAddress;
	
	public OrderAddressVo(int addressId, String receiver, String zipCode, String address, String detailAddress,
			String phoneNumber, int defaultAddress) {
		this.addressId = addressId;
		this.receiver = receiver;
		this.zipCode = zipCode;
		this.address = address;
		this.detailAddress = detailAddress;
		this.phoneNumber = phoneNumber;
		this.defaultAddress = defaultAddress;
	}

	public int getAddressId() {
		return addressId;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getAddress() {
		return address;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getDefaultAddress() {
		return defaultAddress;
	}
	
}
