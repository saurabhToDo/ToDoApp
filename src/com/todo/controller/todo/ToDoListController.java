package com.todo.controller.todo;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todo.business.todo.ToDoDTO;
import com.todo.business.todo.ToDoListServiceIF;
import com.todo.business.common.JsonHelper;
import com.todo.util.AppLoggerUtil;
import com.todo.util.GenericUtils;

@Controller
public class ToDoListController {

	private static final String TO_DO_DTO = "toDoDTO";

	private static final String DELETION_STATUS = "deletionStatus";

	private static final String TO_DO_ITEMS = "ToDoItems";

	@Autowired
	private ToDoListServiceIF toDoListServiceIF;

	@Autowired
	private Validator beanValidator;

	private static final String STATUS = "status";
	private static final String ERROR = "error";
	
	/*
	 * Add ToDo Item
	 * @param text,dueDate
	 */

	@RequestMapping(value = "addToDoItem.html", method = RequestMethod.POST)
	public@ResponseBody void addToDoItem(ToDoListDetailForm toDoListDetailForm, BindingResult bindingResult, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map < String,Object > responseMap = new HashMap < String,Object > ();
		try {
			beanValidator.validate(toDoListDetailForm, bindingResult);
			if (!bindingResult.hasFieldErrors()) {
				ToDoDTO toDoDTO = new ToDoDTO();

				BeanUtils.copyProperties(toDoDTO, toDoListDetailForm);
				String toDoId = toDoListDetailForm.getToDoId();
				if (StringUtils.isNotEmpty(toDoId)) {
					int toDoid = Integer.parseInt(toDoId);
					toDoDTO.setToDoId(toDoid);
				} else {
					toDoDTO.setToDoId(null);
				}
				Date date = GenericUtils.parseDate(toDoListDetailForm.getDueDate());
				toDoDTO.setDueDate(date.getTime());
				java.util.Date createdDate = new java.util.Date();
				java.sql.Date sqlCreatedDate = new java.sql.Date(createdDate.getTime());
				toDoDTO.setText(toDoListDetailForm.getText());
				toDoDTO.setCreatedDate(sqlCreatedDate);
				toDoListServiceIF.addToDoItem(toDoDTO);
				responseMap.put(STATUS, false);
			}
		} catch(Exception e) {
			AppLoggerUtil.logError(ToDoListController.class, "Fatal Error while adding item", e);
			responseMap.put(ERROR, true);
		} finally {
			JsonHelper.writeResponseData(responseMap, response);
		}
	}

	/*
	 * get All ToDo Item
	 */
	@RequestMapping(value = "getToDoItemList.json", method = RequestMethod.GET)@ResponseBody
	public void getToDoItemList(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "filter", required = false) Integer filter) throws IOException {
		Map < String,Object > responseMap = new HashMap < String,Object > ();
		try {
			List < ToDoDTO > toDoDtos = toDoListServiceIF.getToDoItemList(filter);
			if (GenericUtils.isNotEmpty(toDoDtos)) {
				responseMap.put(TO_DO_ITEMS, toDoDtos);
			} else {
				responseMap.put(ERROR, true);
			}
		} catch(Exception e) {
			responseMap.put(ERROR, true);
			AppLoggerUtil.logError(ToDoListController.class, "Fatal error occurred while geting to do item data: ", e);
		}
		JsonHelper.writeResponseData(responseMap, response);
	}

	/*
	 * Delete ToDo Item
	 * @param toDoId
	 */
	@RequestMapping(value = "deleteToDoItem.json", method = RequestMethod.GET)@ResponseBody
	public void deleteToDoItem(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "toDoId", required = false) Integer toDoId) throws IOException {
		Map < String,Object > responseMap = new HashMap < String,Object > ();
		try {
			boolean deletionStatus = toDoListServiceIF.deleteToDoItem(toDoId);
			responseMap.put(DELETION_STATUS, deletionStatus);
		}
		catch(Exception e) {
			responseMap.put(ERROR, true);
			AppLoggerUtil.logError(ToDoListController.class, "Fatal error while deleting to do item : ", e);
		}
		JsonHelper.writeResponseData(responseMap, response);
	}

	@RequestMapping(value = "getPerticularToDoItem.json", method = RequestMethod.GET)@ResponseBody
	public void getPerticularToDoItem(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "toDoId", required = false) Integer toDoId) throws IOException {
		Map < String,Object > responseMap = new HashMap < String,Object > ();
		try {
			ToDoDTO toDoDTO = toDoListServiceIF.getPerticularToDoItem(toDoId);
			responseMap.put(TO_DO_DTO, toDoDTO);
		}
		catch(Exception e) {
			responseMap.put(ERROR, true);
			AppLoggerUtil.logError(ToDoListController.class, "Fatal error while getting to do item : ", e);
		}
		JsonHelper.writeResponseData(responseMap, response);
	}

	@RequestMapping(value = "markStatusAsComplete.json", method = RequestMethod.GET)@ResponseBody
	public void markStatusAsComplete(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "toDoId", required = false) Integer toDoId) throws IOException {
		Map < String,Object > responseMap = new HashMap < String,Object > ();
		try {
			toDoListServiceIF.markStatusAsComplete(toDoId);
		}
		catch(Exception e) {
			responseMap.put(ERROR, true);
			AppLoggerUtil.logError(ToDoListController.class, "Fatal error while getting to do item : ", e);
		}
		JsonHelper.writeResponseData(responseMap, response);
	}

	@RequestMapping(value = "searchItemByDueDays.json", method = RequestMethod.POST)@ResponseBody
	public void searchItemByDueDays(@RequestBody Map < String, Object > searchJson, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map < String,Object > responseMap = new HashMap < String,Object > ();
		try {
			List < ToDoDTO > toDoDtos = toDoListServiceIF.searchItemByDueDays(searchJson);
			responseMap.put(TO_DO_ITEMS, toDoDtos);
		} catch(Exception e) {
			responseMap.put(ERROR, true);
			AppLoggerUtil.logError(ToDoListController.class, "Fatal error occurred while geting to do item data: ", e);
		}
		JsonHelper.writeResponseData(responseMap, response);
	}

	@RequestMapping(value = "searchItemByText.json", method = RequestMethod.POST)@ResponseBody
	public void searchItemByText(@RequestBody Map < String, Object > searchJson, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map < String,Object > responseMap = new HashMap < String,Object > ();
		try {
			List < ToDoDTO > toDoDtos = toDoListServiceIF.searchItemByText(searchJson);
			responseMap.put(TO_DO_ITEMS, toDoDtos);
		} catch(Exception e) {
			responseMap.put(ERROR, true);
			AppLoggerUtil.logError(ToDoListController.class, "Fatal error occurred while geting to do item data: ", e);
		}
		JsonHelper.writeResponseData(responseMap, response);
	}

}