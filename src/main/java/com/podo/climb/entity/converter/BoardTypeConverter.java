package com.podo.climb.entity.converter;

import com.podo.climb.entity.Board;
import com.podo.climb.model.BoardType;
import com.podo.climb.model.MemberRoleType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BoardTypeConverter implements AttributeConverter<BoardType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BoardType attribute) {
        return attribute.getValue();
    }

    @Override
    public BoardType convertToEntityAttribute(Integer dbData) {
        return BoardType.valueOf(dbData);
    }

}
