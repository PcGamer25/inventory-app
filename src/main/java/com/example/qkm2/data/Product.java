package com.example.qkm2.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Yibo Hwang
 */
public class Product {

    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for new product
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method sets the ID
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * THis method sets the name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method sets the price
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method sets the stock
     *
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method sets the min amount
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method sets the max amount
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method gets the ID
     *
     * @return
     */
    public int getId() {
        return this.id;
    }

    /**
     * This method gets the name
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method gets the price
     *
     * @return
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * This method gets the stock
     *
     * @return
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * This method gets the min amount
     *
     * @return
     */
    public int getMin() {
        return this.min;
    }

    /**
     * This method gets the max amount
     *
     * @return
     */
    public int getMax() {
        return this.max;
    }

    /**
     * This method adds an associated part
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * This method removes an associated part
     *
     * @param selectedAssociatedPart
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return this.associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * This method gets all associated parts
     *
     * @return
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }

}
