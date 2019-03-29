app.controller('statisticController',function ($scope,statisticService) {
    //测试拉取
    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        statisticService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }
})