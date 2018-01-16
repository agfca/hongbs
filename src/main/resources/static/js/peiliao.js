$('#user7Json').bootstrapTable({
    url: '/order/?orderStatu=9',
    method : "GET",
    //处理数据返回
    responseHandler: function(result) {
        return result;
    },
    columns: [{
        field: 'orderName',
        title: '订单名称',
    }, {
        field: 'oddNum',
        title: '单号'
    }, {
        field: 'partyA',
        title: '甲方'
    }, {
        field: 'userName',
        title: '销售'
    }, {
        field: 'fileName',
        title: '下单人'
    }, {
        field: 'orderTime',
        title: '下单时间'
    }, {
        field: 'orderStatu',
        title: '订单状态',
        formatter: function (value, row, index) {
        	var htmlStr;
        	if(value=="9")
        		htmlStr="配料";
            return htmlStr;
        }

    }, {
        field: 'deliveryTime',
        title: '交货时间'
    }, {
        field: 'remark',
        title: '备注'
    }, {
        field: 'id',
        title: '操作',
        formatter:function (value, row, index){
            var htmlStr = "<input type='button' onclick=\"getFile('"+row.modelFile+"')\" value='下载下单表'/>";
            /*if(row.file2Name!="" &&row.file2Name!=null){
                htmlStr +="&nbsp;<input type='button' value='下载材料计划单' onclick=\"getFile('"+row.file2Name+"')\"/>";
            }
            if(row.mainFabricFile!="" &&row.mainFabricFile!=null){
            	htmlStr +="&nbsp;<input type='button' value='下载制版图' onclick=\"getFile('"+row.mainFabricFile+"')\"/>";
            }*/
            if(row.auxFabricFile!="" &&row.auxFabricFile!=null){
            	htmlStr +="&nbsp;<input type='button' value='下载采购表' onclick=\"getFile('"+row.auxFabricFile+"')\"/>";
            }
            htmlStr +="&nbsp;<input type='button' value='todo 跳过生产' onclick=\"putOrder1("+row.id+",'"+10+"')\"/>"
            +"&nbsp;<input type='button' value='配料完成' onclick=\"putOrder('"+row.id+"')\"/>";
            return htmlStr;//获取按钮
        }
    }],
});

function getFile(filePath) {
    alert("TODO  下载文件："+filePath);
    window.location.href = "/word/"+filePath;
}

function putOrder(orderId){
    //var orderStatu = $("#select" + orderId).val();
    $.ajax({
        url : "/order/"+orderId+"",
        type : "PUT",
        data : {orderStatu:'15'},
        success : function(data) {
            alert(data);
            $("#user7Json").bootstrapTable("refresh");
        },
        error : function(data) {
            alert(data.status);
            //$( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);
        }
    });
}

function putOrder1(orderId,s){
    //var orderStatu = $("#select" + orderId).val();
    $.ajax({
        url : "/order/"+orderId+"",
        type : "PUT",
        data : {orderStatu:s},
        success : function(data) {
            alert(data);
            $("#user7Json").bootstrapTable("refresh");
        },
        error : function(data) {
            alert(data.status);
            //$( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);
        }
    });
}