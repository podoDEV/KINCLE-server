package com.podo.climb.entity.converter;

import com.podo.climb.model.BoardFilterType;
import org.springframework.core.convert.converter.Converter;

public class BoardFilterTypeConverter implements Converter<String, BoardFilterType> {
    @Override
    public BoardFilterType convert(String source) {
        return BoardFilterType.valueOf(source.toUpperCase());
    }
}