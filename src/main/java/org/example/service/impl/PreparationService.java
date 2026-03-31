package org.example.service.impl;

import org.example.domain.Preparation;
import org.example.domain.Units;
import org.example.IdGenerator;
import org.example.service.PreparationSer;
import org.example.service.SolutionSer;

import java.time.Instant;
import java.util.*;

public class PreparationService implements PreparationSer {
    private final Map<Long, Preparation> preparations = new LinkedHashMap<>();
    private final IdGenerator idGenerator;
    private final SolutionSer solutionService;
    private static final String ENTITY_NAME = "preparation";

    public PreparationService(SolutionSer solutionService, IdGenerator idGenerator) {
        this.solutionService = solutionService;
        this.idGenerator = idGenerator;
    }

    @Override
    public Preparation add(long solutionId, double finalQuantity,
                           Units finalUnit, String comment,
                           String owner, Instant preparedAt) {
        if (!solutionService.exists(solutionId)) {
            throw new IllegalArgumentException("Ошибка: раствор с id " + solutionId + " не найден");
        }

        Preparation prep = new Preparation(solutionId, finalQuantity, finalUnit, comment, owner, preparedAt);
        prep.setId(idGenerator.nextId(ENTITY_NAME));
        preparations.put(prep.getId(), prep);
        return prep;
    }

    @Override
    public Preparation getById(long id) {
        Preparation prep = preparations.get(id);
        if (prep == null) {
            throw new IllegalArgumentException("Ошибка: приготовление с id " + id + " не найдено");
        }
        return prep;
    }

    @Override
    public List<Preparation> getBySolutionId(long solutionId) {
        if (!solutionService.exists(solutionId)) {
            throw new IllegalArgumentException("Ошибка: раствор с id " + solutionId + " не найден");
        }

        List<Preparation> result = new ArrayList<>();
        for (Preparation p : preparations.values()) {
            if (p.getSolutionId() == solutionId) {
                result.add(p);
            }
        }
        result.sort((a, b) -> b.getPreparedAt().compareTo(a.getPreparedAt()));
        return result;
    }

    @Override
    public List<Preparation> getLastBySolutionId(long solutionId, int limit) {
        List<Preparation> all = getBySolutionId(solutionId);
        return all.subList(0, Math.min(limit, all.size()));
    }

    @Override
    public void update(long id, Double finalQuantity, Units finalUnit, String comment) {
        Preparation prep = getById(id);

        if (finalQuantity != null) {
            prep.setFinalQuantity(finalQuantity);
        }
        if (finalUnit != null) {
            prep.setFinalUnit(finalUnit);
        }
        if (comment != null) {
            prep.setComment(comment);
        }
    }

    @Override
    public void remove(long id) {
        if (!preparations.containsKey(id)) {
            throw new IllegalArgumentException("Ошибка: приготовление с id " + id + " не найдено");
        }
        preparations.remove(id);
    }

    @Override
    public boolean exists(long id) {
        return preparations.containsKey(id);
    }

    @Override
    public int countBySolutionId(long solutionId) {
        int count = 0;
        for (Preparation p : preparations.values()) {
            if (p.getSolutionId() == solutionId) count++;
        }
        return count;
    }

    @Override
    public void loadFromData(Map<Long, Preparation> loadedPreparations) {
        preparations.clear();
        preparations.putAll(loadedPreparations);

        long maxId = loadedPreparations.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);
        if (maxId > 0) {
            idGenerator.setInitialId(ENTITY_NAME, maxId + 1);
        }
    }

    @Override
    public int getCount() {
        return preparations.size();
    }

    @Override
    public void clearAll() {
        preparations.clear();
        idGenerator.reset(ENTITY_NAME);
    }

    @Override
    public Collection<Preparation> getAll() {
        return preparations.values();
    }

    @Override
    public Map<Long, Preparation> getAllAsMap() {
        return new LinkedHashMap<>(preparations);
    }
}