package org.example.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public final class PreparationComponent {
    private long id;
    private long preparationId;
    private long batchId;
    private double quantity;
    private Units unit;
    private final Instant createdAt;

    // Конструктор без параметров для Jackson
    public PreparationComponent() {
        this.createdAt = Instant.now();
    }

    @JsonCreator
    public PreparationComponent(@JsonProperty("preparationId") long preparationId,
                                @JsonProperty("batchId") long batchId,
                                @JsonProperty("quantity") double quantity,
                                @JsonProperty("unit") Units unit) {
        setPreparationId(preparationId);
        setBatchId(batchId);
        setQuantity(quantity);
        setUnits(unit);
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
}