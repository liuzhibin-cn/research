package org.liuzhibin.research.mycat.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private long memberId;
	private String contact;
	private String phone;
	private String address;
	private List<CartItem> items;
	
	public Cart(long memberId) {
		this.memberId = memberId;
		this.items = new ArrayList<>();
	}
	public Cart saveAddress(String contact, String phone, String address) {
		this.contact = contact;
		this.phone = phone;
		this.address = address;
		return this;
	}
	public Cart addProduct(int productId, int quantity, double price, double subtotal, double discount) {
		CartItem ci = new CartItem();
		ci.setProductId(productId);
		ci.setQuantity(quantity);
		ci.setPrice(price);
		ci.setSubtotal(subtotal);
		ci.setDiscount(discount);
		this.items.add(ci);
		return this;
	}

	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<CartItem> getItems() {
		return items;
	}
	public void setItems(List<CartItem> items) {
		this.items = items;
	}
}