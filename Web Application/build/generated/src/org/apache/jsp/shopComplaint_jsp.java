package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONArray;
import java.util.ArrayList;
import java.sql.ResultSet;
import org.json.JSONObject;
import java.sql.Connection;
import Db.DbConnector;

public final class shopComplaint_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    if (request.getParameter("opt") != null) {
        Connection con = DbConnector.initConnection();
        if (request.getParameter("opt").equals("insert")) {

            int a = con.createStatement().executeUpdate("INSERT INTO complaint (complainttype,complainerid,date) VALUES('shop'," + request.getParameter("complainerid") + ",CURDATE())");
            int b = con.createStatement().executeUpdate("INSERT INTO shopcomplaint (`idcomplaint`,`title`,`desc`,`complainabout`) VALUES(LAST_INSERT_ID(),'" + request.getParameter("title") + "','" + request.getParameter("desc") + "','" + request.getParameter("shopid") + "')");
            if (a == 1 && b == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
                out.println(new JSONObject().put("Status", "Values not inserted"));
            }
        } else {
            ArrayList<String> itemnames = new ArrayList<String>();
            ArrayList<JSONArray> obs = new ArrayList<JSONArray>();

            JSONObject ob = new JSONObject();
            ResultSet rs = con.createStatement().executeQuery("SELECT complainabout,shopname ,masteruserinfo.email, `title`, `desc` ,complaint.idComplaint "
                    + "FROM complaint,shopcomplaint,masteruserinfo,shop where "
                    + "complaint.idComplaint=shopcomplaint.idComplaint "
                    + "and complainerid=masterid and complaint.status=1 and complainabout=idshop "
                    + "order by shopcomplaintid desc");
            while (rs.next()) {
                //out.println("hi");
                if (itemnames.contains(rs.getString(2))) {

                    int index = itemnames.indexOf(rs.getString(2));
                    JSONArray ad = obs.get(index);
                    JSONObject temp = new JSONObject();
                    temp.put("email", rs.getString(3));
                   // temp.put("shopname", rs.getString(2));
                    temp.put("title", rs.getString(4));
                    temp.put("desc", rs.getString(5));
                    temp.put("id", rs.getString(6));
                    temp.put("shopid", rs.getString(1));
                    ad.put(temp);
                } else {
                    itemnames.add(rs.getString(2));
                    JSONObject temp = new JSONObject();
                    temp.put("email", rs.getString(3));
                    //temp.put("shopname", rs.getString(2));
                    temp.put("title", rs.getString(4));
                    temp.put("desc", rs.getString(5));
                    temp.put("id", rs.getString(6));
                    temp.put("shopid", rs.getString(1));
                    JSONArray a = new JSONArray();
                    a.put(temp);
                    obs.add(a);
                }
                for (int i = 0; i < itemnames.size(); i++) {
                    ob.put(itemnames.get(i), obs.get(i));
                }
                //ob.put(i+"",temp);
            }
            out.println(ob);
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
