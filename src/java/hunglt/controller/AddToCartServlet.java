/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglt.controller;

import hunglt.cart.CartBean;
import hunglt.product.ProductCreateError;
import hunglt.product.ProductDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pc
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {
    private String MARKET_PAGE = "market.jsp";
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

        String url = MARKET_PAGE;
        boolean foundErr = false; // để check có lỗi
        ProductCreateError errors = new ProductCreateError();// để hứng lỗi

        try {
            //1. Cus goes to Cart place
            HttpSession session = request.getSession();// bắt buộc phải có Session
            //2. Cus takes Cart
            CartBean cart = (CartBean) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartBean();//la làng nếu ko có cart thì sẽ tạo new
            }
            //3. Cus drop Item to Cart: //item là ReqParam
            int sku = Integer.parseInt(request.getParameter("skuBook"));
            String name = request.getParameter("nameBook");
            String description = request.getParameter("descriptionBook");
            int quantity = Integer.parseInt(request.getParameter("quantityBook"));
            float price = Float.parseFloat(request.getParameter("priceBook"));
            boolean status = Boolean.parseBoolean(request.getParameter("statusBook"));
            ProductDTO item = new ProductDTO(sku, name, description, quantity, price, status);
            //check error user
            if (!status) {
                foundErr = true;
                errors.setError("Not available!");
            }
            if (quantity == 0) {
                foundErr = true;
                errors.setError("Not available!");
            }
            if (foundErr) {//errors occur
                request.setAttribute("CREATE_ERRORS", errors);

            } else {//no error
                cart.addIteamToCart(item);//có item rồi khỏi check exist
                //update products on website first if checkout successfully we must to update on database
                List<ProductDTO> result = (List<ProductDTO>)session.getAttribute("SEARCH_RESULT");
                for (ProductDTO product : result) {
                    if(product.getSku()==item.getSku()){
                        product.setQuantity(product.getQuantity() - 1);
                        break;
                    }
                }
                session.setAttribute("SEARCH_RESULT", result);//update list product on website
                session.setAttribute("CART", cart);//update session
            }//end no error
            //check error system
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("AddToCartServlet _ Naming: " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("AddToCartServlet _ SQL: " + msg);
        } finally {
            //4. Cus goes to shopping(quay lại bước 3)
            //thích dùng gì thì dùng do mình dùng session Scope
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
