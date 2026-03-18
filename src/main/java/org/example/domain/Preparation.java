package org.example.domain;

import java.time.Instant;

public final class Preparation {
    private long id; // идентификатор записи в БД
    private long solutionId; // ID раствора, к которому относится данное приготовление
    private double finalQuantity; // итоговое количество
    private Units finalUnit; // единицы измерения количества
    private String comment; // комментарий к приготовлению
    private final String ownerUsername; // имя пользователя
    private final Instant preparedAt; // время (во сколько приготовлен)
    private final Instant createdAt; // время создания записи в системе
    private Instant updatedAt; // время последнего обновления записи

    public Preparation(long solutionId, double finalQuantity, // конструктор класса для создания нового объекта
                       Units finalUnit, String comment,
                       String ownerUsername, Instant preparedAt) {
        setSolutionId(solutionId);
        setFinalQuantity(finalQuantity);
        setFinalUnit(finalUnit);
        setComment(comment); // сеттеры (методы для установки значений)
        this.ownerUsername = ownerUsername == null ? "SYSTEM" : ownerUsername; // если null, то присваиваем SYSTEM
        this.preparedAt = preparedAt == null ? Instant.now() : preparedAt; // если null, то считаем, что время приготовления - сейчас
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void setSolutionId(long solutionId) { // устанавливаем ID раствора
        if (solutionId <= 0) {
            throw new IllegalArgumentException("Ошибка: ID раствора должен быть положительным");
        }
        this.solutionId = solutionId; // присваивание значения полю
        this.updatedAt = Instant.now(); // автоматическое обновление времени изменений
    }

    public void setFinalQuantity(double finalQuantity) {
        if (finalQuantity <= 0) {
            throw new IllegalArgumentException("Ошибка: количество должно быть больше 0"); // остановка выполнения метода + соо об ошибке
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
        if (comment != null && comment.length() > 128) { // если не null и длина >128
            throw new IllegalArgumentException("Ошибка: комментарий слишком длинный (макс. 128)");
        }
        this.comment = comment == null ? "" : comment; // если коммент null - вывод пустой строки
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
} // геттеры для возвращения значения полей и сеттер для присваивания ID в конце всех операций