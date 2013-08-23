package org.crued.restcontract.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.MultivaluedMap;

class MultivaluedMapImpl extends HashMap<String, List<String>> implements MultivaluedMap<String, String> {

    int size = 0;

    public MultivaluedMapImpl() {
    }

    public void putSingle(String key, String value) {
        if (!containsKey(key)) {
            put(key, new ArrayList<String>());
        } else {
            final List<String> valueList = get(key);
            size -= valueList.size();
            valueList.clear();
        }
        size++;
        get(key).add(value);
    }

    public void add(String key, String value) {
        List<String> valueList = get(key);
        if (!containsKey(key)) {
            valueList = new ArrayList<String>();
            put(key, valueList);
        }
        size++;
        valueList.add(value);
    }

    public String getFirst(String key) {
        String retVal = null;
        if (containsKey(key)) {
            List<String> values = get(key);
            if (!values.isEmpty()) {
                retVal = values.get(0);
            }
        }
        return retVal;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(Object o) {
        return super.containsKey(o);
    }

    public boolean containsValue(Object o) {
        return super.containsValue(o);
    }

    public List<String> get(Object o) {
        return super.get(o);
    }

    public List<String> put(String k, List<String> v) {
        final List<String> retVal = super.put(k, v);
        if (retVal != null) {
            size -= retVal.size();
        }
        if (v != null) {
            size += v.size();
        }
        return retVal;
    }

    public List<String> remove(Object o) {
        return super.remove(o);
    }

    public void putAll(Map<? extends String, ? extends List<String>> map) {
        for (Map.Entry<? extends String, ? extends List<String>> e : map.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    public void clear() {
        super.clear();
        size = 0;
    }

    public Set<String> keySet() {
        return super.keySet();
    }

    public Collection<List<String>> values() {
        return super.values();
    }

    public Set<Map.Entry<String, List<String>>> entrySet() {
        return super.entrySet();
    }
}
