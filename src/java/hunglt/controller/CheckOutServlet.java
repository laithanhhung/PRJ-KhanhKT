package hunglt.controller;

import hunglt.cart.CartBean;
import hunglt.orderDetail.OrderDetailDAO;
import hunglt.orderDetail.OrderDetailDTO;
import hunglt.orders.OrdersDAO;
import hunglt.orders.OrdersDTO;
import hunglt.product.ProductDAO;
import hunglt.product.ProductDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                CartBean cart = (CartBean) session.getAttribute("CART");
                if (cart != null) {
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        OrdersDAO ordersDAO = new OrdersDAO();
                        String orderId = ordersDAO.generateOrderID();
                        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        String customer = request.getParameter("txtCustomer");
                        String email = request.getParameter("txtEmail");
                        String address = request.getParameter("txtAddress");
                        float totalOfTotal = 0;

                        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                        List<OrderDetailDTO> orderList = new ArrayList<>();
                        ProductDAO productDAO = new ProductDAO();
                        List<ProductDTO> purchaseList = new ArrayList<>();
                        List<ProductDTO> productList = (List<ProductDTO>)session.getAttribute("SEARCH_RESULT");
                        int count = 0;
                        for (String key : items.keySet()) {
                            ProductDTO product = null;
                            for (ProductDTO dto : productList) {
                                if(dto.getName().equals(key)){
                                    product = dto;
                                    break;
                                }
                            }
                            int productId = product.getSku();
                            float unitPrice = product.getPrice();
                            int quantity = items.get(key);
                            float total = unitPrice * quantity;
                            totalOfTotal += total;
                            OrderDetailDTO orderDetail = new OrderDetailDTO(++count, productId, unitPrice, quantity, orderId, total);
                            orderList.add(orderDetail);
                            purchaseList.add(product);
                        }

                        OrdersDTO order = new OrdersDTO(orderId, date, customer, address, email, totalOfTotal);
                        ordersDAO.addToDB(order);//add Order

                        for (OrderDetailDTO orderDetailDTO : orderList) {
                            orderDetailDAO.addToDB(orderDetailDTO);//Add OrderDetail of each Order
                        }
                        
                        for (ProductDTO product : purchaseList) {
                            productDAO.updateProduct(product);
                        }
                        request.setAttribute("ProductList", productList);
                        request.setAttribute("Order", order);
                        request.setAttribute("OrderDetailList", orderList);
                        session.removeAttribute("CART");//xóa giỏ hàng
                    }
                }
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            String url = "checkout.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
