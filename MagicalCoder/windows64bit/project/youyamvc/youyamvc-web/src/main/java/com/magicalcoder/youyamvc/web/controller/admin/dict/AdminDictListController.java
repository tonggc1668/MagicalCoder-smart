package com.magicalcoder.youyamvc.web.controller.admin.dict;
import com.magicalcoder.youyamvc.app.dict.service.DictService;
import com.magicalcoder.youyamvc.app.dict.service.DictProxyService;
import com.magicalcoder.youyamvc.app.dict.constant.DictConstant;
import com.magicalcoder.youyamvc.app.dict.util.DictUtil;
import com.magicalcoder.youyamvc.app.dict.dto.DictDto;
import com.magicalcoder.youyamvc.app.model.Dict;
import com.magicalcoder.youyamvc.app.model.Priority;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.core.common.dto.InputSelectShowDto;
import com.magicalcoder.youyamvc.app.adminuser.util.AdminUserContextUtil;
import java.io.File;
import java.io.IOException;
import com.alibaba.fastjson.JSON;
import com.magicalcoder.youyamvc.core.common.file.FileHelper;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.common.utils.serialize.SerializerFastJsonUtil;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.*;
import java.math.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@RequestMapping({"/admin/dict"})
@Controller
public class AdminDictListController extends AdminLoginController
{
    private final String moduleName = "dict";

    @Resource
    private DictService dictService;
    @Resource
    private DictProxyService dictProxyService;
    //列表页
    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        return "admin/dict/dictList";
    }

    //分页查询
    @RequestMapping(value={"page/{pageIndex}/{pageSize}/{pageCount}"}, method={RequestMethod.GET})
    public void page(@PathVariable Integer pageIndex,@PathVariable Integer pageSize, @PathVariable Integer pageCount,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
        @RequestParam(required = false,value ="dictKeyFirst")                        String dictKeyFirst ,
        @RequestParam(required = false,value ="dictValueFirst")                        String dictValueFirst ,
        @RequestParam(required = false,value ="dictTypeFirst")                        Integer dictTypeFirst ,
        @RequestParam(required = false,value ="dictDescFirst")                        String dictDescFirst ,
        @RequestParam(required = false,value ="dictOrderFirst")                        Integer dictOrderFirst ,
        HttpServletRequest request, HttpServletResponse response)
    {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",id,dict_key,dict_value,dict_type,dict_desc,dict_order,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        pageSize = Math.min(DictConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "dictKeyFirst",dictKeyFirst ,
                "dictValueFirst",dictValueFirst ,
                "dictTypeFirst",dictTypeFirst ,
                "dictDescFirst",dictDescFirst ,
                "dictOrderFirst",dictOrderFirst ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.dictService.getDictList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.dictService.getDictListCount(query);
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", dealForeignField(pageList));
        ajaxData.put("pageCount", pageCount);
        toWebSuccessJson(response, ajaxData);
    }

