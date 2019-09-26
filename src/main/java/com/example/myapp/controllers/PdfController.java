package com.example.myapp.controllers;

import com.example.myapp.controllers.exception.CustomerNotFoundException;
import com.example.myapp.services.PdfService;
import com.example.myapp.util.BcdTransformer;
import com.example.myapp.util.Constants;
import com.example.myapp.util.NnTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/download")
public class PdfController {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String DATE_FORMATTER = "dd-MM-yyyy_HH:mm:ss";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER);
    @Autowired
    ServletContext context;

    @Autowired
    ApplicationContext appContext;

    @Autowired
    private PdfService pdfService;

    @RequestMapping(value = "/pdf/{fileName:.+}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> download(@PathVariable("fileName") String fileName) throws IOException {
        System.out.println("Calling Download:- " + fileName);
        ClassPathResource pdfFile = new ClassPathResource("downloads/" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", String.format("attachment; filename=" + fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        headers.setContentLength(pdfFile.contentLength());
        ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadFromUrl() throws IOException {
        Resource firstResource =
                appContext.getResource("http://acs.ase.ro/Media/Default/documents/cts/curs/DiagrameDesignPatterns.pdf");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", String.format("attachment; filename=" + "DiagrameDesignPatterns.pdf"));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        headers.setContentLength(firstResource.contentLength());
        ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                new InputStreamResource(firstResource.getInputStream()), headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/bill",
            method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> download(@RequestParam("accountId") String accountId,
                                                        @RequestParam("issueDate") String issueDate,
                                                        @RequestParam("userType") String userType,
                                                        @RequestParam("billType") String billType,
                                                        @RequestParam("extension") String extension)
            throws IOException, CustomerNotFoundException, ParseException, NumberFormatException {

        if(accountId == null || accountId.isEmpty()) {
            throw  new CustomerNotFoundException();
        }
        return pdfService.getPdf(accountId, issueDate,userType, billType, extension);
    }

}
