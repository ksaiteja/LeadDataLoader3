package com.fsdbackend.Login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadTrackerService {
    private final UploadTrackerRepository uploadTrackerRepository;
    @Autowired
    public UploadTrackerService(UploadTrackerRepository uploadTrackerRepository) {
        this.uploadTrackerRepository = uploadTrackerRepository;
    }
    public void uploadData(UploadTracker data) {
    	uploadTrackerRepository.save(data);
    }
    public List<UploadTracker> getAllUploads() {
        return uploadTrackerRepository.findAll();
    }
}
