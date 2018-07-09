package com.cetiti.core.support;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：自定义分页
 * @author zhouliyu
 * @version dataX V1.0.0
 *
 * */
public class PageModel<T> implements Serializable {


    private static final long serialVersionUID = -1964343841109742892L;
    //总记录数
    private long total;
    //结果集
    private List<T> rows;
    //页码值
    private int pageNum;
    //每页记录数
    private int pageSize;
    //总页数
    private int pages;
    //当前页的数量 <= pageSize, 该属性来自ArrayList的size属性
    private int size;
    //状态码
    private Integer code;
    //状态内容
    private String msg;


    public PageModel() {
        super();
    }

    public PageModel(List<T> list){
        if(list instanceof Page){
            Page<T> page = (Page<T>)list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            List<T> customList = new ArrayList<>();
            for(T t : list){
                customList.add(t);
            }
            this.rows = customList;
            this.size = page.size();
        }
    }

    public long getTotal() {
        return total;
    }

    public List<T> getRows() {
        return rows;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPages() {
        return pages;
    }

    public int getSize() {
        return size;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setRows(List<T> rows) {
        List<T> list = new ArrayList<>();
        for(T t : rows){
            list.add(t);
        }
        this.rows = rows;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
