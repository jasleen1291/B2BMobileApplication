<%-- 
    Document   : warehouse
    Created on : May 19, 2013, 5:55:32 PM
    Author     : jasleen
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.json.JSONObject"%>
<%@page import="Db.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%
    Connection con = DbConnector.initConnection();
    if (request.getParameter("warehouse") != null) {
        if (request.getParameter("opt") != null) {
            if (request.getParameter("opt").equalsIgnoreCase("add")) {
                int a = con.createStatement().executeUpdate("INSERT INTO `warehouse` (`warehousename`,`address`,`city`,`state`,`country`,`zip`,`ownerid`) VALUES ('" + request.getParameter("name") + "','" + request.getParameter("address") + "','" + request.getParameter("city") + "','" + request.getParameter("state") + "','" + request.getParameter("country") + "','" + request.getParameter("zip") + "','" + request.getParameter("id") + "');");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }

        }
    } else if (request.getParameter("contact") != null) {
        if (request.getParameter("opt") != null) {
            if (request.getParameter("opt").equalsIgnoreCase("add")) {
                int a = con.createStatement().executeUpdate("INSERT INTO `b2b`.`contact`"
                        + "(`empid`,`Name`,`email`,`phone1`,`phone2`,`ContactTitle`)" + " VALUES("
                        + request.getParameter("id") + ","
                        + request.getParameter("name") + ","
                        + request.getParameter("email") + ","
                        + request.getParameter("phone1") + ","
                        + request.getParameter("phone2") + ","
                        + request.getParameter("title") + ")");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }
            if (request.getParameter("opt").equalsIgnoreCase("addw")) {
                int a = con.createStatement().executeUpdate("INSERT INTO `warehouse_contact` (`idwarehouse`,`idcontact`) VALUES  ('" + request.getParameter("wid") + "','" + request.getParameter("cid") + "');");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
            }


        }
    }
    if (request.getParameter("opt").equalsIgnoreCase("selectc")) {
        JSONArray ar=new JSONArray();
        ResultSet rs = con.createStatement().executeQuery("Select * from contact where contact.idcontact in (Select idcontact from warehouse_contact where idwarehouse='" + request.getParameter("wid") + "' )");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        boolean a=false;
        while (rs.next()) {
            JSONObject ob = new JSONObject();
            for (int i = 1; i < count; i++) {
                
                a=true;
                ob.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));

            }
            ar.put(ob);
        }
        
        if(!a)
        {
        out.println(ar.put("No records found"));
        }
        else
        {
             out.println(ar);
        }

    }
    else if (request.getParameter("opt").equalsIgnoreCase("selectw")) {
        JSONArray ar=new JSONArray();
        ResultSet rs = con.createStatement().executeQuery("Select * from warehouse  where ownerid='" + request.getParameter("wid") + "' ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        boolean a=false;
        while (rs.next()) {
            JSONObject ob = new JSONObject();
            for (int i = 1; i < count; i++) {
                
                a=true;
                ob.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));

            }
            ar.put(ob);
        }
        
        if(!a)
        {
        out.println(ar.put(new JSONObject().put("idwarehouse", "-1")));
        }
        else
        {
             out.println(ar);
        }

    }
    else if(request.getParameter("opt").equalsIgnoreCase("update"))
    {
    
    int a=con.createStatement().executeUpdate("UPDATE `warehouse` SET  `warehousename` = '"+request.getParameter("name")+"',`address` = '" + request.getParameter("address") + "',`city` = '" + request.getParameter("city") + "',`state` = '" + request.getParameter("state") + "',`country` = '" + request.getParameter("country") + "',`zip` = '" + request.getParameter("zip") + "'  WHERE `idwarehouse` = '" + request.getParameter("id") + "'");
    if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                }
    }
%>
