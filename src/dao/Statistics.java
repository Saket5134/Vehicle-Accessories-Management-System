package dao;

import admin.AdminDashboard;
import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import supplier.SupplierDashboard;
import user.UserDashboard;

public class Statistics {
    
    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    public Statistics() {
        try {
            this.con = MyConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int total(String tableName) {
        int total = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total' from " + tableName + "");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    private double totalSales() {
        double total = 0.0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select sum(total) as 'total' from purchase");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    private double todaySales() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = new Date();
        String today = df.format(date);
        double total = 0.0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select sum(total) as 'total' from purchase where p_date = '" + today + "'");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    private double totalPurchase(int id) {
        double total = 0.0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select sum(total) as 'total' from purchase where uid = " + id + "");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    private int totalDeliveries(String name) {
        int total = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total' from purchase where supplier = '" + name + "' and status = 'Received'");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    //Admin Dashboard
    public void admin() {
        AdminDashboard.jCat.setText(String.valueOf(total("category")));
        AdminDashboard.jAccess.setText(String.valueOf(total("accessories")));
        AdminDashboard.jUsers.setText(String.valueOf(total("users")));
        AdminDashboard.jSuppliers.setText(String.valueOf(total("supplier")));
        AdminDashboard.jSales.setText(String.valueOf(totalSales()));
        AdminDashboard.jTsales.setText(String.valueOf(todaySales()));
    }
    
    //User Dashboard
    public void user(int id)
    {
        UserDashboard.jCat.setText(String.valueOf(total("category")));
        UserDashboard.jAccess.setText(String.valueOf(total("accessories")));
        UserDashboard.jPur.setText(String.valueOf(totalPurchase(id)));
    }
    
    //Supplier Dashboard
    public void supplier(String name)
    {
        SupplierDashboard.jDel.setText(String.valueOf(totalDeliveries(name)));
    }
}
