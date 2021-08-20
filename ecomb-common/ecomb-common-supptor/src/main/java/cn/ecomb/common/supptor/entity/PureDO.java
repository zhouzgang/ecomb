package cn.ecomb.common.supptor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;


@Getter
@Setter
@EqualsAndHashCode
@SuppressWarnings("all")
public abstract class PureDO implements IDO<Long>{

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
