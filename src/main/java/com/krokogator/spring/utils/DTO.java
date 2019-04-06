package com.krokogator.spring.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.server.ServerErrorException;

import java.util.List;
import java.util.stream.Collectors;

public class DTO {

    public static <TFrom, TDest extends EntityDTO> TDest from(TFrom tFrom, Class<TDest> tDest) {
        return map(tFrom, tDest);
    }

    public static <TDest extends EntityDTO, TFrom> List<TDest> from(List<TFrom> tFroms, Class<TDest> tDest) {
        return tFroms.stream().map(tFrom -> map(tFrom, tDest)).collect(Collectors.toList());
    }

    public static <TDest extends EntityDTO, TFrom> Page<TDest> from(Page<TFrom> tFroms, Class<TDest> tDest) {
        PageImpl<TDest> page = new PageImpl<>(
                tFroms.getContent().stream().map(tFrom -> map(tFrom, tDest)).collect(Collectors.toList()),
                tFroms.getPageable(),
                tFroms.getTotalPages()
        );
        return page;

    }

    private static <T extends EntityDTO, TFrom> T map(TFrom tFrom, Class<T> tDest){
        try {
            T tInstance = tDest.newInstance();
            tInstance.toDto(tFrom);
            return tInstance;
        } catch (Exception e) {
            throw new ServerErrorException("", new Throwable());
        }
    }
}
