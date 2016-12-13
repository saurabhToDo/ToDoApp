package com.todo.model.todo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="to_do")
public class ToDo {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="to_do_id")
	private Integer toDoId;
	
	@Column(name="text")
	private String text;
	
	@Column(name="due_date")
	private Date dueDate;
	
	public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Column(name="created_date")
	private Date createdDate;
	
	@Column(name="is_completed")
	private boolean completed;
	

	public ToDo() {
		// Do nothing
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


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
