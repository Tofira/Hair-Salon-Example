angular.module("mainModule", [])
  .controller("mainController", function ($scope, $http, jsonFilter) {
      var logResult = function (str, data, status, headers, config) {
          return str + "\n\n" +
            "data: " + data + "\n\n" +
            "status: " + status + "\n\n" +
            "headers: " + jsonFilter(headers()) + "\n\n" +
            "config: " + jsonFilter(config);
      };


      $scope.getCallJSON = function () {
          var params = {};
          var config = {};

          $http.get("http://localhost:8080/services", config)
            .success(function (data, status, headers, config) {
                // Since the data returned by the server is a JSON object in this case,
                // I use the json filter to output it as a string for debugging purposes.
                // The $http service automatically converts the response to a JavaScript
                // object whenever it sees that it looks like a JSON string.
                // data = jsonFilter(data);
                console.log(data);

                $scope.returnedServices = data;//logResult("GET SUCCESS", data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                $scope.returnedServices = logResult("GET ERROR", data, status, headers, config);
            });
      };

  });