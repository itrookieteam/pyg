app.service('cartService',function ($http) {


    //添加商品到购物车
    this.addItemToCartList=function (cartList,itemId,num) {
        entity={cartList:cartList,itemId:itemId,num:num};
        return $http.post('./cart/addItemToCartList.do',entity).success(
            function (resp) {
                return resp
            }
        )
    }

    //刷新购物车页面
   this.findCartList=function (cartList) {
       entity={cartList:cartList};
       return $http.post('./cart/findCartList.do',entity).success(
           function (responces) {
               return responces
           }
       )
   }


   //商品总数
    this.sum=function(cartList){
        var totalValue={totalNum:0, totalMoney:0.00 };//合计实体
        for(var i=0;i<cartList.length;i++){
            var cart=cartList[i];
            for(var j=0;j<cart.orderItemList.length;j++){
                var orderItem=cart.orderItemList[j];//购物车明细
                totalValue.totalNum+=orderItem.num;
                totalValue.totalMoney+= orderItem.totalFee;
            }
        }
        return totalValue;
    }



/*

    this.findCartListFromRedis = function () {
        return $http.get('cart/findCartListFromRedis.do')
    }

//http://localhost:9107/cart/addItemToCartList.do?itemId=1369368&num=10
    this.addItemToCartList = function (cartList,itemId,num) {
        return $http.get('cart/addItemToCartList.do?itemId='+itemId+"&num="+num+"&cartList="+cartList);
    }
*/



})