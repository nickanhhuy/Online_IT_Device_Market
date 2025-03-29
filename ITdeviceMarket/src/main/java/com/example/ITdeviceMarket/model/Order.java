package com.example.ITdeviceMarket.model;

import jakarta.persistence.*;

@Entity
@Table(name = "device_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String deviceType;

    @Column(nullable = false)
    private String deviceColor;

    @Column(nullable = false)
    private int deviceQuantity;

    @Column(nullable = false)
    private double totalPrice;

    // Default constructor (needed by JPA)
    public Order() {}

    // Constructor with all parameters
    public Order(String username, String deviceType, String deviceColor, int deviceQuantity, double totalPrice) {
        this.username = username;
        this.deviceType = deviceType;
        this.deviceColor = deviceColor;
        this.deviceQuantity = deviceQuantity;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDevice_type() {
        return deviceType;
    }

    public void setDevice_type(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDevice_color() {
        return deviceColor;
    }

    public void setDevice_color(String deviceColor) {
        this.deviceColor = deviceColor;
    }

    public int getDevice_quantity() {
        return deviceQuantity;
    }

    public void setDevice_quantity(int deviceQuantity) {
        this.deviceQuantity = deviceQuantity;
    }

    public double getTotal_price() {
        return totalPrice;
    }

    public void setTotal_price(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

