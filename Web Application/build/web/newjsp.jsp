<%-- 
    Document   : newjsp
    Created on : May 18, 2013, 1:57:23 PM
    Author     : jasleen
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="Db.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
         Connection con = DbConnector.initConnection();
       // INSERT INTO `b2b`.`item_availability` (`item_shop`,`country`,`shippment`) VALUES (<{item_shop: }>,<{country: }>,<{shippment: }>);
         ResultSet rs=con.createStatement().executeQuery("SELECT `item_shop`.`item_shopid` FROM `b2b`.`item_shop`;");
         while(rs.next())
         {
             out.println(rs.getString(1));
         con.createStatement().executeUpdate("INSERT INTO `b2b`.`item_availability` (`item_shop`,`country`,`shippment`) VALUES ('"+rs.getString(1)+"','USA','0')");
         }
        %>
    </body>
</html>
