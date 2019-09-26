package com.example.myapp.util;

import com.example.myapp.controllers.exception.CustomerNotFoundException;

public class NnTransformer {

    public static int getNN(String accountId) {
        return Integer.parseInt(accountId) % 500;
    }
}
