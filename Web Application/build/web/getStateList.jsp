<%-- 
    Document   : getStateList
    Created on : May 5, 2013, 2:16:38 PM
    Author     : jasleen
--%>
<%@page import="java.sql.*"%>
<%@page import="org.json.JSONArray"%>
<%@page contentType="application/json" pageEncoding="UTF-8" import="Db.DbConnector"%>
<%
JSONArray root=new JSONArray();
Connection con=DbConnector.initConnection();
ResultSet rs=con.createStatement().executeQuery("select distinct(state) from b2b.city where countrycode='"+request.getParameter("cc")+"'");
while(rs.next())
{
    root.put(rs.getString(1));
}
out.println(root);
%>