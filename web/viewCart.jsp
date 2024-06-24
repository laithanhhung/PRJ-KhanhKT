<%-- 
    Document   : viewCart
    Created on : Jun 17, 2024, 11:06:44 AM
    Author     : pc
--%>

<%@page import="java.util.Map"%>
<%@page import="hunglt.cart.CartBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Market</title>
    </head>
    <body>
        <h1>Your cart includes</h1>
        <%
            //1. Cus goes to her/his  Cart Place
            if (session != null) {
                //2. Cus takes his/her Cart
                CartBean cart = (CartBean)session.getAttribute("CART");
                if (cart != null){
                    //3. Cus gets items
                    Map<String, Integer> items = cart.getItems();
                    if(items != null){
                        //4. Cus shows all item
                        %>
                        <form>
                            <table border="1">
                                <thead>
                                    <tr>
                                        <th>No.</th>
                                        <th>Title</th>
                                        <th>Quantity</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% 
                                        int count = 0;
                                        for(String key : items.keySet()){
                                            %>
                                            <tr>
                                                <td>
                                                    <%= ++count %>
                                                </td>
                                                <td>
                                                    <%= key %>
                                                </td>
                                                <td>
                                                    <%= items.get(key) %>
                                                </td>
                                                <td>
                                                    <input type="checkbox" name="chkItem" value="<%= key %>">    
                                                </td>
                                            </tr>
                                    <%
                                        }//each item is processed
                                    %>  
                                    <tr>
                                        <td colspan="3">
                                            <a href="market.html">Add more Books to cart</a>
                                        </td>
                                        <td>
                                            <input type="submit" name="btAction" value="Remove Selected Item"/>
                                        </td>                                   
                                    </tr>
                                </tbody>
                            </table>
                        </form>
        <%
                        return;
                    }//items has existed
                }//cart has existed
                
            }
        %>
        <h2>
            <font color="red">
                No card is existed!!!
                <a href="market.html">Shopping Continue here!</a>
            </font>
        </h2>
    </body>
</html>
