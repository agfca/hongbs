function changePwd(){
	
	var pass = $("#pass").val().replace(/(^\s*)|(\s*$)/g, "");
	var pass1 = $("#pass1").val().replace(/(^\s*)|(\s*$)/g, "");
	if(pass==""){
		alert("密码不能为空");
		return ;
	}
	if(pass1==""){
		alert("请确认密码");
		return ;
	}
	 
 	if(pass1==pass){//修改
 		$.ajax({
	        url : "/users?method=toPass",
	        type:"post",
	        data:{pass:pass},
	        success : function(data) {
	            if(data=="OK"){
	            	alert("密码修改成功，请重新登录");
	            	window.location.href = "/login";
	            }else{
	            	alert("密码修改失败");
	            }
	        },
	        error : function(data) {
	            alert(data.status);
	         }
	    });
	}else{
		alert("两次密码不一样，请重新填写");
	}
}
