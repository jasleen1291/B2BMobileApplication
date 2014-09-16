<%-- 
    Document   : category_item
    Created on : May 27, 2013, 6:03:56 PM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.json.JSONArray"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%! JSONArray items = new JSONArray();
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
%>

<%
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
    else if(request.getParameter("opt").equalsIgnoreCase("wishlistdel"))
    {
    int a=con.createStatement().executeUpdate("DELETE FROM `b2b`.`wishlist` WHERE masterid='"+request.getParameter("id")+"' and itemid='"+request.getParameter("itemid")+"';");
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
        out.println(a);
    }
%>