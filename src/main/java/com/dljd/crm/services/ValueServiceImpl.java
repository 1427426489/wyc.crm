package com.dljd.crm.services;

import com.dljd.crm.beans.Value;
import com.dljd.crm.mapper.ValueMapper;
import com.dljd.crm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValueServiceImpl implements ValueService {

    @Autowired
    private ValueMapper valueMapper;

    @Override
    public Value get(String id) {
        return valueMapper.get(id);
    }

    @Override
    public List<Value> getAll() {
        return valueMapper.getAll();
    }

    @Override
    public int add(Value value) {
        value.setId(UUIDUtil.getUUID());
        return valueMapper.add(value);
    }

    @Override
    public int delete(String[] ids) {
        return valueMapper.delete(ids);
    }

    @Override
    public int update(Value value) {
        return valueMapper.update(value);
    }
}
