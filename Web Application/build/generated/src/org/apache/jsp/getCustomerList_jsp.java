package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONObject;
import java.sql.*;
import org.json.JSONArray;
import Db.DbConnector;

public final class getCustomerList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("application/json;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
 if(request.getParameter("opt")!=null)
{
    JSONArray root = new JSONArray();
    Connection con = DbConnector.initConnection();
    if(request.getParameter("opt").equalsIgnoreCase("ac"))
    {
    ResultSet rs = con.createStatement().executeQuery("SELECT masterid,username FROM b2b.login where usertype='customer' and status='activated'");
    while (rs.next()) {
       JSONObject ob=new JSONObject();
       ob.put("masterid", rs.getString(1));
       ob.put("username",rs.getString(2));
       root.put(ob);
    }
    out.println(root);
    }
    else if(request.getParameter("opt").equalsIgnoreCase("deac"))
    {
    ResultSet rs = con.createStatement().executeQuery("SELECT masterid,username FROM b2b.login where usertype='customer' and status='deactivated'");
    while (rs.next()) {
       JSONObject ob=new JSONObject();
       ob.put("masterid", rs.getString(1));
       ob.put("username",rs.getString(2));
       root.put(ob);
    }
    out.println(root);
  
    }
}


      out.write('\n');
      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
