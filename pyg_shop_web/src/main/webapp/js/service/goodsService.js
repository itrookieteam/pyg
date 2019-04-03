//服务层
app.service('goodsService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../goods/findAll.do');		
	}

    //查询对应商品的信息
    this.findgoodsList=function(){
        return $http.get('../seckillGoods/findgoodsList.do');
    }
    //根据商品的id查询对应sku的信息
    this.findItemList=function(goodsId){
        return $http.get('../seckillGoods/findItemList.do?goodsId='+goodsId);
    }

    //根据sku的id查询对应sku具体商品的信息
    this.findByParentId=function(goodsId){
        return $http.get('../seckillGoods/findByParentId.do?goodsId='+goodsId);
    }


	//分页 
	this.findPage=function(page,rows){
		return $http.get('../goods/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../goods/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../goods/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../goods/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../goods/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../goods/search.do?page='+page+"&rows="+rows, searchEntity);
	}

	this.updateStatus = function (ids,status) {
		return $http.get('../goods/updateStatus.do?ids='+ids+"&status="+status);
    }
});
