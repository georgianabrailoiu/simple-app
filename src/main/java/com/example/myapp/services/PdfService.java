package com.example.myapp.services;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.text.ParseException;

public interface PdfService {

    Resource getResource(String accountId, String issueDate, String userType,
                         String billType,
                         String extension) throws ParseException, IOException;

}
