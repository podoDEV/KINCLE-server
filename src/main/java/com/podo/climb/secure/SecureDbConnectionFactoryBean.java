package com.podo.climb.secure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Paths;

@Slf4j
public class SecureDbConnectionFactoryBean implements FactoryBean<SecureDbConnection> {

    @Override
    public SecureDbConnection getObject() {
        String jsonPath = System.getProperty("secure.db.json");
        SecureDbConnection secureDbConnection = null;
        try {
            if (!StringUtils.isEmpty(jsonPath) && Paths.get(jsonPath).toFile().exists()) {
                secureDbConnection = new ObjectMapper().readValue(new File(jsonPath), SecureDbConnection.class);
            }
        } catch (Exception exception) {
            log.error("-Dsecure.db.json", exception);
        }
        return secureDbConnection;
    }

    @Override
    public Class<?> getObjectType() {
        return SecureDbConnection.class;
    }
}
