package com.example.demo.bean;

public class MessageBean {
	
	private String prodId;
	  
	private int qty;

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public MessageBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageBean(String prodId, int qty) {
		super();
		this.prodId = prodId;
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "MessageBean [prodId=" + prodId + ", qty=" + qty + "]";
	}
	
	

}
