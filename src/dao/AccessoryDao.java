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

public class AccessoryDao {
    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public AccessoryDao() {
        try {
            this.con = MyConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Get Accessory Table Max Row
    public int getMaxRow() {
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(aid) from accessories");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    public int countCategories()
    {
        int total = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total' from category");
            if(rs.next())
            {
             total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    public String[] getCat()
    {
        String[] categories = new String[countCategories()];
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from category");
            int i = 0;
            while(rs.next())
            {
                categories[i] = rs.getString(2);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }
    
    //Check If Accessory ID Already Exists
    public boolean isIDExist(int id) {
        try {
            ps = con.prepareStatement("select * from accessories where aid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
    //Check If Accessory And Category Already Exists
    public boolean isaccessCatExist(String access, String cat) {
        try {
            ps = con.prepareStatement("select * from accessories where aname = ? and cname = ?");
            ps.setString(1, access);
            ps.setString(2, cat);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
    //Insert Data Into Accessory Table
    public void insert(int id, String aname, String cname, int qty, double price) {
        String sql = "insert into accessories values (?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, aname);
            ps.setString(3, cname);
            ps.setInt(4, qty);
            ps.setDouble(5, price);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Accessory Added Successfully !");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Update Accessories Data
    public void update(int id, String aname, String cname, int qty, double price) {
        String sql = "update accessories set aname = ?, cname = ?, aqty = ?, aprice = ? where aid = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, aname);
            ps.setString(2, cname);
            ps.setInt(3, qty);
            ps.setDouble(4, price);
            ps.setInt(5, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Accessory Data Successfully Updated!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Delete Accessory
    public void delete(int id)
    {
        int x = JOptionPane.showConfirmDialog(null, "Are You Sure To Delete This Accessory ?", "Delete Accessory",JOptionPane.OK_CANCEL_OPTION,0);
        if(x == JOptionPane.OK_OPTION)
        {
            try {
                ps = con.prepareStatement("delete from accessories where aid = ?");
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Accessory Deleted Successfully !");
            }
            } catch (SQLException ex) {
                Logger.getLogger(AccessoryDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //Get Accessory Data
    public void getAccessoriesData(JTable table, String search)
    {
        String sql = "select * from accessories where concat(aid, aname, cname) like ? order by aid desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+ search +"%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next())
            {
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getInt(4);
                row[4] = rs.getInt(5);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
