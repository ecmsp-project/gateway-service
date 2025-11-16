package com.ecmsp.gatewayservice.api.rest.cms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class CmsController {

    private final CmsService cmsService;

    @GetMapping("/home")
    public ResponseEntity<Map<String, Object>> getHomeSettings() {
        log.info("GET /api/settings/home - Fetching home page settings");
        try {
            Map<String, Object> content = cmsService.getHomeContent();
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            log.error("Error fetching home settings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/home")
    public ResponseEntity<Map<String, Object>> saveHomeSettings(@RequestBody Map<String, Object> content) {
        log.info("POST /api/settings/home - Saving home page settings");
        try {
            cmsService.saveHomeContent(content);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            log.error("Error saving home settings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/faq")
    public ResponseEntity<Map<String, Object>> getFaqSettings() {
        log.info("GET /api/settings/faq - Fetching FAQ page settings");
        try {
            Map<String, Object> content = cmsService.getFaqContent();
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            log.error("Error fetching FAQ settings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/faq")
    public ResponseEntity<Map<String, Object>> saveFaqSettings(@RequestBody Map<String, Object> content) {
        log.info("POST /api/settings/faq - Saving FAQ page settings");
        try {
            cmsService.saveFaqContent(content);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            log.error("Error saving FAQ settings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/contact")
    public ResponseEntity<Map<String, Object>> getContactSettings() {
        log.info("GET /api/settings/contact - Fetching contact page settings");
        try {
            Map<String, Object> content = cmsService.getContactContent();
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            log.error("Error fetching contact settings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/contact")
    public ResponseEntity<Map<String, Object>> saveContactSettings(@RequestBody Map<String, Object> content) {
        log.info("POST /api/settings/contact - Saving contact page settings");
        try {
            cmsService.saveContactContent(content);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            log.error("Error saving contact settings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
