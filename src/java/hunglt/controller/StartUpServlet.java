/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglt.controller;

import hunglt.registration.RegistrationDAO;
import hunglt.registration.RegistrationDTO;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pc
 */
@WebServlet(name = "StartUpServlet", urlPatterns = {"/StartUpServlet"})
public class StartUpServlet extends HttpServlet {
    private final String LOGIN_PAGE = "login.html";
    private final String SEARCH_PAGE = "search.jsp";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = LOGIN_PAGE;
        
        try {
            //1. get all Cookies
            //Read
            Cookie[] cookies = request.getCookies();
                    
            //2. check Cookies tồn tại
            if (cookies != null) {
                //3. get username and password
                //lấy Cookie gần nhất
                Cookie recentCookie = cookies[cookies.length - 1];
                String username = recentCookie.getName();
                String password = recentCookie.getValue();
                //4. call method of Model/DAO
                RegistrationDAO dao = new RegistrationDAO();
                RegistrationDTO result = dao.checkLogin(username, password);
                //5. process result
                if(result != null){
                    url = SEARCH_PAGE;
                    //sending Cookie
                }//authentication is ok
            }//more than one times

//            request.getSession(false);// we don't want the server to create a new session we only want to use existed session
            
        } catch(SQLException ex){
            ex.printStackTrace();
        } catch(NamingException ex){
            ex.printStackTrace();
        } finally {
            response.sendRedirect(url);
            //dùng forward cũng dc luôn
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
