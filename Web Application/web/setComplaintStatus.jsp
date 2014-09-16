<%-- 
    Document   : setComplaintStatus
    Created on : May 15, 2013, 11:37:42 PM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="Db.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%
if(request.getParameter("id")!=null)
{
    try
    {
    Connection con=DbConnector.initConnection();
    int a=con.createStatement().executeUpdate("UPDATE `complaint` SET `status` = '2' WHERE `idComplaint` = '"+request.getParameter("id")+"';");
    if (a == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
                out.println(new JSONObject().put("Status", "UPDATE `complaint` SET `status` = '2' WHERE `idComplaint` = '"+request.getParameter("id")+"';"));
            }
    }
    catch(Exception e)
    {
     out.println(new JSONObject().put("Error", e.toString()));
    }
}
%>
