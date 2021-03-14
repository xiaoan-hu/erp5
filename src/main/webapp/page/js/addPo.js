var app = new Vue({
    el: '#addPo',
    data: function() {
        return {
            currentPage:1,      // 当前页码
            pagesize:10,      // 每页10条数据
            total:0,        // 商品查询数据的总条数
            goodTitle: '',  // 想查询的商品名称
            category: null,  // 选择的渠道
            categoryOption: [], //渠道选项
            tableData: [],  // 已选择商品并设置数量和采购价
            filterData: [], // 选择商品过滤对象
            showGood: [], // 经筛选的商品信息
            selectedGood: [],  // 选择的商品信息
            dialogVisible: false, // 对话框展示
            goodsInfoVo: [], // 所有的商品信息
            selectGoodsId: ''
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
        // 取到所有商品
        getAllGoods:function(){
            $.ajax({
                url: '/mvc/goodInfoVo',
                type: 'GET',
                success: function (data) {
                    app.goodsInfoVo = data;
                    if(app.category == null){
                        app.showGood = data.all.all;
                        app.total = app.showGood.length;
                    }
                }.bind(this)
            });
        },
        // 删除 row
        deleteRow: function (index,row) {
            app.tableData.splice(index, 1);
            app.selectGoodsId = app.selectGoodsId.replace("_id"+row.id,"");
        },
        // 添加行数
        addDemo() {
            let d = {
                title: '',
                qty: 0,
                price: 0,
                totalPrice: 0,
            };
            app.tableData.push(d);
        },
        // dialog开关
        openDialog:function(){
            if (app.dialogVisible){
                app.dialogVisible=false
            }else {
                app.dialogVisible=true;
            }
        },
        // 拼接图片路径
        getImgUrl(picture){
            return "http://cbu01.alicdn.com/"+picture;
        },
        // 确定选中商品
        selectGood:function () {
            app.filterData.map(function (item) {
                if(app.tableData[app.tableData.length-1].title==''){
                    app.tableData.pop();
                }
                app.tableData.push(item);
                app.toggleSelection();
            });
            app.dialogVisible=false;
            app.filterData = [];
            // 清除筛选和页码
            app.closeGoodsDialog();
        },
        // 关闭选择商品
        closeGoodsDialog:function(){
            app.dialogVisible=false;
            app.showGood = app.goodsInfoVo.all.all;
            app.currentPage = 1;
            app.total = app.showGood.length;
        },
        //
        handleSelectionChange:function (val) {
            app.multipleSelection = val;
        },
        // 检测当前页码
        handleCurrentChange:function(val){
            app.currentPage = val;
        },
        // 取消所有选择
        toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
            } else {
                this.$refs.multipleTable.clearSelection();
            }
        },
        //选中所有产品
        selectAll:function(rows){
            let titles = '';
            let hadGoods = [];
            let num = 1;
            rows.map(function (item,index) {
                if (app.selectGoodsId.indexOf("_id"+item.id) == -1){
                    app.selectGoodsId = app.selectGoodsId + "_id"+item.id;
                    app.filterData.push(item);
                }else{
                    hadGoods.push(item);
                    titles =titles+"\n"+num+"、"+item.title;
                    num++;
                }
          })
            if (hadGoods.length>0) {
                alert("商品：" + titles + "\n已选择！")
                hadGoods.map(function (item) {
                    app.$refs.multipleTable.toggleRowSelection(item);
                })
            }
        },
        // 点击CheckBox判断是选中或者取消
        onTableSelect:function(rows, row){
            let selected = rows.length && rows.indexOf(row) !== -1
            if (selected){
                if (app.selectGoodsId.indexOf("_id"+row.id)!=-1){
                    this.$refs.multipleTable.toggleRowSelection(row);
                    alert("商品已选择");
                }else{
                    app.filterData.push(row);
                    app.selectGoodsId = app.selectGoodsId + "_id"+row.id;
                }
            }else if (selected==false||selected==0){
                app.selectGoodsId = app.selectGoodsId.replace("_id"+row.id,"");
                app.filterData.map(function (item,index) {
                    if (item.id = row.id) app.filterData.splice(index,1);
                })
            }
        },
        // 进行数据查询
        refreshGoods:function () {
            app.showGood=[];
            let categorys='';
            if (app.category.length>0){
                app.category.map(function (item,index) {
                    categorys=categorys+item+",";
                })
            }
            $.ajax({
                url: '/mvc/refreshGoods',
                type: 'POST',
                data:{category:categorys,title:app.goodTitle},
                contentType:"application/x-www-form-urlencoded; charset=utf-8",
                dataType:"json",
                success: function (data) {
                    app.currentPage = 1;
                    data.map(function (item) {
                        let url = "app.goodsInfoVo.gc".concat(item.categoryid,".t",item.id,"[0]");
                        app.showGood.push(eval(url));
                        app.total = app.showGood.length;
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