package com.sweetcard.basic.dao.entities;

import java.util.List;

/**
 Класс ответа AJAX
 */
public class Response {
    private int count;
    private String text;
    private List<Finsproject> finsprojectlist;


    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public List<Finsproject> getProjectList() {
        return finsprojectlist;
    }
    public void setProjectList(List<Finsproject> finsprojectlistIn) {
        this.finsprojectlist = finsprojectlistIn;
    }

}
