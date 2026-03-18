package org.example.domain;

import java.time.Instant;

public final class PreparationComponent {
    private long id; // идентификатор компонента
    private long preparationId; // ID приготовления, к которому относится данный компонент. Это внешний ключ — ссылка на существующий объект Preparation.
    private long batchId; // ID партии реактива
    private double quantity; // количество компонента, использованного в приготовлении
    private Units unit; // единицы измерения количества
    private final Instant createdAt; // время создания записи о компоненте

    public PreparationComponent(long preparationId, long batchId,
                                double quantity, Units unit) {
        setPreparationId(preparationId);
        setBatchId(batchId);
        setQuantity(quantity);
        setUnits(unit); // сеттеры (методы для установки значений)
        this.createdAt = Instant.now();
    }

    public void setPreparationId(long preparationId) {
        if (preparationId <= 0) {
            throw new IllegalArgumentException("Ошибка: ID приготовления должен быть положительным");
        }
        this.preparationId = preparationId;
    }

    public void setBatchId(long batchId) {
        if (batchId <= 0) {
            throw new IllegalArgumentException("Ошибка: ID партии должен быть положительным");
        }
        this.batchId = batchId;
    }

    public void setQuantity(double quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Ошибка: количество должно быть больше 0");
        }
        this.quantity = quantity;
    }

    public void setUnits(Units unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Ошибка: единицы не указаны");
        }
        this.unit = unit;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getPreparationId() { return preparationId; }
    public long getBatchId() { return batchId; }
    public double getQuantity() { return quantity; }
    public Units getUnit() { return unit; }
    public Instant getCreatedAt() { return createdAt; }
} // геттеры для возвращения значения полей и сеттер для присваивания ID в конце всех операций