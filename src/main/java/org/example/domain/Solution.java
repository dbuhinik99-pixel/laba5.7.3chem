package org.example.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public final class Solution {
    private long id;
    private String name;
    private double concentration;
    private SolutionConcentrationUnit concentrationUnit;
    private String solvent;
    private final String ownerUsername;
    private final Instant createdAt;
    private Instant updatedAt;

    // Конструктор без параметров для Jackson
    public Solution() {
        this.ownerUsername = "SYSTEM";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @JsonCreator
    public Solution(@JsonProperty("name") String name,
                    @JsonProperty("concentration") double concentration,
                    @JsonProperty("concentrationUnit") SolutionConcentrationUnit concentrationUnit,
                    @JsonProperty("solvent") String solvent,
                    @JsonProperty("ownerUsername") String ownerUsername) {
        setName(name);
        setConcentration(concentration);
        setConcentrationUnit(concentrationUnit);
        setSolvent(solvent);
        this.ownerUsername = ownerUsername == null ? "SYSTEM" : ownerUsername;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ошибка: название не может быть пустым");
        }
        if (name.length() > 128) {
            throw new IllegalArgumentException("Ошибка: название слишком длинное (макс. 128)");
        }
        this.name = name;
        this.updatedAt = Instant.now();
    }

    public void setConcentration(double concentration) {
        if (concentration < 0) {
            throw new IllegalArgumentException("Ошибка: концентрация не может быть отрицательной");
        }
        this.concentration = concentration;
        this.updatedAt = Instant.now();
    }

    public void setConcentrationUnit(SolutionConcentrationUnit concentrationUnit) {
        if (concentrationUnit == null) {
            throw new IllegalArgumentException("Ошибка: единицы концентрации не указаны");
        }
        this.concentrationUnit = concentrationUnit;
        this.updatedAt = Instant.now();
    }

    public void setSolvent(String solvent) {
        if (solvent != null && solvent.length() > 64) {
            throw new IllegalArgumentException("Ошибка: название растворителя слишком длинное (макс. 64)");
        }
        this.solvent = solvent == null ? "" : solvent;
        this.updatedAt = Instant.now();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public double getConcentration() { return concentration; }
    public SolutionConcentrationUnit getConcentrationUnit() { return concentrationUnit; }
    public String getSolvent() { return solvent; }
    public String getOwnerUsername() { return ownerUsername; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}