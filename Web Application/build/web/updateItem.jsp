<%-- 
    Document   : item
    Created on : May 13, 2013, 10:18:41 PM
    Author     : jasleen
--%>

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
            String id="",itemname = "",  longdescription = "",itemshopid="", shopid = "", qtyperunit = "", unit = "", filename = "",min_order="",sp="",discount="";
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
                            itemname = value;
                        }  else if (name.equals("longdescription")) {
                            longdescription = value;
                        } else if (name.equals("shopid")) {
                            shopid = value;
                        } else if (name.equals("qtyperunit")) {
                            qtyperunit = value;
                        } else if (name.equals("unit_of_measure")) {
                            unit = value;
                        } else if(name.equals("id"))
                        {
                        id = value;
                        }
                        else if(name.equals("itemshopid"))
                        {
                        itemshopid=value;
                        }
                        else if(name.equals("min_order"))
                        {
                        min_order=value;
                        }
                        else if(name.equals("sp"))
                        {
                        sp=value;
                        }
                        else if(name.equals("discount"))
                        {
                        discount=value;
                        }

                    } else {
                        try {
                            
                            if(item.getSize()>0)
                            {
                            ///File root=File.listRoots()[0];
                                 String timeStamp =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            filename = timeStamp+ new BigInteger(130, random).toString(32) + ".jpg";
                            f = new File("/home/jasleen/Dropbox/B2B/web/images/" + filename);
                            //f.createNewFile();
                            f.setWritable(true);
                            f.setReadable(true);
                            item.write(f);
                            //out.println(f.getPath());
                            }
                            else
                            {
                            filename="notavailable.jpg";
                            }

                        } catch (Exception e) {
                            out.println(e);
                        }
                    }

                }
                Connection con = DbConnector.initConnection();
               System.out.println("UPDATE `b2b`.`item` SET `itemname` = '"+itemname+"',`longdesciption` = '"+longdescription+"',`qtyperunit` = '"+qtyperunit+"',`unit_of_measurement` = '"+unit+"',`imagepath` = '"+filename+"' WHERE `iditem` = '"+id+"';");
               System.out.println("UPDATE `b2b`.`item_shop` SET  `sp` = '"+sp+"',`discount` = '"+discount+"',`min_order` = '"+min_order+"' WHERE `item_shopid` = '"+itemshopid+"' and `shopid` = '"+shopid+"'");
               int a = con.createStatement().executeUpdate("UPDATE `b2b`.`item` SET `itemname` = '"+itemname+"',`longdesciption` = '"+longdescription+"',`qtyperunit` = '"+qtyperunit+"',`unit_of_measurement` = '"+unit+"',`imagepath` = '"+filename+"' WHERE `iditem` = '"+id+"';");
               int b=con.createStatement().executeUpdate("UPDATE `b2b`.`item_shop` SET  `sp` = '"+sp+"',`discount` = '"+discount+"',`min_order` = '"+min_order+"' WHERE `item_shopid` = '"+itemshopid+"' and `shopid` = '"+shopid+"'");
               
               if (true) {
                    out.println(new JSONObject().put("Status", "Values inserted"));
                } else {
                    out.println(new JSONObject().put("Status", "Values not inserted"));
                }
            }



        %>
    </body>
</html>
