//version 2016-03-23
$(function(){
    getItemPage(1);
    //绑定排序事件
    $("#thead").find("th").click(function(){
        var orderField = $(this).attr("orderField");
        var thClass = $(this).attr("class");
        //改变自己的
        if(typeof thClass != 'undefined'){
            //还原其他的th
            $("#thead").find("th").each(function(){
                var thCls = $(this).attr("class");
                if(typeof thCls != 'undefined'){
                    $(this).attr("class","sorting");
                }
            })
            if( thClass.indexOf('sorting_asc')!=-1){
                $(this).attr("class","sorting_desc")
                //倒序排列
            }else if(thClass.indexOf('sorting_desc')!=-1){
                //升序排列
                $(this).attr("class","sorting_asc")
            }else if(thClass.indexOf('sorting')!=-1){
                //倒序排列
                $(this).attr("class","sorting_desc")
            }
            getItemPage(1);
        }
    });
    //查询
    $("#querySubmit").click(function(){
        getItemPage(1);
    })
    //批量删除
    $("#checkAll").on("ifClicked",function(){
        if($("#checkAll").is(":checked")){
            $(".tdcheckbox").attr("checked",false);
        }else{
            $(".tdcheckbox").attr("checked",true);
        }
    })
});
function notBlank(str){
    return typeof str != 'undefined' && str !=''
}

function buildOrderByObj(){
    var orderBy = {orderBySqlField:'',descAsc:''}
    $("#thead").find("th").each(function(){
        var thCls = $(this).attr("class");
        var orderField = $(this).attr("orderField");
        if(typeof thCls != 'undefined'){
            if(thCls.indexOf("sorting_desc")!=-1){
                if(notBlank(orderField)){
                    orderBy.orderBySqlField = orderField
                    orderBy.descAsc = "desc"
                }
            }else if(thCls.indexOf("sorting_asc")!=-1){
                if(notBlank(orderField)){
                    orderBy.orderBySqlField = orderField
                    orderBy.descAsc = "asc"
                }
            }
        }
    })
    return orderBy;
}
//废弃 不安全
function buildOrderBy(){
    var orderBy = ''
    $("#thead").find("th").each(function(){
        var thCls = $(this).attr("class");
        var orderField = $(this).attr("orderField");
        if(typeof thCls != 'undefined'){
            if(thCls.indexOf("sorting_desc")!=-1){
                if(notBlank(orderField)){
                    orderBy = orderField + " desc "
                    return
                }
            }else if(thCls.indexOf("sorting_asc")!=-1){
                if(notBlank(orderField)){
                    orderBy = orderField + " asc "
                    return
                }
            }
        }
    })
    return orderBy;
}
function deleteItem(lowerEntity,id){
    if(!confirm("确定要删除？")){
        return
    }
    $.getJSON("admin/"+lowerEntity+"/delete/"+id,{date:new Date().getTime()},function(data){
        if(data.code ==0){
            alert("删除成功")
            id = id+'';
            if(id.indexOf("/")!=-1)
            id = id.replace("/","_");
            $("#tr"+id).remove()
        }else{
            alert(data.message)
        }
    })
}

function truncateTable(lowerEntity){
    if(!confirm("确定要清空表全部数据吗？")){
        return
    }
    $.getJSON("admin/"+lowerEntity+"/truncate",{date:new Date().getTime()},function(data){
        if(data.code ==0){
            alert("操作成功")
            window.location.reload();
        }else{
            alert(data.message)
        }
    })
}

function batchDeleteItem(lowerEntity){
    if(!confirm("确定要删除？")){
        return
    }
    var ids = []
    $(".tdcheckbox").each(function(){
        if($(this).is(":checked")){
            var id = $(this).val();
            ids.push(id)
        }
    })
    if(ids.length<=0){
        alert("请选择要删除的记录");
        return;
    }
    $.getJSON("admin/"+lowerEntity+"/batchdelete/"+ids.join(","),{date:new Date().getTime()},function(data){
        if(data.code ==0){
            alert("删除成功")
            for(var i=0;i<ids.length;i++){
                $("#tr"+ids[i]).remove()
            }
        }else{
            alert(data.message)
        }
    })
}

function setRequestParamById(requestParam,id){
    var v = $("#"+id).val();
    if(v!=''){
        requestParam[id]=v;
    }
}
function setRequestParamByName(requestParam,name){
    var type = $("[name='"+name+"']").attr("type")
    var v ;
    if(type && type=='radio'){
        v = $("[name='"+name+"']:checked").val();
    }else{
        v = $("[name='"+name+"']").val();
    }
    if(v && v!=''){
        requestParam[name]=v;
    }

}
//列表页下拉操作
function buildSelect(map,value,lowerEntity,updateField,autoIncreaseField){
    var html = '<select class="form-control" onchange="changeSelect(this,\''+lowerEntity+'\',\''+updateField+'\',\''+autoIncreaseField+'\')">'
    for(var key in map){
        if(key=='') continue;
        var mapValue = map[key];
        var selected = ''
        if(value+'' == key+''){
            selected = 'selected'
        }
        html +='<option '+selected+' value="'+key+'">'+mapValue+'</option>'
    }
    html +='</select>'
    return html
}
function changeSelect(t,lowerEntity,updateField,autoIncreaseField){
    var selectValue = $(t).val();
    var id = $(t).parent().parent().find(".tdcheckbox").val()
    var entityObj = {}
    entityObj[""+autoIncreaseField] = id
    entityObj[""+updateField] = selectValue
    ajaxUpdate(lowerEntity,entityObj);
}
function ajaxUpdate(lowerEntity,entityObj){
    var url ='admin/'+lowerEntity+'/ajax_update?_date='+new Date().getTime();
    $.post(url,entityObj,function(data){
        if(data.code ==0){
            alert("成功")
        }else{
            alert(data.message)
        }
    })
}