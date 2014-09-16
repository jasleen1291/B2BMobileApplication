package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
import java.util.Date;
import java.sql.Connection;
import Db.DbConnector;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Iterator;
import java.io.File;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.*;

public final class newAdvertisement_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("application/json;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
//Class.forName("com.mysql.jdbc.Driver");
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

       
        if (a == 1) {
            out.println(new JSONObject().put("Status", "Values inserted"));
        } else {
            out.println(new JSONObject().put("Status", "Values not inserted"));
        }
    }




      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
