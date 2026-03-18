package org.example.command;

import org.example.domain.Solution;
import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SolShowCommand extends BaseCommand {

    public SolShowCommand(SolutionService solutionService,
                          PreparationService preparationService,
                          ComponentService componentService,
                          Scanner scanner,
                          DateTimeFormatter timeFormatter) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
    }

    @Override
    public void execute(String args) {
        if (args.isEmpty()) {
            System.out.println("Ошибка: укажите ID раствора");
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
            Solution s = solutionService.getById(id);
            int count = preparationService.countBySolutionId(id);

            System.out.printf("""
                Solution #%d
                name: %s
                concentration: %.2f %s
                solvent: %s
                preparations: %d%n""",
                    s.getId(), s.getName(), s.getConcentration(),
                    s.getConcentrationUnit(),
                    s.getSolvent().isEmpty() ? "-" : s.getSolvent(),
                    count);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}