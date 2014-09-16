/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author jasleen
 */
public class signup extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    HttpServletRequest req;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        req = request;
        PrintWriter out = response.getWriter();
        try {
            if (request.getParameter("opt").equals("avail")) {
                out.println(checkAvail());
            } else if (request.getParameter("opt").equals("signup")) {
                if (request.getParameter("type").equals("customer")) {
                    out.println(asCustomer());
                } else if (request.getParameter("type").equalsIgnoreCase("retailer")) {
                    out.println(asRetailer());
                } else if (request.getParameter("type").equalsIgnoreCase("supplier")) {
                    out.println(asSupplier());
                }
            } else if (request.getParameter("opt").equals("update")) {
                out.println(updateProfile());
            }
        } catch (Exception e) {
            out.println(e);
        }
    }
    Connection con = Db.DbConnector.initConnection();

    public JSONObject asCustomer() {
        JSONObject ob = new JSONObject();
        try {
            con.createStatement().executeUpdate("INSERT INTO `b2b`.`masteruserinfo` ( `email`) VALUES ( '"+
                    req.getParameter("email")+"')");
                   
            ResultSet rs = con.createStatement().executeQuery("select max(masterid) from masteruserinfo");
            while (rs.next()) {
                con.createStatement().executeUpdate("INSERT INTO `login`"
                        + "(`masterid`, `username`,`password`, `usertype`)"
                        + "VALUES ('"
                        + rs.getString(1) + "' , '" + req.getParameter("username") + "' , '"
                        + req.getParameter("password")
                        + "' , 'customer'"
                        + ")");
                ob.put("Status", "Successful");
            }
        } catch (Exception e) {
            ob.put("Status", e);
        } finally {
            return ob;
        }

    }

    public JSONObject asRetailer() {
        JSONObject ob = new JSONObject();
        try {
            con.createStatement().executeUpdate("INSERT INTO `b2b`.`masteruserinfo` (`FirstName` ,"
                    + "`LastName`, `email`,`phone1`,`phone2`,`address`,`city`,`state`,`country`,`zipcode`, "
                    + "`balance`) VALUES ('" + req.getParameter("fname") + "' , '" + req.getParameter("lname") + "' , '"
                    + req.getParameter("email") + "' , '" + req.getParameter("phone1") + "' , '"
                    + req.getParameter("phone2") + "' , '" + req.getParameter("address") + "' , '" + req.getParameter("city") + "' , '"
                    + req.getParameter("state") + "' , '" + req.getParameter("country") + "' , '" + req.getParameter("zip") + "' , '"
                    + req.getParameter("bal") + "')");
            ResultSet rs = con.createStatement().executeQuery("select max(masterid) from masteruserinfo");
            while (rs.next()) {
                con.createStatement().executeUpdate("INSERT INTO `login`"
                        + "(`masterid`, `username`,`password`, `usertype`)"
                        + "VALUES ('"
                        + rs.getString(1) + "' , '" + req.getParameter("username") + "' , '"
                        + req.getParameter("password")
                        + "' , 'Retailer'"
                        + ")");
                ob.put("Status", "Successful");
            }
        } catch (Exception e) {
            ob.put("Status", "INSERT INTO `b2b`.`masteruserinfo` (`FirstName` ,"
                    + "`LastName`, `email`,`phone1`,`phone2`,`address`,`city`,`state`,`country`,`zipcode`, "
                    + "`balance`) VALUES ('" + req.getParameter("fname") + "' , '" + req.getParameter("lname") + "' , '"
                    + req.getParameter("email") + "' , '" + req.getParameter("phone1") + "' , '"
                    + req.getParameter("phone2") + "' , '" + req.getParameter("address") + "' , '" + req.getParameter("city") + "' , '"
                    + req.getParameter("state") + "' , '" + req.getParameter("country") + "' , '" + req.getParameter("zip") + "' , '"
                    + req.getParameter("bal") + "')");
        } finally {
            return ob;
        }

    }

    public JSONObject asSupplier() {
        JSONObject ob = new JSONObject();
        try {
            con.createStatement().executeUpdate("INSERT INTO `b2b`.`masteruserinfo` (`FirstName` ,"
                    + "`LastName`, `email`,`phone1`,`phone2`,`address`,`city`,`state`,`country`,`zipcode`, "
                    + "`balance`) VALUES ('" + req.getParameter("fname") + "' , '" + req.getParameter("lname") + "' , '"
                    + req.getParameter("email") + "' , '" + req.getParameter("phone1") + "' , '"
                    + req.getParameter("phone2") + "' , '" + req.getParameter("address") + "' , '" + req.getParameter("city") + "' , '"
                    + req.getParameter("state") + "' , '" + req.getParameter("country") + "' , '" + req.getParameter("zip") + "' , '"
                    + req.getParameter("bal") + "')");
            ResultSet rs = con.createStatement().executeQuery("select max(masterid) from masteruserinfo");
            while (rs.next()) {
                con.createStatement().executeUpdate("INSERT INTO `login`"
                        + "(`masterid`, `username`,`password`, `usertype`)"
                        + "VALUES ('"
                        + rs.getString(1) + "' , '" + req.getParameter("username") + "' , '"
                        + req.getParameter("password")
                        + "' , 'Supplier'"
                        + ")");
                ob.put("Status", "Successful");
            }
        } catch (Exception e) {
            ob.put("Status", e);
        } finally {
            return ob;
        }
    }

    public JSONObject checkAvail() {
        JSONObject ob = new JSONObject();

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT EXISTS(select username from login where username like '" + req.getParameter("username") + "')");
            if (rs.next()) {
                if (rs.getString(1).equals("0")) {
                    ob.put("Status", "Available");
                } else {
                    ob.put("Status", "Not Available");
                }
            }
        } catch (Exception e) {
            ob.put("Error", "Some error encountered  Try again later");
        } finally {
            return ob;
        }
    }

    public JSONObject updateProfile() {
        JSONObject ob = new JSONObject();
        try {
            con.createStatement().executeUpdate("UPDATE `b2b`.`masteruserinfo`"
                    + "SET"
                    + "`FirstName` = '" + req.getParameter("fname") + "',"
                    + "`LastName` = '" + req.getParameter("lname") + "',"
                    + "`email` = '" + req.getParameter("email") + "',"
                    + "`phone1` = '" + req.getParameter("phone1") + "',"
                    + "`phone2` = '" + req.getParameter("phone2") + "',"
                    + "`address` = '" + req.getParameter("address") + "',"
                    + "`city` = '" + req.getParameter("city") + "',"
                    + "`state` = '" + req.getParameter("state") + "',"
                    + "`country` = '" + req.getParameter("country") + "',"
                    + "`zipcode` = '" + req.getParameter("zip") + "',"
                    + "`balance` = '" + req.getParameter("bal") + "'"
                    + "WHERE email='" + req.getParameter("email") + "';");
            ResultSet rs = con.createStatement().executeQuery("select max(masterid) from masteruserinfo");
            while (rs.next()) {
                con.createStatement().executeUpdate("INSERT INTO `login`"
                        + "(`masterid`, `username`,`password`, `usertype`)"
                        + "VALUES ('"
                        + rs.getString(1) + "' , '" + req.getParameter("username") + "' , '"
                        + req.getParameter("password")
                        + "' , 'supplier'"
                        + ")");
                ob.put("Status", "Successful");
            }
        } catch (Exception e) {
            ob.put("Status", e);
        } finally {
            return ob;
        }


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
