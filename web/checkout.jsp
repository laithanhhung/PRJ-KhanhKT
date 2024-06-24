<%-- 
    Document   : checkout
    Created on : Jun 24, 2024, 4:19:39 PM
    Author     : pc
--%>

<%@page import="hunglt.orders.OrdersDTO"%>
<%@page import="hunglt.product.ProductDTO"%>
<%@page import="hunglt.orderDetail.OrderDetailDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check Out</title>
        <style>
            .container{
                width: 100%;
                text-align: center;
            }
            .table-wrapper {
                display: flex;
                justify-content: center;
                align-items: center;
                text-align: left;
            }
            table {
                margin: 0 auto;
                width: 90%; /* Adjust width as necessary */
            }
            .content--center{
                text-align: center;
            }
            .btn__viewCart{
                margin-top: 15px
            }
        </style>
    </head>
    <body>
        <div class="container">
            <form action="DispatchServlet">
                <%
                    List<OrderDetailDTO> orderDetaiList = (List<OrderDetailDTO>) request.getAttribute("OrderDetailList");
                    List<ProductDTO> productList = (List<ProductDTO>) request.getAttribute("ProductList");
                    OrdersDTO order = (OrdersDTO) request.getAttribute("Order");
                    int count = 0;
                %>
                <h1><%= order.getId()%> is checked out Successfully!</h1>
                <div class="table-wrapper">
                    <table class="table" border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Title</th>
                                <th class="content--center">Quantity</th>
                                <th class="content--center">Total</th>
                            </tr>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (OrderDetailDTO dto : orderDetaiList) {
                                %>
                                    <tr>
                                        <td ><%= ++count%></td>
                                        <td><%= productList.get(count - 1).getName()%></td>
                                        <td class="content--center"><%= dto.getQuantity()%></td>
                                        <td class="content--center"><%= dto.getTotal()%></td>
                                    </tr>
                                <%
                                }
                            %>
                            <tr>
                                <td colspan="3">Total Price</td>
                                <td class="content--center"><%= order.getTotal()%></td>
                            </tr>
                            <tr>
                                <td>Customer</td>
                                <td colspan="3"><%= order.getCustomer()%></td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td colspan="3"><%= order.getEmail()%></td>
                            </tr>
                            <tr>
                                <td>Address</td>
                                <td colspan="3"><%= order.getAddress()%></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn__viewCart">
                    <input type="submit" value="Go to Market"/>
                    <input type="hidden" name="btAction" value="Market"/>
                </div>
            </form>
        </div>
    </body>
</html>
