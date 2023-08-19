package com.fsdbackend.Login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/leads/history")
public class UploadTrackerController {

    private final UploadTrackerService uploadTrackerService;

    @Autowired
    public UploadTrackerController(UploadTrackerService uploadTrackerService) {
        this.uploadTrackerService = uploadTrackerService;
    }
    
    @PostMapping("/post")
    public void uploadData(@RequestBody UploadTracker Data) {
        uploadTrackerService.uploadData(Data);
    }
    
    @GetMapping
    public List<UploadTracker> getUploads() {
    	return uploadTrackerService.getAllUploads();
    }
}
