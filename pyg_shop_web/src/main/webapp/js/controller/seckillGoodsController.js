 //控制层 
app.controller('seckillGoodsController' ,function($scope,$controller ,seckillGoodsService,goodsService){
	
	$controller('baseController',{$scope:$scope});//继承
    //$scope.entity1 = {};
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		seckillGoodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		seckillGoodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){
        alert(id);
		seckillGoodsService.findOne(id).success(
			function(response){
                alert(response);
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.tbSeckillGoods.id!=null){//如果有ID
           // alert(entity.tbSeckillGoods.id)
			serviceObject=seckillGoodsService.update( $scope.entity); //修改
		}else{
			serviceObject=seckillGoodsService.add( $scope.entity );//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		seckillGoodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		seckillGoodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

    //查询对应商品的信息
    $scope.findgoodsList=function(){
        goodsService.findgoodsList().success(
            function(response){
                $scope.goodList=response;
            }
        );
    }

    $scope.aaa=function(){
       document.getElementById("test1").value = "";
        document.getElementById("test2").value = "";
    }



     $scope.itemList = [];
    //使用观察的方法做查询对应的商品表的详情表的信息sku
    $scope.$watch('entity.tbGoods.id',function(newValue,oldValue){
        //根据选择的一级分类id查询二级分类列表数据
        if (newValue!=null) {
            goodsService.findItemList(newValue).success(
                function(response){
                    $scope.itemList = response;
                }
            )
        }
    })


      $scope.entity = [];
    //使用观察的方法做查询对应的商品表的详情表的信息sku
    $scope.$watch('entity.tbItem.id',function(newValue,oldValue){
        if (newValue!=null) {
        	goodsService.findByParentId(newValue).success(
            function(response){
              //  alert(response);
                $scope.entity.tbItem = response;
                //  $scope.entity.itemId = $scope.entity1.itemId;

            }
        )
        }
        //根据选择的一级分类id查询二级分类列表数据

    })


    $scope.status=['草稿','未审核','审核通过','审核未通过']


   $scope.entity = {tbSeckillGoods :{}};
	//日历插件1
    laydate.render({
        elem: '#test1',
        type:'datetime',
        value: ""
        ,isInitValue: false,
        done: function (value, date, endDate) {//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。    console.log(value); //得到日期生成的值，如：2017-08-18    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}    console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。  }
            $scope.entity.tbSeckillGoods.startTime=value;
             alert(value)
        }
    })



    //日历插件1
    laydate.render({
        elem: '#test2',
        type:'datetime',
        done: function (value, date, endDate) {//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。    console.log(value); //得到日期生成的值，如：2017-08-18    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}    console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。  }
            $scope.entity.tbSeckillGoods.endTime=value;
            alert(value)

        }
    })



    //商家进行秒杀商品的提交的审核
    $scope.updateStatus = function (status) {
        seckillGoodsService.updateStatus($scope.selectIds, status).success(
            function (response) {
                if (response.success) {
                    //提交审核成功直接刷新页面即可
                    $scope.reloadList();
                } else {
                    //显示错误的信息
                    alert(response.message);
                }

            }
        )

    }


    //修改秒杀商品
    $scope.findSeckillgoods=function(id){
        alert(id);
        seckillGoodsService.findSeckillgoods(id).success(
            function(response){
                alert(response);
                $scope.entity= response;
            }
        );
    }




});
