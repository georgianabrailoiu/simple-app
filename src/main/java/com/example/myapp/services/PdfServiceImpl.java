package com.example.myapp.services;

import com.example.myapp.util.BcdTransformer;
import com.example.myapp.util.NnTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    ApplicationContext appContext;

    @Value("${designs.diagrams.url}")
    private String pdfFilePath;

    @Override
    public ResponseEntity<InputStreamResource> getPdf(String accountId, String issueDate, String userType, String billType, String extension) throws ParseException, IOException {
        Resource firstResource =
                appContext.getResource(pdfFilePath + NnTransformer.getNN(accountId)
                        + "/" + BcdTransformer.getBcdMM(issueDate)
                        + "/" + BcdTransformer.getBcdFull(issueDate)
                        + "/" + accountId + "_" + BcdTransformer.getBcdFull(issueDate)
                        + "_" + billType + userType + "." + extension);

        HttpHeaders headers = getHeaders(accountId + "_" + BcdTransformer.getBcdFull(issueDate) + "_" + billType + userType + "." + extension);

        headers.setContentLength(firstResource.contentLength());
        ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                new InputStreamResource(firstResource.getInputStream()), headers, HttpStatus.OK);
        return response;
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
