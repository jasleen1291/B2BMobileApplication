package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.sql.ResultSet;
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

public final class item_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
//Class.forName("com.mysql.jdbc.Driver");
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
            URL oracle = new URL("http://192.168.1.101:8080/B2B/batch_avail.jsp?itemid="+itemshopid);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					oracle.openStream()));

			String inputLine, result = "";
			while ((inputLine = in.readLine()) != null)
				result = result + inputLine;
			in.close();
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
