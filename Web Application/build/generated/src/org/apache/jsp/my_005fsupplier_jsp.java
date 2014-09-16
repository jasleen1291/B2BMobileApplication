package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONObject;
import java.sql.ResultSetMetaData;
import org.json.JSONArray;
import java.sql.ResultSet;
import java.sql.Connection;
import Db.DbConnector;

public final class my_005fsupplier_jsp extends org.apache.jasper.runtime.HttpJspBase
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

Connection con=DbConnector.initConnection();
if(request.getParameter("opt")!=null)
{  if(request.getParameter("opt").equalsIgnoreCase("mysupplier"))
    {
if(request.getParameter("shopid")!=null)
{
ResultSet rs=con.createStatement().executeQuery("select distinct(shopid) from item_shop where item_shopid in(Select item_supplier from item_shop where shopid='"+request.getParameter("shopid")+"' );");
JSONArray ob=new JSONArray();
while(rs.next())
{
    ob.put(rs.getString(1));
}
out.println(ob);

}
    } 
else if(request.getParameter("opt").equalsIgnoreCase("mysubscriptions"))
{
    //out.println("A");
JSONArray items2 = new JSONArray();
                ResultSet rs3 = con.createStatement().executeQuery("SELECT * FROM item,item_shop  where itemid=iditem and shopid ='"+request.getParameter("suppid")+"' and item_shopid in(Select item_supplier from item_shop where shopid='"+request.getParameter("shopid")+"' );");
                //System.out.println("SELECT * FROM item,item_shop where item_shop.sellertype='Supplier' and  iditem=item_shop.itemid and iditem not in(select itemid  from item_shop where shopid ='" + rs.getString(rsmd.getColumnLabel(1)) + "')");

                //sSystem.out.println("Select * from item where Owner='" + request.getParameter("id") + "' and iditem not in( SELECT itemid FROM item_shop where shopid='" + rs.getString(rsmd.getColumnLabel(1)) + "')");
                ResultSetMetaData rsmd3 = rs3.getMetaData();
                int count3 = rsmd3.getColumnCount();
                while (rs3.next()) {
                    //System.out.println(rs3.getString(1));
                    JSONObject ob3 = new JSONObject();
                    for (int i = 1; i <= count3; i++) {
                        if (rsmd3.getColumnLabel(i).equalsIgnoreCase("item_shopid")) {
                            JSONArray btd = new JSONArray();
                            ResultSet rs6 = con.createStatement().executeQuery("SELECT * FROM b2b.batchdiscount where `idbatchdiscount`='" + rs3.getString(rsmd3.getColumnLabel(i)) + "'");
                            while (rs6.next()) {
                                JSONObject wq = new JSONObject();

                                wq.put("qty", rs6.getString(2));
                                wq.put("discount", rs6.getString(3));
                                wq.put("id", rs6.getString(4));
                                btd.put(wq);
                            }
                            ob3.put("batch", btd);
                        }
                        ob3.put(rsmd3.getColumnLabel(i), rs3.getString(rsmd3.getColumnLabel(i)));

                    }
                    //System.out.println(ob3);
                    items2.put(ob3);

                }
                out.println(items2);
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
