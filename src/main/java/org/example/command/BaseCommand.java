package org.example.command;

import org.example.service.impl.SolutionService;
import org.example.service.impl.PreparationService;
import org.example.service.impl.ComponentService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public abstract class BaseCommand implements Command {
    protected final SolutionService solutionService;
    protected final PreparationService preparationService;
    protected final ComponentService componentService;
    protected final Scanner scanner;
    protected final DateTimeFormatter timeFormatter;

    public BaseCommand(SolutionService solutionService,
                       PreparationService preparationService,
                       ComponentService componentService,
                       Scanner scanner,
                       DateTimeFormatter timeFormatter) {
        this.solutionService = solutionService;
        this.preparationService = preparationService;
        this.componentService = componentService;
        this.scanner = scanner;
        this.timeFormatter = timeFormatter;
    }

    protected String stripQuotes(String str) {
        if (str == null) return null;
        str = str.trim();
        if (str.startsWith("\"") && str.endsWith("\"") && str.length() >= 2) {
            return str.substring(1, str.length() - 1);
        }
        if (str.startsWith("'") && str.endsWith("'") && str.length() >= 2) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    protected String truncate(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) return str == null ? "" : str;
        return str.substring(0, maxLength - 3) + "...";
    }
}