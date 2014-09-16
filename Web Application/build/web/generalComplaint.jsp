<%-- 
    Document   : generalComplaint
    Created on : May 13, 2013, 3:01:51 PM
    Author     : jasleen
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.Connection"%>
<%@page import="Db.DbConnector"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%
if(request.getParameter("opt")!=null)
{
     Connection con=DbConnector.initConnection();
    if(request.getParameter("opt").equals("insert"))
    {
   
    int a=con.createStatement().executeUpdate("INSERT INTO complaint (complainttype,complainerid,date) VALUES('general',"+request.getParameter("complainerid")+",CURDATE())");
    int b=con.createStatement().executeUpdate("INSERT INTO general_complaint (`idcomplaint`,`title`,`desc`) VALUES(LAST_INSERT_ID(),'"+request.getParameter("title")+"','"+request.getParameter("desc")+"')");
    if(a==1&&b==1)
    {
    out.println(new JSONObject().put("Status", "Values inserted"));
    }
    else
    {
    out.println(new JSONObject().put("Status", "Values not inserted"));
    }
    }
    else
    {
        JSONObject ob=new JSONObject();
    ResultSet rs=con.createStatement().executeQuery("SELECT general_complaint.idComplaint ,masteruserinfo.email, `title`, `desc` FROM complaint,general_complaint,masteruserinfo where"+
 " complaint.idComplaint=general_complaint.idComplaint and complainerid=masterid and complaint.status=1 order by general_complaintid desc ;");
    while(rs.next())
    {
        JSONObject temp=new JSONObject();
        temp.put("email", rs.getString(2));
        temp.put("title", rs.getString(3));
        temp.put("desc", rs.getString(4));
        ob.put(rs.getString(1),temp);
    }
    out.println(ob);
    }
}
%>
