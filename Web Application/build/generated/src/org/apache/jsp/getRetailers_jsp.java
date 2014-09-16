package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import org.json.JSONObject;
import java.sql.*;
import org.json.JSONArray;
import Db.DbConnector;

public final class getRetailers_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\n');
      out.write('\n');
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
 if(request.getParameter("opt")!=null&&request.getParameter("retailerstatus")!=null)
{
    JSONObject root = new JSONObject();
    ArrayList<String> item=new ArrayList<String>();
    ArrayList<JSONArray> obj=new ArrayList<JSONArray>();
    Connection con = DbConnector.initConnection();
    
    ResultSet rs = con.createStatement().executeQuery("SELECT idshop,shopname,username,masterid FROM shop,login where shoptype='Retailer' and shopowner=masterid and login.status='"+request.getParameter("retailerstatus")+"'");
    while (rs.next()) {
       if(item.contains(rs.getString(3)))
       {
           int i=item.indexOf(rs.getString(3));
           JSONObject temp=new JSONObject();
           temp.put("idshop",rs.getString(1) );
           temp.put("shopname",rs.getString(2) );
           obj.get(i).put(temp);
       }
       else
       {
       item.add(rs.getString(3));
       JSONArray a=new JSONArray();
       JSONObject temp=new JSONObject();
       temp.put("idshop",rs.getString(1) );
       temp.put("shopname",rs.getString(2) );
       obj.add(a);
       a.put(temp);
       root.put(rs.getString(4), a);
       }
       
    }
    out.println(root);
    
    
}


      out.write("\n");
      out.write("\n");
      out.write("\n");
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
