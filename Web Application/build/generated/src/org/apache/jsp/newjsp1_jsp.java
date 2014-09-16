package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;

public final class newjsp1_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("\n");
      out.write("        ");

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
