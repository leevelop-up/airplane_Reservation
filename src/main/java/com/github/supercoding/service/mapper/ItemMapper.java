package com.github.supercoding.service.mapper;

import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.web.dto.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    //싱글톤
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);
    //spc의 경우 바로 가져오는 것이 아닌 spec하위에 cpu,capacity가 있기때문에 맵핑 타겟
    // 메소드
    @Mapping(target = "spec.cpu", source = "cpu")
    @Mapping(target = "spec.capacity", source = "capacity")
    Item itemEntityToItem(ItemEntity itemEntity);


}
