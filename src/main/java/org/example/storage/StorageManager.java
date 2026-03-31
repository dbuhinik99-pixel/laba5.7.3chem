package org.example.storage;

import org.example.IdGenerator;
import org.example.domain.Preparation;
import org.example.domain.PreparationComponent;
import org.example.domain.Solution;
import org.example.service.impl.ComponentService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.SolutionService;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StorageManager {
    private final JsonStorage storage;
    private final SolutionService solutionService;
    private final PreparationService preparationService;
    private final ComponentService componentService;
    private final IdGenerator idGenerator;

    public StorageManager(SolutionService solutionService,
                          PreparationService preparationService,
                          ComponentService componentService,
                          IdGenerator idGenerator) {
        this.storage = new JsonStorage();
        this.solutionService = solutionService;
        this.preparationService = preparationService;
        this.componentService = componentService;
        this.idGenerator = idGenerator;
    }

    public void loadData() {
        try {
            DataWrapper data = storage.load();

            if (data.getSolutions() != null && !data.getSolutions().isEmpty()) {
                solutionService.loadFromData(data.getSolutions());
            } else {
                solutionService.loadFromData(new LinkedHashMap<>());
            }

            if (data.getPreparations() != null && !data.getPreparations().isEmpty()) {
                preparationService.loadFromData(data.getPreparations());
            } else {
                preparationService.loadFromData(new LinkedHashMap<>());
            }

            if (data.getComponents() != null && !data.getComponents().isEmpty()) {
                componentService.loadFromData(data.getComponents());
            } else {
                componentService.loadFromData(new LinkedHashMap<>());
            }

            System.out.println("Данные загружены из " + (storage.exists() ? "файла" : "пустого состояния"));

        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных: " + e.getMessage());
            solutionService.loadFromData(new LinkedHashMap<>());
            preparationService.loadFromData(new LinkedHashMap<>());
            componentService.loadFromData(new LinkedHashMap<>());
        }
    }

    public void saveData() {
        try {
            Map<Long, Solution> solutions = solutionService.getAllAsMap();
            Map<Long, Preparation> preparations = preparationService.getAllAsMap();
            Map<Long, PreparationComponent> components = componentService.getAllAsMap();

            storage.save(solutions, preparations, components);
            System.out.println("Данные сохранены в файл");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении данных: " + e.getMessage());
        }
    }
}