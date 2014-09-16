<%-- 
    Document   : newjsp1
    Created on : May 24, 2013, 10:36:12 PM
    Author     : jasleen
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

        <%
            try {
			URL oracle = new URL("http://192.168.1.101:8080/B2B/shop.jsp?opt=myshops&id=21");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					oracle.openStream()));

			String inputLine, result = "";
			while ((inputLine = in.readLine()) != null)
				result = result + inputLine;
			in.close();
			JSONArray array = new JSONArray(result);
			
		
			JSONObject ob = array.getJSONObject(0);
		
			
			JSONArray ard = ob.getJSONArray("available");
			for (int i = 0; i < ard.length(); i++) {
                            //ard.getJSONObject(i).getString("iditem")+"'>"+ard.getJSONObject(i).getString("itemname")+"</option>");
                            URL oracle2 = new URL("http://192.168.1.101:8080/B2B/shop.jsp?opt=additem&shopid=3&sellertype=Supplier&unitcost=100&sp=150&qtyonhand=0&qtyonorder=0&discount=0&item_supplier=-1&min_order=500&itemid="+ard.getJSONObject(i).getString("iditem"));

			BufferedReader in2 = new BufferedReader(new InputStreamReader(
					oracle2.openStream()));
                        out.println(ard.getJSONObject(i).getString("iditem"));
			
                        }}
                catch(Exception e)
                {
                System.out.println(e.toString());
                }
        
        %>
