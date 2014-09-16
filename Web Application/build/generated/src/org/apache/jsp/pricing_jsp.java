package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONArray;
import java.sql.ResultSet;
import org.json.JSONObject;
import Db.DbConnector;
import java.sql.Connection;

public final class pricing_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    if (request.getParameter("opt") != null) {
        Connection con = DbConnector.initConnection();
        if (request.getParameter("opt").equals("insert")) {
            if (request.getParameter("t").equals("advertisement")) {
                int a = con.createStatement().executeUpdate("INSERT INTO `b2b`.`advertisemntpricing` (`Category`,`cost`,`days`,`Desc`,`type`) VALUES ( '" + request.getParameter("category") + "','" + request.getParameter("cost") + "','" + request.getParameter("days") + "','" + request.getParameter("desc") + "','" + request.getParameter("type") + "')");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            } else if (request.getParameter("t").equals("fine")) {
                int a = con.createStatement().executeUpdate("INSERT INTO `fine` (`Finetype`,`cost`,`finecat`) VALUES ('" + request.getParameter("finetype") + "','" + request.getParameter("cost") + "','" + request.getParameter("finecat") + "')");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            } else if (request.getParameter("t").equals("shop")) {
                int a = con.createStatement().executeUpdate("INSERT INTO `b2b`.`shoppricing` (`Name`,`Cost`,`Categories`,`time`,`type`) VALUES ('" + request.getParameter("name") + "','" + request.getParameter("cost") + "','" + request.getParameter("cat") + "','" + request.getParameter("time") + "','" + request.getParameter("type") + "' );");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }
        } else if (request.getParameter("opt").equals("select")) {
            if (request.getParameter("t").equals("advertisement")) {
                JSONObject root = new JSONObject();
                JSONObject add = new JSONObject();
                JSONObject restore = new JSONObject();
                ResultSet rs = con.createStatement().executeQuery("select * from advertisemntpricing");
                while (rs.next()) {
                    if (rs.getString(6).equals("retailer")) {
                        JSONObject ob = new JSONObject();
                        ob.put("size", rs.getString(2));
                        ob.put("cost", rs.getString(3));
                        ob.put("days", rs.getString(4));
                        ob.put("desc", rs.getString(5));
                        add.put(rs.getString(1), ob);
                    } else if (rs.getString(6).equals("supplier")) {
                        JSONObject ob = new JSONObject();
                        ob.put("size", rs.getString(2));
                        ob.put("cost", rs.getString(3));
                        ob.put("days", rs.getString(4));
                        ob.put("desc", rs.getString(5));
                        restore.put(rs.getString(1), ob);
                    } 
                }
                root.put("add", add);
                root.put("restore", restore);
                
                out.println(root);
            } else if (request.getParameter("t").equals("fine")) {
                JSONObject root = new JSONObject();
                JSONArray retailer = new JSONArray();
                JSONArray supplier = new JSONArray();
                ResultSet rs=con.createStatement().executeQuery("select * from fine");
                while(rs.next())
                {
                    if(rs.getString(2).equals("retailer"))
                    {
                        JSONObject ob=new JSONObject();
                        ob.put("cost", rs.getString(3));
                        ob.put("finecat", rs.getString(4));
                        retailer.put(ob);
                    }
                    else
                    {
                        JSONObject ob=new JSONObject();
                        ob.put("cost", rs.getString(3));
                        ob.put("finecat", rs.getString(4));
                        supplier.put(ob);
                    
                    }
                }
                root.put("retailer", retailer);
                root.put("supplier", supplier);
                out.println(root);
            }
            else if (request.getParameter("t").equals("shop")) {
                JSONObject root = new JSONObject();
                JSONArray retailer = new JSONArray();
                JSONArray supplier = new JSONArray();
                ResultSet rs=con.createStatement().executeQuery("select * from fine");
                while(rs.next())
                {
                    if(rs.getString(6).equals("retailer"))
                    {
                        JSONObject ob=new JSONObject();
                        ob.put("name", rs.getString(2));
                        ob.put("cost", rs.getString(3));
                        ob.put("categories", rs.getString(4));
                        ob.put("time", rs.getString(5));
                        retailer.put(ob);
                    }
                    else
                    {
                        JSONObject ob=new JSONObject();
                        ob.put("name", rs.getString(2));
                        ob.put("cost", rs.getString(3));
                        ob.put("categories", rs.getString(4));
                        ob.put("time", rs.getString(5));
                        supplier.put(ob);
                    
                    }
                }
                root.put("retailer", retailer);
                root.put("supplier", supplier);
                out.println(root);
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
