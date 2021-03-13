var app = new Vue({
    el: '#addPo',
    data: function() {
        return {
            goodTitle: '',  // 想查询的商品名称
            category: null,  // 选择的渠道
            categoryOption: [],
            tableData: [],
            goodData: [],
            showGood: [], // 经筛选的商品信息
            selectedGood: [],  // 选择的商品信息
            dialogVisible: false,
            poDialogVisible: false,
            goodsInfoVo: [], // 所有的商品信息
            selectGoodsId:''
        }
    },
    mounted: function() {
        this.getCategory();
    },
    methods: {
        getCategory: function(){
            $.ajax({
                url: '/detail/getCategory',
                type: 'GET',
                success: function (data) {
                    app.categoryOption = data;
                    app.getAllGoods();
                }.bind(this)
            });
        },
        getAllGoods:function(){
            $.ajax({
                url: '/mvc/goodInfoVo',
                type: 'GET',
                success: function (data) {
                    app.goodsInfoVo = data;
                    if(app.category == null){
                        app.showGood = data.all.all;
                    }
                }.bind(this)
            });
        },
        deleteRow: function (index,row) {
            app.tableData.splice(index, 1);
            app.selectGoodsId.replace("_id"+row.id,"");
        },
        addDemo() {
            let d = {
                title: '',
                qty: 0,
                price: 0,
                totalPrice: 0,
            };
            app.tableData.push(d);
        },
        openDialog:function(){
            if (app.dialogVisible){
                app.dialogVisible=false
            }else {
                app.dialogVisible=true;
            }
        },
        selectGood:function () {
            app.selectedGood.map(function (item) {
                let d = {
                    title: item.title,
                    qty: 0,
                    price: 0,
                    totalPrice: 0,
                    id:item.id
                };
                app.tableData.push(d);
            });
            app.dialogVisible=false;
        },
        handleSelectionChange:function (val) {
            val.map(function (item) {
                if (app.selectGoodsId.indexOf("_id"+item.id) == -1){
                    app.selectGoodsId = app.selectGoodsId + "_id"+item.id;
                    app.tableData.push(item);
                }
            })
        },
        refreshGoods:function () {
            app.showGood=[];
            let categorys='';
            if (app.category.length>0){
                app.category.map(function (item,index) {
                    categorys=categorys+item+",";
                })
            }
            let goods= [];
            $.ajax({
                url: '/mvc/refreshGoods',
                type: 'POST',
                data:{category:categorys,title:app.goodTitle},
                contentType:"application/x-www-form-urlencoded; charset=utf-8",
                dataType:"json",
                success: function (data) {
                    goods=data;
                    goods.map(function (item) {
                        let url = "app.goodsInfoVo.gc".concat(item.categoryid,".t",item.id,"[0]");
                        app.showGood.push(eval(url));
                    })
                }.bind(this)
            });
        },
        commitPo:function () {
            if (app.tableData.length<1) return alert("请添加采购商品！");
            $.ajax({
                url: '/detail/savePo',
                type: 'POST',
                contentType:"application/x-www-form-urlencoded; charset=utf-8",
                dataType:"json",
                data: {selectedGood : JSON.stringify(app.tableData)},
                success: function (data) {
                    data.createtime = data.createtime.replace("T"," ").replace("+00:00","");
                    window.location.href="../page/poSuccess.html?orderId="+data.orderId+"&id="+data.id+"&totalprice="+data.totalprice+"&price="+data.price
                    +"&totalqty="+data.totalqty+"&createtime="+data.createtime+"&user="+data.user;
                }.bind(this)
            });
        }
    }
})