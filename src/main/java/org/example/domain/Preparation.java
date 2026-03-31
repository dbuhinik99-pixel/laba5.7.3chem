package org.example.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public final class Preparation {
    private long id;
    private long solutionId;
    private double finalQuantity;
    private Units finalUnit;
    private String comment;
    private final String ownerUsername;
    private final Instant preparedAt;
    private final Instant createdAt;
    private Instant updatedAt;

    // Конструктор без параметров для Jackson
    public Preparation() {
        this.ownerUsername = "SYSTEM";
        this.preparedAt = Instant.now();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @JsonCreator
    public Preparation(@JsonProperty("solutionId") long solutionId,
                       @JsonProperty("finalQuantity") double finalQuantity,
                       @JsonProperty("finalUnit") Units finalUnit,
                       @JsonProperty("comment") String comment,
                       @JsonProperty("ownerUsername") String ownerUsername,
                       @JsonProperty("preparedAt") Instant preparedAt) {
        setSolutionId(solutionId);
        setFinalQuantity(finalQuantity);
        setFinalUnit(finalUnit);
        setComment(comment);
        this.ownerUsername = ownerUsername == null ? "SYSTEM" : ownerUsername;
        this.preparedAt = preparedAt == null ? Instant.now() : preparedAt;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void setSolutionId(long solutionId) {
        if (solutionId <= 0) {
            throw new IllegalArgumentException("Ошибка: ID раствора должен быть положительным");
        }
        this.solutionId = solutionId;
        this.updatedAt = Instant.now();
    }

    public void setFinalQuantity(double finalQuantity) {
        if (finalQuantity <= 0) {
            throw new IllegalArgumentException("Ошибка: количество должно быть больше 0");
        }
        this.finalQuantity = finalQuantity;
        this.updatedAt = Instant.now();
    }

    public void setFinalUnit(Units finalUnit) {
        if (finalUnit == null) {
            throw new IllegalArgumentException("Ошибка: единицы не указаны");
        }
        this.finalUnit = finalUnit;
        this.updatedAt = Instant.now();
    }

    public void setComment(String comment) {
        if (comment != null && comment.length() > 128) {
            throw new IllegalArgumentException("Ошибка: комментарий слишком длинный (макс. 128)");
        }
        this.comment = comment == null ? "" : comment;
        this.updatedAt = Instant.now();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getSolutionId() { return solutionId; }
    public double getFinalQuantity() { return finalQuantity; }
    public Units getFinalUnit() { return finalUnit; }
    public String getComment() { return comment; }
    public String getOwnerUsername() { return ownerUsername; }
    public Instant getPreparedAt() { return preparedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}