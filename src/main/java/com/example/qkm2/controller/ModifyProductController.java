package com.example.qkm2.controller;

import com.example.qkm2.data.Inventory;
import com.example.qkm2.data.Part;
import com.example.qkm2.data.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class ModifyProductController implements Initializable {

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
    private TableView<Part> assPartsTable;
    @FXML
    private TableColumn<Part, Integer> assPartIdColumn;
    @FXML
    private TableColumn<Part, String> assPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> assPartInvColumn;
    @FXML
    private TableColumn<Part, Double> assPartPriceColumn;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField minField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField partsSearchField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label partFoundLabel;

    private Stage stage;
    private Parent root;
    private String errors = "";
    private Product selectedProduct;
    private ObservableList<Part> assParts = FXCollections.observableArrayList();

    /**
     * This method sets up the part and associated part tables
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

        assPartIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        assPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        assPartInvColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        assPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        assPartsTable.setItems(this.assParts);
    }

    /**
     * This method switches to the main page on button click
     *
     * @param event
     * @throws IOException
     */
    public void switchToMain(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/qkm2/Main.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method receives the selected product from the previous page
     *
     * @param selectedProduct
     */
    public void passToModifyProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        fillFields();
    }

    /**
     * This method fills the form with the selected product information
     */
    public void fillFields() {
        idField.setText(String.valueOf(this.selectedProduct.getId()));
        nameField.setText(this.selectedProduct.getName());
        invField.setText(String.valueOf(this.selectedProduct.getStock()));
        priceField.setText(String.valueOf(this.selectedProduct.getPrice()));
        minField.setText(String.valueOf(this.selectedProduct.getMin()));
        maxField.setText(String.valueOf(this.selectedProduct.getMax()));

        assParts.addAll(this.selectedProduct.getAllAssociatedParts());
    }

    /**
     * This method verifies all data entered on the form
     *
     * @return
     */
    private boolean dataCheck() {
        boolean check = true;
        if (nameField.getText().isBlank()) {
            this.errors += "Please enter a Name\n";
            check = false;
        }
        if (priceField.getText().isBlank()) {
            this.errors += "Please enter a Price\n";
            check = false;
        } else {
            try {
                Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                this.errors += "Please enter a double for Price\n";
                check = false;
            }
        }
        if (invField.getText().isBlank()) {
            this.errors += "Please enter a value for Inv\n";
            check = false;
        } else {
            try {
                Integer.parseInt(invField.getText());
            } catch (NumberFormatException e) {
                this.errors += "Please enter an integer for Inv\n";
                check = false;
            }
        }
        if (minField.getText().isBlank()) {
            this.errors += "Please enter a value for Min\n";
            check = false;
        } else {
            try {
                Integer.parseInt(minField.getText());
            } catch (NumberFormatException e) {
                this.errors += "Please enter an integer for Min\n";
                check = false;
            }
        }
        if (maxField.getText().isBlank()) {
            this.errors += "Please enter a value for Max\n";
            check = false;
        } else {
            try {
                Integer.parseInt(maxField.getText());
            } catch (NumberFormatException e) {
                this.errors += "Please enter an integer for Max\n";
                check = false;
            }
        }

        if (check) {
            if (!(Integer.parseInt(minField.getText()) <= Integer.parseInt(maxField.getText()))) {
                this.errors += "Min must be less than Max\n";
                check = false;
            }
            if (!((Integer.parseInt(invField.getText()) >= Integer.parseInt(minField.getText())) &&
                    (Integer.parseInt(invField.getText()) <= Integer.parseInt(maxField.getText())))) {
                this.errors += "Inv must be between Min and Max\n";
                check = false;
            }
        }
        return check;
    }

    /**
     * This method saves the modified product in the inventory
     *
     * @param event
     */
    public void productSave(ActionEvent event) {
        int selectedProductIndex = Inventory.getAllProducts().indexOf(this.selectedProduct);
        if (dataCheck()) {
            Product newProduct = new Product(
                    this.selectedProduct.getId(),
                    nameField.getText(),
                    Double.parseDouble(priceField.getText()),
                    Integer.parseInt(invField.getText()),
                    Integer.parseInt(minField.getText()),
                    Integer.parseInt(maxField.getText())
            );

            this.assParts.forEach(newProduct::addAssociatedPart);
            Inventory.updateProduct(selectedProductIndex, newProduct);
            try {
                switchToMain(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            errorLabel.setText(this.errors);
            this.errors = "";
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
     * This method associates a part with a product
     *
     * @param event
     */
    public void addAssPart(ActionEvent event) {
        if (partsTable.getSelectionModel().getSelectedItem() != null) {
            this.assParts.add(partsTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * This method disassociates a part from a product
     *
     * @param event
     */
    public void removeAssPart(ActionEvent event) {
        if (this.assPartsTable.getSelectionModel().getSelectedItem() != null) {
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("Remove Associated Part");
            confirmDelete.setHeaderText("");
            confirmDelete.setContentText("Are you sure you want to remove this associated part?");
            confirmDelete.getDialogPane().setGraphic(null);

            if (confirmDelete.showAndWait().get() == ButtonType.OK) {
                this.assParts.remove(this.assPartsTable.getSelectionModel().getSelectedItem());
            }
        }
    }

}
