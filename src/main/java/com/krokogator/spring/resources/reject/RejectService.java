package com.krokogator.spring.resources.reject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RejectService<T extends Reject> {

    @Autowired
    RejectRepository<T> rejectRepository;

    public T save(T reject) {
        return rejectRepository.save(reject);
    }
}
