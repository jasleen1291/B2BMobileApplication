package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONArray;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import org.json.JSONObject;
import Db.DbConnector;
import java.sql.Connection;

public final class warehouse_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");

    Connection con = DbConnector.initConnection();
    if (request.getParameter("warehouse") != null) {
        if (request.getParameter("opt") != null) {
            if (request.getParameter("opt").equalsIgnoreCase("add")) {
                int a = con.createStatement().executeUpdate("INSERT INTO `warehouse` (`warehousename`,`address`,`city`,`state`,`country`,`zip`,`ownerid`) VALUES ('" + request.getParameter("name") + "','" + request.getParameter("address") + "','" + request.getParameter("city") + "','" + request.getParameter("state") + "','" + request.getParameter("country") + "','" + request.getParameter("zip") + "','" + request.getParameter("id") + "');");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }

        }
    } else if (request.getParameter("contact") != null) {
        if (request.getParameter("opt") != null) {
            if (request.getParameter("opt").equalsIgnoreCase("add")) {
                int a = con.createStatement().executeUpdate("INSERT INTO `b2b`.`contact`"
                        + "(`empid`,`Name`,`email`,`phone1`,`phone2`,`ContactTitle`)" + " VALUES("
                        + request.getParameter("id") + ","
                        + request.getParameter("name") + ","
                        + request.getParameter("email") + ","
                        + request.getParameter("phone1") + ","
                        + request.getParameter("phone2") + ","
                        + request.getParameter("title") + ")");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }
            if (request.getParameter("opt").equalsIgnoreCase("addw")) {
                int a = con.createStatement().executeUpdate("INSERT INTO `warehouse_contact` (`idwarehouse`,`idcontact`) VALUES  ('" + request.getParameter("wid") + "','" + request.getParameter("cid") + "');");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }


        }
    }
    if (request.getParameter("opt").equalsIgnoreCase("selectc")) {
        JSONArray ar=new JSONArray();
        ResultSet rs = con.createStatement().executeQuery("Select * from contact where contact.idcontact in (Select idcontact from warehouse_contact where idwarehouse='" + request.getParameter("wid") + "' )");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        boolean a=false;
        while (rs.next()) {
            JSONObject ob = new JSONObject();
            for (int i = 1; i < count; i++) {
                
                a=true;
                ob.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));

            }
            ar.put(ob);
        }
        
        if(!a)
        {
        out.println(ar.put("No records found"));
        }
        else
        {
             out.println(ar);
        }

    }
    else if (request.getParameter("opt").equalsIgnoreCase("selectw")) {
        JSONArray ar=new JSONArray();
        ResultSet rs = con.createStatement().executeQuery("Select * from warehouse  where ownerid='" + request.getParameter("wid") + "' ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        boolean a=false;
        while (rs.next()) {
            JSONObject ob = new JSONObject();
            for (int i = 1; i < count; i++) {
                
                a=true;
                ob.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));

            }
            ar.put(ob);
        }
        
        if(!a)
        {
        out.println(ar.put(new JSONObject().put("idwarehouse", "-1")));
        }
        else
        {
             out.println(ar);
        }

    }

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
