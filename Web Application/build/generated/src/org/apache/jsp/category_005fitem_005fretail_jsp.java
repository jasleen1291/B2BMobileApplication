package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONObject;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.Connection;
import org.json.JSONArray;

public final class category_005fitem_005fretail_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

 JSONArray items = new JSONArray();
    Connection con = Db.DbConnector.initConnection();

    void fetchItems(String id) {
        try {
          //  ResultSet rs = con.createStatement().executeQuery("SELECT * FROM item where status='activated' and categoryid='" + id + "'");
            //ResultSetMetaData rsmd = rs.getMetaData();
            //int count = rsmd.getColumnCount();
          
                ResultSet rs3 = con.createStatement().executeQuery("SELECT * FROM item,item_shop where item_shop.sellertype='Retailer' and item.status='activated' and item.categoryid='" + id + "' and  iditem=item_shop.itemid ");
                //System.out.println("SELECT * FROM item,item_shop where item_shop.sellertype='Supplier' and  iditem=item_shop.itemid and iditem not in(select itemid  from item_shop where shopid ='" + rs.getString(rsmd.getColumnLabel(1)) + "')");

                //sSystem.out.println("Select * from item where Owner='" + request.getParameter("id") + "' and iditem not in( SELECT itemid FROM item_shop where shopid='" + rs.getString(rsmd.getColumnLabel(1)) + "')");
                ResultSetMetaData rsmd3 = rs3.getMetaData();
                int count3 = rsmd3.getColumnCount();
                while (rs3.next()) {
                    System.out.println(rs3.getString(1));
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
                    System.out.println(ob3);
                    items.put(ob3);

                }
                
            ResultSet resultSet = con.createStatement().executeQuery("select * from `category` where ParentCategory='" + id + "'");
            while(resultSet.next())
            {
                fetchItems(resultSet.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
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
      out.write('\n');

    if(request.getParameter("category")!=null)
    {
        items=new JSONArray();
fetchItems(request.getParameter("category"));
out.println(items);
    }
    else if(request.getParameter("opt").equals("wishlist"))
    {
    int a=con.createStatement().executeUpdate("INSERT INTO `b2b`.`wishlist` (`masterid`,`itemid`) VALUES ('"+request.getParameter("id")+"','"+request.getParameter("itemid")+"');");
    if (a == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
            }
    }
    else if(request.getParameter("opt").equals("sel"))
    {
        JSONArray a=new JSONArray();
        ResultSet r=con.createStatement().executeQuery("select distinct(itemid) from wishlist where masterid='"+request.getParameter("id")+"'");
        while(r.next())
        {
        a.put(r.getString(1));
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
