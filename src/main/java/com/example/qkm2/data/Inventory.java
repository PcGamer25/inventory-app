package com.example.qkm2.data;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Yibo Hwang
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int partId = 1;
    private static int productId = 1000;

    /**
     * This method generates a part ID
     *
     * @return
     */
    public static int genPartId() {
        return partId++;
    }

    /**
     * This method generates a product ID
     *
     * @return
     */
    public static int genProductId() {
        return productId++;
    }

    /**
     * This method adds a new part
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds a new product
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method searches for a part
     *
     * @param partId
     * @return
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * This method searches for a product
     *
     * @param productId
     * @return
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * This method searches for parts
     *
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.stream()
                .filter(part -> part.getName().contains(partName))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * This method searches for products
     *
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.stream()
                .filter(product -> product.getName().contains(productName))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * This method updates a part
     *
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * This method updates a product
     *
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * This method removes a part
     *
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * This method removes a product
     *
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * This method gets all parts
     *
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method gets all products
     *
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
