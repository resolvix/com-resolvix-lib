package com.resolvix.lib.config;

import com.resolvix.lib.utils.InputStreamUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ConfigurationPropertiesImplUT {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationPropertiesImplUT.class);

    @Test
    public void openPropertiesFile() throws IOException, NullPointerException {
        InputStream inputStream = ConfigurationPropertiesImplUT.class.getResourceAsStream(
                "configurationPropertiesUT.properties");
        LOGGER.debug("inputStream: {}", inputStream);
        inputStream.mark(4096);
        String configurationProperties = InputStreamUtils.toString(inputStream, StandardCharsets.UTF_8);
        inputStream.reset();
        ConfigurationPropertiesImpl cp = ConfigurationPropertiesImpl.parse(inputStream);
        inputStream.close();
        LOGGER.debug("openPropertiesFile: {}", configurationProperties);
        Map<String, Object> map = cp.getMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            LOGGER.debug("configurationProperties: key [{}], value [{}]", entry.getKey(), entry.getValue());
        }
    }
}
