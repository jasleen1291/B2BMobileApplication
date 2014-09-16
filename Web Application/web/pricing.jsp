<%-- 
    Document   : pricing
    Created on : May 15, 2013, 2:18:07 AM
    Author     : jasleen
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.json.JSONObject"%>
<%@page import="Db.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%
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
                int a = con.createStatement().executeUpdate("INSERT INTO `fine` (`Finetype`,`cost`,`Description`) VALUES ('" + request.getParameter("type") + "','" + request.getParameter("cost") + "','" + request.getParameter("desc") + "')");
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
                    if (rs.getString(5).equalsIgnoreCase("Retailer")) {
                        JSONObject ob = new JSONObject();
                        ob.put("category", rs.getString(2));
                        ob.put("cost", rs.getString(3));
                        ob.put("days", rs.getString(4));
                        ob.put("desc", rs.getString(6));
                        add.put(rs.getString(1), ob);
                    } else if (rs.getString(5).equalsIgnoreCase("Supplier")) {
                        JSONObject ob = new JSONObject();
                        ob.put("category", rs.getString(2));
                        ob.put("cost", rs.getString(3));
                        ob.put("days", rs.getString(4));
                        ob.put("desc", rs.getString(6));
                        restore.put(rs.getString(1), ob);
                    } 
                }
                root.put("Retailer", add);
                root.put("Supplier", restore);
                
                out.println(root);
            } else if (request.getParameter("t").equals("fine")) {
                JSONObject root = new JSONObject();
                JSONObject retailer = new JSONObject();
                JSONObject supplier = new JSONObject();
                ResultSet rs=con.createStatement().executeQuery("select * from fine");
                while(rs.next())
                {
                    if(rs.getString(2).equalsIgnoreCase("retailer"))
                    {
                        JSONObject ob=new JSONObject();
                        
                        ob.put("cost", rs.getString(3));
                        ob.put("Description", rs.getString(4));
                        retailer.put(rs.getString(1),ob);
                    }
                    else
                    {
                        JSONObject ob=new JSONObject();
                        ob.put("cost", rs.getString(3));
                        ob.put("Description", rs.getString(4));
                        supplier.put(rs.getString(1),ob);
                    
                    }
                }
                root.put("Retailer", retailer);
                root.put("Supplier", supplier);
                out.println(root);
            }
            else if (request.getParameter("t").equals("shop")) {
                JSONObject root = new JSONObject();
                JSONObject retailer = new JSONObject();
                JSONObject supplier = new JSONObject();
                ResultSet rs=con.createStatement().executeQuery("select * from shoppricing");
                while(rs.next())
                {
                    if(rs.getString(6).equalsIgnoreCase("retailer"))
                    {
                        JSONObject ob=new JSONObject();
                        ob.put("name", rs.getString(2));
                        ob.put("cost", rs.getString(3));
                        ob.put("categories", rs.getString(4));
                        ob.put("time", rs.getString(5));
                        retailer.put(rs.getString(1),ob);
                    }
                    else
                    {
                        JSONObject ob=new JSONObject();
                        ob.put("name", rs.getString(2));
                        ob.put("cost", rs.getString(3));
                        ob.put("categories", rs.getString(4));
                        ob.put("time", rs.getString(5));
                        supplier.put(rs.getString(1),ob);
                    
                    }
                }
                root.put("Retailer", retailer);
                root.put("Supplier", supplier);
                out.println(root);
            }

        }
        else if(request.getParameter("opt").equals("update"))
            
        {
            if(request.getParameter("t").equals("advertisement"))
            {
                int a = con.createStatement().executeUpdate("UPDATE `b2b`.`advertisemntpricing` SET `cost` = '"+request.getParameter("cost")+"', `days` = '"+request.getParameter("days")+"',`Desc` = '"+request.getParameter("desc")+"' WHERE `id` = '"+request.getParameter("id")+"';");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }
            else if(request.getParameter("t").equals("fine"))
            {
                int a =con.createStatement().executeUpdate("UPDATE `b2b`.`fine` SET `cost` = '"+request.getParameter("cost")+"',`Description` = '"+request.getParameter("desc")+"' WHERE `idFine` = '"+request.getParameter("id")+"'");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }
            else if(request.getParameter("t").equalsIgnoreCase("shop"))
            {
            int a =con.createStatement().executeUpdate("UPDATE `b2b`.`shoppricing` SET `Name` = '"+request.getParameter("name")+"',`Cost` = '"+request.getParameter("cost")+"',`Categories` = '"+request.getParameter("categories")+"',`time` = '"+request.getParameter("time")+"' WHERE `idShopPricing` = '"+request.getParameter("id")+"'");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            
            }
        }
        else if(request.getParameter("opt").equals("delete"))
            
        {
            if(request.getParameter("t").equals("advertisement"))
            {
                int a = con.createStatement().executeUpdate("DELETE FROM `b2b`.`advertisemntpricing` WHERE `id` = '"+request.getParameter("id")+"';");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }
            else if(request.getParameter("t").equals("fine"))
            {
                int a = con.createStatement().executeUpdate("DELETE FROM `b2b`.`fine` WHERE `idFine` = '"+request.getParameter("id")+"';");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }
             else if(request.getParameter("t").equalsIgnoreCase("shop"))
            {
           int a = con.createStatement().executeUpdate("DELETE FROM `b2b`.`shoppricing` WHERE `idshoppricing` = '"+request.getParameter("id")+"';");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            
            }
        }
    }
%>