app.service('statisticService',function ($http) {
    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../statistic/findAll.do');
    }
})