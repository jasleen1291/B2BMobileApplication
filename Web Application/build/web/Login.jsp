<%-- 
    Document   : Login
    Created on : May 19, 2013, 4:02:41 AM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="Db.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>

<%
if(request.getParameter("opt")!=null)
{
    Connection con=DbConnector.initConnection();
if(request.getParameter("opt").equalsIgnoreCase("select"))
{
    JSONObject ob=new JSONObject();
    ResultSet rs=con.createStatement().executeQuery("SELECT * FROM b2b.login,masteruserinfo where username='"+request.getParameter("username")+"' and password='"+request.getParameter("password")+"';");
    ResultSetMetaData rsmd = rs.getMetaData();
    int count=rsmd.getColumnCount();
    if(rs.next())
    {
    for(int i=1;i<count;i++)
    { 
        //int j=i+1;
        ob.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));
    
    }
    out.println(ob);
    }
    else
    {
    out.println(ob.put("masterid","not found"));
    }
}
}

%>
