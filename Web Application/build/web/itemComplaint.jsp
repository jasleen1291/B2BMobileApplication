<%-- 
    Document   : itemComplaint
    Created on : May 13, 2013, 10:12:16 PM
    Author     : jasleen
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%-- 
    Document   : generalComplaint
    Created on : May 13, 2013, 3:01:51 PM
    Author     : jasleen
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.Connection"%>
<%@page import="Db.DbConnector"%>

<%
if(request.getParameter("opt")!=null)
{
     Connection con=DbConnector.initConnection();
    if(request.getParameter("opt").equals("insert"))
    {
  
    int a=con.createStatement().executeUpdate("INSERT INTO complaint (complainttype,complainerid,date) VALUES('item',"+request.getParameter("complainerid")+",CURDATE())");
    int b=con.createStatement().executeUpdate("INSERT INTO `itemcomplaint` ( `idComplaint`,`shopid`,`itemid`,`title`,`desc`) VALUES(LAST_INSERT_ID(),'"+request.getParameter("shopid")+"','"+request.getParameter("itemid")+"','"+request.getParameter("title")+"','"+request.getParameter("desc")+"')");
    if(a==1&&b==1)
    {
    out.println(new JSONObject().put("Status", "Values inserted"));
    }
    else
    {
    out.println(new JSONObject().put("Status", "Values not inserted"));
    }
    }
    else if(request.getParameter("opt").equals("selecti"))
    {
        ArrayList<String> itemnames=new ArrayList<String>();
        ArrayList<JSONArray> obs=new ArrayList<JSONArray>();
        
        JSONObject ob=new JSONObject();
    ResultSet rs=con.createStatement().executeQuery("select item.itemname,itemcomplaint.itemid,itemcomplaint.shopid,masteruserinfo.email,shopname,"+
 "`title`, `desc`,itemcomplaint.idComplaint FROM item,complaint,shop,itemcomplaint,masteruserinfo where  "+
"complaint.idComplaint=itemcomplaint.idComplaint and complainerid=masterid and complaint.status=1 and shopid=shop.idshop and itemid=iditem order by shopid;");
    while(rs.next())
    { 
        //out.println("hi");
    if(itemnames.contains(rs.getString(1)))
    {
        
        int index=itemnames.indexOf(rs.getString(1));
        JSONArray ad=obs.get(index);
        JSONObject temp=new JSONObject();
        temp.put("email", rs.getString(4));
        temp.put("shopname", rs.getString(5));
        temp.put("title",rs.getString(6));
        temp.put("desc",rs.getString(7));
        temp.put("itemid",rs.getString(2));
        temp.put("shopid",rs.getString(3));
        temp.put("idComplaint", rs.getString(8));
        ad.put(temp);
    }
    else
    {
        itemnames.add(rs.getString(1));
        JSONObject temp=new JSONObject();
        temp.put("email", rs.getString(4));
        temp.put("shopname", rs.getString(5));
        temp.put("title",rs.getString(6));
        temp.put("desc",rs.getString(7));
        temp.put("itemid",rs.getString(2));
        temp.put("shopid",rs.getString(3));
        temp.put("idComplaint", rs.getString(8));
        JSONArray a=new JSONArray();
        a.put(temp);
        obs.add(a);
    }
        for(int i=0;i<itemnames.size();i++)
        {
        ob.put(itemnames.get(i),obs.get(i));
        }
        //ob.put(i+"",temp);
    }
    out.println(ob);
    }
    else
    {
    ArrayList<String> itemnames=new ArrayList<String>();
        ArrayList<JSONArray> obs=new ArrayList<JSONArray>();
        
        JSONObject ob=new JSONObject();
    ResultSet rs=con.createStatement().executeQuery("select item.itemname,itemcomplaint.itemid,itemcomplaint.shopid,masteruserinfo.email,shopname,"+
 "`title`, `desc`,itemcomplaint.idComplaint FROM item,complaint,shop,itemcomplaint,masteruserinfo where  "+
"complaint.idComplaint=itemcomplaint.idComplaint and complainerid=masterid and complaint.status=1 and shopid=shop.idshop and itemid=iditem order by shopid;");
    while(rs.next())
    { 
        //out.println("hi");
    if(itemnames.contains(rs.getString(5)))
    {
        
        int index=itemnames.indexOf(rs.getString(5));
        JSONArray ad=obs.get(index);
        JSONObject temp=new JSONObject();
        temp.put("email", rs.getString(4));
        temp.put("itemid", rs.getString(2));
        temp.put("title",rs.getString(6));
        temp.put("desc",rs.getString(7));
        temp.put("itemname",rs.getString(1));
        temp.put("idComplaint", rs.getString(8));
        temp.put("shopid",rs.getString(3));
        ad.put(temp);
    }
    else
    {
        itemnames.add(rs.getString(5));
        JSONObject temp=new JSONObject();
        temp.put("email", rs.getString(4));
        temp.put("itemid", rs.getString(2));
        temp.put("title",rs.getString(6));
        temp.put("desc",rs.getString(7));
        temp.put("itemname",rs.getString(1));
        temp.put("shopid",rs.getString(3));
        temp.put("idComplaint", rs.getString(8));
        JSONArray a=new JSONArray();
        a.put(temp);
        obs.add(a);
    }
        for(int i=0;i<itemnames.size();i++)
        {
        ob.put(itemnames.get(i),obs.get(i));
        }
        //ob.put(i+"",temp);
    }
    out.println(ob);
    }
}
%>

