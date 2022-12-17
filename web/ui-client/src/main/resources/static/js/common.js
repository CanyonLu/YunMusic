document.write('<script src="js/constants.js" type="text/javascript"></script>');
document.write('<script src="lib/jquery-3.4.1/jquery-3.4.1.min.js" type="text/javascript"></script>');
document.write('<script src="lib/layui/layui.js" type="text/javascript"></script>');

var token;
var loginState;

/**
 * 判断用户是否已登录
 * @param loggedinfun 已登录回调
 * @param notloggedinfun 未登录回调
 */
function checkUserState(callbackFunction) {
    token = localStorage.getItem("token");
    $.ajax({
        url : realm_dev + client + "user/checkUserLoginState",
        dataType : "json",
        type : "get",
        success : function(res){
            if(res.state){
                loginState = true;
            }else {
                loginState = false;
                localStorage.removeItem("token");
                layer.msg(res.msg, {icon: 3});
            }
            typeof callbackFunction === "function" && callbackFunction();
        },
        error : function(data){
            layer.msg("系统忙！", {icon: 3});
            localStorage.removeItem("token");
            loginState = false;
            setTimeout(function(){
                location.href = "login";
            },1000);
        },
        beforeSend: function (XMLHttpRequest) {
            if(token){
                XMLHttpRequest.setRequestHeader("token", token);
            }
        },
    });
}