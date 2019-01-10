package com.askdog.model.repository;

import com.askdog.model.entity.IncentivePolicy;
import com.askdog.model.entity.inner.IncentiveId;
import org.springframework.data.repository.CrudRepository;

public interface IncentivePolicyRepository extends CrudRepository<IncentivePolicy, IncentiveId> {

}
