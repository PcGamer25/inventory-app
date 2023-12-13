package com.example.qkm2.controller;

import com.example.qkm2.data.Inventory;
import com.example.qkm2.data.Part;
import com.example.qkm2.data.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yibo Hwang
 */
public class MainController implements Initializable {

    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInvColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TextField partsSearchField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productsIdColumn;
    @FXML
    private TableColumn<Product, String> productsNameColumn;
    @FXML
    private TableColumn<Product, Integer> productsInvColumn;
    @FXML
    private TableColumn<Product, Double> productsPriceColumn;
    @FXML
    private TextField productsSearchField;
    @FXML
    private Label partFoundLabel;
    @FXML
    private Label productFoundLabel;

    private Stage stage;
    private Parent root;

    /**
     * This method sets up the part and product tables
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partsTable.setItems(Inventory.getAllParts());

        productsIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productsInvColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productsPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productsTable.setItems(Inventory.getAllProducts());
    }

    /**
     * This method switches to the add part page on button click
     *
     * @param event
     * @throws IOException
     */
    public void switchToAddPart(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/qkm2/AddPart.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method switches to the modify part page on button click
     *
     * @param event
     * @throws IOException
     */
    public void switchToModifyPart(ActionEvent event) throws IOException {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qkm2/ModifyPart.fxml"));
            this.root = loader.load();

            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.passToModifyPart(selectedPart);

            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            this.stage.setScene(new Scene(this.root));
            this.stage.show();
        }
    }

    /**
     * This method switches to the add product page on button click
     *
     * @param event
     * @throws IOException
     */
    public void switchToAddProduct(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/qkm2/AddProduct.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method switches to the modify product page on button click
     *
     * @param event
     * @throws IOException
     */
    public void switchToModifyProduct(ActionEvent event) throws IOException {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qkm2/ModifyProduct.fxml"));
            this.root = loader.load();

            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.passToModifyProduct(selectedProduct);

            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            this.stage.setScene(new Scene(this.root));
            this.stage.show();
        }
    }

    /**
     * This method searches for the part entered on enter press
     *
     * @param event
     */
    public void searchParts(ActionEvent event) {
        if (partsSearchField.getText().isBlank()) {
            partsTable.setItems(Inventory.getAllParts());
            partFoundLabel.setText("");
        } else {
            try {
                if (Inventory.lookupPart(Integer.parseInt(partsSearchField.getText())) == null) {
                    partsTable.setItems(FXCollections.observableArrayList());
                } else {
                    partsTable.setItems(FXCollections.observableArrayList(Inventory.lookupPart(Integer.parseInt(partsSearchField.getText()))));
                    partFoundLabel.setText("");
                }
            } catch (NumberFormatException e) {
                partsTable.setItems(Inventory.lookupPart(partsSearchField.getText()));
                partFoundLabel.setText("");
            }
            if (partsTable.getItems().isEmpty()) {
                partFoundLabel.setText("Part not found");
            }
        }
    }

    /**
     * This method searches for the product entered on enter press
     *
     * @param event
     */
    public void searchProducts(ActionEvent event) {
        if (productsSearchField.getText().isBlank()) {
            productsTable.setItems(Inventory.getAllProducts());
            productFoundLabel.setText("");
        } else {
            try {
                if (Inventory.lookupProduct(Integer.parseInt(productsSearchField.getText())) == null) {
                    productsTable.setItems(FXCollections.observableArrayList());
                } else {
                    productsTable.setItems(FXCollections.observableArrayList(Inventory.lookupProduct(Integer.parseInt(productsSearchField.getText()))));
                    productFoundLabel.setText("");
                }
            } catch (NumberFormatException e) {
                productsTable.setItems(Inventory.lookupProduct(productsSearchField.getText()));
                productFoundLabel.setText("");
            }
            if (productsTable.getItems().isEmpty()) {
                productFoundLabel.setText("Product not found");
            }
        }
    }

    /**
     * This method deletes a selected part from the inventory
     */
    public void deletePart() {
        if (partsTable.getSelectionModel().getSelectedItem() != null) {
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("Delete Part");
            confirmDelete.setHeaderText("");
            confirmDelete.setContentText("Are you sure you want to delete this part?");
            confirmDelete.getDialogPane().setGraphic(null);

            if (confirmDelete.showAndWait().get() == ButtonType.OK) {
                Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
                searchParts(new ActionEvent());
            }
        }
    }

    /**
     * This method deletes a selected product from the inventory
     */
    public void deleteProduct() {
        if (productsTable.getSelectionModel().getSelectedItem() != null) {
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("Delete Product");
            confirmDelete.setHeaderText("");
            confirmDelete.setContentText("Are you sure you want to delete this product?");
            confirmDelete.getDialogPane().setGraphic(null);

            if (confirmDelete.showAndWait().get() == ButtonType.OK) {
                if (productsTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem());
                    searchProducts(new ActionEvent());
                } else {
                    Alert hasAssPartsError = new Alert(Alert.AlertType.ERROR);
                    hasAssPartsError.setTitle("Delete Product");
                    hasAssPartsError.setHeaderText("");
                    hasAssPartsError.setContentText("Can't delete product because it has an associated part");
                    hasAssPartsError.getDialogPane().setGraphic(null);
                    hasAssPartsError.show();
                }
            }
        }
    }

    /**
     * This method exits the application
     *
     * @param event
     */
    public void exit(ActionEvent event) {
        System.exit(0);
    }

}