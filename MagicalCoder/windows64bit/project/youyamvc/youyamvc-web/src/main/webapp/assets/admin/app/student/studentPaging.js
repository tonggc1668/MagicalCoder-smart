/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
//构造入参
function buildReqParam(){
    var requestParam = {date:new Date().getTime()}
    setRequestParamById(requestParam,'nameFirst')
    setRequestParamById(requestParam,'classIdFirst')
    setRequestParamById(requestParam,'sexFirst')
    setRequestParamById(requestParam,'adminUserIdFirst')
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

                    '<td>{name}</td>' +
            '<td><a title="点击查看详情" target="_blank" onclick="openUrl(this,\'admin/classes/detail_param?id={classId}\')"> {classIdForeignShowValue}</a></td>' +
                    '<td>{sex}</td>' +
            '<td><a title="点击查看详情" target="_blank" onclick="openUrl(this,\'admin/admin_user/detail_param?id={adminUserId}\')"> {adminUserIdForeignShowValue}</a></td>' +
        '<td><a href="admin/student/detail/{id}">编辑</a>' +
        '<a href="javascript:{}" onclick="deleteItem(\'student\',{id})">删除</a></td></tr>';
    var pageSize = 20;//每页多少条记录
    var pageCount = parseInt($("#pageCount").val());//总共多少条记录
    var url ='admin/student/page/'+pageIndex+'/'+pageSize+'/'+pageCount;
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
                        var sexMap = {"":"全部","0":"类型一","1":"类型二"}
                        value.sex = buildSelect(sexMap,value.sex,'student','sex','id')
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
    var url ='admin/student/export/json/'+start+'/'+limit;
    window.location.href=url+'?'+buildUrlParam(buildReqParam());
}