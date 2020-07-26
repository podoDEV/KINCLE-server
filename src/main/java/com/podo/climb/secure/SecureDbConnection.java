package com.podo.climb.secure;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class SecureDbConnection {

    private String jdbcDriverClassName;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

}
