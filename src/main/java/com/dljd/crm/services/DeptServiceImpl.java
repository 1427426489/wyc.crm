package com.dljd.crm.services;

import com.dljd.crm.beans.Dept;
import com.dljd.crm.beans.Page;
import com.dljd.crm.mapper.DeptMapper;
import com.dljd.crm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public Page getSome(Page page) {
        //todo 如何通过切面实现所有getSome方法，减少代码重复率
        int rowsPerPage = page.getRowsPerPage();
        int beginRow = (page.getCurrentPage() - 1) * rowsPerPage;
        List<Dept> depts = deptMapper.getSome(beginRow, rowsPerPage);
        page.setData(depts);
        int totalRows = deptMapper.getCount();
        page.setTotalRows(totalRows);
        int totalPages = totalRows / rowsPerPage;
        if (totalRows % rowsPerPage != 0) {
            totalPages += 1;
        }
        page.setTotalPages(totalPages);
        return page;
    }

    @Override
    public boolean getExists(String no) {
        Dept dept = deptMapper.getByNo(no);
        return dept!=null;
    }

    @Override
    public Dept get(String id) {
        return deptMapper.get(id);
    }

    @Override
    public List<Dept> getAll() {
        return deptMapper.getAll();
    }

    @Override
    public int add(Dept param) {
        param.setId(UUIDUtil.getUUID());
        return deptMapper.add(param);
    }

    @Override
    public int delete(String[] ids) {
        return deptMapper.delete(ids);
    }

    @Override
    public int update(Dept param) {
        return deptMapper.update(param);
    }
}
