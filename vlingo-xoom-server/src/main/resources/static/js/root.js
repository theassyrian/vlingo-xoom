var app = angular.module("myApp", ["ngRoute"]);
var pageId;
var windowService;
var locationService;
var scopeService;
app.config(function ($routeProvider) {
    $routeProvider.when("/", {
        templateUrl: "/static/partials/main.html",
        cache: false
    });
}).directive('loader', ['$routeParams', '$window', '$location', function ($routeParams, $window, $location) {
    return {
        link: function (scope, element, attr) {

            scope.domain = attr.domain;

            pageId = $routeParams.id;
            windowService = $window;
            locationService = $location;
            scopeService = scope;

            $('#accordion').collapse({
                toggle: false
            });
            $("#accordion").click(function (e) {
                e.preventDefault();
            });
            $('.collapse').on('shown.bs.collapse', function () {
                $(this).parent().find('.panel-title').removeClass("expandable").addClass("expanded");
            }).on('hidden.bs.collapse', function () {
                $(this).parent().find('.panel-title').removeClass("expanded").addClass("expandable");
            });

            var selector = ".item-title";
            var loadGraph = function () {
                processorClient.get(function (data) {
                    if (data.statusCode != null) {
                    } else {
                        renderProcessorGraph(function () {
                            console.log("Loaded graph");
                        }, data)
                    }
                });
            };

            loadGraph();

            return attr;
        }
    };
}]);