package client;

import java.sql.Connection;  
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;  
import java.sql.ResultSet;
import java.sql.SQLException;  
import java.sql.Statement;

public class SQLiteDB {
    
    private String name;
    Connection con = null;
    Statement stmt = null;
    
    public SQLiteDB(String name){
        this.name = name;
    }
    
    
    public void connect() throws SQLException{
        try{
           con = DriverManager.getConnection(String.format("jdbc:sqlite:build/localdbs/%s.db", this.name));
           if(con != null) {
               stmt = con.createStatement();
               System.out.println("Conn success.!");  
           }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        if(!tableExists()){
            createTable();
        }
        else{
            
        }
    }
    
    
    public boolean tableExists() throws SQLException{
        DatabaseMetaData dbm = con.getMetaData();
        
        ResultSet table = dbm.getTables(null, null, this.name, null);
        
        if(table.next())
            return true;
        
        return false;
    }
    
    public void createTable() throws SQLException{
         String sql = "CREATE TABLE Resposta (\n"
                + "	nome_loja TEXT NOT NULL,\n"
                + "	produto TEXT NOT NULL,\n"
                + "	stock INTEGER NOT NULL\n"
                + "     horas text NOT NULL\n"
                + ");";
         try{
             stmt.execute(sql);
         }
         catch(SQLException e){
             System.out.println(e.getMessage());
         }
    }
    
    
    public void selectLast() throws SQLException{
        String sql = "SELECT * FROM Resposta ORDER BY data DESC LIMIT 1;";
        
        ResultSet rs  = stmt.executeQuery(sql);
            
        if (rs.next()) {
            System.out.println(rs.getString("nome_loja") +  "\t" + 
                               rs.getString("produto") + "\t" +
                               rs.getDouble("stock") + "\t" +
                               rs.getString("data"));
        }
    }
    
    public void insertAnswer(String produto, String loja, int stock) throws SQLException {
        stmt.executeUpdate(String.format("INSERT INTO %s VALUES (%s, %s, %d, CURRENT_TIME); ", this.name, loja, produto, stock));
    }
    
    public static void main(String args[]) throws SQLException{
        SQLiteDB s = new SQLiteDB("test2");
        s.connect();
    }
}
