<%-- 
    Document   : getCityList
    Created on : May 5, 2013, 1:03:01 AM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.*"%>
<%@page import="org.json.JSONArray"%>
<%@page contentType="application/json" pageEncoding="UTF-8" import="Db.DbConnector"%>

<%
    JSONObject root = new JSONObject();
    Connection con = DbConnector.initConnection();
    ResultSet rs = con.createStatement().executeQuery("select distinct(state) from city where countrycode='" + request.getParameter("cc") + "'");
    while (rs.next()) {
       JSONArray as=new JSONArray();
       ResultSet rs1=con.createStatement().executeQuery("select name from city where state='"+rs.getString(1)+"'");
       while(rs1.next())
       {
       as.put(rs1.getString(1));
       }
       root.put(rs.getString(1),as);
    }
    out.println(root);
%>

