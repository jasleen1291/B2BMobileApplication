<%-- 
    Document   : getCityList
    Created on : May 5, 2013, 1:03:01 AM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.*"%>
<%@page import="org.json.JSONArray"%>
<%@page contentType="application/json" pageEncoding="UTF-8" import="Db.DbConnector"%>

<% if(request.getParameter("opt")!=null)
{
    JSONArray root = new JSONArray();
    Connection con = DbConnector.initConnection();
    if(request.getParameter("opt").equalsIgnoreCase("ac"))
    {
    ResultSet rs = con.createStatement().executeQuery("SELECT masterid,username,email FROM customerlist where usertype='"+request.getParameter("usertype")+"' and status='activated'");
   // out.println("SELECT masterid,username FROM customerlist where usertype='"+request.getParameter("usertype")+"' and status='activated'");
    while (rs.next()) {
       JSONObject ob=new JSONObject();
       ob.put("masterid", rs.getString(1));
       ob.put("username",rs.getString(2));
       ob.put("email",rs.getString(3));
       root.put(ob);
    }
    out.println(root);
    }
    else if(request.getParameter("opt").equalsIgnoreCase("deac"))
    {
    ResultSet rs = con.createStatement().executeQuery("SELECT masterid,username,email FROM customerlist where usertype='"+request.getParameter("usertype")+"' and status='deactivated'");
    while (rs.next()) {
       JSONObject ob=new JSONObject();
       ob.put("masterid", rs.getString(1));
       ob.put("username",rs.getString(2));
       ob.put("email",rs.getString(3));
       root.put(ob);
    }
    out.println(root);
  
    }
}

%>

