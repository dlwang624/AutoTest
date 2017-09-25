package com.utils.tools;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MultiValueMap<K, V> {

    /**
     * 添加Key-Value�?
     *
     * @param key   key.
     * @param value value.
     */
    void add(K key, V value);

    /**
     * 添加Key-List<Value>�?
     *
     * @param key    key.
     * @param values values.
     */
    void add(K key, List<V> values);

    /**
     * 设置�?��Key-Value，如果这个Key存在就被替换，不存在则被添加�?
     *
     * @param key   key.
     * @param value values.
     */
    void set(K key, V value);

    /**
     * 设置Key-List<Value>，如果这个Key存在就被替换，不存在则被添加�?
     * @param key    key.
     * @param values values.
     * <a href="http://www.jobbole.com/members/heydee@qq.com">@see</a> #set(Object, Object)
     */
    void set(K key, List<V> values);

    /**
     * 替换�?��的Key-List<Value>�?
     *
     * @param values values.
     */
    void set(Map<K, List<V>> values);

    /**
     * 移除某一个Key，对应的�?��值也将被移除�?
     *
     * @param key key.
     * @return value.
     */
    List<V> remove(K key);

    /**
     * 移除�?��的�?�?
     * Remove all key-value.
     */
    void clear();

    /**
     * 拿到Key的集合�?
     * @return Set.
     */
    Set<K> keySet();

    /**
     * 拿到�?��的�?的集合�?
     *
     * @return List.
     */
    List<V> values();

    /**
     * 拿到某一个Key下的某一个�?�?
     *
     * @param key   key.
     * @param index index value.
     * @return The value.
     */
    V getValue(K key, int index);

    /**
     * 拿到某一个Key的所有�?�?
     *
     * @param key key.
     * @return values.
     */
    List<V> getValues(K key);

    /**
     * 拿到MultiValueMap的大�?
     *
     * @return size.
     */
    int size();

    /**
     * 判断MultiValueMap是否为null.
     *
     * @return True: empty, false: not empty.
     */
    boolean isEmpty();

    /**
     * 判断MultiValueMap是否包含某个Key.
     *
     * @param key key.
     * @return True: contain, false: none.
     */
    boolean containsKey(K key);

}