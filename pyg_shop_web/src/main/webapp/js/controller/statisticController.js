app.controller('statisticController',function ($scope,statisticService) {
    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        statisticService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }
})