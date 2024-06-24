/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglt.orderDetail;
import hunglt.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author pc
 */
public class OrderDetailDAO {
    private List<OrderDetailDTO> orderDetails;

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }
    public void addToDB(OrderDetailDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            // 1. connect DB
            con = DBHelper.getConnection();
            if (con != null) {
                // 2. create SQL String
                String sql = "INSERT INTO OrderDetail (productId, unitPrice, quantity, orderId, total) VALUES (?, ?, ?, ?, ?)";
                // 3. create Statement Object
                stm = con.prepareStatement(sql);
                stm.setInt(1, dto.getProductId());
                stm.setFloat(2, dto.getUnitPrice());
                stm.setInt(3, dto.getQuantity());
                stm.setString(4, dto.getOrderId());
                stm.setFloat(5, dto.getTotal());
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
