function addPerson(){
	
	var name = $("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var job = $("#jobe").val().replace(/(^\s*)|(\s*$)/g, "");
	var phonenumber = $("#phonenumber").val().replace(/(^\s*)|(\s*$)/g, "");
	var re = /^1\d{10}$/;
	var apartment="";
	if(job=='1'){
		apartment = $("#apartment").val();
 	}else{
 		apartment=$("#jobe").find("option:selected").text();
 	}
	var id=$("#id").val();

	if(job==""){
		alert("登录帐号不能为空，请填写");
		return ;
	}
	if(phonenumber==""){
		alert("手机号不能为空，请填写");
		return ;
	}
	if (!re.test(phonenumber)) {
 	    alert("手机号码格式不正确");
	    return ;
	}
	if(id!=""&&id!=undefined&&id!="null"&&id!=null){
		$.ajax({
            url : "/users/",
            type : "POST",
            data : {id:id,name:name,level:job,code:phonenumber,title:apartment},
            success : function(data) {
                 alert("修改成功");
                 $("#id").val("");
                 $("#jobe").val(0);
                 $("#jobe").attr('readonly', false);
                 $("#jobe").attr('disabled', false);
             	 $("#name").val(""); 
            	 $("#phonenumber").val("");
            	 $("#addbutton").val("确定添加");
                 $("#userJson").bootstrapTable("refresh");
             },
            error : function(data) {
                alert("修改失败，网络问题...");
            }
        });
	}else{
		$.ajax({
	        url : "/users?method=findUserByName&username="+name ,
	        type: "GET",
	        success : function(data) {
	            if(data!=null && data!=""){
	            	alert("帐号已经存在，请重填帐号名称");
	            }else{
	            	$.ajax({
	                    url : "/users/",
	                    type : "POST",
	                    data : {id:id,name:name,level:job,code:phonenumber,title:apartment},
	                    success : function(data) {
	                        alert("添加成功");
	                        $("#jobe").val(0);
 	                    	$("#name").val(""); 
	                   	    $("#phonenumber").val("");
	                   	    $("#apartment_p").hide();
	                        $("#userJson").bootstrapTable("refresh");
	                     },
	                    error : function(data) {
	                        alert("添加失败，网络问题...");
	                    }
	                });
	             }
	        },
	        error : function(data) {
	            alert(data.status);
	        }
	    });
	}
	
}

$('#userJson').bootstrapTable({
    url: '/users?method=findAllUser',
    method : "GET",
    pagination: true,
    sortOrder: "desc", 
    //处理数据返回
    responseHandler: function(result) {
        return result;
    },
    columns: [{
        field: 'name',
        title: '帐号名',
    }, {
        field: 'level',
        title: '角色',
        formatter: function (value, row, index) {
        	var htmlStr;
        	if(value=="9")
        		htmlStr="配料";
        	else if(row.title=="111")
        		htmlStr="直营部_销售";
        	else if(row.title=="112")
        		htmlStr="招商部_销售";
        	else if(row.title=="113")
        		htmlStr="外贸部_销售";
        	else if(value=="2_5")
        		htmlStr="工艺/算料";
        	else if(value=="3")
        		htmlStr="下单";
        	else if(value=="4")
        		htmlStr="制版";
        	else if(value=="6")
        		htmlStr="采购";
        	else if(value=="7_11_12")
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
        		htmlStr="扫尾";
         	else if(value=="111_131")
        		htmlStr="直营部主管";
        	else if(value=="112_132")
        		htmlStr="招商部主管";
        	else if(value=="113_133")
        		htmlStr="外贸部主管";
        	else if(value=="131_132_133")
        		htmlStr="财务";
        	else if(value=="15")
        		htmlStr="裁床";
        	else if(value=="0")
        		htmlStr="管理员";
        	else if(value=="16")
        		htmlStr="复核";
        	else if(value=="17_16")
        		htmlStr="总管";
	            return htmlStr;
        }
    },{
        field: 'code',
        title: '手机号',
    }, {
        field: 'id',
        title: '操作',
        formatter:function (value, row, index){
            var htmlStr =" <input type='button' value='删除帐号' onclick=\"deleteUser("+value+")\"/>" +
            		"&nbsp;<input type='button' value='修改帐号' onclick=\"changeUser("+value+",'"+row.level+"','"+row.name+"','"+row.code+"','"+row.apartment_p+"')\"/>";
            return htmlStr;//获取按钮
        }
    }],
});

function deleteUser(id){
	$.ajax({
        url : "/users/"+id,
        type : "DELETE",
         success : function(data) {
            alert("删除成功");
            $("#userJson").bootstrapTable("refresh");
          },
        error : function(data) {
            alert("添加失败，网络问题...");
         }
    });
	
}


function changeUser(id,jobe,name,phonenumber,apartment_p){
	 $("#id").val(id);
	 $("#jobe").val(jobe);
	 $("#jobe").attr('readonly', true);
	 $("#jobe").attr('disabled', true);
  	 $("#name").val(name); 
 	 $("#phonenumber").val(phonenumber);
 	 $("#addbutton").val("确定修改");
}

function choose_apart(){
	var aprt = $("#jobe").val();
	if(aprt=='1'){
		$("#apartment_p").show();
	}else{
 		$("#apartment_p").hide();
	}
}
