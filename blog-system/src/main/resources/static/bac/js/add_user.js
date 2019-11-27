$(function () {
    init_roles();
});


var roles = new Vue({
    el:"#roles",
    data:{
        roles:null
    }
});
var init_roles = function () {
    $.ajax({
        url:"/blog/roles",
        type:"POST",
        success:function (result) {
            var code = result.code;
            if (code == 201){
                var data = result.data;
                roles.roles = data;
            } else {
                alert(result.msg)
            }
        }
    })
};

var updateRoleData = function () {
   var form = $("#form").serialize();
   $.ajax({
       url:"/blog/addUser",
       data:form,
       type:"POST"
   });
};