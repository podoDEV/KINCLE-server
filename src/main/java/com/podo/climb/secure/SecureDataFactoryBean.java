package com.podo.climb.secure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Paths;

@Slf4j
public class SecureDataFactoryBean implements FactoryBean<SecureData> {

    @Override
    public SecureData getObject() {
        String jsonPath = System.getProperty("secure.json");
        SecureData secureData = null;
        try {
            if (!StringUtils.isEmpty(jsonPath) && Paths.get(jsonPath).toFile().exists()) {
                secureData = new ObjectMapper().readValue(new File(jsonPath), SecureData.class);
            }
        } catch (Exception exception) {
            log.error("-Dsecure.json", exception);
        }
        return secureData;
    }

    @Override
    public Class<?> getObjectType() {
        return SecureData.class;
    }
}
