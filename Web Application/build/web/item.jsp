<%-- 
    Document   : item
    Created on : May 13, 2013, 10:18:41 PM
    Author     : jasleen
--%>

<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Connection"%>
<%@page import="Db.DbConnector"%>
<%@page import="java.math.BigInteger"%>
<%@page import="java.security.SecureRandom"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.io.File" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.*"%>




<%//Class.forName("com.mysql.jdbc.Driver");
    File f;
    SecureRandom random = new SecureRandom();
    //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "admin");
    String itemname = "", longdescription = "", categoryid = "",
            cp = "", qtyperunit = "", unit = "", filename = "", 
            owner = "", shopid = "", sellertype = "", sp = "",
            discount = "", supplier = "", minorder = "";
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    if (!isMultipart) {
    } else {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator itr = items.iterator();
        while (itr.hasNext()) {
            FileItem item = (FileItem) itr.next();
            if (item.isFormField()) {
                String name = item.getFieldName();
                String value = item.getString();
                if (name.equals("itemname")) {
                    itemname = value.replace("'", "");
                } else if (name.equals("longdescription")) {
                    longdescription = value.replace("'", "");
                } else if (name.equals("categoryid")) {
                    categoryid = value;
                } else if (name.equals("qtyperunit")) {
                    qtyperunit = value;
                } else if (name.equals("unit_of_measure")) {
                    unit = value;
                } else if (name.equals("ownerid")) {
                    owner = value;
                } else if (name.equals("shopid")) {
                    shopid = value;
                } else if (name.equals("sellertype")) {
                    sellertype = value;
                } else if (name.equals("cp")) {
                    cp = value;
                } else if (name.equals("sp")) {
                    sp = value;
                } else if(name.equals("discount")){
                    discount=value;
                }else if(name.equals("supplier"))
                {
                supplier=value;
                }
                else if(name.equals("minorder"))
                {
                    minorder=value;
                }


            } else {
                try {

                    if (item.getSize() > 0) {
                        ///File root=File.listRoots()[0];
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        filename = timeStamp + new BigInteger(130, random).toString(32) + ".jpg";
                        f = new File("/home/jasleen/Dropbox/B2B/web/images/" + filename);
                        f.createNewFile();
                        f.setWritable(true);
                        f.setReadable(true);
                        item.write(f);
                        //out.println(f.getPath());
                    } else {
                        filename = "notavailable.jpg";
                    }

                } catch (Exception e) {
                    out.println(e);
                }
            }

        }
        Connection con = DbConnector.initConnection();
        int a = con.createStatement().executeUpdate("INSERT INTO `item` ( `itemname`,`longdesciption`,"
                + "`categoryid`, `qtyperunit`,`unit_of_measurement`,`imagepath`,owner) VALUES ('" + itemname + "',"
                + "'" + longdescription + "','" + categoryid + "','" + qtyperunit + "','" + unit + "','" + filename + "','" + owner + "')");

        int ab = con.createStatement().executeUpdate("INSERT INTO `b2b`.`item_shop` (`itemid`,`shopid`,`sellertype`,`unitcost`,`sp`,`qtyonhand`,`qtyonorder`,`discount`,`item_supplier`,`min_order`) "
                + "VALUES (LAST_INSERT_ID(),'" + shopid + "','" + sellertype + "','" + cp + "','" + sp + "','0','0','" + discount + "','" + supplier + "','" + minorder+ "');");
        String itemshopid=""; 
        ResultSet rs = con.createStatement().executeQuery("select LAST_INSERT_ID()");
            if (rs.next()) {
               itemshopid = rs.getString(1);
            }
            
        if (a == 1) {
            out.println(new JSONObject().put("ID", itemshopid));
        } else {
            out.println(new JSONObject().put("Status", "Values not inserted"));
        }
    }



%>
