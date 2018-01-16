/**
 * 前台逻辑js
 * */
//配置名称
$('#infoName').editable({
    type: 'text',//输入类型
    pk: 1,//记录的主键
    name:"infoName",
    url: '/orderInfo/edit/'+orderInfo.id,
    value:orderInfo.infoName,
    //emptytext:'请填写配置名称',
    title: '配置名称',
});
//工艺
$('#gongyi').editable({
    type: 'textarea',//输入类型
    pk: 1,//记录的主键
    name:"gongyi",
    url: '/orderInfo/edit/'+orderInfo.id,
    value:orderInfo.gongyi,
    title: '工艺',
});
//是否有样衣
$('#giveKH').editable({
    type: 'text',//输入类型
    pk: 1,//记录的主键
    name:"giveKH",
    url: '/orderInfo/edit/'+orderInfo.id,
    value:orderInfo.giveKH,
    title: '样衣',
});
//校徽位置
$('#logoStation').editable({
    type: 'text',//输入类型
    pk: 1,//记录的主键
    name:"logoStation",
    url: '/orderInfo/edit/'+orderInfo.id,
    value:orderInfo.logoStation,
    title: '校徽位置',
});

var modelName = "";
/*找到前面td 的innerText 文本  */
$(".modelNum").click(function () {
    var nameTemp = $(this).parent().parent().children()[0].innerText;
    modelName = nameTemp.slice(0,nameTemp.indexOf("("));
})
//数量  更改为根据class获取
$('.modelNum').editable({
    type: 'text',//输入类型
    pk: 1,//记录的主键
    //name:modelName,
    url: '/orderInfo/edit/'+orderInfo.id,
    params: function(params) {
        //originally params contain pk, name and value
        params.name = modelName;
        return params;
    },
    title: '数量',
});


/*$('#total').editable({
    type: 'text',//输入类型
    pk: 1,//记录的主键
    name:"total",
    url: '/orderInfo/edit/'+orderInfo.id,
    value:orderInfo.total,
    title: '数量',
});*/
//款式
$('#style').editable({
    type: 'text',//输入类型
    pk: 1,//记录的主键
    name:"style",
    url: '/orderInfo/edit/'+orderInfo.id,
    value:orderInfo.style,
    title: '款式',
});
//含量
$('#containFabric').editable({
    type: 'text',//输入类型
    pk: 1,//记录的主键
    name:"containFabric",
    url: '/orderInfo/edit/'+orderInfo.id,
    value:orderInfo.containFabric,
    title: '含量',
});
//型号数量
$('.modelNum').editable({
    type: 'text',//输入类型
    //emptytext:'请填写工艺',
    title: '型号数量',
    value:'0',
});
//工艺，配置名称不能为空  验证
$("#gongyi,#infoName,#style,#giveKH,#logoStation,#containFabric").editable('option','validate', function(v) {
    if (!v) return '不能为空!';
});
//数量，型号数量，数字验证
$(".modelNum").editable('option','validate', function(v) {
    if (!v) return '不能为空!';
    if (isNaN(v)) {//判断是否为数字
        return '请填写正确的数字!';
    }
    if(v.length>8){
        return "请小于8位数";
    }
});
//更新总数
setInterval(function(){
    var sum = 0;
    var nums = $(".modelNum");
    for(var i = 0; i<nums.length; i++){
        sum += parseInt(nums[i].innerText);
    }
    $("#total").html(sum+"");
},1000);
// var test = $(".modelNum")[0];
// $("body").on("onpropertychange a",function () {
//     alert();
// })
// alert($(".modelNum"))
// $(".modelNum").val().change(function(){
//     alert(1);
// });
// $("body").on("onpropertychange input",function () {
//     alert("input");
// })

//打开添加面料div
function openFabricDiv(){
    $("#fabricDiv").modal("show");
    //$("#fabricTable tbody").append("");
}
var fabric = new Array();//面料
//添加面料和颜色
function addFabric() {
    var fabricName = $("#fabricName").val();
    var fabricColor = $("#fabricColor").val();
    if(fabricName.length<1||fabricColor.length<1){
        layer.msg("请填写面料和颜色");
        return;
    }
    $("#fabricTable tbody").append("<tr><td><span class='fabricName'>"+fabricName+"</span><button onclick=\"delFabric(this);\"" +
        " type='button' class='btn btn-sm' style=\"float: right\">" +
        "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>删除</button></td><td><span class='fabricColor'>"+fabricColor+"</span></td></tr>");

    var fabric = getFabric();//根据class获取面料
    jQuery.ajax({
        url : '/orderInfo/edit/'+orderInfo.id,
        type : "POST",
        data:{name:"fabric",value:JSON.stringify(fabric)},
        success : function(data) {
        },
        error : function(data) {
            layer.msg(data.status);
            //$( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);
        }
    });
}
//移除面料和颜色
function delFabric(btn) {
    $(btn).parent().parent().remove();
    var fabric = getFabric();//根据class获取面料
    if(fabric.length<1){
        layer.msg("请记得添加面料");
    }
    jQuery.ajax({
        url : '/orderInfo/edit/'+orderInfo.id,
        type : "POST",
        data:{name:"fabric",value:JSON.stringify(fabric)},
        success : function(data) {
        },
        error : function(data) {
            layer.msg(data.status);
            //$( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);
        }
    });
}
/*根据class获取面料*/
function getFabric() {
    /*面料获取*/
    var fabric = new Array();//面料
    var spanName = $(".fabricName");
    var spanColor = $(".fabricColor");
    for(var i = 0; i<spanName.length; i++){
        var obj = new Object();
        obj.name = spanName[i].innerText;
        obj.color = spanColor[i].innerText;
        fabric.push(obj);
    }
    return fabric;
}
var fileNum = 1;
//添加款式图
function addStyleFile() {
    fileNum = 20;
    var index = layer.open({
        type: 2,
        content: '/orderFile/uploadPicture',
        area:['800px','400px'],
        title:false,
        shade: [0.8, '#393D49'],
        //max: true,
        end:function () {
            if(styleFile.length>0){

                jQuery.ajax({
                    url : '/orderInfo/edit/'+orderInfo.id,
                    type : "POST",
                    data:{name:"styleImage",value:JSON.stringify(styleFile)},
                    success : function(data) {
                        /*成功添加图片显示*/
                        var tempHtml = "";
                        for(var i = 0 ; i<styleFile.length; i++){
                            tempHtml += "<img width='200px' src='/word/"+orderId+"/"+styleFile[i]+"'>"
                        }
                        $("#styleImage").html(tempHtml);
                    },
                    error : function(data) {
                        layer.msg(data.status);
                        //$( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);
                    }
                });
            }
            //$('#userJson').bootstrapTable("refresh");
        }
    });
}
//修改校徽图
function addLogoFile() {
    fileNum = 1;
    var index = layer.open({
        type: 2,
        content: '/orderFile/uploadPicture',
        area:['800px','400px'],
        title:false,
        shade: [0.8, '#393D49'],
        //max: true,
        end:function () {
            if(logoFile!=null){

                jQuery.ajax({
                    url : '/orderInfo/edit/'+orderInfo.id,
                    type : "POST",
                    data:{name:"logoFile",value:logoFile},
                    success : function(data) {
                        /*成功添加图片显示*/
                        $("#logoImage").html("<img width='200px' src='/word/"+orderId+"/"+logoFile+"'>");
                    },
                    error : function(data) {
                        layer.msg(data.status);
                        //$( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);
                    }
                });
            }
            //$('#userJson').bootstrapTable("refresh");
        }
    });
}