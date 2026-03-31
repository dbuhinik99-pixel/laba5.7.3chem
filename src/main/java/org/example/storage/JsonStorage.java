package org.example.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.domain.Preparation;
import org.example.domain.PreparationComponent;
import org.example.domain.Solution;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonStorage {
    private static final String DEFAULT_FILE_NAME = "data.json";
    private final ObjectMapper objectMapper;
    private final String fileName;

    public JsonStorage() {
        this(DEFAULT_FILE_NAME);
    }

    public JsonStorage(String fileName) {
        this.fileName = fileName;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void save(Map<Long, Solution> solutions,
                     Map<Long, Preparation> preparations,
                     Map<Long, PreparationComponent> components) throws IOException {
        DataWrapper data = new DataWrapper(solutions, preparations, components);
        objectMapper.writeValue(new File(fileName), data);
    }

    public DataWrapper load() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            return new DataWrapper(new LinkedHashMap<>(), new LinkedHashMap<>(), new LinkedHashMap<>());
        }
        return objectMapper.readValue(file, DataWrapper.class);
    }

    public boolean exists() {
        return new File(fileName).exists();
    }
}