package com.krokogator.spring.utils;

public interface EntityDTO<From> {
    void toDto(From tll);
}
