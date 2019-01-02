/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
//构造入参
function buildReqParam(){
    var requestParam = {date:new Date().getTime()}
    setRequestParamById(requestParam,'priorityNameFirst')
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

                    '<td>{priorityName}</td>' +
                    '<td>{canInsert}</td>' +
                    '<td>{canDelete}</td>' +
                    '<td>{canUpdate}</td>' +
                    '<td>{canQuery}</td>' +
                    '<td>{canTruncate}</td>' +
                    '<td>{canExport}</td>' +
                    '<td>{canImport}</td>' +
        '<td><a href="admin/priority/detail/{id}">编辑</a>' +
        '<a href="javascript:{}" onclick="deleteItem(\'priority\',{id})">删除</a></td></trid>';
    var pageSize = 20;//每页多少条记录
    var pageCount = parseInt($("#pageCount").val());//总共多少条记录
    var url ='admin/priority/page/'+pageIndex+'/'+pageSize+'/'+pageCount;
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
                        var canInsertMap = {"":"全部","false":"否","true":"是"}
                        value.canInsert = buildSelect(canInsertMap,value.canInsert,'priority','canInsert')
                        //value.canInsert = canInsertMap[value.canInsert+""]
                        var canDeleteMap = {"":"全部","false":"否","true":"是"}
                        value.canDelete = buildSelect(canDeleteMap,value.canDelete,'priority','canDelete')
                        //value.canDelete = canDeleteMap[value.canDelete+""]
                        var canUpdateMap = {"":"全部","false":"否","true":"是"}
                        value.canUpdate = buildSelect(canUpdateMap,value.canUpdate,'priority','canUpdate')
                        //value.canUpdate = canUpdateMap[value.canUpdate+""]
                        var canQueryMap = {"":"全部","false":"否","true":"是"}
                        value.canQuery = buildSelect(canQueryMap,value.canQuery,'priority','canQuery')
                        //value.canQuery = canQueryMap[value.canQuery+""]
                        var canTruncateMap = {"":"全部","false":"否","true":"是"}
                        value.canTruncate = buildSelect(canTruncateMap,value.canTruncate,'priority','canTruncate')
                        //value.canTruncate = canTruncateMap[value.canTruncate+""]
                        var canExportMap = {"":"全部","false":"否","true":"是"}
                        value.canExport = buildSelect(canExportMap,value.canExport,'priority','canExport')
                        //value.canExport = canExportMap[value.canExport+""]
                        var canImportMap = {"":"全部","false":"否","true":"是"}
                        value.canImport = buildSelect(canImportMap,value.canImport,'priority','canImport')
                        //value.canImport = canImportMap[value.canImport+""]
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
    var url ='admin/priority/export/json/'+start+'/'+limit;
    window.location.href=url+'?'+buildUrlParam(buildReqParam());
}