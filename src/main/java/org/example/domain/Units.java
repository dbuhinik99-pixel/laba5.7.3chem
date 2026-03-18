package org.example.domain;

import java.util.Set;

public enum Units {
    ML(Set.of("ml", "milliliter")),
    G(Set.of("g", "gram"));

    private final Set<String> validStrings;

    Units(Set<String> validStrings) {
        this.validStrings = validStrings;
    }

    public static Units fromString(String str) {
        if (str == null) return null;

        str = str.trim().toLowerCase();

        for (Units unit : values()) {
            if (unit.validStrings.contains(str)) {
                return unit;
            }
        }

        System.out.println("Ошибка: доступны только миллилитры (ml, milliliter) или граммы (g, gram)");
        return null;
    }

    @Override
    public String toString() {
        return this == ML ? "mL" : "g";
    }
}
