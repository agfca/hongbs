/**
 * 前台逻辑js
 * */
//配置名称
$('#infoName').editable({
    type: 'text',//输入类型
    //pk: 1,//记录的主键
    //url: '/post',//编辑之后提交
    //value:'初始值',
    emptytext:'请填写配置名称',
    title: '配置名称',
    //mode:'inline',//popup   inline
});
//是否有样衣
$('#giveKH').editable({
    type: 'text',//输入类型
    //pk: 1,//记录的主键
    //url: '/post',//编辑之后提交
    value:'没有',
    //emptytext:'请填写配置名称',
    title: '样衣',
    //mode:'inline',//popup   inline
});
//校徽位置
$('#logoStation').editable({
    type: 'text',//输入类型
    //pk: 1,//记录的主键
    //url: '/post',//编辑之后提交
    value:'左胸缝校微',
    title: '校徽位置',
});
//数量
/*$('#total').editable({
    type: 'text',//输入类型
    emptytext:'请填写数量',
    title: '数量',
    value:'0',
});*/
//工艺
$('#gongyi').editable({
    type: 'textarea',//输入类型
    emptytext:'请填写工艺',
    title: '工艺',
});
//款式
$('#style').editable({
    type: 'text',//输入类型
    //emptytext:'请填写工艺',
    title: '款式',
    value:"照样衣"
});
//含量
$('#containFabric').editable({
    type: 'text',//输入类型
    //emptytext:'请填写工艺',
    title: '含量',
    value:"95%聚酯纤维 5%氨纶"
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
}
//移除面料和颜色
function delFabric(btn) {
    $(btn).parent().parent().remove();
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
                var tempHtml = "";
                for(var i = 0 ; i<styleFile.length; i++){
                    tempHtml += "<img width='200px' src='/word/"+orderId+"/"+styleFile[i]+"'>"
                }
                $("#styleImage").html(tempHtml);
            }
            //$('#userJson').bootstrapTable("refresh");
        }
    });
}
//添加校徽图
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
                $("#logoImage").html("<img width='200px' src='/word/"+orderId+"/"+logoFile+"'>");
            }
            //$('#userJson').bootstrapTable("refresh");
        }
    });
}