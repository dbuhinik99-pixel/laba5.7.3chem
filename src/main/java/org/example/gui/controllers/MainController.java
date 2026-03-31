package org.example.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.domain.Preparation;
import org.example.domain.Solution;
import org.example.service.impl.ComponentService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.SolutionService;
import org.example.storage.StorageManager;

import java.io.IOException;
import java.util.Collection;

public class MainController {

    // Таблица растворов
    @FXML private TableView<Solution> solutionTable;
    @FXML private TableColumn<Solution, Long> colId;
    @FXML private TableColumn<Solution, String> colName;
    @FXML private TableColumn<Solution, Double> colConcentration;
    @FXML private TableColumn<Solution, String> colUnit;
    @FXML private TableColumn<Solution, String> colSolvent;

    // Таблица приготовлений
    @FXML private TableView<Preparation> preparationTable;
    @FXML private TableColumn<Preparation, Long> prepColId;
    @FXML private TableColumn<Preparation, Double> prepColQuantity;
    @FXML private TableColumn<Preparation, String> prepColUnit;
    @FXML private TableColumn<Preparation, String> prepColComment;
    @FXML private TableColumn<Preparation, String> prepColTime;

    @FXML private Label lblStatus;

    private SolutionService solutionService;
    private PreparationService preparationService;
    private ComponentService componentService;
    private StorageManager storageManager;

    private ObservableList<Solution> solutions = FXCollections.observableArrayList();
    private ObservableList<Preparation> preparations = FXCollections.observableArrayList();

    public void setServices(SolutionService solutionService,
                            PreparationService preparationService,
                            ComponentService componentService,
                            StorageManager storageManager) {
        this.solutionService = solutionService;
        this.preparationService = preparationService;
        this.componentService = componentService;
        this.storageManager = storageManager;

        initTable();
        loadSolutions();
    }

    private void initTable() {
        // Настройка колонок для растворов
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colConcentration.setCellValueFactory(new PropertyValueFactory<>("concentration"));
        colUnit.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(
                        () -> cellData.getValue().getConcentrationUnit().toString()
                )
        );
        colSolvent.setCellValueFactory(new PropertyValueFactory<>("solvent"));

        // Настройка колонок для приготовлений
        prepColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        prepColQuantity.setCellValueFactory(new PropertyValueFactory<>("finalQuantity"));
        prepColUnit.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(
                        () -> cellData.getValue().getFinalUnit().toString()
                )
        );
        prepColComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        prepColTime.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(
                        () -> cellData.getValue().getPreparedAt().toString().substring(0, 19)
                )
        );

        solutionTable.setItems(solutions);
        preparationTable.setItems(preparations);

        // При выборе раствора показываем его приготовления
        solutionTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        loadPreparations(newSelection.getId());
                    } else {
                        preparations.clear();
                    }
                }
        );
    }

    private void loadSolutions() {
        solutions.clear();
        Collection<Solution> allSolutions = solutionService.getAll();
        solutions.addAll(allSolutions);
        updateStatus("Загружено растворов: " + allSolutions.size());
    }

    private void loadPreparations(long solutionId) {
        preparations.clear();
        Collection<Preparation> preps = preparationService.getBySolutionId(solutionId);
        preparations.addAll(preps);
        updateStatus("Приготовлений для раствора #" + solutionId + ": " + preps.size());
    }

    private void updateStatus(String message) {
        lblStatus.setText(message);
    }

    @FXML
    private void handleAddSolution() {
        showSolutionDialog(null);
    }

    @FXML
    private void handleEditSolution() {
        Solution selected = solutionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите раствор для редактирования");
            return;
        }
        showSolutionDialog(selected);
    }

    @FXML
    private void handleDeleteSolution() {
        Solution selected = solutionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите раствор для удаления");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Удаление раствора");
        alert.setContentText("Вы уверены, что хотите удалить раствор \"" + selected.getName() + "\"?\nБудут удалены все связанные приготовления и компоненты.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Удаляем все приготовления и компоненты
            Collection<Preparation> preps = preparationService.getBySolutionId(selected.getId());
            for (Preparation prep : preps) {
                componentService.removeByPreparationId(prep.getId());
                preparationService.remove(prep.getId());
            }
            loadSolutions();
            storageManager.saveData();
            updateStatus("Раствор удален");
        }
    }

    @FXML
    private void handleAddPreparation() {
        Solution selected = solutionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите раствор для добавления приготовления");
            return;
        }
        showPreparationDialog(selected, null);
    }

    @FXML
    private void handleEditPreparation() {
        Preparation selected = preparationTable.getSelectionModel().getSelectedItem();
        Solution currentSolution = solutionTable.getSelectionModel().getSelectedItem();
        if (selected == null || currentSolution == null) {
            showAlert("Ошибка", "Выберите приготовление для редактирования");
            return;
        }
        showPreparationDialog(currentSolution, selected);
    }

    @FXML
    private void handleDeletePreparation() {
        Preparation selected = preparationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите приготовление для удаления");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Удаление приготовления");
        alert.setContentText("Вы уверены, что хотите удалить приготовление #" + selected.getId() + "?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            componentService.removeByPreparationId(selected.getId());
            preparationService.remove(selected.getId());
            Solution currentSolution = solutionTable.getSelectionModel().getSelectedItem();
            if (currentSolution != null) {
                loadPreparations(currentSolution.getId());
            }
            storageManager.saveData();
            updateStatus("Приготовление удалено");
        }
    }

    @FXML
    private void handleRefresh() {
        storageManager.loadData();
        loadSolutions();
        updateStatus("Данные обновлены");
    }

    @FXML
    private void handleSave() {
        storageManager.saveData();
        updateStatus("Данные сохранены");
    }

    private void showSolutionDialog(Solution solution) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gui/views/solution-dialog.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(solution == null ? "Добавить раствор" : "Редактировать раствор");

            SolutionDialogController controller = loader.getController();
            controller.setServices(solutionService, storageManager);
            controller.setSolution(solution);

            stage.showAndWait();
            loadSolutions();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось открыть диалог: " + e.getMessage());
        }
    }

    private void showPreparationDialog(Solution solution, Preparation preparation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gui/views/preparation-dialog.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(preparation == null ? "Добавить приготовление" : "Редактировать приготовление");

            PreparationDialogController controller = loader.getController();
            controller.setServices(solution, preparationService, componentService, storageManager);
            controller.setPreparation(preparation);

            stage.showAndWait();
            if (solution != null) {
                loadPreparations(solution.getId());
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось открыть диалог: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleExit() {
        Stage stage = (Stage) lblStatus.getScene().getWindow();
        stage.close();
    }
}
