/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglt.controller;

import hunglt.cart.CartBean;
import hunglt.product.ProductDTO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pc
 */
@WebServlet(name = "RemoveFromCartServlet", urlPatterns = {"/RemoveFromCartServlet"})
public class RemoveFromCartServlet extends HttpServlet {

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

        try {
            //1. Cus go to his/her Cart Place
            HttpSession session = request.getSession();
            if (session != null) {
                //2. Cus take his/her Cart
                CartBean cart = (CartBean) session.getAttribute("CART");
                if (cart != null) {
                    //3. Cus get item
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        //4. Cus choose remove some items
                        List<ProductDTO> result = (List<ProductDTO>) session.getAttribute("SEARCH_RESULT");
                        String[] selectedItems = request.getParameterValues("chkItem");
                        if (selectedItems != null) {
                            for (String item : selectedItems) {
                                for (ProductDTO product : result) {
                                    if (product.getName().equals(item)) {
                                        product.setQuantity(product.getQuantity() + items.get(item));
                                        break;
                                    }
                                }
                                cart.removeItemFromCart(item);
                            }
                        }//user chose at least 1 item
                        //update Product Quantity from market page
                        session.setAttribute("SEARCH_RESULT", result);
                    }//items has existed
                }//Cart has existed
            }//Cart Place has existed
        } finally {
            //5. Refresh by calling previous function again using URL Rewriting
            String urlRewriting = "DispatchServlet"
                    + "?btAction=View Your Cart";
            response.sendRedirect(urlRewriting);//nếu dùng forward sẽ bị trùng btAction
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
