package com.github.supercoding.web.dto;

import com.github.supercoding.repository.items.ItemEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Item {
    @ApiModelProperty(name="id",value="item id",example = "1") private String id;
    @ApiModelProperty(name="name",value="item 이름",example = "dell xps15") private String name;
    @ApiModelProperty(name="type",value="item 기기타입",example = "laptop") private String type;
    @ApiModelProperty(name="price",value="item 가격",example = "1200") private  Integer price;
    private Spec spec;

}
