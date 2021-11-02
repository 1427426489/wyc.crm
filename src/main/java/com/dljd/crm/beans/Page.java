package com.dljd.crm.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页对象
 * 需要在后台计算的属性有：
 *  1. 总记录数，select count(*) from xxx
 *  2. 总页数，需要根据总记录数和每页条数进行计算
 */
public class Page implements Serializable {
    private Integer currentPage = 1;        // 当前页（查询条件）
    private Integer rowsPerPage = 10;       // 每页显示的记录条数（查询条件）
    private Integer maxRowsPerPage = 100;   // 每页最多显示的记录条数(配置)
    private Integer totalPages;             // 总页数
    private Integer totalRows;              // 总记录数（需要计算）
    private Integer visiblePageLinks = 5;   // 显示几个卡片(配置)
    private List data;
    private Map<String, Object> searchMap; // 查询条件

    public Map<String, Object> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<String, Object> searchMap) {
        this.searchMap = searchMap;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public Integer getMaxRowsPerPage() {
        return maxRowsPerPage;
    }

    public void setMaxRowsPerPage(Integer maxRowsPerPage) {
        this.maxRowsPerPage = maxRowsPerPage;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getVisiblePageLinks() {
        return visiblePageLinks;
    }

    public void setVisiblePageLinks(Integer visiblePageLinks) {
        this.visiblePageLinks = visiblePageLinks;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Page{" +
                "currentPage=" + currentPage +
                ", rowsPerPage=" + rowsPerPage +
                ", maxRowsPerPage=" + maxRowsPerPage +
                ", totalPages=" + totalPages +
                ", totalRows=" + totalRows +
                ", visiblePageLinks=" + visiblePageLinks +
                ", data=" + data +
                ", searchMap=" + searchMap +
                '}';
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
