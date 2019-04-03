app.controller('cartController', function ($scope, cartService, addressService, orderService, $window, $location) {


    var storage = $window.localStorage;


    //刷新页面
    $scope.findCartList = function () {
        //先获取localStorage中的购物车
        var cartList = storage.getItem("cartList");
        if (cartList == null) {
            cartList = [];
            cartService.findCartList(JSON.parse(cartList)).success(
                function (response) {
                    //页面变量赋值
                    $scope.cartList = response;
                    $scope.totalValue=cartService.sum($scope.cartList);
                });
        }
            cartService.findCartList(JSON.parse(cartList)).success(
                function (response) {
                    //页面变量赋值
                    $scope.cartList = response;
                    $scope.totalValue=cartService.sum($scope.cartList);
                });
    }


    //页面初始化方法
    $scope.initObject = function () {
        //search 属性是一个可读可写的字符串，可设置或返回当前 URL 的查询部分（问号 ? 之后的部分）
        var itemId = $location.search()['itemId']
        var num = $location.search()['num']
        if (itemId == null) {
            //如果id==空就不是从item页面过来的,直接刷新购物车
            $scope.findCartList();
            return;
        }
        //如果 itemId 有值,那么应该先把选择的商品加入购物车然后返回显示列表
        //获取 LocalStorage中的cartList 数据
           var cartList = storage.getItem("cartList");
           if(cartList ==  null){
               cartList =  [];
               //如果cartList是null,cartList赋值空对象直接传入后端
               cartService.addItemToCartList( cartList,itemId,num).success( function(response){
                       if(response. success){
                           //把购物车集合放在LocalStorage 中
                           storage.setItem( "cartList",response.message);
                           //刷新购物车列表页面
                           $scope.findCartList();
                       }
                   }
               );
           }
                //如果cartservic不是null,就把cartList转换成Json格式传入后端
                cartService.addItemToCartList( JSON.parse(cartList),itemId,num).success( function(response) {
                        if(response. success){
                            //把购物车集合放在LocalStorage 中
                            storage.setItem( "cartList",response. message);
                            //刷新购物车列表页面
                            $scope.findCartList();
                        }
                }
                );


    }


    /**
     * 点击加减号添加购物车列表
     * @param itemId
     * @param num
     */
    $scope.addOrderItemToCart=function(itemId,num) {
        var cartList = storage.getItem("cartList");
        if (cartList == null) {
            cartList = [];
        }
        cartService.addItemToCartList(JSON.parse(cartList), itemId, num).success(
            function (response) {
                if (response.success) {
                    storage.setItem("cartList", response.message);
                    //刷新购物车页面
                    $scope.findCartList();
                } else {
                    alert(response.message);
                }
            }
        )
    }








        /**
         * 获取购物车列表
         */
        $scope.findCartListFromRedis = function () {
            cartService.findCartListFromRedis().success(
                function (response) {
                    $scope.cartList = response;
                    sum();  //每次查购物车的时候，求和
                }
            )
        }


    /**
     * 求合计数和总金额
     */
    sum = function () {
        $scope.totalMoney = 0;  //总金额
        $scope.totalNum = 0;    //总数量

        //循环购物车
        for (var i = 0; i < $scope.cartList.length; i++) {
            var cart = $scope.cartList[i] //从购物车列表获取购物车
            for (var j = 0; j < cart.orderItemList.length; j++) {
                //循环获取购物车中的商品对象
                $scope.totalMoney += cart.orderItemList[j].totalFee;  //将每个商品的总金额累加
                $scope.totalNum += cart.orderItemList[j].num;         //累加总数量
            }
        }
    }

    /**
     * 查询当前登录人收货地址列表
     */
    $scope.findAddressList = function () {
        addressService.findListByUserId().success(
            function (response) {
                $scope.addressList = response; //将返回的收货人地址列表保存到$scope.addressList

                //循环所有的地址列表查找默认的地址
                for (var i = 0; i < response.length; i++) {
                    if (response[i].isDefault == '1') { //找到默认地址
                        $scope.address = response[i];
                    }
                }
            }
        )
    }

    //保存用户选择地址对象
    $scope.selectAddress = function (address) {
        $scope.address = address;
    }

    //判断是否是用户选择的地址
    $scope.isSelectAddress = function (address) {
        if ($scope.address == address) {
            return true;
        }
        return false;
    }

    $scope.order = {paymentType: '1'};  //初始化一个订单对象，为了传递参数，传递支付方式 1是微信，2是货到付款
    //选择支付方式
    $scope.selectPaymentType = function (value) {
        alert(value);
        $scope.order.paymentType = value;
    }

    //创建订单
    $scope.createOrder = function () {
        $scope.order.receiverMobile = $scope.address.mobile; //收货人电话
        $scope.order.receiverAreaName = $scope.address.address; //收货地址
        $scope.order.receiver = $scope.address.contact;      //收货人

        orderService.add($scope.order).success(
            function (response) {
                if (response.success) {
                    location.href = "pay.html";  //如果订单生成成功跳转支付页面
                } else {
                    alert(response.message);
                }
            }
        )
    }
})