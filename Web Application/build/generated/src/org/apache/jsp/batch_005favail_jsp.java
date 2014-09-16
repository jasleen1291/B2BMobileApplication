package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import Db.DbConnector;
import java.sql.Connection;

public final class batch_005favail_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
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
