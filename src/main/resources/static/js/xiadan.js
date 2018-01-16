//下单人员表格$('#userJson').bootstrapTable({    url: '/order/?orderStatu='+level,    method : "GET",    search:true,//搜索框    uniqueId:'id',//主键列    pagination:true,//启用分页    paginationLoop:false,//页码循环    sidePagination:'client',//客户端分页    pageSize:10,    pageList:[5,10,15,20],    //sortName:'id',//自定义排序列    //sortOrder:'desc',//倒序    showColumns:true,//选择显示columns    //detailView:true,//详细页面模式    toolbar:'#toolbar',//指定工具栏    undefinedText:'-',//undefined时显示字符    smartDisplay:true,//智能切换视图    maintainSelected:true,//切换页面记录选中的按钮    //singleSelect:true,//禁止多选    clickToSelect:true,//点击行时选中    //cardView:true,//手机端视图    //showPaginationSwitch:true,    //showHeader:true,//显示表头    //showFooter:true,//显示表尾    selectItemName:'btSelectItem',//	radio or checkbox 的字段名    detailView:true,//显示子父表    //处理数据返回    responseHandler: function(result) {        return result;    },    columns: [{        field: 'orderName',        title: '订单名称',    }, {        field: 'oddNum',        title: '单号',    }, {        field: 'partyA',        title: '甲方'    }, {        field: 'userName',        title: '销售'    }, {        field: 'deliveryTime',        title: '交货时间'    }, {        field: 'remark',        title: '备注'    }, {        field: 'id',        title: '操作',        formatter:function (value, row, index){            return getButton(row);        }    }],    //注册加载子表的事件。注意下这里的三个参数！    onExpandRow: function (index, row, $detail) {        childTable(index, row, $detail);    },});//订单配置表  子表var childTable = function (index, row, $detail) {    var orderId = row.id;    cur_table = $detail.html('<table></table>').find('table');    $(cur_table).bootstrapTable({        url: 'order/infos/'+orderId,        method: 'get',        //处理数据返回        responseHandler: function(result) {            return result;        },        columns: [{            field: 'orderid',            title: "序号",            formatter: function (value, row, index) {                return index+1;            }        }, {            field: 'infoName',            title: '配置名称',        }, {            field: 'fabric',            title: '面料',            formatter: function (value, row, index) {                var tempHtml = "";                var fabricArr = eval(value);                if(fabricArr!=null&&fabricArr.length>0){                    for(var i = 0; i<fabricArr.length; i++){                        tempHtml += fabricArr[i].name +":"+ fabricArr[i].color+"，&nbsp;";                    }                }                return tempHtml;            }        }, {            field: 'style',            title: '款式'        }, {            field: 'gongyi',            title: '工艺'        }, {            field: 'giveKH',            title: '客户有无样衣'        }, {            field: 'containFabric',            title: '含量'        }, {            field: 'logoStation',            title: '校徽位置'        }, {            field: 'total',            title: '总数'        }, {            field: 'id',            title: '操作',            formatter:function (value, row, index) {                var strTemp = "<button type='button' class='btn btn-info btn-sm' onclick='findOrderInfo("+value+")'>"                    +"查看详情</button>";                strTemp += "&nbsp;<button type='button' class='btn btn-info btn-sm' onclick=\"editInfo('"+row.id+"')\">修改配置</button>";//修改配置                return strTemp;            }        }],    });};/*修改配置页面*/function editInfo(infoId){    window.open('/orderInfo/edit/'+infoId);}/*弹出div*///添加订单编号  弹出orderdivfunction openAddOrderDiv(orderName,partyA,deliveryTime,remark,id,oddNum) {    $("#orderName").val(orderName);    $("#partyA").val(partyA);    $("#deliveryTime").val(deliveryTime);    $("#remark").val(remark);    $("#orderId").val(id);    $("#orderNum").val(oddNum);    $("#addOrderDiv").modal("show");}/*修改订单  添加单号 （判断不重复） 修改交货时间*/function addOrderNum() {    var orderId = $("#orderId").val();    var orderNum = $("#orderNum").val().replace(/(^\s*)|(\s*$)/g, "");//单号    var remark = $("#remark").val().replace(/(^\s*)|(\s*$)/g, "");//备注    var deliveryTime = $("#deliveryTime").val().replace(/(^\s*)|(\s*$)/g, "");//交货时间    if(!!orderNum&&orderNum.length>3){    }else{        layer.msg("请输入正确的单号");        return;    }    layer.msg('确认修改订单？', {        time: 0 //不自动关闭        ,shade:0.01        ,btn: ['确认', '取消']        ,yes: function(index){            layer.close(index);            jQuery.ajax({                url : "/order/"+orderNum ,                type: "GET",                success : function(data) {                    if(data!=null && data!=""){                        layer.msg("订单编号已存在，请修改单号");                        return;                    }else{//添加单号  更改交货时间  备注                        jQuery.ajax({                            url : "order/addOrderNum",                            type : "POST",                            data:{orderId:orderId,orderNum:orderNum,remark:remark,deliveryTime:deliveryTime},                            beforeSend:function(){                                index = layer.msg('修改中...', {icon: 16,time: 20000,shade:0.01});                            },                            success : function(data) {                                layer.close(index);                                $("#addOrderDiv").modal("hide");                                layer.msg(data);                                $("#userJson").bootstrapTable('refresh');                            },                            error : function(data) {                                layer.msg(data.status);                                //$( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);                            }                        });                    }                },                error : function(data) {                    alert(data.status);                }            });        }    });}/*查看配置详情*/function findOrderInfo(infoId){    window.open('/orderDetail/info/'+infoId);}/*操作按钮*/function getButton(row){    var tempNum = "";    var tempRemark = "";    if(!!row.oddNum){//订单单号不为null        tempNum = row.oddNum;    }    if(!!row.oddNum){//备注不为null        tempRemark = row.remark;    }    var htmlStr = "<button type='button' class='btn-sm' onclick=\"openAddOrderDiv('"        +row.orderName+"','"+row.partyA+"','"+row.deliveryTime+"','"+tempRemark+"','"+row.id+"','"+tempNum+"')\">修改订单</button>";    if(row.oddNum!=null&&row.oddNum!=""){        htmlStr += "&nbsp;<button type='button' class='btn-sm' onclick=\"putOrder('"+row.id+"')\">提交</button>";    }    return htmlStr;//获取按钮}/*日期插件*/$('#deliveryTime').daterangepicker({    singleDatePicker: true,    minDate:new Date(),    locale: {        format: 'YYYY-MM-DD',        daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],        monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',            '七月', '八月', '九月', '十月', '十一月', '十二月' ],    }});/*提交订单 修改订单状态为4*/function putOrder(orderId){    layer.msg('确认提交订单？', {        time: 0 //不自动关闭        ,shade:0.01        ,btn: ['确认', '取消']        ,yes: function(index){            layer.close(index);            $.ajax({                url : "/order/"+orderId+"",                type : "PUT",                data : {orderStatu:"4"},                beforeSend:function(){                    index = layer.msg('提交中...', {icon: 16,time: 20000,shade:0.01});                },                success : function(data) {                    layer.close(index);                    layer.msg(data);                    $("#userJson").bootstrapTable("refresh");                },                error : function(data) {                    alert(data.status);                    //$( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);                }            });        }    });}//TODO  没用function addfile(){ 	var size=$("#filesize").val(); 	size=parseInt(size)+1; 	var html='<p id="file_p'+size+'"><span class="span-z"><label>文件'+size+':</label><input name="orderFile" type="file" multiple="multiple"></span></p>' 	$("#filesize").val(size); 	if(size==1){		$("#file_p").after(html);	}else{		size=parseInt(size)-1;		$("#file_p"+size).after(html);	}}//TODO  没用function uploadFile(){    $.ajax({        url : "/orderFile/",        type : "POST",        data : $( "#orderFile").serialize(),        success : function(data) {            $( '#serverResponse').html(data);        },        error : function(data) {            $( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);        }    });}//TODO  没用function doUpload() {    //var formdata=new FormData($("#form1").[0]);//获取文件法一    var formdata=new FormData( );    formdata.append("file" , $("#orderFile")[0].files[0]);//获取文件法二    //var formData = new FormData($( "#uploadForm" )[1]);    $.ajax({        url: "/orderFile/" ,        type: "POST",        data: formdata,        async: false,        cache: false,        processData: false,  // 告诉jQuery不要去处理发送的数据        contentType: false,  // 告诉jQuery不要去设置Content-Type请求头        success: function (returndata) {            alert(returndata);        },        error: function (returndata) {            alert(returndata);        }    });}//TODO  没用  添加单号function postOrder(){    var oddNum = $("#oddNum").val().replace(/(^\s*)|(\s*$)/g, "");    var orderFile = $("#orderFile").val().replace(/(^\s*)|(\s*$)/g, "");    if(oddNum==""){        alert("单号不能为空，请填写");        return ;    }    if(orderFile==""){        alert("请上传文件");        return ;    }    $.ajax({        url : "/order/"+oddNum ,        type: "GET",        success : function(data) {            if(data!=null && data!=""){                alert("订单编号已存在，请修改单号");            }else{                var orderId = $("#orderId").val();                $('#ff').attr('action','/order/'+orderId);                $('#ff').submit();            }        },        error : function(data) {            alert(data.status);        }    });}//TODO  没用  修改订单function changeOrder(orderName,partyA,deliveryTime,remark,orderId){    $("#orderName").val(orderName);    $("#partyA").val(partyA);    $("#deliveryTime").val(deliveryTime);    $("#remark").val(remark);    $("#orderId").val(orderId);}  