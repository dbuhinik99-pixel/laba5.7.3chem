package org.example.command;

import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PrepDeleteCommand extends BaseCommand {

    public PrepDeleteCommand(SolutionService solutionService,
                             PreparationService preparationService,
                             ComponentService componentService,
                             Scanner scanner,
                             DateTimeFormatter timeFormatter) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
    }

    @Override
    public void execute(String args) {
        if (args.isEmpty()) {
            System.out.println("Ошибка: укажите ID приготовления");
            return;
        }

        long id;
        try {
            id = Long.parseLong(args.trim());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
            return;
        }

        try {
            componentService.removeByPreparationId(id);
            preparationService.remove(id);
            System.out.println("OK deleted");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}