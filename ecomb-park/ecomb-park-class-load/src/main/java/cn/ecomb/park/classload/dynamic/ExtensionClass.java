package cn.ecomb.park.classload.dynamic;

import lombok.Data;

/**
 * 扩展功能说明类
 *
 * @author brian.zhou
 * @date 2021/7/12
 */
@Data
public class ExtensionClass<T> {

    /** 扩展服务实现类 */
    protected final Class<? extends T> clazz;

    /** 扩展服务实现类实例 */
    protected volatile transient T instance;

//    public ExtensionClass(Class<? extends T> clazz) {
//        this.clazz = clazz;
//    }
}
