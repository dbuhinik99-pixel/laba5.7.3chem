package org.example.service;

import org.example.domain.Solution;
import org.example.domain.SolutionConcentrationUnit;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SolutionSer {

    Solution add(String name, double concentration,
                 SolutionConcentrationUnit unit, String solvent, String owner);

    Solution getById(long id);

    Collection<Solution> getAll();

    List<Solution> searchByName(String query);

    boolean exists(long id);

    void loadFromData(Map<Long, Solution> loadedSolutions);

    int getCount();

    void clearAll();
}