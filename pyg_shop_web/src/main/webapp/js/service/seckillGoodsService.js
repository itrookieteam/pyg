//服务层
app.service('seckillGoodsService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../seckillGoods/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../seckillGoods/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../seckillGoods/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../seckillGoods/add.do',entity);
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../seckillGoods/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../seckillGoods/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seckillGoods/search.do?page='+page+"&rows="+rows, searchEntity);
	}

	//商家提交审核
    //商家对商品进行提交审核发送异步的请求
    this.updateStatus=function (ids,status) {
        return $http.get('../seckillGoods/updateStatus.do?ids=' + ids + "&status=" + status);

    }



    //查询实体修改秒杀商品额
    this.findSeckillgoods=function(id){
        return $http.get('../seckillGoods/findSeckillgoods.do?id='+id);
    }
});
