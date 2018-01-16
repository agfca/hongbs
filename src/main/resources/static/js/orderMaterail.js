/**
 * 前台逻辑js
 * */
/*<td><a href="javascript:void(0);" class="materialName"></a></td>  材料
<td><a href="javascript:void(0);" class="supplierName"></a></td>    供应商
<td><a href="javascript:void(0);" class="quantity"></a></td>        数量
<td><a href="javascript:void(0);" class="unit"></a></td>            单位
<td><a href="javascript:void(0);" class="summation"></a></td>       合计米
<td><a href="javascript:void(0);" class="summation2"></a></td>      合计公斤
<td><a href="javascript:void(0);" class="isorder"></a></td>         下单
<td><a href="javascript:void(0);" class="isover"></a></td>          到货
<td><a href="javascript:void(0);" class="remark"></a></td>          备注*/

$(function () {
    initEdit();
});
/*可编辑初始化*/
function initEdit() {

    //材料名称
    $('.materialName').editable({
        type: 'text',//输入类型
        emptytext:'请填写材料名称',
        title: '材料名称',
    });
    //供应商
    $('.supplierName').editable({
        type: 'text',//输入类型
        emptytext:'供应商',
        title: '供应商',
    });
//数量
    $('.quantity').editable({
        type: 'text',//输入类型
        //emptytext:'请填写数量',
        title: '数量',
        //max:1000,
        value:0,
    });
//单位耗料/幅宽
    $('.unit').editable({
        type: 'text',//输入类型
        //emptytext:'单位耗料/幅宽',
        value:0,
        title: '单位耗料/幅宽',
    });
//合计耗料（米）
    $('.summation').editable({
        type: 'text',//输入类型
        //emptytext:'请填写工艺',
        title: '合计耗料（米）',
        value:0
    });
//合计耗料（公斤）
    $('.summation2').editable({
        type: 'text',//输入类型
        //emptytext:'请填写工艺',
        title: '合计耗料（公斤）',
        value:0
    });
//是否下单
    $('.isorder').editable({
        type: 'select',//输入类型
        //emptytext:'请填写工艺',
        title: '是否下单',
        value:'0',
        source: [
            {value: 0, text: '否'},
            {value: 1, text: '是'}
        ],
    });
//是否到货
    $('.isover').editable({
        type: 'select',//输入类型
        //emptytext:'请填写工艺',
        title: '是否到货',
        value:'0',
        source: [
            {value: 0, text: '否'},
            {value: 1, text: '是'}
        ],
    });
//备注
    $('.remark').editable({
        type: 'text',//输入类型
        emptytext:'请填写备注',
        title: '备注',
    });

    //工艺，配置名称不能为空  验证
    $(".materialName").editable('option','validate', function(v) {
        if (!v) return '不能为空!';
    });

    //数量，型号数量，数字验证
    $(".quantity").editable('option','validate', function(v) {
        if (!v) return '不能为空!';
        if (isNaN(v)) {//判断是否为数字
            return '请填写正确的数字!';
        }
    });
}


//数量总数自动计算
var oldSum = 0;
//更新总数
setInterval(function(){
    var sum = 0;
    var nums = $(".modelNum");
    for(var i = 0; i<nums.length; i++){
        sum += parseInt(nums[i].innerText);
    }
    $("#sum").html(sum+"");
    if($("#total").html()==oldSum){
        $("#total").html(sum+"");
    }
    oldSum = sum;
},1000);

//来自哪一个添加button
var tbodyButton = null;
//打开添加材料div
function openAddDiv(value) {
    $("#fabricDiv").modal("show");
    tbodyButton =$(value).parent().parent().parent();
    /*var tbody = $(value).parent().parent().parent();
    tbody.append("<tr>\n" +
        "\t\t\t\t\t\t\t\t<td>1</td>\n" +
        "\t\t\t\t\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"materialName\"></a></td>\n" +
        "\t\t\t\t\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"supplierName\"></a></td>\n" +
        "\t\t\t\t\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"quantity\"></a></td>\n" +
        "\t\t\t\t\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"unit\"></a></td>\n" +
        "\t\t\t\t\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"summation\"></a></td>\n" +
        "\t\t\t\t\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"summation2\"></a></td>\n" +
        "\t\t\t\t\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"isorder\"></a></td>\n" +
        "\t\t\t\t\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"isover\"></a></td>\n" +
        "\t\t\t\t\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"remark\"></a></td>\n" +
        "\t\t\t\t\t\t\t</tr>");*/
}
//添加材料  备注 数量等
function addFabric(value) {
    var materialName = $("#materialName").val();//材料
    var supplierName = $("#supplierName").val();//供应商
    var quantity = $("#quantity").val();//数量
    var remark = $("#remark").val();//备注
    var unit = $("#unit").val();//单位
    var unitType = $("input[name=unitType]:checked").val();//单位种类

    if(!materialName||materialName.length<2){
        layer.msg("请输入正确的材料名称");
        return;
    }
    /*if(!supplierName||supplierName.length<2){
        layer.msg("请输入正确的供应商");
        return;
    }*/
    if(!quantity||isNaN(quantity)||quantity<0){
        layer.msg("请输入正确的数字");
        return;
    }

    var sumTd = "";//根据单位显示不同的td
    if(unitType==0){//标准数字--单位种类
        if(!unit||isNaN(unit)||unit<0){
            layer.msg("请输入正确的单位（数字），或者选择其他类型");
            return;
        }
        var sum = parseFloat(quantity)*parseFloat(unit);
        sumTd = "<td><a href=\"javascript:void(0);\" class=\"unit\">"+unit+"</a></td>" +
            "<td><a href=\"javascript:void(0);\" class=\"summation\">"+sum+"</a></td>";
    }else{//其他  合并单位和合计
        sumTd = "<td><a href=\"javascript:void(0);\" class=\"unit\">"+unit+"</a></td>" +
            "<td><a href=\"javascript:void(0);\" class=\"summation\"></a></td>";
    }
    tbodyButton.append("<tr>" +
        "<td><button onclick=\"delFabric(this);\"" +
        " type='button' class='btn btn-sm' style=\"float: right\">" +
        "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>删除</button></td>" +
        "<td><a href=\"javascript:void(0);\" class=\"materialName\">"+materialName+"</a></td>" +
        "<td><a href=\"javascript:void(0);\" class=\"supplierName\">"+supplierName+"</a></td>" +
        "<td><a href=\"javascript:void(0);\" class=\"quantity\">"+quantity+"</a></td>" +
        sumTd+
        "<td><a href=\"javascript:void(0);\" class=\"summation2\"></a></td>" +
        "<td><a href=\"javascript:void(0);\" class=\"isorder\"></a></td>" +
        "<td><a href=\"javascript:void(0);\" class=\"isover\"></a></td>" +
        "<td><a href=\"javascript:void(0);\" class=\"remark\">"+remark+"</a></td>" +
        "</tr>");

    //材料名称
    initEdit();
    /*$("#fabricTable tbody").append("<tr><td><span class='fabricName'>"+fabricName+"</span><button onclick=\"delFabric(this);\"" +
        " type='button' class='btn btn-sm' style=\"float: right\">" +
        "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>删除</button></td><td><span class='fabricColor'>"+fabricColor+"</span></td></tr>");*/
}
//移除材料td
function delFabric(btn) {
    $(btn).parent().parent().remove();
}

