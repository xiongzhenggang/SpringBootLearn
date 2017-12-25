package com.xzg.cn.entity;
//关联order不需要任何注解，与关系型数据库不同
public class Item {

    private Long id;
    private Order order;
    private String product;
    private double price;
    private int quantity;
public Item(){}
public Item(String product,double price,int quantity){
    this.price = price;
    this.product = product;
    this.quantity = quantity;
}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
