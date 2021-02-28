package com.chisom.eurekaserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chisom.Iwowo
 */
@Service
public class EurekaService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;
    private final String eurekaServerUrl;

    @Autowired
    public EurekaService(
            RestTemplate restTemplate,
            @Value("${eureka-server-health}") String eurekaServerUrl
    ) {
        this.restTemplate = restTemplate;
        this.eurekaServerUrl = eurekaServerUrl;
    }

    /**
     * ping url every 5min to keep alive
     */
    @Async
    @Scheduled(fixedRate = 300000)
    public void health() {
        try {
            CompletableFuture.runAsync(() ->
                    restTemplate.getForObject(eurekaServerUrl, Object.class));
        } catch (Exception e) {
            log.error("caught an exception :::", e);
        }
    }
}
