//version 2016-03-23
/**
 * Created by Administrator on 2015/3/17.
 *
 */
var $ = jQuery;
$(document).ready(function(){
    $.getJSON("admin/loginMessage",{},function(data){
        $("#magicalCoderUserName").text(data.result.userName)
    })
    var aid = localStorage.aid
    var a = $("#"+aid);
    var li = a.parent();
    var ul = li.parent();
    var treeviewLi = ul.parent();
    li.addClass("active bg-yellow color-palette")
    ul.addClass("menu-open")
    treeviewLi.addClass("active")

    //buildLeftHtml();
    $("#leftNavigate").find("ul").find('a').bind('click',function(){
        var aid = $(this).attr("id")
        localStorage.aid = aid
        //setCookie("aid",aid, '1 HOUR','','/db/admin');
    })
})
function buildLeftHtml(){
    $.getJSON("admin/user_modules/list",{},function(data){
        if(data.code == 0){
             var template = '<li class="treeview">'+
                             '<a href="#"><i class="fa fa-laptop"></i><span>{moduleCategoryName}</span><i class="fa fa-angle-left pull-right"></i></a>'+
                             '<ul class="treeview-menu">'+
                             '{buildModuleListHtml}'+
                             '</ul>'+
                        '</li>';
            var templateHtml = ''
            var categoryDtoList = data.info;
            for(var i=0;i<categoryDtoList.length;i++){
                var tm = template;
                var categoryDto =  categoryDtoList[i]
                var moduleList =categoryDto.module;
                var category =categoryDto.category;
                category.buildModuleListHtml = buildModuleListHtml(moduleList)
                for(var key in category){
                    var reg = new RegExp('{'+key+'}','g');
                    tm = tm.replace(reg , category[key]);
                }
                templateHtml+=tm;
            }
            $("#leftNavigate").html(templateHtml)
        }
    })
}

function buildModuleListHtml(moduleList){
    var template = '<li><a id="{moduleName}" href="{moduleUrl}"><i class="fa fa-circle-o"></i> {moduleTitle}</a></li>'
    var templateHtml = ''
    for(var i=0;i<moduleList.length;i++){
        var tm = template;
        var module = moduleList[i]
        for(var key in module){
            var reg = new RegExp('{'+key+'}','g');
            tm = tm.replace(reg , module[key]);
        }
        templateHtml+=tm;
    }
    return templateHtml;
}