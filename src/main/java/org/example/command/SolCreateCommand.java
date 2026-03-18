package org.example.command;

import org.example.domain.Solution;
import org.example.domain.SolutionConcentrationUnit;
import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SolCreateCommand extends BaseCommand {

    public SolCreateCommand(SolutionService solutionService,
                            PreparationService preparationService,
                            ComponentService componentService,
                            Scanner scanner,
                            DateTimeFormatter timeFormatter) {
        super(solutionService, preparationService, componentService, scanner, timeFormatter);
    }

    @Override
    public void execute(String args) {
        System.out.println("Создание раствора:");

        System.out.print("Название: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Ошибка: название не может быть пустым");
            return;
        }

        System.out.print("Концентрация: ");
        String concStr = scanner.nextLine().trim();

        double concentration;
        try {
            concentration = Double.parseDouble(concStr);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: концентрация должна быть числом");
            return;
        }

        System.out.print("Единицы концентрации (%|mol/L|g/L): ");
        String unitStr = scanner.nextLine().trim();
        SolutionConcentrationUnit unit = SolutionConcentrationUnit.fromString(unitStr);
        if (unit == null) {
            return;
        }

        System.out.print("Растворитель: ");
        String solvent = scanner.nextLine().trim();

        try {
            Solution solution = solutionService.add(name, concentration, unit, solvent, "SYSTEM");
            System.out.println("OK solution_id=" + solution.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}