function login(){
    var username = $("#username").val();
    var pwd = $("#pwd").val();
//TODO reg 判断
    $.ajax({
        url : "/login/",
        type : "POST",
        timeout:5000,
        //dataType:'JSON',
        data : {username:username,pwd:pwd},
        success:function (data) {
            if(data!=""){
                layer.msg(data);
            }else{
                window.location.href = "/index";
            }
        },
        error:function(jqXHR, textStatus){
            layer.msg("连接超时，请检查网络");
        },
    });
}
/*
var index0 = "";
function uNameReg(val){
    var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]{2,10}$/;
    if(!reg.test(val)){
        uFlag = false;
        $("#loginButton").attr("disabled",true);
        index0 = "";
        $("#username").attr("style","border-color:#ff8888");
    }else{
        $("#username").attr("style","");
        return 1;
    }
}
var index1 = "";
function pwdReg(val){
    //   `~!@#$%^&*()-=_+[]\{}|;':",./<>?
    var reg = /^([a-zA-Z0-9`~!@#$%^&*()-=_+\[\]\\{}\|;':",.\/<>?]){4,18}$/;
    if(!reg.test(val)){
        pwdFlag = false;
        $("#loginButton").attr("disabled",true);
        index1="";
        $("#pwd").attr("style","border-color:#ff8888");
    }else{
        $("#pwd").attr("style","");
        return 1;
    }
}*/
