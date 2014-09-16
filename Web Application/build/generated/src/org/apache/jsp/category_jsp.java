package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONArray;
import java.sql.ResultSet;
import org.json.JSONObject;
import Db.DbConnector;
import java.sql.Connection;

public final class category_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

Connection con = DbConnector.initConnection();

    public JSONArray fetchchildren(String id) {
        try {
            JSONArray temp = new JSONArray();
            ResultSet resultSet = con.createStatement().executeQuery("select * from `category` where ParentCategory='" + id + "'");
            if (!resultSet.next()) {
                //temp.put(id, "null");
            } else {

                do {
                    JSONObject tr = new JSONObject();
                    tr.put("id", resultSet.getString(1));
                    tr.put("name", resultSet.getString(2));
                    tr.put("children", fetchchildren(resultSet.getString(1)));
                    temp.put(tr);
                } while (resultSet.next());

            }
            return temp;
        } catch (Exception e) {
            return new JSONArray().put(e.toString());
        }

    }

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
      out.write('\n');

    if (request.getParameter("opt") != null) {

        if (request.getParameter("opt").equals("insert")) {

            int a = con.createStatement().executeUpdate("INSERT INTO `category` (`Categoryname`,`ParentCategory`) VALUES('" + request.getParameter("name") + "','" + request.getParameter("parent") + "')");
            if (a == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
            }
        } else if (request.getParameter("opt").equals("select")) {
            JSONArray ad = new JSONArray();
            ResultSet rs = con.createStatement().executeQuery("select * from `category` where ParentCategory='0'");
            while (rs.next()) {
                JSONObject tr = new JSONObject();
                tr.put("id", rs.getString(1));
                tr.put("name", rs.getString(2));
                tr.put("children", fetchchildren(rs.getString(1)));
                ad.put(tr);
            }
            out.println(ad);
        }
        else if(request.getParameter("opt").equalsIgnoreCase("editcategories"))
        {
          con.createStatement().executeUpdate("DELETE FROM `b2b`.`category_user` WHERE masterid='"+request.getParameter("id")+"'");
          String ad[]=request.getParameterValues("catid");
          
          for(int i=0;i<ad.length;i++)
          {
          con.createStatement().executeUpdate("INSERT INTO `b2b`.`category_user` (`idCategory`,`masterid`) VALUES ('"+ad[i]+"','"+request.getParameter("id")+"');");
          }
          out.println(new JSONObject().put("Status", "Values inserted"));
        }
        else if(request.getParameter("opt").equalsIgnoreCase("selectcategories"))
        {
       // SELECT `idCategory` FROM `category_user` where `masterid`='';
            JSONArray a=new JSONArray();
            ResultSet rs=con.createStatement().executeQuery("SELECT `idCategory` FROM `category_user` where `masterid`='"+request.getParameter("id")+"'");
            while(rs.next())
            {
            a.put(rs.getString(1));
            }
            
        }
        else if(request.getParameter("opt").equalsIgnoreCase("gettc"))
        {
        JSONArray a=new JSONArray();
        a.put(fetchchildren("0"));
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
