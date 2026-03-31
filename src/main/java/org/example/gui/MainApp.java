package org.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.IdGenerator;
import org.example.gui.controllers.MainController;
import org.example.service.impl.ComponentService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.SolutionService;
import org.example.storage.StorageManager;

import java.net.URL;

public class MainApp extends Application {
    private StorageManager storageManager;
    private SolutionService solutionService;
    private PreparationService preparationService;
    private ComponentService componentService;

    static {
        // Настройка для Java 24
        System.setProperty("javafx.allowjs", "false");
        System.setProperty("prism.order", "sw");
    }

    @Override
    public void init() throws Exception {
        IdGenerator idGenerator = new IdGenerator();
        solutionService = new SolutionService(idGenerator);
        preparationService = new PreparationService(solutionService, idGenerator);
        componentService = new ComponentService(preparationService, idGenerator);

        storageManager = new StorageManager(solutionService, preparationService, componentService, idGenerator);
        storageManager.loadData();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlUrl = getClass().getResource("/org/example/gui/views/main-view.fxml");
        if (fxmlUrl == null) {
            System.err.println("FXML файл не найден!");
            return;
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Scene scene = new Scene(loader.load(), 1000, 700);

        MainController controller = loader.getController();
        controller.setServices(solutionService, preparationService, componentService, storageManager);

        primaryStage.setTitle("Система учета растворов");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (storageManager != null) {
            storageManager.saveData();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}