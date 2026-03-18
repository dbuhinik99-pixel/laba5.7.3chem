package org.example.service.impl;

import org.example.domain.Solution;
import org.example.domain.SolutionConcentrationUnit;
import org.example.IdGenerator;
import org.example.service.SolutionSer;

import java.util.*;

public class SolutionService implements SolutionSer { // implements
    private final Map<Long, Solution> solutions = new LinkedHashMap<>();
    private final IdGenerator idGenerator;
    private static final String ENTITY_NAME = "solution";

    public SolutionService(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Solution add(String name, double concentration,
                        SolutionConcentrationUnit unit, String solvent, String owner) {
        Solution solution = new Solution(name, concentration, unit, solvent, owner);
        solution.setId(idGenerator.nextId(ENTITY_NAME));
        solutions.put(solution.getId(), solution);
        return solution;
    }

    @Override
    public Solution getById(long id) {
        Solution solution = solutions.get(id);
        if (solution == null) {
            throw new IllegalArgumentException("Ошибка: раствор с id " + id + " не найден");
        }
        return solution;
    }

    @Override
    public Collection<Solution> getAll() {
        return solutions.values();
    }

    @Override
    public List<Solution> searchByName(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(solutions.values());
        }
        if (query.length() > 64) {
            throw new IllegalArgumentException("Ошибка: запрос слишком длинный (макс. 64)");
        }

        String lowerQuery = query.toLowerCase().trim();
        List<Solution> result = new ArrayList<>();
        for (Solution s : solutions.values()) {
            if (s.getName().toLowerCase().contains(lowerQuery)) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public boolean exists(long id) {
        return solutions.containsKey(id);
    }

    @Override
    public void loadFromData(Map<Long, Solution> loadedSolutions) {
        solutions.clear();
        solutions.putAll(loadedSolutions);

        long maxId = loadedSolutions.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);
        if (maxId > 0) {
            idGenerator.setInitialId(ENTITY_NAME, maxId + 1);
        }
    }

    @Override
    public int getCount() {
        return solutions.size();
    }

    @Override
    public void clearAll() {
        solutions.clear();
        idGenerator.reset(ENTITY_NAME);
    }
}
