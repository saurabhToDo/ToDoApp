toDoApp.factory("toDoService", ["$http", function($http) {
    return {
        addToDoItem: function(callback) {
            var flag = false; //Assuming validation is OK
            var toDoDetailsForm = $('#toDoDetailsForm');
            $.ajax({
                url: 'addToDoItem.html',
                dataType: 'html',
                type: 'POST',
                data: $(toDoDetailsForm).serialize(),
                success: function(response) {
                    alertify.success("Item saved successfully");
                    callback(response);
                },
                error: function(e) {
                    console.log(e.status);
                }
            });
        },

        getToDoItemList: function(filter, callback) {
            $http.get("getToDoItemList.json?filter=" + filter).success(function(ToDoList) {
                callback(ToDoList);
            });
        },

        deleteToDoItem: function(toDoId, callback) {
            $http.get('deleteToDoItem.json?toDoId=' + toDoId).success(function(response) {
                callback(response);
            });
        },

        getPerticularToDoItem: function(toDoId, callback) {
            $http.get('getPerticularToDoItem.json?toDoId=' + toDoId).success(function(response) {
                callback(response);
            });
        },
        markStatusAsComplete: function(toDoId, callback) {
            $http.get('markStatusAsComplete.json?toDoId=' + toDoId).success(function(response) {
                callback(response);
            });
        },
        searchItem: function(dueDays, filter, callback) {
            var searchJson = {
                'dueDays': dueDays,
                'filter': filter
            }
            $http.post('searchItemByDueDays.json?searchJson', searchJson).success(function(response) {
                callback(response);
            });
        },
        searchItemByText: function(text, filter, callback) {
            var searchJson = {
                'text': text,
                'filter': filter
            }
            $http.post('searchItemByText.json?searchJson', searchJson).success(function(response) {
                callback(response);
            });
        },
    }
}]);