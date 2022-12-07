package com.backend.backend.models;

import java.util.List;

public class Filter {
    private Object filters;
    private int first;
    private String globalFilter;
//    private String multiSortMeta;
    private int rows;
    private String sortField;
    private int sortOrder;

    public Object getFilters() {
        return filters;
    }

    public void setFilters(Object filters) {
        this.filters = filters;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public String getGlobalFilter() {
        return globalFilter;
    }

    public void setGlobalFilter(String globalFilter) {
        this.globalFilter = globalFilter;
    }

//    public String getMultiSortMeta() {
//        return multiSortMeta;
//    }
//
//    public void setMultiSortMeta(String multiSortMeta) {
//        this.multiSortMeta = multiSortMeta;
//    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
