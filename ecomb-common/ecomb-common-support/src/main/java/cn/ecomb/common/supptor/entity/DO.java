package cn.ecomb.common.supptor.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("all")
public abstract class DO extends PureDO{

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 修改时间
     */
    private Date updatedAt;
}
