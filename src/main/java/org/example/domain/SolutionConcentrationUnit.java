package org.example.domain;

import java.util.Set;

public enum SolutionConcentrationUnit {
    PERCENT(Set.of("%", "percent")),
    MOL_PER_L(Set.of("mol/l", "mol_per_l")),
    G_PER_L(Set.of("g/l", "g_per_l"));

    private final Set<String> validStrings;

    SolutionConcentrationUnit(Set<String> validStrings) {
        this.validStrings = validStrings;
    }

    public static SolutionConcentrationUnit fromString(String str) {
        if (str == null) return null;

        str = str.trim().toLowerCase();

        for (SolutionConcentrationUnit unit : values()) {
            if (unit.validStrings.contains(str)) {
                return unit;
            }
        }

        System.out.println("Ошибка: доступны только проценты (%, percent), моль/л (mol/l, mol_per_l) или г/л (g/l, g_per_l)");
        return null;
    }

    @Override
    public String toString() {
        return switch (this) {
            case PERCENT -> "%";
            case MOL_PER_L -> "mol/L";
            case G_PER_L -> "g/L";
        };
    }
}