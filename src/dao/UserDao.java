/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Marius
 */
public class UserDao {
    private Connection con;
    private PreparedStatement stmt1;
    private PreparedStatement stmt2;
    
    public UserDao (Connection con){
        
        try {
            this.con = con;
            this.stmt1 = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            this.stmt2 = con.prepareStatement("INSERT INTO users VALUES (null, ?,?)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public Optional<User> findUser (String username) throws SQLException{
        stmt1.setString(1, username);
        User user = null;
        
        try (
            ResultSet rs = stmt1.executeQuery();
            ) {
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         
        return Optional.ofNullable(user);
    }
    
    public void adaugaUser(User user)throws SQLException{
            stmt2.setString(1, user.getUsername());
            stmt2.setString(2, user.getPassword());
            
            stmt2.executeUpdate();
            
        }
}
