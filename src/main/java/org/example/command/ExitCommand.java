package org.example.command;

import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ExitCommand extends BaseCommand {

    public ExitCommand(SolutionService solutionService,
                       PreparationService preparationService,
                       ComponentService componentService,
                       Scanner scanner,
                       DateTimeFormatter timeFormatter) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
    }

    @Override
    public void execute(String args) {
        System.out.println("До свидания!");
        System.exit(0);
    }
}