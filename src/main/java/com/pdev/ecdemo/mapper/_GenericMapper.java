package com.pdev.ecdemo.mapper;

import com.pdev.ecdemo.dto._GenericDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class _GenericMapper<T, DTO extends _GenericDTO> {

    public abstract DTO toDTO(T entity);

    public List<DTO> toDTOList(List<T> entityList) {

        return entityList.stream()
                .map(client -> this.toDTO(client))
                .collect(Collectors.toList());
    }

    public abstract T toEntity(DTO dto);
}

