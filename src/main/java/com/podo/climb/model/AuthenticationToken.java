package com.podo.climb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class AuthenticationToken {

    @JsonIgnore
    private String username;
    @JsonIgnore
    private Collection authorities;
    private String token;

    public AuthenticationToken(String username, Collection collection, String token) {
        this.username = username;
        this.authorities = collection;
        this.token = token;
    }

}