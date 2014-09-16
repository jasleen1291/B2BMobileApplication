<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.*"%>
<%@page import="org.json.JSONArray"%>
<%@page contentType="application/json" pageEncoding="UTF-8" import="Db.DbConnector"%>

<%
    if (request.getParameter("opt") != null) {
        Connection con = DbConnector.initConnection();
        if (request.getParameter("opt").equals("update")) {
            if (request.getParameter("id") != null && request.getParameter("status") != null) {

                
                int a = con.createStatement().executeUpdate("UPDATE `item` SET `status` = '" + request.getParameter("status") + "' WHERE `iditem` = '" + request.getParameter("id") + "'");
                if (a == 1) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                    out.println(new JSONObject().put("Status", "Values not inserted"));
                }
            }
        }
        else if(request.getParameter("opt").equals("getActive"))
        {
            JSONArray arr=new JSONArray();
            ResultSet rs=con.createStatement().executeQuery("select * from item where status='activated'");
            while(rs.next())
            {
            JSONObject temp=new JSONObject();
            temp.put("iditem", rs.getString(1));
            temp.put("itemname", rs.getString(2));
            temp.put("longdescription", rs.getString(3));
            temp.put("categoryid", rs.getString(4));
            temp.put("qtyperunit", rs.getString(5));
            temp.put("unit", rs.getString(6));
            temp.put("imagepath", rs.getString(7));
            arr.put(temp);
            }
            out.println(arr);
        }
        else if(request.getParameter("opt").equals("getDeleted"))
        {
        JSONArray arr=new JSONArray();
            ResultSet rs=con.createStatement().executeQuery("select * from item where status='deactivated'");
            while(rs.next())
            {
            JSONObject temp=new JSONObject();
            temp.put("iditem", rs.getString(1));
            temp.put("itemname", rs.getString(2));
            temp.put("longdescription", rs.getString(3));
            temp.put("categoryid", rs.getString(4));
            temp.put("qtyperunit", rs.getString(5));
            temp.put("unit", rs.getString(6));
            temp.put("imagepath", rs.getString(7));
            arr.put(temp);
            }
            out.println(arr);
        }
        else if(request.getParameter("opt").equals("search"))
        {
            String query=request.getParameter("query");
            String like="";
            String wrds[]=query.split("\\s+");
            for(int i=0;i<wrds.length;i++)
            {
                
                if(!wrds[i].replaceAll("[^\\p{Alnum}]+", "").isEmpty())
                    if(i!=wrds.length-1)
                    like=like+("%"+wrds[i].replaceAll("[^\\p{Alnum}]+", "")+"% and ");
                else
                      like=like+("%"+wrds[i].replaceAll("[^\\p{Alnum}]+", "")+"%");  
            }
            JSONArray arr=new JSONArray();
            ResultSet rs=con.createStatement().executeQuery("select * from item where status='activated' and itemname like '"+like+"'");
           
            while(rs.next())
            {
            JSONObject temp=new JSONObject();
            temp.put("iditem", rs.getString(1));
            temp.put("itemname", rs.getString(2));
            temp.put("longdescription", rs.getString(3));
            temp.put("categoryid", rs.getString(4));
            temp.put("qtyperunit", rs.getString(5));
            temp.put("unit", rs.getString(6));
            temp.put("imagepath", rs.getString(7));
            arr.put(temp);
            }
            out.println(arr);
        }
        else if(request.getParameter("opt").equals("cat"))
        {
        
            JSONArray arr=new JSONArray();
            ResultSet rs=con.createStatement().executeQuery("select * from item where status='activated' and categoryid='"+request.getParameter("catid")+"'");
           
            while(rs.next())
            {
            JSONObject temp=new JSONObject();
            temp.put("iditem", rs.getString(1));
            temp.put("itemname", rs.getString(2));
            temp.put("longdescription", rs.getString(3));
            temp.put("categoryid", rs.getString(4));
            temp.put("qtyperunit", rs.getString(5));
            temp.put("unit", rs.getString(6));
            temp.put("imagepath", rs.getString(7));
            arr.put(temp);
            }
            out.println(arr);
        }
    }
%>