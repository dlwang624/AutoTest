package com.utils.tools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class LinkedMultiValueMap<K, V> implements MultiValueMap<K, V> {

    protected Map<K, List<V>> mSource = new LinkedHashMap<K, List<V>>();

    public LinkedMultiValueMap() {
    }

    @Override
    public void add(K key, V value) {
        if (key != null) {
            // 如果有这个Key就继续添加Value，没有就创建�?��List并添加Value
            if (!mSource.containsKey(key))
                mSource.put(key, new ArrayList<V>(2));
            mSource.get(key).add(value);
        }
    }

    @Override
    public void add(K key, List<V> values) {
        // 便利添加进来的List的Value，调用上面的add(K, V)方法添加
        for (V value : values) {
            add(key, value);
        }
    }

    @Override
    public void set(K key, V value) {
        // 移除这个Key，添加新的Key-Value
        mSource.remove(key);
        add(key, value);
    }

    @Override
    public void set(K key, List<V> values) {
        // 移除Key，添加List<V>
        mSource.remove(key);
        add(key, values);
    }

    @Override
    public void set(Map<K, List<V>> map) {
        // 移除�?��值，便利Map里的�?��值添加进�?        mSource.clear();
        mSource.putAll(map);
    }

    @Override
    public List<V> remove(K key) {
        return mSource.remove(key);
    }

    @Override
    public void clear() {
        mSource.clear();
    }

    @Override
    public Set<K> keySet() {
        return mSource.keySet();
    }

    @Override
    public List<V> values() {
        // 创建�?��临时List保存�?��的Value
        List<V> allValues = new ArrayList<V>();

        // 便利�?��的Key的Value添加到临时List
        Set<K> keySet = mSource.keySet();
        for (K key : keySet) {
            allValues.addAll(mSource.get(key));
        }
        return allValues;
    }

    @Override
    public List<V> getValues(K key) {
        return mSource.get(key);
    }

    @Override
    public V getValue(K key, int index) {
        List<V> values = mSource.get(key);
        if (values != null && index < values.size())
            return values.get(index);
        return null;
    }

    @Override
    public int size() {
        return mSource.size();
    }

    @Override
    public boolean isEmpty() {
        return mSource.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
        return mSource.containsKey(key);
    }

	@Override
	public String toString() {
		Set<Entry<K, List<V>>> entrySet = mSource.entrySet();
		StringBuilder sb=new StringBuilder();
		for (Entry<K, List<V>> entry : entrySet) {
			sb.append("[");
			sb.append(entry.getKey()+" : ");
			List<V> list = entry.getValue();
			if (list!=null) {
				for (V v : list) {
					sb.append(v + " , ");
				}
				sb.append("]");
			}
			
		}
		return sb.toString();
	}
    
    

}