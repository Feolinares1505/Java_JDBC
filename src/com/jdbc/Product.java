package com.jdbc;


/**
 * Esta clase será utilizada para procesar un registro de la base de datos
 * */
public class Product {
    private int id_product;
    private String name;
    private double price;
    private int quantity;

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString(){
        return "Product: ID= " + id_product + " Name= " + name + "Price= " + price + "Quantity= " + quantity;
    }
}
