<%-- 
    Document   : batch_avail
    Created on : May 28, 2013, 12:47:53 AM
    Author     : jasleen
--%>

<%@page import="Db.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Connection con = DbConnector.initConnection();
    if(request.getParameter("itemid")!=null&&request.getParameter("itemid").length()>0)
    {
    String itemshopid=request.getParameter("itemid");
if(request.getParameter("country")!=null)
            {
                String country[]=request.getParameterValues("country");
                String shipment[]=request.getParameterValues("shipment");
                for(int i=0;i<country.length;i++)
                {
                    //System.out.println("INSERT INTO `b2b`.`item_availability` (`item_shop`,`country`,`shippment`) VALUES ('"+itemshopid+"','"+country[i]+"','"+shipment[i]+"')");
                con.createStatement().executeUpdate("INSERT INTO `b2b`.`item_availability` (`item_shop`,`country`,`shippment`) VALUES ('"+itemshopid+"','"+country[i]+"','"+shipment[i]+"')");
                }
            }
            if(request.getParameter("qty")!=null)
            {
                String qty[]=request.getParameterValues("qty");
                String dis[]=request.getParameterValues("dis");
                for(int i=0;i<qty.length;i++)
                {
                    //System.out.println("INSERT INTO `batchdiscount` (`idbatchdiscount`, `qty`,`discount%`) VALUES ('"+itemshopid+"', '"+qty[i]+"', '"+dis[i]+"' );");
                con.createStatement().executeUpdate("INSERT INTO `batchdiscount` (`idbatchdiscount`, `qty`,`discount%`) VALUES ('"+itemshopid+"', '"+qty[i]+"', '"+dis[i]+"' );");
                
                }
            }
    }
%>
