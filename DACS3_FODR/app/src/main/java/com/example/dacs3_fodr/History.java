package com.example.dacs3_fodr;

import com.example.dacs3_fodr.Adapter.Cart;

import java.util.List;

public class History {
    private String userId;
    private String name;
    private String phone;
    private String address;
    private List<Cart> cartList;
    private double totalAmount;
    private int status;
    private String orderID;

    public History() {
    }

    public History(String userId, String name, String phone, String address, List<Cart> cartList, double totalAmount, int status, String orderID) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.cartList = cartList;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderID = orderID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", cartList=" + cartList +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                '}';
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
