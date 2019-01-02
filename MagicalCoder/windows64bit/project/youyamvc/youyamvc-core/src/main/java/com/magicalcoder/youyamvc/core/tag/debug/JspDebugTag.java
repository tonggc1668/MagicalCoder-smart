package com.magicalcoder.youyamvc.core.tag.debug;

import com.magicalcoder.youyamvc.core.common.utils.JsonFormatUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.serialize.SerializerFastJsonUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by www.magicalcoder.com on 2015/5/26.
 * 799374340@qq.com
 */
public class JspDebugTag extends BodyTagSupport {
    private static final long serialVersionUID = 1L;
    private Object item;
    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        if(item instanceof Map){
            if(item!=null){
                Map<Object,Object> map = (Map<Object,Object>)item;
                List<Object> removeKey = new ArrayList<Object>();
                Set keys = map.keySet();
                for(Object key:keys){
                    String keyStr = key+"";
                    if(keyStr.startsWith("org.springframework.")
                            || keyStr.startsWith("javax.servlet.")){
                        removeKey.add(key);
                    }
                }
                if(ListUtils.isNotBlank(removeKey)){
                    for(Object key:removeKey){
                        map.remove(key);
                    }
                }
            }
        }


        String json = JsonFormatUtil.format2Json(SerializerFastJsonUtil.get().toJSONString(item,null));
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (EVAL_PAGE);
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}
