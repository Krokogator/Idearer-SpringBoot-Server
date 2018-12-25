package com.krokogator.spring.resources.reject;

import org.springframework.data.repository.CrudRepository;

public interface RejectRepository<T extends Reject> extends CrudRepository<T, Long> {
}
