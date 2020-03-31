package com.shiro.boot.common.result;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用来测试 JSON 传输数据的 实体类
 * JSON校验
 *
 * @author jzl
 * @date 2020/3/27 11:47
 */
@Data
@JsonPropertyOrder(alphabetic = true)
public class TestJSONResult implements Serializable {
    private static final long serialVersionUID = -1179021448480862181L;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String name;

    @JsonIgnore
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @JsonProperty(value = "d", access = JsonProperty.Access.WRITE_ONLY)
    private String desc;
}
