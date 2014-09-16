<%-- 
    Document   : my_supplier
    Created on : May 28, 2013, 3:26:32 PM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="Db.DbConnector"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%
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
%>
