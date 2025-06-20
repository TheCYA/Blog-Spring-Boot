package com.example.demo.auth;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenBlackList {
    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public TokenBlackList() {}

    public void addToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean containsToken(String token){
        return blacklistedTokens.contains(token);
    }
}
