<%-- 
    Document   : userInfo
    Created on : May 16, 2013, 5:11:49 AM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="Db.DbConnector"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%
    Connection con=DbConnector.initConnection();
if(request.getParameter("id")!=null)
{
ResultSet rs=con.createStatement().executeQuery("Select email from masteruserinfo where masterid='"+request.getParameter("id")+"'");
if(rs.next())
{
out.println(new JSONObject().put("User", rs.getString(1)));
}
else
{
out.println(new JSONObject().put("User", "-1"));
}
}

if(request.getParameter("email")!=null)
{
ResultSet rs=con.createStatement().executeQuery("Select masterid from masteruserinfo where email='"+request.getParameter("email")+"'");
if(rs.next())
{
out.println(new JSONObject().put("User", rs.getString(1)));
}
else
{
out.println(new JSONObject().put("User", "-1"));
}
}
%>
