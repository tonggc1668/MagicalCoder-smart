//version 2016-03-23
//pIdx：当前页
//pSumCnt：总记录数
//pSize：每页数量
function build_bar(pIdx, pSumCnt, pSize, show) {
    pSize = pSize || 20
    show = show || 'pflsa'
    //偏移量
    var excusion = 3
    //
    var begin = 1
    var mod = pSumCnt%pSize
    var addOne = 0

    if (mod > 0) {
        addOne = 1
    }
    //分多少页
    var end_idx = parseInt(pSumCnt / pSize) + addOne
    //要显示的区域[min,max]
    var min = pIdx - excusion
    var max = pIdx + excusion

    if (min < begin + 1) { //最小值小于2
        min = begin + 1
    }
    if (max > end_idx) {
        max = end_idx
    }
    //构造pagingInfo参数
    //      var params=""
    //是否隐藏首页
    //    var isChiefPageHide=pIdx<=begin
    var isChiefPageHide = find(show, "f")
    var chiefPage = cond(isChiefPageHide, "", "<li class=''><a  pg='1' href='javascript:{}'>首页</a></li>")
    //是否隐藏上一页
    var isUpPageHide = pIdx <= begin
    var upPage = cond(isUpPageHide, "", concat(["<li class='prev'><a  pg='"+(pIdx - 1)+"' href='javascript:{}'><上一页</a></li>"]))
    //是否隐藏下一页
    var isDownPageHide = pIdx >= end_idx
    var downPage = cond(isDownPageHide, "", concat(["<li class='next'><a  pg='"+(pIdx + 1)+"' href='javascript:{}'>下一页></a></li>"]))
    //是否隐藏末页
    //    var isend_idxPageHide = pIdx>=end_idx
    var isend_idxPageHide = find(show, "l")
    var end_idxPag = cond(isend_idxPageHide, "", concat(["<li class=''><a  pg='"+end_idx+"' href='javascript:{}'>末页</a></li>"]))
    //
    var mmArr = []
    var idx = 1
    for (var i = min;i<=max;i++) {
        if (i != pIdx) {
            mmArr[idx] = concat(["<li class=''><a href='javascript:{}' pg='"+i+"' >", i, "</a></li>"])
        }else{
            mmArr[idx] = concat(["<li class='active'><a href='javascript:{}' pg='"+i+"' >", i, "</a></li>"])
        }
        idx = idx + 1
    }
    var mmStr = mmArr.join("");//
    //是否加左省略号
    var leftShenglue = ""
    if (min > 2) {//超过2就可以显示省略号了
        leftShenglue = "..."
    }
    //右省略号
    var rigthShenglue = ""
    if (max < end_idx) {
        rigthShenglue = "..."
    }
    if (pSumCnt <= 0 || end_idx <= 1 )
    {
        return ""
    }
    //第一页
    var pIdx1Str = cond(pIdx == 1, "<li class='active'><a href='javascript:{}' pg='1' >1</a></li>", "<li class=''><a href='javascript:{}'  pg='1' >1</a></li>")
    var currPageStr = cond(find(show, "p") , concat(["<li><a>第", pIdx, "页</a></li>"]), "")
    var pageStr = cond(find(show, "s") , concat(["<li><a>共", end_idx, "页</a></li>"]), "")
    var sumPageStr = cond(find(show, "a") , cond(end_idx > 0, concat([pageStr, "<li><a>总记录", pSumCnt, "条</a></li>"]), "<li><a>暂无数据</a></li>"), "")
    return concat([chiefPage, upPage, pIdx1Str, leftShenglue, mmStr, rigthShenglue, downPage, end_idxPag, currPageStr, sumPageStr])
}
function cond(conditions, a, b) {
    if (conditions) {
        return a;
    }
    else{
        return b;
    }
}
function find(str1,f){
    return str1.indexOf(f)!=-1
}
function concat(arr){
    return arr.join("");
}
/*计算当前页第一个的排名*/
function calcRankNum(pageIndex,pageSize){
    return (pageIndex-1)*pageSize;
}
function appendPagingBarHtml(id,pageIndex,pageCount,pageSize){
    var page_bar =  build_bar(pageIndex,pageCount,pageSize);
    $("#"+id).html(page_bar);
    $("#"+id).find('a').click(function(){
        var pg = $(this).attr('pg');
        var li = $(this).parent();
        var liclass = li.attr('class')
        if(typeof liclass != 'undefined'){
            if(liclass.indexOf('active')==-1){
                getItemPage(parseInt(pg));
            }
        }
    })
}