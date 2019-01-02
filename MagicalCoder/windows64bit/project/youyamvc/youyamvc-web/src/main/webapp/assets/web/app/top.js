/**
 * Created by Administrator on 2015/3/17.
 */
$(document).ready(function(){
    $("#loginOut").click(function(){
        $.getJSON("admin/loginOut",{},function(){
            window.location.href="admin"
        })
    })
})