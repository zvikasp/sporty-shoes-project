package model;

public class OrderDetailInfo {
	private String id;
	private String productCode;
	private String productName;
	private int quanity;
	private double price;
	private double amount;

	public OrderDetailInfo() {

	}

	public OrderDetailInfo(String id, String productCode, //
			String productName, int quanity, double price, double amount) {
		this.id = id;
		this.productCode = productCode;
		this.productName = productName;
		this.quanity = quanity;
		this.price = price;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getProductName() {
		return productName;
	}

	public int getQuanity() {
		return quanity;
	}

	public double getPrice() {
		return price;
	}

	public double getAmount() {
		return amount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}