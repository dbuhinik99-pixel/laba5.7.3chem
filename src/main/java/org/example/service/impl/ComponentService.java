package org.example.service.impl;

import org.example.domain.PreparationComponent;
import org.example.domain.Units;
import org.example.IdGenerator;
import org.example.service.ComponentSer;
import org.example.service.PreparationSer;

import java.util.*;

public class ComponentService implements ComponentSer {
    private final Map<Long, PreparationComponent> components = new LinkedHashMap<>();
    private final IdGenerator idGenerator;
    private final PreparationSer preparationService;
    private static final String ENTITY_NAME = "component";

    public ComponentService(PreparationSer preparationService, IdGenerator idGenerator) {
        this.preparationService = preparationService;
        this.idGenerator = idGenerator;
    }

    @Override
    public PreparationComponent add(long preparationId, long batchId,
                                    double quantity, Units unit) {
        if (!preparationService.exists(preparationId)) {
            throw new IllegalArgumentException("Ошибка: приготовление с id " + preparationId + " не найдено");
        }

        PreparationComponent component = new PreparationComponent(preparationId, batchId, quantity, unit);
        component.setId(idGenerator.nextId(ENTITY_NAME));
        components.put(component.getId(), component);
        return component;
    }

    @Override
    public List<PreparationComponent> getByPreparationId(long preparationId) {
        if (!preparationService.exists(preparationId)) {
            throw new IllegalArgumentException("Ошибка: приготовление с id " + preparationId + " не найдено");
        }

        List<PreparationComponent> result = new ArrayList<>();
        for (PreparationComponent c : components.values()) {
            if (c.getPreparationId() == preparationId) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public void removeByPreparationId(long preparationId) {
        List<Long> idsToRemove = new ArrayList<>();
        for (PreparationComponent c : components.values()) {
            if (c.getPreparationId() == preparationId) {
                idsToRemove.add(c.getId());
            }
        }
        for (Long id : idsToRemove) {
            components.remove(id);
        }
    }

    @Override
    public int countByPreparationId(long preparationId) {
        int count = 0;
        for (PreparationComponent c : components.values()) {
            if (c.getPreparationId() == preparationId) count++;
        }
        return count;
    }

    @Override
    public boolean exists(long id) {
        return components.containsKey(id);
    }

    @Override
    public void loadFromData(Map<Long, PreparationComponent> loadedComponents) {
        components.clear();
        components.putAll(loadedComponents);

        long maxId = loadedComponents.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);
        if (maxId > 0) {
            idGenerator.setInitialId(ENTITY_NAME, maxId + 1);
        }
    }

    @Override
    public int getCount() {
        return components.size();
    }

    @Override
    public void clearAll() {
        components.clear();
        idGenerator.reset(ENTITY_NAME);
    }

    @Override
    public PreparationComponent getById(long id) {
        PreparationComponent component = components.get(id);
        if (component == null) {
            throw new IllegalArgumentException("Ошибка: компонент с id " + id + " не найден");
        }
        return component;
    }

    @Override
    public Collection<PreparationComponent> getAll() {
        return components.values();
    }

    @Override
    public void remove(long id) {
        if (!components.containsKey(id)) {
            throw new IllegalArgumentException("Ошибка: компонент с id " + id + " не найден");
        }
        components.remove(id);
    }

    @Override
    public Map<Long, PreparationComponent> getAllAsMap() {
        return new LinkedHashMap<>(components);
    }
}