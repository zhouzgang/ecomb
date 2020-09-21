package cn.ecomb.common.utils.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhouzg
 * @description 对象处理类
 * 封装其他工具类，便于替换不同的 Bean 工具类
 * todo 这个工具的拷贝集合还有问题
 * @create 2019-09-18 11:09
 **/
@Slf4j
public class BeanHandleUtil<T, K> {

    /**
     * 对象属性拷贝
     * 无法copy的情况：内部类、集合
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 对象拷贝
     * 直接返回copy后的对象，这里能copy属性是内部类的字段；但内部类的字段如果还是内部类，暂无法解决
     *
     * @param source
     * @param target
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> K copyProperties(T source, Class<K> target) {
        if (source == null) {
            return null;
        }
        K newInstance = null;
        try {
            newInstance = target.newInstance();
            copyProperties(source, newInstance);
            // 首先得到pojo所定义的字段
            Field[] fields = getAllFields(newInstance);
            for (Field curField : fields) {
                // 设置字段可访问（必须，否则报错）
                Class<?> curFieldType = curField.getType();
                curField.setAccessible(true);
                // 集合List元素
                if (curFieldType.equals(List.class)) {
                    List listObj = (List)getFieldValue(source, curField.getName());
                    if (Objects.isNull(listObj)) {
                        continue;
                    }
                    // 当前集合的泛型类型
                    Type genericType = curField.getGenericType();
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) genericType;
                        // 得到泛型里的class类型对象
                        Class<?> actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];
                        // todo 这里处理对象内部有集合情况

                        Object curO = new Object();

//                        BeanUtil.copyProperties((List<Object>) o, actualTypeArgument);

                        // todo 这里处理对

                        curField.set(newInstance, curO);
                    }
                } else if (curFieldType.getName().contains("$")) {
                    Object o = getFieldValue(source, curField.getName());
                    if (Objects.isNull(o)) {
                        continue;
                    }
                    Object curO = curFieldType.newInstance();
                    BeanUtils.copyProperties(o, curO);
                    curField.set(newInstance, curO);
                }
            }
        } catch (Exception e) {
            log.error("copy异常",e);
        }
        return newInstance;
    }

    /**
     * 列表拷贝
     *
     * @param sourceList
     * @param target     此类不能覆盖默认构造函数
     * @return
     */
    public static <T, K> List<K> copyPropertiesList(List<T> sourceList, Class<K> target) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        List<K> result = sourceList.stream()
                .map(item -> {
                    try {
                        K d = copyProperties(item, target);
                        return d;
                    } catch (Exception e) {
                        log.info("对象拷贝异常", e);
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
        return result;
    }

    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {

        }
        return map;
    }


    public static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }


    public static <T> Object getFieldValue(T source, String fieldName) {

        if (source == null) {
            return null;
        }

        Object value = null;
        try {
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(source);
            value = beanWrapper.getPropertyValue(fieldName);
        } catch (Exception e) {
            log.error("获取对象属性值报错：{}", e.getMessage());
        }
        return value;
    }
}
