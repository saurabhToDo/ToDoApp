package com.todo.business.todo;

import java.util.List;
import java.util.Map;


public interface ToDoListServiceIF {

    void addToDoItem(ToDoDTO toDoDTO) throws Exception;
    boolean deleteToDoItem(Integer toDoId);
    ToDoDTO getPerticularToDoItem(Integer toDoId);
    void markStatusAsComplete(Integer toDoId);
    List<ToDoDTO> getToDoItemList(Integer filter);
    List<ToDoDTO> searchItemByDueDays(Map<String, Object> searchJson);
    List<ToDoDTO> searchItemByText(Map<String, Object> searchJson);

}
