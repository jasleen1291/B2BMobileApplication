<%-- 
    Document   : getCountryList
    Created on : May 5, 2013, 12:08:10 AM
    Author     : jasleen
--%>

<%@page import="java.sql.*"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="application/json" pageEncoding="UTF-8" import="Db.DbConnector"%>
<%
JSONObject root=new JSONObject();
Connection con=DbConnector.initConnection();
ResultSet rs=con.createStatement().executeQuery("select * from country");
while(rs.next())
{
    root.put(rs.getString(1), rs.getString(2));
}
out.println(root);
%>
