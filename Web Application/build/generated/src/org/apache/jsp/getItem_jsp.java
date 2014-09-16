package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONObject;
import java.sql.*;
import org.json.JSONArray;
import Db.DbConnector;

public final class getItem_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    if (request.getParameter("opt") != null) {
        Connection con = DbConnector.initConnection();
        if (request.getParameter("opt").equals("update")) {
            if (request.getParameter("id") != null && request.getParameter("status") != null) {

                
                int a = con.createStatement().executeUpdate("UPDATE `item` SET `status` = '" + request.getParameter("status") + "' WHERE `iditem` = '" + request.getParameter("id") + "'");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                    out.println(new JSONObject().put("Status", "Values not inserted"));
                }
            }
        }
        else if(request.getParameter("opt").equals("getActive"))
        {
            JSONArray arr=new JSONArray();
            ResultSet rs=con.createStatement().executeQuery("select * from item where status='activated'");
            while(rs.next())
            {
            JSONObject temp=new JSONObject();
            temp.put("iditem", rs.getString(1));
            temp.put("itemname", rs.getString(2));
            temp.put("longdescription", rs.getString(3));
            temp.put("categoryid", rs.getString(4));
            temp.put("qtyperunit", rs.getString(5));
            temp.put("unit", rs.getString(6));
            temp.put("imagepath", rs.getString(7));
            arr.put(temp);
            }
            out.println(arr);
        }
        else if(request.getParameter("opt").equals("getDeleted"))
        {
        JSONArray arr=new JSONArray();
            ResultSet rs=con.createStatement().executeQuery("select * from item where status='deactivated'");
            while(rs.next())
            {
            JSONObject temp=new JSONObject();
            temp.put("iditem", rs.getString(1));
            temp.put("itemname", rs.getString(2));
            temp.put("longdescription", rs.getString(3));
            temp.put("categoryid", rs.getString(4));
            temp.put("qtyperunit", rs.getString(5));
            temp.put("unit", rs.getString(6));
            temp.put("imagepath", rs.getString(7));
            arr.put(temp);
            }
            out.println(arr);
        }
        else if(request.getParameter("opt").equals("search"))
        {
            String query=request.getParameter("query");
            query=query.replaceAll("[^\\p{Alnum}]+", "");
            String wrds[]=query.split("\\s+");
            for(int i=0;i<wrds.length;i++)
            {
            out.println("%"+wrds[i]+"%");
            }
        }
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
