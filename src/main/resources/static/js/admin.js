function findOrder(){
    var startTime =  $("#startTime").val();
    var endTime =  $("#endTime").val();
    var oddNum =  $("#oddNum").val();
    var partyA =  $("#partyA").val();
      alert(startTime);
      var formdata=new FormData( );
 
     formdata.append("startTime" , startTime);//获取文件
     formdata.append("oddNum" , oddNum);
     formdata.append("endTime" , endTime);
     formdata.append("partyA" , partyA);

	$('#user7Json').bootstrapTable({
	    url: '/order?method=findOrder&startTime='+startTime+'&endTime='+endTime+'&oddNum='+oddNum+'&partyA='+partyA,
	    method : "get",
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
	        	else if(value=="1")
	        		htmlStr="销售";
	        	else if(value=="2")
	        		htmlStr="工艺师核对销售";
	        	else if(value=="3")
	        		htmlStr="下单";
	        	else if(value=="4")
	        		htmlStr="制版";
	        	else if(value=="5")
	        		htmlStr="工艺师核对制版";
	        	else if(value=="6")
	        		htmlStr="采购";
	        	else if(value=="7")
	        		htmlStr="生产安排";
	        	else if(value=="8")
	        		htmlStr="厂部生产";
	        	else if(value=="9_1")
	        		htmlStr="衬衫部主管";
	        	else if(value=="9_2")
	        		htmlStr="礼服部主管";
	        	else if(value=="9_3")
	        		htmlStr="运动服部主管";
	        	else if(value=="10")
	        		htmlStr="扫尾包装";
	        	else if(value=="11")
	        		htmlStr="后勤部发货";
	        	else if(value=="111")
	        		htmlStr="直营部发货跟踪";
	        	else if(value=="112")
	        		htmlStr="招商部发货跟踪";
	        	else if(value=="113")
	        		htmlStr="外贸部发货跟踪";
	        	else if(value=="12")
	        		htmlStr="售后服务";
	        	else if(value=="131")
	        		htmlStr="直营部收款";
	        	else if(value=="132")
	        		htmlStr="招商部收款";
	        	else if(value=="133")
	        		htmlStr="外贸部收款";
	        	else if(value=="0")
	        		htmlStr="完成收款";
	        	else if(value=="15")
	        		htmlStr="裁床";
	        	 
 	            return htmlStr;
	        }
 	    }, {
	        field: 'remark',
	        title: '备注'
	    }, {
	        field: 'deliveryTime',
	        title: '交货时间'
	    },{
	        field: 'id',
	        title: '下载文档',
	        formatter:function (value, row, index){
	        	  
                 var htmlStr = "<input type='button' onclick=\"getFile('"+row.modelFile+"')\" value='下载生产计划单'/>";
                 if(row.file2Name!="" &&row.file2Name!=null){
                      htmlStr +="&nbsp;<input type='button' value='下载材料计划单' onclick=\"getFile('"+row.file2Name+"')\"/>";
                 }
                 if(row.mainFabricFile!="" &&row.mainFabricFile!=null){
                  	  htmlStr +="&nbsp;<input type='button' value='下载制版图' onclick=\"getFile('"+row.mainFabricFile+"')\"/>";
                 }
                  if(row.auxFabricFile!="" &&row.auxFabricFile!=null){
                      htmlStr += "&nbsp;<input type='button' onclick=\"getFile('"+row.auxFabricFile+"')\" value='下载采购单'>";
             	 }
  	 			  
	             return htmlStr;//获取按钮
	        }
	    }],
	}); 
}




/*获取制版文件*/
function getFile(orderId,filePath) {
    window.open("/word/"+orderId+"/"+filePath);
}

/*页面加载完成   初始化日期  绑定时间更改事件*/
$(function () {
    initDateRangePicker();
    //日期更改 刷新表格
    $("#reportrange").change(function(){
        $('#userJson').bootstrapTable("refresh");
    });
});
/*初始化日期范围选择器*/
function initDateRangePicker(){
    //默认最近30天
    var start = moment().subtract(29, 'days');
    var end = moment();

    $('#reportrange').daterangepicker({
        startDate: start,
        endDate: end,
        ranges: {
            '今天': [moment(), moment()],
            '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            '最近7天': [moment().subtract(6, 'days'), moment()],
            '最近30天': [moment().subtract(29, 'days'), moment()],
            '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
            '下月': [moment().startOf('month'), moment().endOf('month')]
        },
        locale: {
            format: 'YYYY-MM-DD',
            separator: " 至 ",
            applyLabel : '确定',
            cancelLabel : '取消',
            fromLabel : '起始时间',
            toLabel : '结束时间',
            customRangeLabel : '自定义',
            daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
            monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月' ],
            firstDay : 1
        }
    });
    //cb(start, end);
}

