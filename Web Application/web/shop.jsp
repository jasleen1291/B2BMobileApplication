<%-- 
    Document   : shop
    Created on : May 19, 2013, 3:50:15 AM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.json.JSONArray"%>
<%@page import="Db.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%!Connection con = DbConnector.initConnection();%>
<%
    if (request.getParameter("opt") != null) {

        JSONArray a = new JSONArray();
        if (request.getParameter("opt").equalsIgnoreCase("myshops")) {
            ResultSet rs = con.createStatement().executeQuery("Select * from shop where ShopOwner='" + request.getParameter("id") + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            while (rs.next()) {
                JSONObject ob = new JSONObject();
                for (int i = 1; i <= count; i++) {

                    ob.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));

                }
                //System.out.println(rsmd.getColumnLabel(1));
                JSONArray items = new JSONArray();
                ResultSet rs2 = con.createStatement().executeQuery("SELECT * FROM item_shop,item where shopid='" + rs.getString(rsmd.getColumnLabel(1)) + "' and  iditem=item_shop.itemid and item.status='activated' order by iditem DESC");
                ResultSetMetaData rsmd2 = rs2.getMetaData();
                int coun2t = rsmd2.getColumnCount();
                while (rs2.next()) {
                    JSONObject ob2 = new JSONObject();
                    for (int i = 1; i <= coun2t; i++) {
                        if (rsmd2.getColumnLabel(i).equalsIgnoreCase("item_shopid")) {
                            JSONArray btd = new JSONArray();
                            ResultSet rs6 = con.createStatement().executeQuery("SELECT * FROM b2b.batchdiscount where `idbatchdiscount`='" + rs2.getString(rsmd2.getColumnLabel(i)) + "'");
                            while (rs6.next()) {
                                JSONObject wq = new JSONObject();

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
                JSONArray items2 = new JSONArray();
                ResultSet rs3 = con.createStatement().executeQuery("SELECT * FROM item,item_shop where item_shop.sellertype='Supplier' and  iditem=item_shop.itemid and itemid not in(select itemid from item_shop where shopid ='" + rs.getString(rsmd.getColumnLabel(1)) + "')");
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
                    //System.out.println(ob3);
                    items2.put(ob3);

                }
                ob.put("children", items);
                ob.put("available", items2);
                JSONArray ad = new JSONArray();
                ResultSet rs4 = con.createStatement().executeQuery("select * from `category` where ParentCategory='0' and idcategory in(SELECT idCategory FROM b2b.category_user where masterid='" + request.getParameter("id") + "')");
                //System.out.println("select * from `category` where ParentCategory='0' and idcategory in(SELECT idCategory FROM b2b.category_user where masterid='"+request.getParameter("id")+"')");
                while (rs4.next()) {
                    JSONObject tr = new JSONObject();

                    tr.put("id", rs4.getString(1));
                    tr.put("name", rs4.getString(2));
                    tr.put("children", fetchchildren(rs4.getString(1)));
                    //System.out.println(rs.getString(1)+rs.getString(2)+fetchchildren(rs.getString(1)));
                    ad.put(tr);
                    // System.out.println(tr);
                }
                ob.put("categories", ad);
                a.put(ob);
            }
            out.println(a);
        } else if (request.getParameter("opt").equalsIgnoreCase("myitems")) {

            ResultSet rs = con.createStatement().executeQuery("Select * from item where Owner='" + request.getParameter("id") + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            while (rs.next()) {
                JSONObject ob = new JSONObject();
                for (int i = 1; i <= count; i++) {

                    ob.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));

                }
                a.put(ob);
            }
            out.println(a);
        } else if (request.getParameter("opt").equalsIgnoreCase("additem")) {

            int ab = con.createStatement().executeUpdate("INSERT INTO `b2b`.`item_shop` (`itemid`,`shopid`,`sellertype`,`unitcost`,`sp`,`qtyonhand`,`qtyonorder`,`discount`,`item_supplier`,`min_order`) "
                    + "VALUES ('" + request.getParameter("itemid") + "','" + request.getParameter("shopid") + "','" + request.getParameter("sellertype") + "','" + request.getParameter("unitcost") + "','" + request.getParameter("sp") + "','" + request.getParameter("qtyonhand") + "','" + request.getParameter("qtyonorder") + "','" + request.getParameter("discount") + "','" + request.getParameter("item_supplier") + "','" + request.getParameter("min_order") + "');");

            if (ab == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
            }
        } else if (request.getParameter("opt").equalsIgnoreCase("addshop")) {
            int ab = con.createStatement().executeUpdate("INSERT INTO `shop` (`ShopName`,`ShopType`,`ShopOwner`,`description`,`status`,`expirydate`,`paymentemail`,categoriesallowed)  VALUES('" + request.getParameter("name") + "','" + request.getParameter("type") + "','" + request.getParameter("id") + "','" + request.getParameter("desc") + "','added',date_add(CURDATE(), INTERVAL " + request.getParameter("days") + " DAY),'" + request.getParameter("email") + "','" + request.getParameter("no") + "');");

            if (ab == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
                out.println(new JSONObject().put("Status", "Values not inserted"));
            }


        } else if (request.getParameter("opt").equalsIgnoreCase("update")) {
            int ab = con.createStatement().executeUpdate("UPDATE `b2b`.`shop` SET `ShopName` = '" + request.getParameter("name") + "',`description` = '" + request.getParameter("desc") + "',`paymentemail` = '" + request.getParameter("paymentemail") + "',`categoriesallowed` = '" + request.getParameter("noofcategories") + "' wHERE `idshop` = '" + request.getParameter("id") + "';");
            if (ab == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
            }
        } else if (request.getParameter("opt").equalsIgnoreCase("deleteitem")) {
            int ab = con.createStatement().executeUpdate("UPDATE `b2b`.`item` SET status = 'deactivated' WHERE `iditem` = '" + request.getParameter("id") + "';");

            if (ab == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
            }
        } else if (request.getParameter("opt").equalsIgnoreCase("selectall")) {
            JSONObject as = new JSONObject();
            ResultSet rs5 = con.createStatement().executeQuery("select idshop,shopname from shop");
            while (rs5.next()) {
                as.put(rs5.getString(1), rs5.getString(2));
            }
            out.println(as);
        } else if(request.getParameter("opt").equalsIgnoreCase("retail"))
        {
            JSONArray ar=new JSONArray();
            ResultSet rs=con.createStatement().executeQuery("SELECT * FROM item,item_shop where item_shop.sellertype='Retailer' and  iditem=item_shop.itemid");
            ResultSetMetaData rsmd=rs.getMetaData();
            while(rs.next())
            {
                JSONObject ob=new JSONObject();
                for(int i=1;i<=rsmd.getColumnCount();i++)
                {
                ob.put(rsmd.getColumnName(i), rs.getString(i));
                }
                ar.put(ob);
            }
            out.println(ar);
        
        
        }

    }
    else if(request.getParameter("shopid")!=null)
    {
    ResultSet rs=con.createStatement().executeQuery("SELECT paymentemail FROM b2b.shop where idshop="+request.getParameter("shopid"));
    while(rs.next())
    {
    out.println(new JSONArray().put(rs.getString(1)));
    }
    }
%>
<%!
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
%>