package com.cetiti.dataX.entity;

import java.util.List;

public class OpenApi {
    private String Action;
    private int CurrentPage;
    private int PageSize;
    private String Parameters;

    public String getAction() {
        return Action;
    }

    public int getCurrentPage() {
        return CurrentPage;
    }

    public int getPageSize() {
        return PageSize;
    }

    public String getParameters() {
        return Parameters;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setCurrentPage(int currentPage) {
        CurrentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public void setParameters(String parameters) {
        Parameters = parameters;
    }

    @Override
    public String toString() {
        return "OpenApi{" +
                "Action='" + Action + '\'' +
                ", CurrentPage=" + CurrentPage +
                ", PageSize=" + PageSize +
                ", Parameters=" + Parameters +
                '}';
    }
}
