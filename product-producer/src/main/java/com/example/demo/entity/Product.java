
  package com.example.demo.entity;
  
  import org.springframework.data.annotation.Id; import
  org.springframework.data.mongodb.core.mapping.Document;
  
  import lombok.Data;
  
  @Data 
  @Document(collection = "products") 
  public class Product {
  
  private String productId;
  
  private String prodName;
  
  private int price;
  
  }
 