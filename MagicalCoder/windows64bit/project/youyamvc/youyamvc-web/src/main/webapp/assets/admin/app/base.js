//version 2016-03-23
//导入json文件
function importJsonFile(url){
    $.ajaxFileUpload({
        //处理文件上传操作的服务器端地址(可以传参数)
        url:CTX+url+'?date='+new Date().getTime(),
        secureuri:false,                       //是否启用安全提交,默认为false
        fileElementId:"importJsonFile",           //文件选择框的id属性
        dataType:'json',                       //服务器返回的格式,可以是json或xml等
        success:function(data, status){        //服务器响应成功时的处理函数
            if(data.code==0){     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                alert("导入成功")
            }else{
                alert(data.message)
            }
        },
        error:function(data, status, e){ //服务器响应失败时的处理函数
            alert("服务器响应超时")
        }
    });
}

function uploadFile(index,folder){
    $.ajaxFileUpload({
        //处理文件上传操作的服务器端地址(可以传参数)
        url:CTX+'admin/commonfile/fileupload/'+folder+'?date='+new Date().getTime(),
        secureuri:false,                       //是否启用安全提交,默认为false
        fileElementId:index+"File",           //文件选择框的id属性
        dataType:'json',                       //服务器返回的格式,可以是json或xml等
        success:function(data, status){        //服务器响应成功时的处理函数
            if(data.code==0){     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                var uploadSrc = data.url
                $("#"+index+"Review").attr("src", CTX+uploadSrc);
                $("#"+index).val(uploadSrc);
            }else{
                alert(data.message)
            }
        },
        error:function(data, status, e){ //服务器响应失败时的处理函数
            alert("服务器响应超时")
        }
    });
}
function createKindEditor(textName,itemArr){
    var items =
        itemArr ||
        [
        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|','image', 'multiimage',
        'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
        'anchor', 'link', 'unlink', '|', 'about'
        ]
    KindEditor.create('textarea[name="'+textName+'"]', {
        uploadJson : CTX+'admin/commonfile/ckfileupload/business?maxWidth=952',
        allowFileManager : true,
        extraFileUploadParams : {sessionid : 'abcdef'},
        items : items,
        afterCreate : function() {
            var self = this;
            self.sync();
        },afterChange : function() {
            var self = this;
            self.sync();
        }
    });
}
//如果不空 就必须是数字
function mustBeNumOrNull(idArr,alertArr){
    for(var i=0;i<idArr.length;i++){
        var v = $("#"+idArr[i]).val()
        if(v!='' && isNaN(v)){
            alert(alertArr[i]+"必须为数字");
            $("#"+idArr[i]).focus()
            return false
        }
    }
    return true
}
//不能为空
function mustBeNotBlank(idArr,alertArr){
    for(var i=0;i<idArr.length;i++){
        var v = $("#"+idArr[i]).val()
        if(v==''){
            alert("请输入"+alertArr[i]);
            $("#"+idArr[i]).focus()
            return false
        }
    }
    return true
}

function trimInput(idArr){
    for(var i=0;i<idArr.length;i++){
        var jqDom = $("#"+idArr[i]);
        jqDom.val($.trim(jqDom.val()))
    }
}

function openUrl(_t,url){
    $(_t).attr("href",url)
}

