package cn.ecomb.common.supptor.entity;

import java.io.Serializable;

public interface IDO<TKey extends Serializable> extends Serializable {

    /**
     * 获取id
     * @return id
     */
    TKey getId();

    /**
     * 设置id
     * @param id id
     */
    void setId(TKey id);
}
