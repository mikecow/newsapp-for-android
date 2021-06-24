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
public class AndUser implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;

    private Integer favor1;

    private Integer favor2;

    private Integer favor3;

    private Integer favor4;

    private Integer favor5;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getFavor1() {
        return favor1;
    }

    public void setFavor1(Integer favor1) {
        this.favor1 = favor1;
    }

    public Integer getFavor2() {
        return favor2;
    }

    public void setFavor2(Integer favor2) {
        this.favor2 = favor2;
    }

    public Integer getFavor3() {
        return favor3;
    }

    public void setFavor3(Integer favor3) {
        this.favor3 = favor3;
    }

    public Integer getFavor4() {
        return favor4;
    }

    public void setFavor4(Integer favor4) {
        this.favor4 = favor4;
    }

    public Integer getFavor5() {
        return favor5;
    }

    public void setFavor5(Integer favor5) {
        this.favor5 = favor5;
    }

    @Override
    public String toString() {
        return "AndUser{" +
        "id=" + id +
        ", name=" + name +
        ", password=" + password +
        ", favor1=" + favor1 +
        ", favor2=" + favor2 +
        ", favor3=" + favor3 +
        ", favor4=" + favor4 +
        ", favor5=" + favor5 +
        "}";
    }
}
