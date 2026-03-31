package org.example;

import org.example.cli.CliApp;
import org.example.gui.MainApp;

public class Main {
    public static void main(String[] args) {
        // Проверяем аргументы командной строки
        if (args.length > 0 && args[0].equals("--cli")) {
            // Консольный режим
            new CliApp().start();
        } else {
            // Графический режим по умолчанию
            MainApp.main(args);
        }
    }
}