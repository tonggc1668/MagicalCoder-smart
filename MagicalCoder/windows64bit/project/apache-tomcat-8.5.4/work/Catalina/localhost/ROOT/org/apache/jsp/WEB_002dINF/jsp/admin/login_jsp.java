/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.4
 * Generated at: 2016-07-19 02:30:29 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(6);
    _jspx_dependants.put("/WEB-INF/jsp/admin/include/head.jsp", Long.valueOf(1462930713633L));
    _jspx_dependants.put("/WEB-INF/jsp/admin/include/tail.jsp", Long.valueOf(1462930713634L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1395814099278L));
    _jspx_dependants.put("/WEB-INF/jsp/admin/../common/head.jsp", Long.valueOf(1462930713642L));
    _jspx_dependants.put("jar:file:/D:/ProgrameFiles/MagicalCoder-all/MagicalCoder/windows64bit/project/apache-tomcat-8.5.4/../youyamvc/youyamvc-web/target/youyamvc-web/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153356282000L));
    _jspx_dependants.put("jar:file:/D:/ProgrameFiles/MagicalCoder-all/MagicalCoder/windows64bit/project/apache-tomcat-8.5.4/../youyamvc/youyamvc-web/target/youyamvc-web/WEB-INF/lib/jstl-1.2.jar!/META-INF/fmt.tld", Long.valueOf(1153356282000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html class=\"bg-black\">\r\n");
      out.write("<head>\r\n");
      out.write("    <base href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${CTX}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\r\n");
      out.write("    ");
      out.write("<base href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${CTX}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\r\n");
      out.write("<meta charset=\"UTF-8\">");
      out.write("\r\n");
      out.write("    <title>AdminLTE | Log in</title>\r\n");
      out.write("    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>\r\n");
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- bootstrap 3.0.2 -->\r\n");
      out.write("<link href=\"assets/admin/css/bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<!-- font Awesome -->\r\n");
      out.write("<link href=\"assets/admin/css/font-awesome.min.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<!-- Ionicons -->\r\n");
      out.write("<link href=\"assets/admin/css/ionicons.min.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<!-- Morris chart -->\r\n");
      out.write("<link href=\"assets/admin/css/morris/morris.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<!-- jvectormap -->\r\n");
      out.write("<link href=\"assets/admin/css/jvectormap/jquery-jvectormap-1.2.2.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<!-- bootstrap wysihtml5 - text editor -->\r\n");
      out.write("<link href=\"assets/admin/css/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<!-- Theme style -->\r\n");
      out.write("<link href=\"assets/admin/css/AdminLTE.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<link href=\"assets/admin/css/datatables/dataTables.bootstrap.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"assets/admin/js/jquery/1.8.2/jquery-min-1.8.2.js\"></script>\r\n");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<!-- Theme style -->\r\n");
      out.write("<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->\r\n");
      out.write("<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->\r\n");
      out.write("<!--[if lt IE 9]>\r\n");
      out.write("<script src=\"assets/admin/js/AdminLTE/html5shiv-3.7.0.js\"></script>\r\n");
      out.write("<script src=\"assets/admin/js/AdminLTE/respond.min.js\"></script>\r\n");
      out.write("<![endif]-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"assets/admin/js/json.js\"></script>\r\n");
      out.write("<script  type=\"text/javascript\">\r\n");
      out.write("    var CTX = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${CTX}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("';\r\n");
      out.write("</script>");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"bg-black\">\r\n");
      out.write("\r\n");
      out.write("<div class=\"form-box\" id=\"login-box\">\r\n");
      out.write("    <div class=\"header\">登录</div>\r\n");
      out.write("    <form action=\"admin\" method=\"post\">\r\n");
      out.write("        <div class=\"body bg-gray\">\r\n");
      out.write("            <div class=\"form-group\">\r\n");
      out.write("                <input type=\"text\" name=\"userName\" class=\"form-control\" placeholder=\"用户名\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${userName}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\"/>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"form-group\">\r\n");
      out.write("                <input type=\"password\" name=\"password\" class=\"form-control\" placeholder=\"密码\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${password}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\"/>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"form-group\">\r\n");
      out.write("                <input type=\"text\" name=\"checkCode\" class=\"form-control\" placeholder=\"验证码\" value=\"\"/>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"form-group\">\r\n");
      out.write("                <img alt=\"验证码看不清，换一张\" src=\"admin/checkCode\" id=\"checkCode\" onclick=\"changeImg(this)\">\r\n");
      out.write("                <span class=\"alert-danger\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${alertMsg}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("</span>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"footer\">\r\n");
      out.write("            <button type=\"submit\" class=\"btn bg-olive btn-block\">登录</button>\r\n");
      out.write("        </div>\r\n");
      out.write("    </form>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<!-- Bootstrap -->\r\n");
      out.write("<script src=\"assets/admin/js/bootstrap.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<!--https://github.com/twitter/typeahead.js/blob/master/doc/jquery_typeahead.md-->\r\n");
      out.write("<link href=\"assets/admin/css/typeahead.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"assets/admin/js/bootstrap/typeahead.jquery.min-0.11.1.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- AdminLTE App -->\r\n");
      out.write("<script src=\"assets/admin/js/AdminLTE/app.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- magiccoder app-->\r\n");
      out.write("<script src=\"assets/admin/js/page/common_paging.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    //刷新验证码\r\n");
      out.write("    function changeImg(obj){\r\n");
      out.write("        document.getElementById(obj.id).src=\"admin/checkCode?d=\"+Math.random();\r\n");
      out.write("    }\r\n");
      out.write("</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
