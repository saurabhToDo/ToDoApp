package com.todo.business.todo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.enterprise.todo.ToDoListDaoIF;
import com.todo.model.todo.ToDo;
import com.todo.util.GenericUtils;

@Service@Transactional
public class ToDoListServiceImpl implements ToDoListServiceIF {

	private static final String FILTER = "filter";
	private static final String DUE_DAYS = "dueDays";@Autowired
	private ToDoListDaoIF toDoListDaoIF;

	@Override
	public void addToDoItem(ToDoDTO toDoDTO) throws Exception {
		ToDo toDo = new ToDo();
		BeanUtils.copyProperties(toDo, toDoDTO);
		if (toDo.getToDoId() == 0) toDo.setToDoId(null);
		toDoListDaoIF.addToDoItem(toDo);
	}

	@Override
	public List < ToDoDTO > getToDoItemList(Integer filter) {
		List < ToDo > toDo = toDoListDaoIF.getToDoItemList(filter);
		if (GenericUtils.isNotEmpty(toDo)) {
			List < ToDoDTO > ToDoDTOList = new ArrayList < >();
			ToDoDTO toDoDTO = null;
			for (ToDo todo: toDo) {
				toDoDTO = new ToDoDTO();
				toDoDTO.setText(todo.getText());
				toDoDTO.setSqlDueDate(todo.getDueDate());
				toDoDTO.setCreatedDate(todo.getCreatedDate());
				toDoDTO.setToDoId(todo.getToDoId());
				toDoDTO.setCompleted(todo.isCompleted());
				ToDoDTOList.add(toDoDTO);
			}
			return ToDoDTOList;
		}
		return Collections.emptyList();
	}

	@Override
	public boolean deleteToDoItem(Integer toDoId) {
		if (toDoId != null) {
			toDoListDaoIF.deleteToDoItem(toDoId);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ToDoDTO getPerticularToDoItem(Integer toDoId) {
		ToDo toDo = toDoListDaoIF.getPerticularToDoItem(toDoId);
		if (toDo != null) {
			ToDoDTO toDoDTO = new ToDoDTO();
			toDoDTO.setText(toDo.getText());
			toDoDTO.setSqlDueDate(toDo.getDueDate());
			toDoDTO.setToDoId(toDo.getToDoId());
			return toDoDTO;
		} else {
			return null;
		}
	}

	@Override
	public void markStatusAsComplete(Integer toDoId) {
		toDoListDaoIF.markStatusAsComplete(toDoId);
	}

	@Override
	public List < ToDoDTO > searchItemByDueDays(Map < String, Object > searchJson) {
		Integer dueDays = Integer.valueOf(String.valueOf(searchJson.get(DUE_DAYS)));
		Integer status = Integer.valueOf(String.valueOf(searchJson.get(FILTER)));
		java.util.Date currentDate = new java.util.Date();
		java.sql.Date current = new java.sql.Date(currentDate.getTime());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, dueDays);
		java.sql.Date toDate = new Date(cal.getTimeInMillis());
		List < ToDo > toDo = toDoListDaoIF.searchItemByDueDays(toDate, current, validateStatus(status));
		if (GenericUtils.isNotEmpty(toDo)) {
			List < ToDoDTO > ToDoDTOList = new ArrayList < >();
			ToDoDTO toDoDTO = null;
			for (ToDo todo: toDo) {
				toDoDTO = new ToDoDTO();
				toDoDTO.setText(todo.getText());
				toDoDTO.setSqlDueDate(todo.getDueDate());
				toDoDTO.setCreatedDate(todo.getCreatedDate());
				toDoDTO.setToDoId(todo.getToDoId());
				toDoDTO.setCompleted(todo.isCompleted());
				ToDoDTOList.add(toDoDTO);
			}
			return ToDoDTOList;
		}
		return Collections.emptyList();
	}

	@Override
	public List < ToDoDTO > searchItemByText(Map < String, Object > searchJson) {
		String text = String.valueOf(searchJson.get("text"));
		Integer status = Integer.valueOf(String.valueOf(searchJson.get(FILTER)));

		List < ToDo > toDo = toDoListDaoIF.searchItemByText(text, validateStatus(status));
		if (GenericUtils.isNotEmpty(toDo)) {
			List < ToDoDTO > ToDoDTOList = new ArrayList < >();
			ToDoDTO toDoDTO = null;
			for (ToDo todo: toDo) {
				toDoDTO = new ToDoDTO();
				toDoDTO.setText(todo.getText());
				toDoDTO.setSqlDueDate(todo.getDueDate());
				toDoDTO.setCreatedDate(todo.getCreatedDate());
				toDoDTO.setToDoId(todo.getToDoId());
				toDoDTO.setCompleted(todo.isCompleted());
				ToDoDTOList.add(toDoDTO);
			}
			return ToDoDTOList;
		}
		return Collections.emptyList();
	}

	public static boolean validateStatus(Integer status) {
		if (status == 1) {
			return true;
		} else return false;
	}
}