/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
//构造入参
function buildReqParam(){
    var requestParam = {date:new Date().getTime()}
    setRequestParamById(requestParam,'idFirst')
    setRequestParamById(requestParam,'userNameFirst')
    setRequestParamById(requestParam,'realNameFirst')
    setRequestParamById(requestParam,'emailFirst')
    setRequestParamById(requestParam,'createTimeFirst')
    setRequestParamById(requestParam,'createTimeSecond')
    setRequestParamById(requestParam,'updateTimeFirst')
    setRequestParamById(requestParam,'updateTimeSecond')
    setRequestParamById(requestParam,'superAdminFirst')
    setRequestParamById(requestParam,'roleIdFirst')
    //排序
    var orderBy = buildOrderByObj()
    requestParam.orderBySqlField = orderBy.orderBySqlField;
    requestParam.descAsc = orderBy.descAsc;
    return requestParam;
}

/*pageIndex 当前是第几页 1:第一页*/
function  getItemPage(pageIndex){
    var template =
            '<tr id="tr{id}"><td><input type="checkbox" value="{id}" class="tdcheckbox"></td><td>{rankNum}</td>'+

                    '<td>{id}</td>' +
                    '<td>{userName}</td>' +
                    '<td>{realName}</td>' +
                    '<td>{email}</td>' +
                    '<td>{updateTime}</td>' +
            '<td><a title="点击查看详情" target="_blank" onclick="openUrl(this,\'admin/role/detail_param?id={roleId}\')"> {roleIdForeignShowValue}</a></td>' +
                    '<td>{superAdmin}</td>' +
                    '<td>{createTime}</td>' +
        '<td><a href="admin/admin_user/detail/{id}">编辑</a>' +
        '<a href="javascript:{}" onclick="deleteItem(\'admin_user\',{id})">删除</a></td></trid>';
    var pageSize = 20;//每页多少条记录
    var pageCount = parseInt($("#pageCount").val());//总共多少条记录
    var url ='admin/admin_user/page/'+pageIndex+'/'+pageSize+'/'+pageCount;
    $.ajax({
        url:url,
        type:'get',
        dataType:'json',
        data:buildReqParam(),
        cache:false,
        beforeSend:function(xhr){},
        complete:function(xhr){},
        success:function(data){
            if(data.code!=0){
                alert(data.message);
                return;
            }
            var rankNum = calcRankNum(pageIndex,pageSize);
            $.each(data.info, function(index, val){
                if(index =="pageCount"){
                    $("#pageCount").val(pageCount)
                    pageCount = val;
                }
                if(index == "pageList"){
                    var templateHtml = "";
                    $.each(val, function(index1, value){
                        //排名
                        value.rankNum = ++rankNum;
                        value.CTX = CTX;
                        //自定义输出
                        var superAdminMap = {"":"全部","0":"否","1":"是"}
                        value.superAdmin = buildSelect(superAdminMap,value.superAdmin,'admin_user','superAdmin')
                        //value.superAdmin = superAdminMap[value.superAdmin+""]
                        //赋值替换
                        var tm = template;
                        for(var key in value){
                            var reg = new RegExp('{'+key+'}','g');
                            tm = tm.replace(reg , value[key]);
                        }
                        templateHtml+=tm;
                    });
                    $("#tbody").html(templateHtml);
                }
            });
            //构造分页html
            appendPagingBarHtml("pagination",pageIndex,pageCount,pageSize)
        }
    });
}
//导出json格式文件
function exportJsonFile(){
    var start = $("#start").val()
    var limit = $("#limit").val()
    var url ='admin/admin_user/export/json/'+start+'/'+limit;
    window.location.href=url+'?'+buildUrlParam(buildReqParam());
}