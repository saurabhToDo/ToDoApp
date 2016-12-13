package com.todo.business.todo;

import java.sql.Date;

public class ToDoDTO {

    private Integer toDoId;
    private String text;
    private long dueDate;
    private Date createdDate;
    private Date sqlDueDate;
    private boolean completed;
    
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public Date getSqlDueDate() {
        return sqlDueDate;
    }
    public void setSqlDueDate(Date sqlDueDate) {
        this.sqlDueDate = sqlDueDate;
    }
    public long getDueDate() {
        return dueDate;
    }
    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public Integer getToDoId() {
        return toDoId;
    }
    public void setToDoId(Integer toDoId) {
        this.toDoId = toDoId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
