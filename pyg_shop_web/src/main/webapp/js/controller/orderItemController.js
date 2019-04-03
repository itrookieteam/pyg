 //控制层 
app.controller('orderItemController' ,function($scope,$controller   ,orderItemService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		orderItemService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		orderItemService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		orderItemService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=orderItemService.update( $scope.entity ); //修改  
		}else{
			serviceObject=orderItemService.add( $scope.entity  );//增加 
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
		orderItemService.dele( $scope.selectIds ).success(
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
		orderItemService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    //订单列表查询
	$scope.findBySellerId=function () {
		orderItemService.findBySellerId().success(
			function (response) {
				$scope.list= response;
			}
		);
	}
	//订单列表mohu 查询
	$scope.selectByRecord=function () {
		orderItemService.selectByRecord($scope.entity).success(
			function (response) {
				$scope.list= response;
			}
		);
	}
//日历插件
	laydate.render({
		elem: '#test',
		done: function (value, date, endDate) {//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。    console.log(value); //得到日期生成的值，如：2017-08-18    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}    console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。  }
			$scope.entity.createTime=value;
		}
	})
	$scope.entity={checkType:"1"};//定义搜索对象
	$scope.status=["全部","未付款","已付款","未发货","已发货","交易成功","交易关闭","待评价"];
	$scope.sourceType=["app端","pc端","M端","微信端","手机qq端"]
	$scope.check=function (status) {
		if(status!=1){
			return true;
		}else{
			return false;
		}

	}

});	
