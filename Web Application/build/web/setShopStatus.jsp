<%-- 
    Document   : getRetailers
    Created on : May 14, 2013, 5:25:38 PM
    Author     : jasleen
//SELECT idshop,shopname,username,masterid FROM shop,login where shoptype='Retailer' and shopowner=masterid and shop.status='added' and login.status='activated';
--%>
<%@page import="java.util.ArrayList"%>
<%-- 
    Document   : getCityList
    Created on : May 5, 2013, 1:03:01 AM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.*"%>
<%@page import="org.json.JSONArray"%>
<%@page contentType="application/json" pageEncoding="UTF-8" import="Db.DbConnector"%>

<% if(request.getParameter("id")!=null&&request.getParameter("status")!=null)
{
    
    Connection con = DbConnector.initConnection();
    int a=con.createStatement().executeUpdate("UPDATE `b2b`.`shop` SET `status` = '"+request.getParameter("status")+"' WHERE `idshop` = '"+request.getParameter("id")+"'");
    if(a==1)
    {
    out.println(new JSONObject().put("Status", "Values inserted"));
    }
    else
    {
    out.println(new JSONObject().put("Status", "Values not inserted"));
    }
}

%>


