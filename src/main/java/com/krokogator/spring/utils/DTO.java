package com.krokogator.spring.utils;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public class DTO {

    public static <T extends EntityDTO> T from(Object object, Class<T> dtoClass) {
        Mapper mapper = new DozerBeanMapper();
        return mapper.map(object, dtoClass);
    }
}
