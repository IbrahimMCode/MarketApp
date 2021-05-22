package com.example.marketapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("product")
    @Expose
    private Product product;

    public OrderItem(Integer productId, Integer price, Integer quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s : - %2d x %2d",this.product.getName(), this.quantity, this.price);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Object getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}