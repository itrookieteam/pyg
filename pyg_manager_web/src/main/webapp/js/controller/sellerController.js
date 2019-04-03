 //控制层 
app.controller('sellerController' ,function($scope,$controller   ,sellerService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		sellerService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		sellerService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		sellerService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=sellerService.update( $scope.entity ); //修改  
		}else{
			serviceObject=sellerService.add( $scope.entity  );//增加 
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

    //注册方法
    $scope.register = function(){
        sellerService.add( $scope.entity  ).success(
            function(response){
                if(response.success){
                    //重新查询
                    //$scope.reloadList();//重新加载
					location.href = "shoplogin.html"; //注册成功，跳转登录页面
                }else{
                    alert(response.message);
                }
            }
        );
    }

	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		sellerService.dele( $scope.selectIds ).success(
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
		sellerService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//运营商审核商家
	$scope.updateStatus = function (id, status) {
		sellerService.updateStatus(id,status).success(
			function (response) {
				if(response.success){
					//刷新列表
					$scope.reloadList();
				}else{
					alert(response.message);
				}
            }
		)
    }

    $scope.status = ["未审核","已审核"];

	//导出Excel
	$scope.exportExcel = function () {
		sellerService.exportExcel().success(
			function (response) {
				alert(response.message);
            }
		)
    }

    layui.use('upload', function(){
        var upload = layui.upload;

        //执行实例
        var uploadInst = upload.render({
            elem: '#importExcel' //绑定元素
            ,url: '../seller/importExcel.do' //上传接口
            ,acceptMime:'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'//文件的mime类型
            ,accept:'file'//文件类型
            ,done: function(res){
                //上传完毕回调
                alert(res.message);
                $scope.reloadList()//重新加载
            }
            ,error: function(){
                //请求异常回调
                alert("网络异常！");
            }
        });
    });
});	
