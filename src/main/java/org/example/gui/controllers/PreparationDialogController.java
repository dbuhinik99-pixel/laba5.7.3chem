package org.example.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.Preparation;
import org.example.domain.Solution;
import org.example.domain.Units;
import org.example.service.impl.ComponentService;
import org.example.service.impl.PreparationService;
import org.example.storage.StorageManager;

import java.time.Instant;

public class PreparationDialogController {
    @FXML private TextField txtQuantity;
    @FXML private ComboBox<Units> cbUnit;
    @FXML private TextArea txtComment;

    private Solution solution;
    private PreparationService preparationService;
    private ComponentService componentService;
    private StorageManager storageManager;
    private Preparation preparation;

    public void setServices(Solution solution,
                            PreparationService preparationService,
                            ComponentService componentService,
                            StorageManager storageManager) {
        this.solution = solution;
        this.preparationService = preparationService;
        this.componentService = componentService;
        this.storageManager = storageManager;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
        if (preparation != null) {
            txtQuantity.setText(String.valueOf(preparation.getFinalQuantity()));
            cbUnit.setValue(preparation.getFinalUnit());
            txtComment.setText(preparation.getComment());
        }
    }

    @FXML
    private void initialize() {
        cbUnit.getItems().setAll(Units.ML, Units.G);
        cbUnit.setValue(Units.ML);
    }

    @FXML
    private void handleOk() {
        try {
            double quantity;
            try {
                quantity = Double.parseDouble(txtQuantity.getText().trim());
            } catch (NumberFormatException e) {
                showError("Количество должно быть числом");
                return;
            }

            Units unit = cbUnit.getValue();
            String comment = txtComment.getText().trim();

            if (preparation == null) {
                // Создание нового приготовления
                preparationService.add(solution.getId(), quantity, unit, comment, "SYSTEM", Instant.now());
            } else {
                // Обновление существующего приготовления
                preparation.setFinalQuantity(quantity);
                preparation.setFinalUnit(unit);
                preparation.setComment(comment);
            }

            storageManager.saveData();

            Stage stage = (Stage) txtQuantity.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) txtQuantity.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}