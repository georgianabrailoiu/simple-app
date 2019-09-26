package com.example.myapp.controllers;

import com.example.myapp.controllers.exception.CustomerNotFoundException;
import com.example.myapp.services.PdfService;
import com.example.myapp.util.BcdTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/download")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @RequestMapping(value = "/bill",
            method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> download(@RequestParam("accountId") String accountId,
                                                        @RequestParam("issueDate") String issueDate,
                                                        @RequestParam("userType") String userType,
                                                        @RequestParam("billType") String billType,
                                                        @RequestParam("extension") String extension)
            throws IOException, CustomerNotFoundException, ParseException, NumberFormatException {

        if (accountId == null || accountId.isEmpty()) {
            throw new CustomerNotFoundException();
        }

        Resource resource = pdfService.getResource(accountId, issueDate, userType, billType, extension);
        HttpHeaders headers = getHeaders(accountId + "_" + BcdTransformer.getBcdFull(issueDate) + "_"
                + billType + userType + "." + extension);

        headers.setContentLength(resource.contentLength());
        return new ResponseEntity<>(
                new InputStreamResource(resource.getInputStream()), headers, HttpStatus.OK);
    }

    private HttpHeaders getHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", String.format("attachment; filename=" + filename));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }

}
