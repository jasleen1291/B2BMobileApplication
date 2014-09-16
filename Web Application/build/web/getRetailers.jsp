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

<% if(request.getParameter("opt")!=null&&request.getParameter("retailerstatus")!=null)
{
    JSONObject root = new JSONObject();
    ArrayList<String> item=new ArrayList<String>();
    ArrayList<JSONArray> obj=new ArrayList<JSONArray>();
    Connection con = DbConnector.initConnection();
    
    ResultSet rs = con.createStatement().executeQuery("SELECT idshop,shopname,username,masterid,shop.status FROM shop,login where shoptype='Retailer' and shopowner=masterid and login.status='"+request.getParameter("retailerstatus")+"'");
    while (rs.next()) {
       if(item.contains(rs.getString(3)))
       {
           int i=item.indexOf(rs.getString(3));
           JSONObject temp=new JSONObject();
           temp.put("idshop",rs.getString(1) );
           temp.put("shopname",rs.getString(2) );
           temp.put("shopstatus",rs.getString(5) );
           obj.get(i).put(temp);
       }
       else
       {
       item.add(rs.getString(3));
       JSONArray a=new JSONArray();
       JSONObject temp=new JSONObject();
       temp.put("idshop",rs.getString(1) );
       temp.put("shopname",rs.getString(2) );
       temp.put("shopstatus",rs.getString(5) );
       obj.add(a);
       a.put(temp);
       root.put(rs.getString(3), new JSONObject().put(rs.getString(4), a));
       }
       
    }
    out.println(root);
    
    
}

%>


