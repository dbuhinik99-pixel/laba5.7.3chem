package org.example.service;

import org.example.domain.Preparation;
import org.example.domain.Units;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface PreparationSer {

    Preparation add(long solutionId, double finalQuantity,
                    Units finalUnit, String comment,
                    String owner, Instant preparedAt);

    Preparation getById(long id);

    List<Preparation> getBySolutionId(long solutionId);

    List<Preparation> getLastBySolutionId(long solutionId, int limit);

    void update(long id, Double finalQuantity, Units finalUnit, String comment);

    void remove(long id);

    boolean exists(long id);

    int countBySolutionId(long solutionId);

    void loadFromData(Map<Long, Preparation> loadedPreparations);

    int getCount();

    void clearAll();

    Collection<Preparation> getAll();

    Map<Long, Preparation> getAllAsMap();
}