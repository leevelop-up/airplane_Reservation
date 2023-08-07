package com.github.supercoding.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Spec {
    @ApiModelProperty(name="cpu",value="item cpu",example = "구글텐서")private String cpu;
    @ApiModelProperty(name="capacity",value="item 용량",example = "250기가")private String capacity;

}
