<%-- 
    Document   : search
    Created on : Jun 6, 2024, 10:42:16 AM
    Author     : pc
--%>
<%@page import="hunglt.registration.RegistrationDAO"%>
<%@page import="hunglt.registration.RegistrationDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <% 
            //có cookie => login thành công
            //1. get all cookies
            Cookie[] cookies = request.getCookies();
            //2. check existed cookies 
            if (cookies != null) {
                //3. get username and password
                //lấy Cookie gần nhất
                Cookie recentCookie = cookies[cookies.length - 1];
                String username = recentCookie.getName();
        %>
                <font style="color:red">
                    Welcome, <%= username %>
                </font>
        
        <% 
            }//Login successfully
        %>
        <h1>Search Page</h1>
        <form action="DispatchServlet">
            Search Value<input type="text" name="txtSearchValue" value="<%= request.getParameter("txtSearchValue") %>" /><br>
            <input type="submit" value="Search" name="btAction" />
        </form> <br/>
        <%  
            String searchValue = request.getParameter("txtSearchValue");
            if(searchValue != null){
                //tới bước này thì đang nằm trong Atribute của ReqObj
                List<RegistrationDTO> result = (List<RegistrationDTO>) request.getAttribute("SEARCH_RESULT");//do getAttribute trả ra Serializable thì phải ép kiểu
                if(result != null){//have result
                    %>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Full Name</th>
                                <th>Role</th>
                                <th>Delete</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            int count = 0;
                            for(RegistrationDTO dto : result){
                                String urlRewriting = "DispatchServlet"
                                                    + "?btAction=Delete"
                                                    + "&pk=" + dto.getUsername()
                                                    + "&lastSearchValue=" + searchValue;
                                    %> 
                                <form action="DispatchServlet" method="POST">
                                    <tr>
                                        <td><%= ++count %></td>
                                        <td>
                                            <%= dto.getUsername()%>
                                            <input type="hidden" name="txtUsername" 
                                                   value="<%= dto.getUsername()%>"/>
                                        </td>
                                        <td>
                                            <input type="text" name="txtPassword" 
                                                   value="<%= dto.getPassword()%>"/>
                                        </td>
                                        <td><%= dto.getFullName()%></td>
                                        <td>
                                            <input type="checkbox" name="chkAdmin" value="ON" 
                                            <%
                                                if(dto.isRole()){
                                            %>
                                                    checked="checked"
                                            <%
                                                }//admin roll
                                            %>
                                            />
                                        </td>
                                        <td><a href="<%= urlRewriting %>">Delete</a></td>
                                        <td>
                                            <input type="submit" value="Update" name="btAction" />
                                            <input type="hidden" name="lastSearchValue" 
                                                   value="<%= searchValue %>"/>
                                        </td>
                                    </tr>
                                    
                                </form>
                        <%
                            }//process each dto in result
                        %>
                        </tbody>
                    </table>

            <%
                }else {//no result
                    //scriptlet: fragment code( chứa javaCode lẫn html)
                    %>
                    <h2>
                        No record is matched!!!
                    </h2>
            <%
                }
                        
            }//not access directly
        %>
        <form action="DispatchServlet">
            <input type="submit" value="Logout" name="btAction" />
        </form>
    </body>
</html>
