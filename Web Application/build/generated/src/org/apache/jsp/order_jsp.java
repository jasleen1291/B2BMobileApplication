package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.ResultSetMetaData;
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.ResultSet;
import Db.DbConnector;
import java.sql.Connection;

public final class order_jsp extends org.apache.jasper.runtime.HttpJspBase
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
            int a = con.createStatement().executeUpdate("INSERT INTO `b2b`.`order`"
                    + "("
                    + "`date`,"
                    + "`placedby`,"
                    + "`placedto`,"
                    + "`warehouse`,"
                    + "`total`,"
                    + "`state`)"
                    + "VALUES"
                    + "( CURDATE(),'" + request.getParameter("pby") + "','" + request.getParameter("pto")
                    + "','" + request.getParameter("whouse") + "','" + request.getParameter("total") + "','" + request.getParameter("status") + "')");
            String ab = "";
            ResultSet rs = con.createStatement().executeQuery("select LAST_INSERT_ID()");
            if (rs.next()) {
                ab = rs.getString(1);
            }
            /*
             INSERT INTO `b2b`.`orderdetails` (`idorder`,`itemid`,`qty`,`cost`,`discount`,`afterdiscount`) VALUES
             (<{idorder: }>,<{itemid: }>,<{qty: }>,<{cost: }>,<{discount: }>,<{afterdiscount: }>);
             */
            String afterdiscount[] = request.getParameterValues("afterdiscount");
            String discount[] = request.getParameterValues("discount");
            String cost[] = request.getParameterValues("cost");
            String qty[] = request.getParameterValues("qty");
            String itemid[] = request.getParameterValues("itemid");
            String shipmentcost[]=request.getParameterValues("shipmentcost");
            for (int j = 0; j < itemid.length; j++) {
                con.createStatement().executeUpdate("INSERT INTO `b2b`.`orderdetails` (`idorder`,`itemid`,`qty`,`cost`,`discount`,`afterdiscount`,shipmentcost) VALUES"
                        + "('" + ab + "','" + itemid[j] + "','" + qty[j] + "','" + cost[j] + "','" + discount[j] + "','" + afterdiscount[j] + "','"+shipmentcost[j]+"')");
            }

            if (a == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
                out.println(new JSONObject().put("Status", "Values not inserted"));
            }

        } else if (request.getParameter("opt").equals("updateitemsorder")) {
            String itemshopid[] = request.getParameterValues("itemshopid");
            String qtyonorder[] = request.getParameterValues("qtyonorder");
            int a = 0;
            for (int i = 0; i < itemshopid.length; i++) {
                a = con.createStatement().executeUpdate("UPDATE `b2b`.`item_shop` SET `qtyonorder` = '" + qtyonorder[i] + "' WHERE `item_shopid` = '" + itemshopid[i] + "'");
            }
            if (a == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
                out.println(new JSONObject().put("Status", "Values not inserted"));
            }
        }
        else if(request.getParameter("opt").equals("select"))
        {
        
            JSONArray ar=new JSONArray();
        ResultSet rs4 = con.createStatement().executeQuery("Select * from warehouse  where ownerid='" + request.getParameter("wid") + "' ");
        ResultSetMetaData rsmd4 = rs4.getMetaData();
        int count4 = rsmd4.getColumnCount();
        
        while (rs4.next()) {
            JSONObject ob = new JSONObject();
            for (int i = 1; i < count4; i++) {
                
               
                ob.put(rsmd4.getColumnLabel(i), rs4.getString(rsmd4.getColumnLabel(i)));

            }
            ar.put(ob);
        }
        
        

        JSONArray items = new JSONArray();
                ResultSet rs2 = con.createStatement().executeQuery("SELECT * FROM item_shop,item where shopid='" + request.getParameter("shopid") + "' and  iditem=item_shop.itemid and item.status='activated' order by iditem DESC");
                ResultSetMetaData rsmd2 = rs2.getMetaData();
                int coun2t = rsmd2.getColumnCount();
                while (rs2.next()) {
                    JSONObject ob2 = new JSONObject();
                    for (int i = 1; i <= coun2t; i++) {
                        if(rsmd2.getColumnLabel(i).equalsIgnoreCase("item_shopid"))
                        {
                            JSONArray btd=new JSONArray();
                            ResultSet rs6=con.createStatement().executeQuery("SELECT * FROM b2b.batchdiscount where `idbatchdiscount`='"+rs2.getString(rsmd2.getColumnLabel(i))+"'");
                            while(rs6.next())
                            {
                                JSONObject wq=new JSONObject();
                                
                                wq.put("qty", rs6.getString(2));
                                wq.put("discount", rs6.getString(3));
                                wq.put("id", rs6.getString(4));
                                btd.put(wq);
                            }
                            ob2.put("batch", btd);
                        }
                        ob2.put(rsmd2.getColumnLabel(i), rs2.getString(rsmd2.getColumnLabel(i)));

                    }
                    items.put(ob2);

                }
             JSONArray orders=new JSONArray();
             ResultSet rs=con.createStatement().executeQuery("SELECT * FROM b2b.order where placedby='"+request.getParameter("shopid")+"'");
             ResultSetMetaData rsmd = rs.getMetaData();
              int count = rsmd.getColumnCount();
            while (rs.next()) {
                JSONObject ob = new JSONObject();
                for (int i = 1; i <= count; i++) {

                    ob.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));
                    
                }
                ResultSet rs3=con.createStatement().executeQuery("SELECT * FROM b2b.orderdetails where idorder='"+rs.getString("idorder")+"'");
                ResultSetMetaData rsmd3 = rs3.getMetaData();
                int count3 = rsmd3.getColumnCount();
                JSONArray d=new JSONArray();
                while(rs3.next())
                {
                JSONObject details = new JSONObject();
                 for (int i = 1; i <= count3; i++) {

                    details .put(rsmd3.getColumnLabel(i), rs3.getString(rsmd3.getColumnLabel(i)));
                    
                }
                 d.put(details);
                }
                ob.put("details", d);
                orders.put(ob);
        }
            JSONObject result=new JSONObject();
            result.put("children", items);
            result.put("warehouses",ar);
            result.put("orders", orders);
            out.println(result);
    }
        else if(request.getParameter("opt").equals("cancel"))
        {
        int c=con.createStatement().executeUpdate("DELETE FROM `b2b`.`order` WHERE idorder="+request.getParameter("id"));
        String itemshopid[] = request.getParameterValues("itemshopid");
       //System.out.println("DELETE FROM `b2b`.`orderdetails` WHERE idorder="+request.getParameter("id"));
        String qtyonorder[] = request.getParameterValues("qtyonorder");
            int a = 0;
            for (int i = 0; i < itemshopid.length; i++) {
                System.out.println("UPDATE `b2b`.`item_shop` SET qtyonorder = qtyonorder-'" + qtyonorder[i] + "' WHERE `item_shopid` = '" + itemshopid[i] + "'");
                a = con.createStatement().executeUpdate("UPDATE `b2b`.`item_shop` SET qtyonorder = qtyonorder-'" + qtyonorder[i] + "' WHERE `item_shopid` = '" + itemshopid[i] + "'");
                
            }
            if (a == 1) {
                System.out.println(a);
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
                out.println(new JSONObject().put("Status", "Values not inserted"));
            }
        }
        else if(request.getParameter("opt").equals("Received"))
        {
        int b=con.createStatement().executeUpdate("DELETE FROM `b2b`.`orderdetails` WHERE idorder="+request.getParameter("id"));
        int c=con.createStatement().executeUpdate("DELETE FROM `b2b`.`order` WHERE idorder="+request.getParameter("id"));
        String itemshopid[] = request.getParameterValues("itemshopid");
        System.out.println("DELETE FROM `b2b`.`orderdetails` WHERE idorder="+request.getParameter("id"));
        String qtyonorder[] = request.getParameterValues("qtyonorder");
            int a = 0;
            for (int i = 0; i < itemshopid.length; i++) {
                System.out.println("UPDATE `b2b`.`item_shop` SET qtyonorder = qtyonorder-'" + qtyonorder[i] + "', qtyonhand = qtyonhand+'" + qtyonorder[i] + "' WHERE `item_shopid` = '" + itemshopid[i] + "'");
                a = con.createStatement().executeUpdate("UPDATE `b2b`.`item_shop` SET qtyonorder = qtyonorder-'" + qtyonorder[i] + "' , qtyonhand = qtyonhand+'" + qtyonorder[i] + "' WHERE `item_shopid` = '" + itemshopid[i] + "'");
                
            }
            if (a == 1) {
                System.out.println(a);
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
                out.println(new JSONObject().put("Status", "Values not inserted"));
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
