app.controller('statisticController',function ($scope,statisticService) {
    //测试拉取
    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        statisticService.findAll().success(
            function(response){
                $scope.list=response;
                var goodsTitle =[];
                for(var i = 0; i < response.length; i++){
                    goodsTitle[i] = response[i].name;
                }
                // goodsValues = data;
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));
                option = {
                    title : {
                        text: '商品库存数量',
                        // subtext: '纯属虚构',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data:goodsTitle
                    },
                    series : [
                        {
                            name: '商品库存',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:response,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        );
    }
    $scope.findOne=function (id) {
        statisticService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        )
    }
    //保存
    $scope.save=function(){
        statisticService.update($scope.entity).success(
            function(response){
                if(response.success){
                    //重新查询
                    $scope.findAll();
                    // $scope.reloadList();//重新加载
                }else{
                    alert(response.message);
                }
            }
        );
    }
});

