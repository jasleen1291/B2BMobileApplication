<%-- 
    Document   : getAdvetisement
    Created on : May 28, 2013, 2:19:20 AM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%
    Connection con = Db.DbConnector.initConnection();
    if (request.getParameter("type") != null) {
        JSONArray ar=new JSONArray();
        ResultSet rs4 = con.createStatement().executeQuery("SELECT * FROM b2b.advertisement where advertype='"+request.getParameter("type")+"' and enddate>now()");
         //ResultSet rs4 = con.createStatement().executeQuery("Select * from warehouse  where ownerid='" + request.getParameter("wid") + "' ");
        ResultSetMetaData rsmd4 = rs4.getMetaData();
        int count4 = rsmd4.getColumnCount();
        
        while (rs4.next()) {
            JSONObject ob = new JSONObject();
            for (int i = 1; i <= count4; i++) {
                
               
                ob.put(rsmd4.getColumnLabel(i), rs4.getString(rsmd4.getColumnLabel(i)));

            }
          ar.put(ob);
        }
        out.println(ar);
    }
%>