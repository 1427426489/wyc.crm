package com.dljd.crm.services;

import com.dljd.crm.beans.Type;
import com.dljd.crm.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Type get(String code) {
        return typeMapper.get(code);
    }

    @Override
    public List<Type> getAll() {
        return typeMapper.getAll();
    }

    @Override
    public boolean getCheckCode(String code) {
        Type type = typeMapper.get(code);
        return type != null;
    }

    @Override
    public int add(Type type) {
        return typeMapper.add(type);
    }

    @Override
    public int delete(String[] ids) {
        return typeMapper.delete(ids);
    }

    @Override
    public int update(Type type) {
        return typeMapper.update(type);
    }

}
