package com.podo.climb.entity.converter;

import com.podo.climb.model.BoardFilterType;
import com.podo.climb.model.MembersBoardFlagType;
import org.springframework.core.convert.converter.Converter;

public class MembersBoardFlagTypeConverter implements Converter<String, MembersBoardFlagType> {
    @Override
    public MembersBoardFlagType convert(String source) {
        return MembersBoardFlagType.valueOf(source.toUpperCase());
    }
}