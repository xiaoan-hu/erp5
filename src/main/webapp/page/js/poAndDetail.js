let app = new Vue({
    el:"#poAndDetail",
    data:function(){
        return {
            form:{
                title:'',
                category:'',
                status: '',
                orderId:''
            },
            status: '',
            statusOption: [{
                id : 4,
                name : "未收货"
            },{
                id : 1,
                name : "部分收货，相符"
            },{
                id : 2,
                name : "部分收货，不相符"
            },{
                id : 3,
                name : "已完结"
            }],
            categoryOption:[],
            pos:[],
            dialogVisible:false,
            receiveGoods:[],
            selectedOrderId:'',
            selectedPoId:'',
            selectedDetails: []
        }
    },
    mounted:function(){
        this.getCategory();
    },
    methods:{
        getCategory: function(){
            $.ajax({
                url: '/detail/getCategory',
                type: 'GET',
                success: function (data) {
                    app.categoryOption = data;
                    this.getPoAndDetail();
                }.bind(this)
            });
        },
        getPoAndDetail:function () {
            let url = '/detail/getPoAndDetail';
            let param = [];
            if(app.form.title!=''){
                param.push("title="+app.form.title);
            }
            if (app.form.orderId != ''){
                param.push("orderId="+app.form.orderId);
            }
            if(app.form.category!=''){
                param.push("category="+app.form.category);
            }
            if (app.form.status!='' && app.form.status!=0 ){
                if (app.form.status==4){
                    param.push("status="+0);
                }else{
                    param.push("status="+app.form.status);
                }
            }
            if(param.length>0) url = url+"?";
            for (let i=0;i<param.length-1;i++){
                url=url+param[i].concat("&");
            }
            if(param.length>0) url=url+param[param.length-1];

            $.ajax({
                url:url,
                type:'GET',
                success: function (data) {
                    app.pos = data;
                }.bind(this)
            })
        },
        takeOver:function (row) {
            $.ajax({
                url:"/detail/getDetail",
                type:'POST',
                data:{id:row.id},
                success: function (data) {
                    app.selectedDetails  = data;
                    app.selectedOrderId=row.orderId;
                    app.selectedPoId=row.id;
                }.bind(this)
            })
            app.dialogVisible=true;
        },
        handleSelectionChange:function(val) {
            app.receiveGoods = val;
        },
        checkReceive:function (row) {
            if (app.receiveGoods.length<1) return alert("请选择收货商品!");
            let isZero = false;
            let allOver = '';
            app.receiveGoods.map(function (item,index) {
                if (item.overQty > 0){isZero=true}
                else {app.receiveGoods.splice(index,1)}

                if (item.received<item.qty){
                    allOver=allOver+item.goodsId;
                }
            });
            if (!isZero && allOver != ''){
                return alert("请确认收货数量!");
            }else {
                let status = 0;
                let param = [];
                if(app.receiveGoods.length<app.selectedDetails.length){
                    status = 1;
                }else{
                    status=3;
                }
                app.receiveGoods.map(function (item) {
                    if (item.overQty<item.qty) {
                        status=2;
                    }
                });
                if (allOver == '') status =3;
                param.push({poId:app.selectedPoId});
                param.push({status:status});
                param.push(app.receiveGoods);
                $.ajax({
                    url:"/in/saveIn",
                    type:'POST',
                    dataType:"json",
                    data:{receiveGood:JSON.stringify(param)},
                    success: function (data) {
                        window.location.reload();
                    }.bind(this)
                })
            }
        },
        handleClose:function (done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    app.receiveGoods = [];
                    done();
                })
                .catch(_ => {});
        }
    }
})