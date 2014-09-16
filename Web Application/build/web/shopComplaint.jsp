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

            int a = con.createStatement().executeUpdate("INSERT INTO complaint (complainttype,complainerid,date) VALUES('shop'," + request.getParameter("complainerid") + ",CURDATE())");
            int b = con.createStatement().executeUpdate("INSERT INTO shopcomplaint (`idcomplaint`,`title`,`desc`,`complainabout`) VALUES(LAST_INSERT_ID(),'" + request.getParameter("title") + "','" + request.getParameter("desc") + "','" + request.getParameter("shopid") + "')");
            if (a == 1 && b == 1) {
                out.println(new JSONObject().put("Status", "Values inserted"));
            } else {
                out.println(new JSONObject().put("Status", "Values not inserted"));
            }
        } else {
            ArrayList<String> itemnames = new ArrayList<String>();
            ArrayList<JSONArray> obs = new ArrayList<JSONArray>();

            JSONObject ob = new JSONObject();
            ResultSet rs = con.createStatement().executeQuery("SELECT complainabout,shopname ,masteruserinfo.email, `title`, `desc` ,complaint.idComplaint "
                    + "FROM complaint,shopcomplaint,masteruserinfo,shop where "
                    + "complaint.idComplaint=shopcomplaint.idComplaint "
                    + "and complainerid=masterid and complaint.status=1 and complainabout=idshop "
                    + "order by shopcomplaintid desc");
            while (rs.next()) {
                //out.println("hi");
                if (itemnames.contains(rs.getString(2))) {

                    int index = itemnames.indexOf(rs.getString(2));
                    JSONArray ad = obs.get(index);
                    JSONObject temp = new JSONObject();
                    temp.put("email", rs.getString(3));
                   // temp.put("shopname", rs.getString(2));
                    temp.put("title", rs.getString(4));
                    temp.put("desc", rs.getString(5));
                    temp.put("id", rs.getString(6));
                    temp.put("shopid", rs.getString(1));
                    ad.put(temp);
                } else {
                    itemnames.add(rs.getString(2));
                    JSONObject temp = new JSONObject();
                    temp.put("email", rs.getString(3));
                    //temp.put("shopname", rs.getString(2));
                    temp.put("title", rs.getString(4));
                    temp.put("desc", rs.getString(5));
                    temp.put("id", rs.getString(6));
                    temp.put("shopid", rs.getString(1));
                    JSONArray a = new JSONArray();
                    a.put(temp);
                    obs.add(a);
                }
                for (int i = 0; i < itemnames.size(); i++) {
                    ob.put(itemnames.get(i), obs.get(i));
                }
                //ob.put(i+"",temp);
            }
            out.println(ob);
        }
    }
%>
