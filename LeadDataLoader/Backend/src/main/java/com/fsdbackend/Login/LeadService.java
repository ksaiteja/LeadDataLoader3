package com.fsdbackend.Login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeadService {
    private final LeadRepository leadRepository;
    @Autowired
    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    public void uploadLeads(Lead leadData) {
        leadRepository.save(leadData);
    }
}

