document.write('<script src="js/constants.js" type="text/javascript"></script>');
document.write('<script src="lib/jquery-3.4.1/jquery-3.4.1.min.js" type="text/javascript"></script>');
document.write('<script src="lib/layui/layui.js" type="text/javascript"></script>');

var token;
var loginState;

function checkstate(successCallback) {
    token = localStorage.getItem("token");
    $.ajax({
        url : realm_dev + index + "user/checkUserLoginState",
        dataType : "json",
        type : "get",
        success : function(res){
            if(res.state){
                loginState = true;
                typeof successCallback === "function" && successCallback();
            }else {
                layer.msg(res.msg, {icon: 3});
            }
        },
        error : function(data){
            layer.msg("系统忙！", {icon: 3});
            localStorage.removeItem("token");
            location.href = "login";
        },
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("token", token);
        },
    });
}