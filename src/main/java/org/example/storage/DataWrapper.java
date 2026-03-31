package org.example.storage;

import org.example.domain.Preparation;
import org.example.domain.PreparationComponent;
import org.example.domain.Solution;

import java.util.Map;

public class DataWrapper {
    private Map<Long, Solution> solutions;
    private Map<Long, Preparation> preparations;
    private Map<Long, PreparationComponent> components;

    public DataWrapper() {}

    public DataWrapper(Map<Long, Solution> solutions,
                       Map<Long, Preparation> preparations,
                       Map<Long, PreparationComponent> components) {
        this.solutions = solutions;
        this.preparations = preparations;
        this.components = components;
    }

    public Map<Long, Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(Map<Long, Solution> solutions) {
        this.solutions = solutions;
    }

    public Map<Long, Preparation> getPreparations() {
        return preparations;
    }

    public void setPreparations(Map<Long, Preparation> preparations) {
        this.preparations = preparations;
    }

    public Map<Long, PreparationComponent> getComponents() {
        return components;
    }

    public void setComponents(Map<Long, PreparationComponent> components) {
        this.components = components;
    }
}