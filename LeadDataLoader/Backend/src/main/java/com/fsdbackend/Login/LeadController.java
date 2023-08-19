package com.fsdbackend.Login;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.io.IOException;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;
    List<Lead> badLeads = new ArrayList<>();
    
    @Autowired
    private UploadTrackerController uploadTrackerController;

    @Autowired
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Lead>> uploadLeads(@RequestParam("file") MultipartFile file,@RequestParam("username") String username) throws Exception {
    	badLeads=new ArrayList<>();
    	try {
            List<Lead> leads = CSVUtils.parseCSV(file);

            int goodRecords = 0;
            int badRecords = 0;

            for (Lead lead : leads) {
                if (isValidLead(lead)) {
                    leadService.uploadLeads(lead);
                    goodRecords++;
                } else {
                    badRecords++;
                    badLeads.add(lead);
                }
            }
            UploadTracker uploadTracker = new UploadTracker(new Date(),file.getOriginalFilename(), leads.size(), goodRecords, badRecords, username);
            uploadTrackerController.uploadData(uploadTracker);

            return ResponseEntity.ok(badLeads);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }
    
    @GetMapping("/download-bad-records")
    public ResponseEntity<Resource> downloadBadRecords() {
         // Retrieve bad leads from your repository

        if (badLeads.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource;
        try {
            resource = CSVUtils.generateCSV(badLeads);
        } catch (java.io.IOException e) {
            return ResponseEntity.badRequest().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bad_records.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
    private boolean isValidLead(Lead lead) {
        if (lead.getFirstName().isEmpty() || lead.getLastName().isEmpty() ||
            lead.getEmail().isEmpty() || lead.getPhone().isEmpty() ||
            lead.getStatus().isEmpty() || lead.getAssignedTo().isEmpty()) {
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z]+$", lead.getFirstName()) ||
            !Pattern.matches("^[a-zA-Z]+$", lead.getLastName())) {
            return false;
        }

        if (!Pattern.matches("^[0-9]{10}$", lead.getPhone())) {
            return false;
        }

        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", lead.getEmail())) {
            return false;
        }

        return true;
    }
    
    @GetMapping
    public List<Lead> getAllLeads() {
        return leadService.getAllLeads();
    }  
}
