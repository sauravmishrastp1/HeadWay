package com.headwayagent.salesadviser_headwaygms.models;

public class MyOrderItemModel {
   private String OrderStatus;
   private int rating;
   private String productImage;
   private String productTitle;
   private String Productprice;
   private String OrderId;
   private String orderDate;
   private String ProductdId;

   public String getOrderStatus() {
      return OrderStatus;
   }

   public void setOrderStatus(String orderStatus) {
      OrderStatus = orderStatus;
   }

   public int getRating() {
      return rating;
   }

   public void setRating(int rating) {
      this.rating = rating;
   }

   public String getProductImage() {
      return productImage;
   }

   public void setProductImage(String productImage) {
      this.productImage = productImage;
   }

   public String getProductTitle() {
      return productTitle;
   }

   public void setProductTitle(String productTitle) {
      this.productTitle = productTitle;
   }

   public String getProductprice() {
      return Productprice;
   }

   public void setProductprice(String productprice) {
      Productprice = productprice;
   }

   public String getOrderId() {
      return OrderId;
   }

   public void setOrderId(String orderId) {
      OrderId = orderId;
   }

   public String getOrderDate() {
      return orderDate;
   }

   public void setOrderDate(String orderDate) {
      this.orderDate = orderDate;
   }

   public String getProductdId() {
      return ProductdId;
   }

   public void setProductdId(String productdId) {
      ProductdId = productdId;
   }

   public MyOrderItemModel(String orderStatus, int rating, String productImage, String productTitle, String productprice, String orderId, String orderDate, String productdId) {
      OrderStatus = orderStatus;
      this.rating = rating;
      this.productImage = productImage;
      this.productTitle = productTitle;
      Productprice = productprice;
      OrderId = orderId;
      this.orderDate = orderDate;
      ProductdId = productdId;
   }
}



