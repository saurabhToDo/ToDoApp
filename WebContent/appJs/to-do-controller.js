toDoApp.controller("toDoController", ["$scope", "$filter", "$location", "toDoService", function($scope, $filter, $location, toDoService) {
    $scope.addToDoItem = function() {
        var text = $('#text').val();
        var due = $('#dueDate').val();
        if (text != "" && due != "") {
            toDoService.addToDoItem(function(responseData) {
                $scope.filter = $('#filter :selected').val();
                $scope.getToDoItemList($scope.filter);
            });
        } else {
            alertify.error("please fill all the fields")
        }
        $scope.resetTheForm();
        $("#dueDate").val("");
    };

    $scope.getToDoItemList = function() {
        $scope.filter = $('#filter :selected').val();
        toDoService.getToDoItemList($scope.filter, function(ToDoList) {
            $scope.ToDoItems = ToDoList.ToDoItems;
        });
    }

    $scope.resetTheForm = function() {
        $scope.text = "";
        $scope.toDoId = "";
        $scope.dueDate = "";
    }

    $scope.deleteToDoItem = function(toDoId) {
        alertify.confirm("Do you want to delete this item?",
            function(e) {
                if (e) {
                    console.log("hello");
                    toDoService.deleteToDoItem(toDoId, function(response) {
                        $('tr#toDoId-' + toDoId + '').remove();
                    });
                }
            });
    }

    $scope.EditToDoItem = function(toDoId) {
        toDoService.getPerticularToDoItem(toDoId, function(response) {
            $("#text").focus();
            $scope.text = response.toDoDTO.text;
            $scope.toDoId = response.toDoDTO.toDoId;
            $scope.dueDate = ($filter('date')(response.toDoDTO.sqlDueDate, 'MMM dd, yyyy'));
        });
    }

    $scope.markStatusAsComplete = function(toDoId) {
        toDoService.markStatusAsComplete(toDoId, function(response) {
            $scope.filter = $('#filter :selected').val();
            $scope.getToDoItemList($scope.filter);
        });
    }

    $scope.searchItem = function() {
        var searchText = $("#SearchInputext").val()
        var search = $('#search :selected').val();
        var filter = $("#filter").val();
        if (search == 1) {
            toDoService.searchItemByText(searchText, filter, function(response) {
                $scope.ToDoItems = response.ToDoItems;
            });
        } else if ($.isNumeric(searchText) && search == 2) {
            toDoService.searchItem(searchText, filter, function(response) {
                $scope.ToDoItems = response.ToDoItems;
            });
        } else {
            alertify.error("input should be numeric")
        }
    }

    $scope.filter = $("#filter").val(2);
    $("#SearchInputext").val("");
    $scope.getToDoItemList($scope.filter);
}]);