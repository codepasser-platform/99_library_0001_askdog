package com.askdog.store.model.repository.mongo;

import com.askdog.store.model.data.Banner;
import com.askdog.store.model.data.ResourceRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BannerRepository extends MongoRepository<Banner, String> {
}
