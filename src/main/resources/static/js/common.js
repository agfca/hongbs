//返回订单状态
function getOrderStatu(value) {
    var htmlStr = "";
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
    else if(value=="16")
        htmlStr="复核";
    else if(value=="17")
        htmlStr="总管";
    return htmlStr;
}
//根据订单返回操作按钮
function getOperation(row) {
    var buttonvalue="确认";
    var htmlStr="";
    if(row.orderStatu!="9_1"&&row.orderStatu!="9_2"&&row.orderStatu!="9_3"){
        var statu = parseInt(row.orderStatu);
        if(statu==7||statu==8||statu==9||statu==16||statu==17||statu==15){
            if(row.modelFile!="" &&row.modelFile!=null &&row.modelFile!='null'){
                //htmlStr +="&nbsp;<button type='button' onclick=\"getFile('"+row.modelFile+"')\" >下载下单表</button>";
            }
        }
        if(statu==16||statu==17||statu==15){
            if(row.mainFabricFile!="" &&row.mainFabricFile!=null &&row.mainFabricFile!='null'){
                htmlStr +="&nbsp;<button type='button' class='btn-sm' onclick=\"getFile('"+row.id+"','"+row.mainFabricFile+"')\">下载制版文件</button>";
            }
        }
        if(statu==9||statu==16||statu==17){
            if(row.material!="" &&row.material!=null&&row.material==1){
                htmlStr +="&nbsp;<button type='button' class='btn-sm' onclick=\"findOrderMaterial('"+row.id+"')\" >查看材料单</button>";
            }
        }
        if(statu==9||statu==16||statu==17){
            if(row.auxFabricFile!="" &&row.auxFabricFile!=null){
                //htmlStr +="&nbsp;<input type='button' value='下载采购表' onclick=\"getFile('"+row.auxFabricFile+"')\"/>";
            }
        }
        if(statu==9){
            htmlStr +="&nbsp;<button type='button' class='btn-sm' onclick=\"putOrder("+row.id+",'"+10+"')\">跳过生产</button>";
        }
        if(statu==15){
            htmlStr +="&nbsp;<select id='select"+row.id+"'><option value='9_1'>衬衫</option><option value='9_3'>运动服</option><option value='9_2'>礼服</option></select>";
        }
        if(statu==7){
            buttonvalue="生产确认";
        }else if(statu==11){
            buttonvalue="送货";
        }else if(statu==12){
            buttonvalue="售后完毕";
        }else if(statu==9){
            buttonvalue="配料完毕";
        }else if(statu==10){
            buttonvalue="包装完毕";
        }else if(statu==131||statu==132||statu==133){
            buttonvalue="收款完成";
        }
    }else{
        buttonvalue="完成生产";
    }
    if(buttonvalue=="收款完成"){
        if(level=='111_131'||level=='112_132'||level=='113_133'){
            htmlStr +="&nbsp;收款中";
        }else{
            htmlStr +="&nbsp;<button type='button' class='btn-sm' onclick=\"putOrder("+row.id+",'"+row.orderStatu+"')\">"+buttonvalue+"</button>";
        }
    }else{
        htmlStr +="&nbsp;<button type='button' class='btn-sm' onclick=\"putOrder("+row.id+",'"+row.orderStatu+"')\">"+buttonvalue+"</button>";
    }
    return htmlStr;// 获取按钮
}

/*查看材料单详情*/
function findOrderMaterial(orderId){
    window.open('/orderMaterial/detail/'+orderId);
}