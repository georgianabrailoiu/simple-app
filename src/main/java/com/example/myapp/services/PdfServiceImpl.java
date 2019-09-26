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
    private ApplicationContext appContext;

    @Value("${pdf.file.path}")
    private String pdfFilePath;

    @Override
    public Resource getResource(String accountId, String issueDate, String userType, String billType, String extension) throws ParseException {
        return appContext.getResource(pdfFilePath + NnTransformer.getNN(accountId)
                        + "/" + BcdTransformer.getBcdMM(issueDate)
                        + "/" + BcdTransformer.getBcdFull(issueDate)
                        + "/" + accountId + "_" + BcdTransformer.getBcdFull(issueDate)
                        + "_" + billType + userType + "." + extension);

    }

}
