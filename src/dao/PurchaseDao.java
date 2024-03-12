package dao;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PurchaseDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public PurchaseDao() {
        try {
            this.con = MyConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Get Accessory Table Max Row
    public int getMaxRow() {
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(id) from purchase");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    //Get User Value
    public String[] getUserValue(String email) {
        String[] value = new String[4];
        String sql = "select uid,uname,umobile,uaddress from users where uemail = '" + email + "'";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

    //Insert Data Into Purchase Table
    public void insert(int id, int uid, String uName, String uMobile, int aid, String aname, int qty, double price, double total, String payment ,
            String pDate, String uaddress, String rDate, String supplier, String status) {
        String sql = "insert into purchase values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, uid);
            ps.setString(3, uName);
            ps.setString(4, uMobile);
            ps.setInt(5, aid);
            ps.setString(6, aname);
            ps.setInt(7, qty);
            ps.setDouble(8, price);
            ps.setDouble(9, total);
            ps.setString(10, payment);
            ps.setString(11, pDate);
            ps.setString(12, uaddress);
            ps.setString(13, rDate);
            ps.setString(14, supplier);
            ps.setString(15, status);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Get Accessory Quantity
    public int getQty(int aid)
    {
        int qty = 0;
        
        try {
            st = con.createStatement();
            rs = ps.executeQuery("select aqty from accessories where aid = " + aid + "");
            if(rs.next())
            {
                qty = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qty;
    }
    
    //Update Accessory Quantity
    public void qtyUpdate(int aid, int qty)
    {
        String sql = "update accessories set aqty = ? where aid = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, qty);
            ps.setInt(2, aid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setSuppStatus(int id, String supp, String status)
    {
        String sql = "update purchase set supplier = ?, status = ? where id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, supp);
            ps.setString(2, status);
            ps.setInt(3, id);
            if(ps.executeUpdate() > 0)
            {
                JOptionPane.showMessageDialog(null, "Supplier Selected Successfully..");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setDateStatus(int id, String rDate, String status)
    {
        String sql = "update purchase set receive_date = ?, status = ? where id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, rDate);
            ps.setString(2, status);
            ps.setInt(3, id);
            if(ps.executeUpdate() > 0)
            {
                JOptionPane.showMessageDialog(null, "Accessory Delivered Successfully..");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Get Specific User Purchased Accessories
    public void getAccessoriesData(JTable table, String search, int uid)
    {
        String sql = "select * from purchase where concat(id,aid,accessory_name) like ? and uid = ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+ search +"%");
            ps.setInt(2, uid);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next())
            {
                row = new Object[11];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(5);
                row[2] = rs.getString(6);
                row[3] = rs.getInt(7);
                row[4] = rs.getDouble(8);
                row[5] = rs.getDouble(9);
                row[6] = rs.getString(10);
                row[7] = rs.getString(11);
                row[8] = rs.getString(13);
                row[9] = rs.getString(14);
                row[10] = rs.getString(15);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getAccessoriesData(JTable table, String search)
    {
        String sql = "select * from purchase where concat(id,aid,uname,accessory_name) like ? and status = 'Pending' order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+ search +"%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next())
            {
                row = new Object[14];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getInt(5);
                row[5] = rs.getString(6);
                row[6] = rs.getInt(7);
                row[7] = rs.getDouble(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                row[11] = rs.getString(12);
                row[12] = rs.getString(14);
                row[13] = rs.getString(15);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Get All On The Way Purchased Accessories
    public void getOnTheWayAccessoriesData(JTable table, String search, String supplier)
    {
        String sql = "select * from purchase where concat(id,aid,uname,accessory_name) like ? and supplier = ? and status = 'On The Way...' order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+ search +"%");
            ps.setString(2, supplier);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next())
            {
                row = new Object[15];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getInt(5);
                row[5] = rs.getString(6);
                row[6] = rs.getInt(7);
                row[7] = rs.getDouble(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                row[11] = rs.getString(12);
                row[12] = rs.getString(13);
                row[13] = rs.getString(14);
                row[14] = rs.getString(15);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Get Supplier Delivered Accessories
    public void getSuppDeliAccessories(JTable table, String search, String supplier)
    {
        String sql = "select * from purchase where concat(id,aid,uname,accessory_name) like ? and supplier = ? and status = 'Received' order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+ search +"%");
            ps.setString(2, supplier);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next())
            {
                row = new Object[14];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getInt(5);
                row[5] = rs.getString(6);
                row[6] = rs.getInt(7);
                row[7] = rs.getDouble(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                row[11] = rs.getString(12);
                row[12] = rs.getString(14);
                row[13] = rs.getString(15);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void refund(int id)
    {
        int x = JOptionPane.showConfirmDialog(null, "Are You Sure To Refund This Accessory ?", "Refund Accessory",JOptionPane.OK_CANCEL_OPTION,0);
        if(x == JOptionPane.OK_OPTION)
        {
            try {
                ps = con.prepareStatement("delete from purchase where id = ?");
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Accessory Refunded Successfully !");
            }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //Get Supplier Delivered Accessories
    public void transaction(JTable table, String search)
    {
        String sql = "select * from purchase where concat(id,supplier,payment) like ? and status = 'Received' order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+ search +"%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next())
            {
                row = new Object[9];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(5);
                row[3] = rs.getInt(7);
                row[4] = rs.getDouble(8);
                row[5] = rs.getDouble(9);
                row[6] = rs.getString(10);
                row[7] = rs.getString(13);
                row[8] = rs.getString(14);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
