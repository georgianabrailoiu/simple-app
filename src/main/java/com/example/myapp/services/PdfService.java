package com.example.myapp.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.text.ParseException;

public interface PdfService {

    ResponseEntity<InputStreamResource> getPdf(String accountId, String issueDate, String userType,
                                               String billType,
                                               String extension) throws ParseException, IOException;

}