//bootstrap table 日期 参数改变
var changeDateParam = function (params) {
    var rangeDate = $("#reportrange").val();
    var dateArr = rangeDate.split(/ 至 /);
    var startDate = "";
    var endDate = "";
    if(dateArr.length==2){
        startDate = dateArr[0];
        endDate = dateArr[1];
    }else{//获取最近30天
        startDate = moment().subtract(29, 'days').format('YYYY-MM-DD');
        endDate = moment().format('YYYY-MM-DD');
    }
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        /* limit: params.limit,   //页面大小
        offset: params.offset,  //页码  */
        startDate:startDate,
        endDate:endDate
    };
    return temp;
}
//bootstrap table 初始化
$('#userJson').bootstrapTable({
    url: '/order/adminFindOrder',
    method : "get",
    search:true,//搜索框
    uniqueId:'id',//主键列
    pagination:true,//启用分页
    paginationLoop:false,//页码循环
    sidePagination:'client',//客户端分页
    pageSize:10,
    pageList:[5,10,15,20],
    queryParams: changeDateParam,
    //sortName:'id',//自定义排序列
    //sortOrder:'desc',//倒序
    showColumns:true,//选择显示columns
    //detailView:true,//详细页面模式
    toolbar:'#toolbar',//指定工具栏
    undefinedText:'-',//undefined时显示字符
    smartDisplay:true,//智能切换视图
    maintainSelected:true,//切换页面记录选中的按钮
    //singleSelect:true,//禁止多选
    clickToSelect:true,//点击行时选中
    //cardView:true,//手机端视图
    //showPaginationSwitch:true,
    //showHeader:true,//显示表头
    //showFooter:true,//显示表尾
    selectItemName:'btSelectItem',//	radio or checkbox 的字段名
    detailView:true,//显示子父表
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
            if(value=="1")
                htmlStr="销售";
            else if(value=="2")
                htmlStr="工艺师核对销售";
            else if(value=="3")
                htmlStr="下单";
            else if(value=="4")
                htmlStr="制版";
            else if(value=="5")
                htmlStr="工艺师核对制版";
            else if(value=="6")
                htmlStr="采购";
            else if(value=="7")
                htmlStr="生产安排";
            else if(value=="8")
                htmlStr="厂部生产";
            else if(value=="9")
                htmlStr="配料";
            else if(value=="9_1")
                htmlStr="厂部-衬衫部主管";
            else if(value=="9_2")
                htmlStr="厂部-礼服部主管";
            else if(value=="9_3")
                htmlStr="厂部-运动服部主管";
            else if(value=="10")
                htmlStr="扫尾包装";
            else if(value=="11")
                htmlStr="后勤送货配车";
            else if(value=="111")
                htmlStr="直营部发货跟踪";
            else if(value=="112")
                htmlStr="招商部发货跟踪";
            else if(value=="113")
                htmlStr="外贸部发货跟踪";
            else if(value=="12")
                htmlStr="售后服务";
            else if(value=="131")
                htmlStr="直营部收款中";
            else if(value=="132")
                htmlStr="招商部收款中";
            else if(value=="133")
                htmlStr="外贸部收款中";
            else if(value=="0")
                htmlStr="完成收款";
            else if(value=="15")
                htmlStr="裁床";
            return htmlStr;
        }
    }, {
        field: 'remark',
        title: '备注'
    }, {
        field: 'deliveryTime',
        title: '交货时间'
    },{
        field: 'id',
        title: '下载文档',
        formatter:function (value, row, index){
            var htmlStr = "<button type='button' class='btn-sm' onclick=\"findOrderMaterial('"+row.id+"')\" >查看材料单</button>";
            if(row.file2Name!="" &&row.file2Name!=null){
                htmlStr +="&nbsp;<input type='button' value='下载材料计划单' onclick=\"getFile('"+row.file2Name+"')\"/>";
            }
            if(row.mainFabricFile!="" &&row.mainFabricFile!=null){
                htmlStr +="&nbsp;<button type='button' class='btn-sm' onclick=\"getFile('"+row.id+"','"+row.mainFabricFile+"')\">下载制版文件</button>";
            }
            if(row.auxFabricFile!="" &&row.auxFabricFile!=null){
                htmlStr += "&nbsp;<input type='button' onclick=\"getFile('"+row.auxFabricFile+"')\" value='下载采购单'>";
            }

            return htmlStr;//获取按钮
        }
    }],
    //注册加载子表的事件。注意下这里的三个参数！
    onExpandRow: function (index, row, $detail) {
        childTable(index, row, $detail);
    },
});
//订单配置表  子表
var childTable = function (index, row, $detail) {
    var orderId = row.id;
    cur_table = $detail.html('<table></table>').find('table');
    $(cur_table).bootstrapTable({
        url: 'order/infos/'+orderId,
        method: 'get',
        //处理数据返回
        responseHandler: function(result) {
            return result;
        },
        columns: [{
            field: 'orderid',
            title: "序号",
            formatter: function (value, row, index) {
                return index+1;
            }
        }, {
            field: 'infoName',
            title: '配置名称',
        }, {
            field: 'fabric',
            title: '面料',
            formatter: function (value, row, index) {
                var tempHtml = "";
                var fabricArr = eval(value);
                if(fabricArr!=null&&fabricArr.length>0){
                    for(var i = 0; i<fabricArr.length; i++){
                        tempHtml += fabricArr[i].name +":"+ fabricArr[i].color+"，&nbsp;";
                    }
                }
                return tempHtml;
            }
        }, {
            field: 'style',
            title: '款式'
        }, {
            field: 'gongyi',
            title: '工艺'
        }, {
            field: 'giveKH',
            title: '客户有无样衣'
        }, {
            field: 'containFabric',
            title: '含量'
        }, {
            field: 'logoStation',
            title: '校徽位置'
        }, {
            field: 'total',
            title: '总数'
        }, {
            field: 'id',
            title: '操作',
            formatter:function (value, row, index) {
                var strTemp = "<button type='button' class='btn btn-info btn-sm' onclick='findOrderInfo("+value+")'>"
                    +"查看详情</button>";
                return strTemp;
            }
        }],
    });
};
/*查看配置详情*/
function findOrderInfo(infoId){
    window.open('/orderDetail/info/'+infoId);
}

/*查看材料单详情*/
function findOrderMaterial(orderId){
    window.open('/orderMaterial/detail/'+orderId);
}