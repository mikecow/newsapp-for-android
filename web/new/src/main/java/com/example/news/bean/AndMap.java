package com.example.news.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Dorado
 * @since 2021-05-14
 */
public class AndMap implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "key", type = IdType.AUTO)
    private String key;

    private Integer value;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AndMap{" +
        "key=" + key +
        ", value=" + value +
        "}";
    }
}
