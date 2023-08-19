package com.fsdbackend.Login;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends MongoRepository<Lead, String> {
    // Custom methods if needed
}

