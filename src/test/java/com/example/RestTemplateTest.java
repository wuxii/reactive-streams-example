package com.example;

import com.example.tools.Server;
import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * @author wuxii
 */
@Log4j2
public class RestTemplateTest {

    static RestTemplate restTemplate;
    static final String REQUEST_URI = "http://localhost:" + Server.PORT + "/greeting";

    @BeforeClass
    public static void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void test() {
        String responseText = restTemplate.getForEntity(REQUEST_URI, String.class).getBody();
        log.info(responseText);
    }

}
