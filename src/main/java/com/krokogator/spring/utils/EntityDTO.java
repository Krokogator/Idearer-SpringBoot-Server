package com.krokogator.spring.utils;

public interface EntityDTO<Entity> {
    void toDto(Entity entity);
}
