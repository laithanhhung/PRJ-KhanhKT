<%-- 
    Document   : market
    Created on : Jun 19, 2024, 6:41:40 PM
    Author     : pc
--%>


<%@page import="hunglt.product.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Market</title>
    </head>
    <body>
        <h1>Book</h1>
        <%
            List<ProductDTO> result = (List<ProductDTO>) request.getAttribute("SEARCH_RESULT");//do getAttribute trả ra Serializable thì phải ép kiểu
            if(result != null){//have result
        %>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Sku</th>
                            <th>Name</th>
                            <th>Discription</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Status</th>
                            <th>Add Book to Your Cart</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (ProductDTO dto : result) {
                        %> 
                                <form action="DispatchServlet" method="POST">
                                    <tr>
                                        <td>
                                            <%= dto.getSku() %>
                                            <input type="hidden" value="<%= dto.getSku() %>" name="skuBook" />
                                        </td>
                                        <td>
                                            <%= dto.getName() %>
                                            <input type="hidden" value="<%= dto.getName() %>" name="nameBook" />
                                        </td>
                                        <td><%= dto.getDescription() %></td>
                                        <td>
                                            <%= dto.getQuantity() %>
                                            <input type="hidden" value="<%= dto.getQuantity() %>" name="quantityBook" />
                                        </td>
                                        <td>
                                            <%= dto.getPrice() %>
                                            <input type="hidden" value="<%= dto.getPrice() %>" name="priceBook" />
                                        </td>
                                        <td>
                                            <%= dto.isStatus() %>
                                            <input type="hidden" value="<%= dto.isStatus() %>" name="statusBook" />
                                        </td>
                                        <td>
                                            <input type="submit" value="Add Book to Your Cart" name="btAction" />
                                        </td>
                                    </tr>
                                </form>
                        <%
                            }//process each dto in result
                        %>
                    </tbody>
                </table>
                <form action="DispatchServlet" method="POST">
                    <input type="submit" value="View Your Cart" name="btAction" />
                </form>
            <%
                }
            %>
    </body>
</html>
