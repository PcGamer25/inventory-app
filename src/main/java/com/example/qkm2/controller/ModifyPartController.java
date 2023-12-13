package com.example.qkm2.controller;

import com.example.qkm2.data.InHouse;
import com.example.qkm2.data.Inventory;
import com.example.qkm2.data.Outsourced;
import com.example.qkm2.data.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Yibo Hwang
 */
public class ModifyPartController {
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceCostField;
    @FXML
    private TextField minField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField machineCompanyField;
    @FXML
    private Label machineCompanyLabel;
    @FXML
    private Label errorLabel;

    private Stage stage;
    private Parent root;
    private String errors = "";

    private Part selectedPart;

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
     * This method receives the selected part from the previous page
     *
     * @param selectedPart
     */
    public void passToModifyPart(Part selectedPart) {
        this.selectedPart = selectedPart;
        fillFields();
    }

    /**
     * This method fills the form with the selected part information
     */
    public void fillFields() {
        if (this.selectedPart instanceof InHouse) {
            inHouseRadio.setSelected(true);
            machineCompanyField.setText(String.valueOf(((InHouse) this.selectedPart).getMachineId()));
        }
        if (this.selectedPart instanceof Outsourced) {
            outsourcedRadio.setSelected(true);
            machineCompanyField.setText(((Outsourced) this.selectedPart).getCompanyName());
        }

        idField.setText(String.valueOf(this.selectedPart.getId()));
        nameField.setText(this.selectedPart.getName());
        invField.setText(String.valueOf(this.selectedPart.getStock()));
        priceCostField.setText(String.valueOf(this.selectedPart.getPrice()));
        minField.setText(String.valueOf(this.selectedPart.getMin()));
        maxField.setText(String.valueOf(this.selectedPart.getMax()));
    }

    /**
     * This method sets up the correct form for an in-house part
     *
     * @param event
     */
    public void toggleInHouse(ActionEvent event) {
        machineCompanyLabel.setText("Machine ID");
    }

    /**
     * This method sets up the correct form for an outsourced part
     *
     * @param event
     */
    public void toggleOutsourced(ActionEvent event) {
        machineCompanyLabel.setText("Company Name");
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
        if (priceCostField.getText().isBlank()) {
            this.errors += "Please enter a Price/Cost\n";
            check = false;
        } else {
            try {
                Double.parseDouble(priceCostField.getText());
            } catch (NumberFormatException e) {
                this.errors += "Please enter a double for Price/Cost\n";
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

        if (inHouseRadio.isSelected()) {
            if (machineCompanyField.getText().isBlank()) {
                this.errors += "Please enter a Machine ID\n";
                check = false;
            } else {
                try {
                    Integer.parseInt(machineCompanyField.getText());
                } catch (NumberFormatException e) {
                    this.errors += "Please enter an integer for Machine ID\n";
                    check = false;
                }
            }
        }

        if (outsourcedRadio.isSelected()) {
            if (machineCompanyField.getText().isBlank()) {
                this.errors += "Please enter a Company Name\n";
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
     * This method saves the modified part in the inventory
     *
     * @param event
     */
    public void partSave(ActionEvent event) {
        int selectedPartIndex = Inventory.getAllParts().indexOf(this.selectedPart);
        if (inHouseRadio.isSelected()) {
            if (dataCheck()) {
                Part newPart = new InHouse(
                        this.selectedPart.getId(),
                        nameField.getText(),
                        Double.parseDouble(priceCostField.getText()),
                        Integer.parseInt(invField.getText()),
                        Integer.parseInt(minField.getText()),
                        Integer.parseInt(maxField.getText()),
                        Integer.parseInt(machineCompanyField.getText())
                );
                Inventory.updatePart(selectedPartIndex, newPart);
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
        if (outsourcedRadio.isSelected()) {
            if (dataCheck()) {
                Part newPart = new Outsourced(
                        this.selectedPart.getId(),
                        nameField.getText(),
                        Double.parseDouble(priceCostField.getText()),
                        Integer.parseInt(invField.getText()),
                        Integer.parseInt(minField.getText()),
                        Integer.parseInt(maxField.getText()),
                        machineCompanyField.getText()
                );
                Inventory.updatePart(selectedPartIndex, newPart);
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
    }

}
