package com.ecmsp.gatewayservice.api.rest.cms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CmsService {

    private final ObjectMapper objectMapper;

    private final Map<String, Map<String, Object>> cache = new ConcurrentHashMap<>();

    private static final String CMS_PATH_PREFIX = "data/cms/";
    private static final String HOME_FILE = "home.json";
    private static final String FAQ_FILE = "faq.json";
    private static final String CONTACT_FILE = "contact.json";

    public CmsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        initializeCache();
    }

    private void initializeCache() {
        log.info("Initializing CMS cache...");
        try {
            cache.put(HOME_FILE, loadJsonFromFile(HOME_FILE));
            cache.put(FAQ_FILE, loadJsonFromFile(FAQ_FILE));
            cache.put(CONTACT_FILE, loadJsonFromFile(CONTACT_FILE));
            log.info("CMS cache initialized successfully with {} entries", cache.size());
        } catch (Exception e) {
            log.error("Error initializing CMS cache", e);
            cache.put(HOME_FILE, new HashMap<>());
            cache.put(FAQ_FILE, new HashMap<>());
            cache.put(CONTACT_FILE, new HashMap<>());
        }
    }

    public Map<String, Object> getHomeContent() {
        log.debug("Fetching home content from cache");
        return new HashMap<>(cache.get(HOME_FILE));
    }

    public Map<String, Object> getFaqContent() {
        log.debug("Fetching FAQ content from cache");
        return new HashMap<>(cache.get(FAQ_FILE));
    }

    public Map<String, Object> getContactContent() {
        log.debug("Fetching contact content from cache");
        return new HashMap<>(cache.get(CONTACT_FILE));
    }

    public void saveHomeContent(Map<String, Object> content) throws IOException {
        log.info("Saving home content");
        saveJsonToFile(HOME_FILE, content);
        cache.put(HOME_FILE, new HashMap<>(content));
        log.info("Home content saved successfully");
    }

    public void saveFaqContent(Map<String, Object> content) throws IOException {
        log.info("Saving FAQ content");
        saveJsonToFile(FAQ_FILE, content);
        cache.put(FAQ_FILE, new HashMap<>(content));
        log.info("FAQ content saved successfully");
    }

    public void saveContactContent(Map<String, Object> content) throws IOException {
        log.info("Saving contact content");
        saveJsonToFile(CONTACT_FILE, content);
        cache.put(CONTACT_FILE, new HashMap<>(content));
        log.info("Contact content saved successfully");
    }

    private Map<String, Object> loadJsonFromFile(String fileName) throws IOException {
        String resourcePath = CMS_PATH_PREFIX + fileName;
        log.debug("Loading JSON from classpath: {}", resourcePath);

        try {
            Resource resource = new ClassPathResource(resourcePath);
            InputStream inputStream = resource.getInputStream();
            Map<String, Object> data = objectMapper.readValue(inputStream, new TypeReference<>() {});
            log.debug("Successfully loaded {} from classpath", fileName);
            return data;
        } catch (IOException e) {
            log.error("Failed to load JSON file: {}", resourcePath, e);
            throw new IOException("Failed to load CMS content: " + fileName, e);
        }
    }

    private void saveJsonToFile(String fileName, Map<String, Object> content) throws IOException {
        // Użyj working directory jako bazy
        String workingDir = System.getProperty("user.dir");
        Path filePath = Paths.get(workingDir, "src", "main", "resources", CMS_PATH_PREFIX, fileName);

        log.info("Saving JSON to file: {}", filePath.toAbsolutePath());

        try {
            // Upewnij się, że katalog istnieje
            Files.createDirectories(filePath.getParent());

            // Zapisz JSON z ładnym formatowaniem
            String jsonContent = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(content);

            Files.writeString(filePath, jsonContent,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            log.info("Successfully saved {} to file: {}", fileName, filePath.toAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to save JSON file: {}", filePath.toAbsolutePath(), e);
            throw new IOException("Failed to save CMS content: " + fileName, e);
        }
    }
}
