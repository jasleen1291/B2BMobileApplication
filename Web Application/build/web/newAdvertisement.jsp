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
    String days="",filename="",advertype="",advertiserid="",title="",desc="",catid="";
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
                if (name.equals("days")) {
                    days = value;
                } else if (name.equals("advertype")) {
                    advertype = value;
                } else if (name.equals("id")) {
                   advertiserid= value;
                } else if (name.equals("title")) {
                    title = value;
                } else if (name.equals("desc")) {
                    desc = value;
                } else if (name.equals("catid")) {
                   catid = value;
                } 


            } else {
                try {

                    if (item.getSize() > 0) {
                        ///File root=File.listRoots()[0];
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        filename = timeStamp + new BigInteger(130, random).toString(32) + ".jpg";
                        f = new File("/home/jasleen/Dropbox/B2B/web/images/advertisement/" + filename);
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
        int a = con.createStatement().executeUpdate("INSERT INTO `advertisement` (`advertype`,`advertiserid`,`title`,`desc`,`bannerPath`,`startdate`,`catid`,`enddate`) VALUES ('"+advertype+"','"+advertiserid+"','"+title+"','"+desc+"','"+filename+"',CURDATE(),'"+catid+"',date_add(CURDATE(), INTERVAL " + days + " DAY));");

       System.out.println("INSERT INTO `advertisement` (`advertype`,`advertiserid`,`title`,`desc`,`bannerPath`,`startdate`,`catid`,`enddate`) VALUES ('"+advertype+"','"+advertiserid+"','"+title+"','"+desc+"','"+filename+"',CURDATE(),'"+catid+"',date_add(CURDATE(), INTERVAL " + days + " DAY));");
        if (a == 1) {
            out.println(new JSONObject().put("Status", "Values inserted"));
        } else {
            out.println(new JSONObject().put("Status", "Values not inserted"));
        }
    }



%>
