package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.Connection;

public final class getAdvetisement_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");

    Connection con = Db.DbConnector.initConnection();
    if (request.getParameter("type") != null) {
        JSONArray ar=new JSONArray();
        ResultSet rs4 = con.createStatement().executeQuery("SELECT * FROM b2b.advertisement where advertype='"+request.getParameter("type")+"' and enddate>now()");
         //ResultSet rs4 = con.createStatement().executeQuery("Select * from warehouse  where ownerid='" + request.getParameter("wid") + "' ");
        ResultSetMetaData rsmd4 = rs4.getMetaData();
        int count4 = rsmd4.getColumnCount();
        
        while (rs4.next()) {
            JSONObject ob = new JSONObject();
            for (int i = 1; i <= count4; i++) {
                
               
                ob.put(rsmd4.getColumnLabel(i), rs4.getString(rsmd4.getColumnLabel(i)));

            }
          ar.put(ob);
        }
        out.println(ar);
    }

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
