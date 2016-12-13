package com.todo.controller.todo;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

public class ToDoListDetailForm {
    
    private String toDoId;
    @NotBlank
    private String text;
    @NotBlank
    private String dueDate;
    
   
   
  
    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public String getToDoId() {
        return toDoId;
    }
    public void setToDoId(String toDoId) {
        this.toDoId = toDoId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
