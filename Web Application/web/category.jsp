<%-- 
    Document   : category
    Created on : May 15, 2013, 3:31:27 AM
    Author     : jasleen
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.json.JSONObject"%>
<%@page import="Db.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%!Connection con = DbConnector.initConnection();%>
<%
    if (request.getParameter("opt") != null) {

        if (request.getParameter("opt").equals("insert")) {

            int a = con.createStatement().executeUpdate("INSERT INTO `category` (`Categoryname`,`ParentCategory`) VALUES('" + request.getParameter("name") + "','" + request.getParameter("parent") + "')");
            if (a == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
            }
        } else if (request.getParameter("opt").equals("select")) {
            JSONArray ad = new JSONArray();
            ResultSet rs = con.createStatement().executeQuery("select * from `category` where ParentCategory='0'");
            while (rs.next()) {
                JSONObject tr = new JSONObject();
                tr.put("id", rs.getString(1));
                tr.put("name", rs.getString(2));
                tr.put("children", fetchchildren(rs.getString(1)));
                ad.put(tr);
            }
            out.println(ad);
        }
        else if(request.getParameter("opt").equalsIgnoreCase("editcategories"))
        {
          con.createStatement().executeUpdate("DELETE FROM `b2b`.`category_user` WHERE masterid='"+request.getParameter("id")+"'");
          String ad[]=request.getParameterValues("catid");
          
          for(int i=0;i<ad.length;i++)
          {
          con.createStatement().executeUpdate("INSERT INTO `b2b`.`category_user` (`idCategory`,`masterid`) VALUES ('"+ad[i]+"','"+request.getParameter("id")+"');");
          }
          out.println(new JSONObject().put("Status", "Values inserted"));
        }
        else if(request.getParameter("opt").equalsIgnoreCase("selectcategories"))
        {
       // SELECT `idCategory` FROM `category_user` where `masterid`='';
            JSONArray a=new JSONArray();
            ResultSet rs=con.createStatement().executeQuery("SELECT `idCategory` FROM `category_user` where `masterid`='"+request.getParameter("id")+"'");
            while(rs.next())
            {
            a.put(rs.getString(1));
            }
            
        }
        else if(request.getParameter("opt").equalsIgnoreCase("gettc"))
        {
        JSONArray a=new JSONArray();
        a.put(fetchchildren("0"));
        out.println(fetchchildren("0"));
        }
            }


%>
<%!
    public JSONArray fetchchildren(String id) {
        try {
            JSONArray temp = new JSONArray();
            ResultSet resultSet = con.createStatement().executeQuery("select * from `category` where ParentCategory='" + id + "'");
            if (!resultSet.next()) {
                //temp.put(id, "null");
            } else {

                do {
                    JSONObject tr = new JSONObject();
                    tr.put("id", resultSet.getString(1));
                    tr.put("name", resultSet.getString(2));
                    tr.put("children", fetchchildren(resultSet.getString(1)));
                    temp.put(tr);
                } while (resultSet.next());

            }
            return temp;
        } catch (Exception e) {
            return new JSONArray().put(e.toString());
        }

    }
%>