//处理外键显示字段 而不是难读懂的关联字段
    private List<Map<String,Object>> dealForeignField(List<Dict> pageList){
        List<Map<String,Object>> newPageList = new ArrayList<Map<String, Object>>(pageList.size());
        if(ListUtils.isNotBlank(pageList)){
        //step1 转化map快速索引

            //使用索引替换外键展示值
            for(Dict item:pageList){
                String json = JSON.toJSONString(item);
                Map<String,Object> obj = (Map<String,Object>)JSON.parse(json);
                newPageList.add(obj);
            }
        }
        return newPageList;
    }
    //根据主键到编辑
    @RequestMapping({"/refresh/{id}"})
    public void refresh(@PathVariable Long id, HttpServletResponse response) {
        Dict entity = this.dictService.getDict(id);
        DictUtil.refreshCache(entity.getDictKey());
        toWebSuccessJson(response,"刷新成功",false);
    }
    //根据自定义查询条件到编辑
    @RequestMapping({"/detail_param"})
    public String detailId(HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        Map<String,Object> reqMap = ProjectUtil.getParams(request);
        Dict entity = this.dictService.selectOneDictWillThrowException(reqMap);
        model.addAttribute("dict", entity);
        foreignModel(entity,model);
        return "admin/dict/dictDetail";
    }
    //新增
    @RequestMapping({"/detail"})
    public String detail( HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
            return "admin/priorityAlert";
        }
        model.addAttribute("dict", new Dict());
        return "admin/dict/dictDetail";
    }

    private void foreignModel(Dict entity,ModelMap model){
        Map<String,Object> map = null;
    }

    //根据主键到编辑
    @RequestMapping({"/detail/{id}"})
        public String detailId(@PathVariable Long id,
        HttpServletRequest request,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanQuery()){
            return "admin/priorityAlert";
        }
        Dict entity = this.dictService.getDict(id);
        model.addAttribute("dict", entity);
        foreignModel(entity,model);
        return "admin/dict/dictDetail";
    }

    //ajax更新状态
    @RequestMapping(value="ajax_update", method={RequestMethod.POST})
    public void ajaxUpdate(@ModelAttribute Dict dict,
        HttpServletRequest request,HttpServletResponse response,ModelMap model) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        try{
            this.dictService.updateDictWithoutNull(dict);
            toWebSuccessJson(response);
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            toWebFailureJson(response, exceptionMsg);
        }
    }
    //保存
    @RequestMapping(value="save", method={RequestMethod.POST})
    public String save(@ModelAttribute Dict dict,
        HttpServletRequest request,ModelMap model) {
        try{
            model.addAttribute("dict",dict);
            foreignModel(dict,model);
            Date now = new Date();
            if (dict.getId() == null) {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanInsert()){
                    return "admin/priorityAlert";
                }
                this.dictService.insertDict(dict);
            } else {
                Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
                if(adminQueryPriority==null || !adminQueryPriority.getCanUpdate()){
                    return "admin/priorityAlert";
                }
                Dict entity = this.dictService.getDict(dict.getId());
                Copyer.copy(dict, entity);
                this.dictService.updateDict(entity);
            }
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            return "admin/dict/dictDetail";
        }
        return "redirect:/admin/dict/list";
    }

    //删除
    @RequestMapping({"/delete/{id}"})
    public void delete(@PathVariable Long id,
        HttpServletRequest request, HttpServletResponse response) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanDelete()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        this.dictService.deleteDict(id);
        toWebSuccessJson(response);
    }
    //批量删除
    @RequestMapping({"/batchdelete/{ids}"})
    public void batchDelete(@PathVariable String ids, HttpServletRequest request,HttpServletResponse response) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanDelete()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        if(StringUtils.isNotBlank(ids)){
            String[] idArr = ids.split(",");
            List<Long> list = new ArrayList<Long>();
            for(String id:idArr){
                if(StringUtils.isNotBlank(id)){
                    list.add(Long.valueOf(id));
                }
            }
            if(ListUtils.isNotBlank(list)){
                this.dictService.batchDeleteDict(list);
                toWebSuccessJson(response);
            }
        }else{
            toWebFailureJson(response, "没有要删除的主键");
        }
    }
    //清空表结构
    @RequestMapping(value = "truncate")
    public void truncate(HttpServletRequest request,HttpServletResponse response) {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanTruncate()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        this.dictService.truncateDict();
        toWebSuccessJson(response);
    }
    //json文件导入数据
    @RequestMapping(value = "import/json")
    public void importJson(@RequestParam MultipartFile myfiles,HttpServletRequest request,HttpServletResponse response) throws IOException {
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanImport()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        if(myfiles.getOriginalFilename().endsWith(".txt") ||
            myfiles.getOriginalFilename().endsWith(".js")||
            myfiles.getOriginalFilename().endsWith(".json")){
            String fileContent = FileHelper.fastReadFileUTF8(myfiles.getInputStream());
            List<Dict> list = SerializerFastJsonUtil.get().readJsonList(fileContent,Dict.class);
            if(ListUtils.isNotBlank(list)){
                this.dictService.transactionImportJsonList(list);
            }
            toWebSuccessJson(response);
        }else {
            toWebFailureJson(response, "不支持的文件后缀");
        }
    }
    //json文件导出
    @RequestMapping(value = "export/json/{start}/{limit}",method = RequestMethod.GET)
    public void exportJson(@PathVariable Integer start,@PathVariable Integer limit,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
        @RequestParam(required = false,value ="dictKeyFirst")                        String dictKeyFirst ,
        @RequestParam(required = false,value ="dictValueFirst")                        String dictValueFirst ,
        @RequestParam(required = false,value ="dictTypeFirst")                        Integer dictTypeFirst ,
        @RequestParam(required = false,value ="dictDescFirst")                        String dictDescFirst ,
        @RequestParam(required = false,value ="dictOrderFirst")                        Integer dictOrderFirst ,
        HttpServletRequest request,HttpServletResponse response){
        Priority adminQueryPriority = AdminUserContextUtil.priority(request, moduleName);
        if(adminQueryPriority==null || !adminQueryPriority.getCanExport()){
            toWebFailureJson(response,"权限不足");
            return;
        }
        String orderBySqlFieldStr = ",id,dict_key,dict_value,dict_type,dict_desc,dict_order,";
        String orderBy = ProjectUtil.filterOrderBy(orderBySqlFieldStr,orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "dictKeyFirst",dictKeyFirst ,
                "dictValueFirst",dictValueFirst ,
                "dictTypeFirst",dictTypeFirst ,
                "dictDescFirst",dictDescFirst ,
                "dictOrderFirst",dictOrderFirst ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        List pageList;
            pageList = this.dictService.getDictList(query);

        String file = "dict";
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(file,".txt");
            String json = JSON.toJSONString(pageList, true);
            FileHelper.fastWriteFileUTF8(tmpFile, json);
            toFile(response, tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(tmpFile!=null){
                tmpFile.delete();
            }
        }
    }


//===================搜索下拉框 外键查询使用begin=================================
    @RequestMapping(value = "type_ahead_search",method = RequestMethod.GET)
    public void typeAheadSearch(@RequestParam(value = "keyword",required = false) String keyword,
        @RequestParam(value = "foreignJavaField",required = false) String foreignJavaField,
        @RequestParam(value = "selectValue",required = false) String selectValue,
        HttpServletResponse response){
        if(StringUtils.isBlank(selectValue)){
            StringBuffer sb = new StringBuffer();
            selectValue = StringUtils.deleteLastChar(sb.toString());
        }
        List<Dict> list = dictService.tryQueryList(keyword, selectValue, foreignJavaField);
        toSimpleJson(response, ProjectUtil.showList(list, selectValue, foreignJavaField));
    }
    //===================end=================================

}
