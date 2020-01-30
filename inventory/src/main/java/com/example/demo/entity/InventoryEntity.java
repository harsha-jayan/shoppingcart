package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Inventory")
public class InventoryEntity {
	
	@Id
	@Column(name="productid")
	private String productId;
	
	@Column(name="quantity")
	private int quantity;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	public InventoryEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public InventoryEntity(String productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "InventoryEntity [productId=" + productId + ", quantity=" + quantity + "]";
	}

	

}
