document.write('<script src="js/constants.js" type="text/javascript"></script>');
document.write('<script src="js/renderer.js" type="text/javascript"></script>');
document.write('<script src="lib/jquery-3.4.1/jquery-3.4.1.min.js" type="text/javascript"></script>');
document.write('<script src="lib/layui/layui.js" type="text/javascript"></script>');

var token;
var loginState;
var userInfo;

/**
 * 判断用户是否已登录，结果写入 loginState / userInfo，然后执行回调
 * @param callbackFunction 回调（无论登录与否都会执行）
 */
function checkUserState(callbackFunction) {
    token = localStorage.getItem("token");
    $.ajax({
        url: API + "user/checkUserLoginState",
        dataType: "json",
        type: "get",
        success: function (res) {
            if (res.state) {
                loginState = true;
                userInfo = res;
            } else {
                loginState = false;
                // 匿名访问不提示，只有本地残留失效 token 时才提醒
                if (token) {
                    layui.layer.msg(res.msg, {icon: 3});
                }
                localStorage.removeItem("token");
            }
            if (typeof callbackFunction === "function") {
                callbackFunction();
            }
        },
        error: function () {
            layui.layer.msg("系统忙！", {icon: 3});
            localStorage.removeItem("token");
            loginState = false;
            if (typeof callbackFunction === "function") {
                callbackFunction();
            }
        },
        beforeSend: function (XMLHttpRequest) {
            if (token) {
                XMLHttpRequest.setRequestHeader("token", token);
            }
        }
    });
}
