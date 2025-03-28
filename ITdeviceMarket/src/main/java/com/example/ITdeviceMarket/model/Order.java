package com.example.ITdeviceMarket.model;

import jakarta.persistence.*;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String device_type;
    private String device_color;
    private int device_quantity;

    public Order() {}
    public Order(long id, String device_type, String device_color, int device_quantity) {
        super();
        this.id = id;
        this.device_type = device_type;
        this.device_color = device_color;
        this.device_quantity = device_quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_color() {
        return device_color;
    }

    public void setDevice_color(String device_color) {
        this.device_color = device_color;
    }

    public int getDevice_quantity() {
        return device_quantity;
    }

    public void setDevice_quantity(int device_quantity) {
        this.device_quantity = device_quantity;
    }
}
