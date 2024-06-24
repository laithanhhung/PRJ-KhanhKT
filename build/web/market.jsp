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
            <h1>Book</h1>
            <%
                List<ProductDTO> result = (List<ProductDTO>) request.getAttribute("SEARCH_RESULT");//do getAttribute trả ra Serializable thì phải ép kiểu
                if (result != null) {//have result
            %>
            <div class="table-wrapper">
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
                            <td class="content--center">
                                <%= dto.getSku()%>
                                <input type="hidden" value="<%= dto.getSku()%>" name="skuBook" />
                            </td>
                            <td>
                                <%= dto.getName()%>
                                <input type="hidden" value="<%= dto.getName()%>" name="nameBook" />
                            </td>
                            <td><%= dto.getDescription()%></td>
                            <td>
                                <%= dto.getQuantity()%>
                                <input type="hidden" value="<%= dto.getQuantity()%>" name="quantityBook" />
                            </td>
                            <td class="content--center">
                                <%= dto.getPrice()%>
                                <input type="hidden" value="<%= dto.getPrice()%>" name="priceBook" />
                            </td>
                            <td class="content--center">
                                <%= dto.isStatus()%>
                                <input type="hidden" value="<%= dto.isStatus()%>" name="statusBook" />
                            </td>
                            <td class="content--center">
                                <input type="submit" value="Add Book to Your Cart" name="btAction" />
                            </td>
                        </tr>
                    </form>
                    <%
                        }//process each dto in result
                    %>
                    </tbody>
                </table>
            </div>
        </div>
        <form class="content--center btn__viewCart" action="DispatchServlet" method="POST">
            <input type="submit" value="View Your Cart" name="btAction" />
        </form>
        <%
            }
        %>
    </body>
</html>