function buildUrlParam(reqParam){
    if(reqParam){
        var arr = [];
        for(var key in reqParam){
            if(reqParam[key]){
                arr.push("&")
                arr.push(key)
                arr.push('=')
                arr.push(reqParam[key])
            }
        }
        arr[0]=''
        return arr.join('')
    }
    return ''
}
//============================表单验证===========================
//表单验证
function validateForm(formJquery){
    $(formJquery).find("input").on('keyup blur', function() {
        var _t = $(this);
        validateItem(_t)
    })
    $(formJquery).find("textarea").on('keyup blur', function() {
        var _t = $(this);
        validateItem(_t)
    })
}
//提交验证
function submitForm(formJquery,callback){
    $(formJquery).find("input").each(function(){
        var _t = $(this);
        _t.val($.trim(_t.val()))
        validateItem(_t)
    })
    $(formJquery).find("textarea").on('keyup blur', function() {
        var _t = $(this);
        _t.val($.trim(_t.val()))
        validateItem(_t)
    })
    //提交表单
    var length = $(formJquery).find(".has-error").length
    if(length<=0){
        if(callback){
            callback.call()
        }else{
            $(formJquery).submit();
        }
    }else{
        alert("请仔细检查后再提交")
    }
}
//验证单个元素
function validateItem(_t){
    var type = _t.attr("type");
    if(type && type=='hidden'){
        return;//忽略隐藏表单
    }
    var msg = validate(_t);
    var formGroup = _t.parent().parent();
    if(msg!=null){
        formGroup.addClass("has-error")
        formGroup.find('.validateMsg').text(msg)
    }else{
        formGroup.removeClass("has-error")
        formGroup.find('.validateMsg').text('')
    }
    var v = _t.val()
    //长度
    formGroup.find(".minLength").text(v.length)
}
//验证正则
function validate(_t){
    var title = _t.attr("title")
    var v = _t.val();
    //验证
    var required = _t.attr("required")
    var mathNumber = _t.attr("mathNumber")
    var digits = _t.attr("digits")
    var accept = _t.attr("accept")
    var minValues = _t.attr("minValues")
    var maxValues = _t.attr("maxValues")
    var minLength= _t.attr("minLength")
    var maxLength= _t.attr("maxLength")
    var email= _t.attr("email")
    var url= _t.attr("url")
    var variable= _t.attr("variable")
    var chineseCharacter= _t.attr("chineseCharacter")
    var definedOne= _t.attr("definedOne")
    var definedTwo= _t.attr("definedTwo")
    var definedThree= _t.attr("definedThree")
    if(required=='required'){
        if(v==''){
            return "必填字段";
        }
    }
    if(v!=''){
        if(mathNumber==''){
            if(isNaN(v)){
                return "必须为数值";
            }
        }
        if(digits==''){
            var reg=/^[-+]?\d*$/;
            if(!reg.test(v)){
                return "必须为整数";
            }
        }
        if(maxValues && maxValues!=''){
            if(!isNaN(v) && !isNaN(maxValues)){
                if(parseFloat(v)>parseFloat(maxValues)){
                    return "值不能超过"+maxValues;
                }
            }
        }
        if(minValues && minValues!=''){
            if(!isNaN(v) && !isNaN(minValues)){
                if(parseFloat(v)<parseFloat(minValues)){
                    return "值不能低于"+minValues;
                }
            }
        }

        if(minLength && minLength!=''){
            if(v.length<minLength){
                return "长度不能低于"+minLength;
            }
        }
        if(maxLength && maxLength!=''){
            if(v.length>maxLength){
                return "长度不能超过"+maxLength;
            }
        }
        if(accept && accept!=''){
            if(v.lastIndexOf(accept)==-1){
                return "必须以"+accept+"结尾"
            }
        }
        if(email==''){
            var reg  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            if (!reg.test(v)){
                return "邮箱格式不正确";
            }
        }
        if(url==''){
            var reg = /http:\/\/([\w-]+\.)+[\w-]+(\/[\w-\s.\/?%&=]*)?/;
            if (!reg.test(v)){
                return "Url格式不正确";
            }
        }
        if(variable==''){
            var reg = /\w+/;
            if (!reg.test(v)){
                return "请使用字母数字下划线组成";
            }
        }
        if(chineseCharacter==''){
            var reg = /[\u4e00-\u9fa5]+/;
            if (!reg.test(v)){
                return "请使用中文汉字";
            }
        }
        if(definedOne && definedOne!=''){
            var reg=new RegExp(definedOne,'g');
            if (!reg.test(v)){
                return "不满足"+definedOne;
            }
        }
        if(definedTwo && definedTwo!=''){
            var reg=new RegExp(definedTwo,'g');
            if (!reg.test(v)){
                return "不满足"+definedTwo;
            }
        }
        if(definedThree && definedThree!=''){
            var reg=new RegExp(definedThree,'g');
            if (!reg.test(v)){
                return "不满足"+definedThree;
            }
        }
    }
    return null;
}
//====================================表单验证结束=======================================
//====================================外键下来查询=======================================
function foreignSearch(tableName,inputId,selectValue,foreignJavaField){
    $('#'+inputId+'Search').typeahead({hint: true,highlight: true,minLength: 0},
        {
            name: inputId,
            display: "selectValue",
            source: new Bloodhound({
                datumTokenizer: Bloodhound.tokenizers.obj.whitespace('className'),
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                remote: {
                    url: CTX+'admin/'+tableName+'/type_ahead_search?keyword=%QUERY&selectValue='+selectValue+'&foreignJavaField='+foreignJavaField,
                    wildcard: '%QUERY'
                }
            }),
            limit:20
        });
    $('#'+inputId+'Search').bind('typeahead:select', function(ev, suggestion) {
        $('#'+inputId).val(suggestion.hiddenId)
    });
}