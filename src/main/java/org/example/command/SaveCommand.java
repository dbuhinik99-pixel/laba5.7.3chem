package org.example.command;

import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;
import org.example.storage.StorageManager;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SaveCommand extends BaseCommand {
    private final StorageManager storageManager;

    public SaveCommand(SolutionService solutionService,
                       PreparationService preparationService,
                       ComponentService componentService,
                       Scanner scanner,
                       DateTimeFormatter timeFormatter,
                       StorageManager storageManager) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
        this.storageManager = storageManager;
    }

    @Override
    public void execute(String args) {
        storageManager.saveData();
    }
}