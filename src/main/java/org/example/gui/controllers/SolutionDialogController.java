package org.example.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.Solution;
import org.example.domain.SolutionConcentrationUnit;
import org.example.service.impl.SolutionService;
import org.example.storage.StorageManager;

public class SolutionDialogController {
    @FXML private TextField txtName;
    @FXML private TextField txtConcentration;
    @FXML private ComboBox<SolutionConcentrationUnit> cbUnit;
    @FXML private TextField txtSolvent;

    private SolutionService solutionService;
    private StorageManager storageManager;
    private Solution solution;
    private Stage dialogStage;

    public void setServices(SolutionService solutionService, StorageManager storageManager) {
        this.solutionService = solutionService;
        this.storageManager = storageManager;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        if (solution != null) {
            txtName.setText(solution.getName());
            txtConcentration.setText(String.valueOf(solution.getConcentration()));
            cbUnit.setValue(solution.getConcentrationUnit());
            txtSolvent.setText(solution.getSolvent());
        }
    }

    @FXML
    private void initialize() {
        cbUnit.getItems().setAll(SolutionConcentrationUnit.values());
        cbUnit.setValue(SolutionConcentrationUnit.PERCENT);
    }

    @FXML
    private void handleOk() {
        try {
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                showError("Название не может быть пустым");
                return;
            }

            double concentration;
            try {
                concentration = Double.parseDouble(txtConcentration.getText().trim());
            } catch (NumberFormatException e) {
                showError("Концентрация должна быть числом");
                return;
            }

            SolutionConcentrationUnit unit = cbUnit.getValue();
            String solvent = txtSolvent.getText().trim();

            if (solution == null) {
                // Создание нового раствора
                solutionService.add(name, concentration, unit, solvent, "SYSTEM");
            } else {
                // Обновление существующего раствора
                solution.setName(name);
                solution.setConcentration(concentration);
                solution.setConcentrationUnit(unit);
                solution.setSolvent(solvent);
            }

            storageManager.saveData();

            Stage stage = (Stage) txtName.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) txtName.getScene().getWindow();
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