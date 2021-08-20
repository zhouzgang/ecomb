package cn.ecomb.common.supptor.mapper;

import cn.ecomb.common.supptor.entity.IDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

public interface AbstractMapper <T extends IDO<? extends Serializable>> extends BaseMapper<T> {
}
