<html>
	<head><%@include file="/common_jstl.jsp"%><%@include file="/includes/to-do-list-script.jsp"%>
		<title>To Do List:Home</title>
	</head>
	<body ng-app="toDoApp" ng-controller="toDoController">
		<div class="mainContainer">
			<div class="dataContainer">
				<div class="toDoListWrapper">
					<div class="toDoListHeading">
						<h3 class="title">Add/Edit To Do Item</h3>
					</div>
					<div class="toDoFormBody">
						<form  name="toDoDetailsForm" method="post" id="toDoDetailsForm" >
							<div class="wrapper">
								<div class="wrapperLabel">Enter Item Name</div>
								<div class="wrapperElement">
									<input type="text" name="text" id="text" placeholder="Type Text" ng-model="text">
									</div>
									<br>
										<div class="dispN">
											<input type="text" id="toDoId"  name="toDoId" ng-model="toDoId"/>
										</div>
									</div>
									<div class="wrapper">
										<div class="wrapperLabel">Select Due Date</div>
										<div class="wrapperElement">
											<input type="text" name="dueDate" id="dueDate" placeholder="Select Due Date" ng-model="dueDate">
											</div>
										</div>
										<div class="buttonPanel" position="center">
											<input type="button"  id="saveToDo" name="Save" value="Save"  ng-click="addToDoItem()"/>
											<input type="button"  id="reset" name="reset" value="Reset" ng-click="resetTheForm()" />
										</div>
									</form>
								</div>
								<div class="toDoListHeading">
									<h3 class="title">To Do List</h3>
								</div>
								<div>
									<span class="searchLabel">Search By</span>
									<span class="searchElement">
										<select class="searchDrop" id="search">
											<option value="1">Text</option>
											<option value="2">Due in next N days</option>
										</select>
									</span>
									<span>
										<input  class="searchInput" type="text" name="text" id="SearchInputext" placeholder="Type Text">
										</span>
										<span>
											<input type="button"  id="Search" name="Search" value="Search" ng-click="searchItem()"/>
										</span>
										<div class="filter">
											<span class="wrapperLabel">Filter</span>
											<span class="wrapperElement">
												<select id="filter" ng-model="filter" ng-change="getToDoItemList()">
													<option value="1">Completed</option>
													<option value="0">Pending</option>
													<option value="2">All</option>
												</select>
											</span>
										</div>
									</div>
									<div>
										<table id="createdBatchList" class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th>Item Name</th>
													<th>Due Date</th>
													<th>Created Date</th>
													<th>Status</th>
													<th>Edit Record</th>
													<th>Delete Record</th>
												</tr>
											</thead>
											<tr ng-repeat="ToDoItems in ToDoItems"
							ng-model="toDoId"
							id="toDoId-{{ToDoItems.toDoId}}">
												<td>{{ToDoItems.text}}</td>
												<td>{{ToDoItems.sqlDueDate | date:'MMM dd, yyyy'}}</td>
												<td>{{ToDoItems.createdDate | date:'MMM dd, yyyy'}}</td>
												<td ng-if="ToDoItems.completed==false">
													<a href='javascript:void(0)' title="You can mark it as complete" ng-click="markStatusAsComplete(ToDoItems.toDoId)">Mark as complete</a>
												</td>
												<td ng-if="ToDoItems.completed==true">
													<span>Completed</span>
												</td>
												<td ng-if="ToDoItems.completed==false">
													<a href='javascript:void(0)' ng-click="EditToDoItem(ToDoItems.toDoId)">Edit</a>
												</td>
												<td ng-if="ToDoItems.completed==true">
													<a href='javascript:void(0)'></a>
												</td>
												<td>
													<a href='javascript:void(0)' ng-click="deleteToDoItem(ToDoItems.toDoId)">Delete</a>
												</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>
					</body>
				</html>
		