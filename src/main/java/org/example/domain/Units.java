package org.example.domain;

public enum Units {
        ML("ml, milliliter"),
        G("g, gram");

        private final String val;

        Units(String val) {
            this.val = val;
        }

        public static Units fromString(String str) {
            if (str == null) return null;

            str = str.trim().toLowerCase();

            for (Units unit : values()) {
                if (unit.val.equals(str)) {
                    return unit;
                }
            }

            // Проверяем полные названия
            if (str.equals("milliliter")) {
                return ML;
            }
            if (str.equals("gram")) {
                return G;
            }

            System.out.println("Ошибка: доступны только миллилитры (ml, milliliter) или граммы (g, gram)");
            return null;
        }

        @Override
        public String toString() {
            return this == ML ? "mL" : "g";
        }
    }

