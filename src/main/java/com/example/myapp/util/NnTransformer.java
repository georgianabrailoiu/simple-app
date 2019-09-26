package com.example.myapp.util;

public class NnTransformer {

    public static int getNN(String accountId) {
        return Integer.parseInt(accountId) % 500;
    }
}
