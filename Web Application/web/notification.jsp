<%-- 
    Document   : generalComplaint
    Created on : May 13, 2013, 3:01:51 PM
    Author     : jasleen
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.Connection"%>
<%@page import="Db.DbConnector"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%
    if (request.getParameter("opt") != null) {
        Connection con = DbConnector.initConnection();
        if (request.getParameter("opt").equals("insert")) {

            int a = con.createStatement().executeUpdate("INSERT INTO `b2b`.`notification` (`title`, `message`,`type`,`receiverid`) VALUES ('"+request.getParameter("title")+"','"+request.getParameter("message")+"','"+request.getParameter("type")+"','"+request.getParameter("rid")+"');");
            if (a == 1 ) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
                out.println(new JSONObject().put("Status", "Values not inserted"));
            }
        } else if(request.getParameter("opt").equals("select")){
            

            JSONArray ob = new JSONArray();
            ResultSet rs = con.createStatement().executeQuery("SELECT `title`,`message`,`type` FROM `b2b`.`notification` where `status`=1 and `receiverid`='"+request.getParameter("rid")+"'");
            while (rs.next()) {
              
                JSONObject temp=new JSONObject();
                temp.put("title", rs.getString(1));
                temp.put("message", rs.getString(2));
                temp.put("type", rs.getString(3));
                ob.put(temp);
                        
            }
            out.println(ob);
        }
    }
%>
