package model;

import java.util.Date;
import java.util.List;

public class OrderInfo {
	private String id;
	private Date orderDate;
	private int orderNum;
	private double amount;
	private String customerName;
	private String customerAddress;
	private String customerEmail;
	private String customerPhone;
	private List<OrderDetailInfo> details;

	public OrderInfo() {
	}

	public OrderInfo(String id, Date orderDate, int orderNum, //
			double amount, String customerName, String customerAddress, //
			String customerEmail, String customerPhone) {
		this.id = id;
		this.orderDate = orderDate;
		this.orderNum = orderNum;
		this.amount = amount;

		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
	}

	public String getId() {
		return id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public double getAmount() {
		return amount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public List<OrderDetailInfo> getDetails() {
		return details;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public void setDetails(List<OrderDetailInfo> details) {
		this.details = details;
	}
}
