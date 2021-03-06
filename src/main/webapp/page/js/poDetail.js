new Vue({
    el: '#poDetail',
    data: function() {
        return {
            goodTitle: '',
            category: [],
            categoryOption: [],
            tableData: []
        }
    },
    mounted: function() {
        this.getCategory();
    },
    methods: {
        getCategory: function(){
            $.ajax({
                url: 'http://localhost:8080/detail/getCategory',
                type: 'GET',
                success: function (data) {
                    this.categoryOption = data;
                }.bind(this)
            });
        },
        searchPO: function () {
            let category = '';
            this.category.map(function (item) {
                category = category + item + ",";
            });
            let url = "/detail/getPoDetail";
            $.ajax({
                type: 'POST',
                url: url,
                dataType:"json",
                data: {goodTitle: this.goodTitle,category:category},
                success: function (data) {
                    this.tableData = data;
                }.bind(this)
            });
        },
        deletePo: function (id) {
            $.ajax({
                type: 'POST',
                url: "/detail/deletePo",
                data: {id: id},
                success: function (data) {
                    this.searchPO();
                }.bind(this)
            });
        }
    }
})