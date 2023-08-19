package com.fsdbackend.Login;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadTrackerRepository extends MongoRepository<UploadTracker, String> {
}

