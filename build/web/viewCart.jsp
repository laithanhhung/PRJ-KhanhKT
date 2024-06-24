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
        <style>
            .content--center{
                text-align: center;
            }
            .table{
                width: 90%;
                text-align: center;
            }
            .checkout__form{
                display: flex;
                justify-content: space-between;
            }
            .yourCart{
                width: 55%;
            }
            .yourCart table{
                width: 100%;
            }
            .yourInformation{
                display: flex;
                justify-content: center;
                flex-wrap:wrap;
                width: 40%;
                height: 260px;
                border:#080710 1px solid;
                border-radius: 4px;
                
            }
            .form__input{
                width: 90%;
            }
            .form__input input {
                margin-bottom: 15px;
                margin-left: 5%;
                margin-right: 5%;
                width: 90%;
            }
            .form__input label {
                margin-left: 10%;
                display: block;
            }
            .btn__checkout{
               margin-bottom: 15px;
            }
        </style>
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
                        <form class="checkout__form">
                            <div class="yourCart">
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
                                                    <td class="content--center">
                                                        <%= ++count %>
                                                    </td>
                                                    <td>
                                                        <%= key %>
                                                    </td>
                                                    <td class="content--center">
                                                        <%= items.get(key) %>
                                                    </td>
                                                    <td class="content--center">
                                                        <input type="checkbox" name="chkItem" value="<%= key %>">    
                                                    </td>
                                                </tr>
                                        <%
                                            }//each item is processed
                                        %>  
                                        <tr>
                                            <td colspan="3" class="content--center">
                                                <a href="DispatchServlet?btAction=Market" style="text-decoration: none; color: black">Add more Books to cart</a>
                                            </td>
                                            <td class="content--center">
                                                <input type="submit" name="btAction" value="Remove Selected Item"/>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="yourInformation">
                                <h3 class="content--center">Your Information</h3><br>
                                <div class="form__input">
                                    <label for="customer">Customer</label>
                                    <input id="customer" type="text"" name="txtCustomer">
                                </div><br>
                                <div class="form__input">
                                    <label for="email">Email</label>
                                    <input id="email"  type="text" name="txtEmail">
                                </div><br>
                                <div class="form__input">
                                    <label for="address">Address</label>
                                    <input id="address"  type="text" name="txtAddress">
                                </div><br>
                                <input class="btn__checkout" type="submit" name ="btAction" value="Check Out" />
                            </div>
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
                <a href="DispatchServlet?btAction=Market">Shopping Continue here!</a>
            </font>
        </h2>
    </body>
</html>
