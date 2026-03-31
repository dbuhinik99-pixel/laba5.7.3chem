package org.example.service;

import org.example.domain.PreparationComponent;
import org.example.domain.Units;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ComponentSer {

    PreparationComponent add(long preparationId, long batchId,
                             double quantity, Units unit);

    List<PreparationComponent> getByPreparationId(long preparationId);

    void removeByPreparationId(long preparationId);

    int countByPreparationId(long preparationId);

    boolean exists(long id);

    void loadFromData(Map<Long, PreparationComponent> loadedComponents);

    int getCount();

    void clearAll();

    PreparationComponent getById(long id);

    Collection<PreparationComponent> getAll();

    void remove(long id);

    Map<Long, PreparationComponent> getAllAsMap();
}