/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglt.orders;

import hunglt.registration.RegistrationDTO;
import hunglt.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author pc
 */
public class OrdersDAO {

    private List<OrdersDTO> orders;

    public List<OrdersDTO> getOrders() {
        return orders;
    }

    public String generateOrderID() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String orderID = null;
            //1. connect DB
            con = DBHelper.getConnection();
            if (con != null) {
                //2. create SQL String
                String sql = "SELECT TOP(1) id FROM Orders "
                        + "ORDER BY id DESC";
                //3. create Statement Object
                stm = con.prepareStatement(sql);
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process result
                if (rs.next()) {
                    orderID = rs.getString("id");
                    int number = Integer.parseInt(orderID.substring(2));
                    number++;
                    return String.format("OD%03d", number);
                } else {
                    return "OD001";
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public void addToDB(OrdersDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            // 1. connect DB
            con = DBHelper.getConnection();
            if (con != null) {
                // 2. create SQL String
                String sql = "INSERT INTO Orders (id, date, customer, address, email, total) VALUES (?, ?, ?, ?, ?, ?)";
                // 3. create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getId());
                stm.setString(2, dto.getDate());
                stm.setString(3, dto.getCustomer());
                stm.setString(4, dto.getAddress());
                stm.setString(5, dto.getEmail());
                stm.setFloat(6, dto.getTotal());
                // 4. Execute Query
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
