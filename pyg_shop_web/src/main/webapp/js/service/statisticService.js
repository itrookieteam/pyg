app.service('statisticService',function ($http) {
    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../statistic/findAll.do');
    }
    this.findOne=function (id) {
        return $http.get('../statistic/findOne.do?id='+id);
    }
    this.update=function (entity) {
        return $http.post('../statistic/update.do',entity);
    }
})