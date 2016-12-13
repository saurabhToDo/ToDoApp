package com.todo.enterprise.todo;

import java.sql.Date;
import java.util.List;

import org.joda.time.LocalDate;

import com.todo.model.todo.ToDo;

public interface ToDoListDaoIF {

    void addToDoItem(ToDo toDo);

    void deleteToDoItem(Integer toDoId);

    ToDo getPerticularToDoItem(Integer toDoId);

    void markStatusAsComplete(Integer toDoId);

    List<ToDo> getToDoItemList(Integer filter);

    List<ToDo> searchItemByDueDays(Date toDate, Date current, Boolean filterFlag);

    List<ToDo> searchItemByText(String text, Boolean filterFlag);


}
