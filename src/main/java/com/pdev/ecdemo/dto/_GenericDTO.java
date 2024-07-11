package com.pdev.ecdemo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class _GenericDTO {

    Long id;

    public _GenericDTO(Long id) {
        this.id = id;
    }

}
