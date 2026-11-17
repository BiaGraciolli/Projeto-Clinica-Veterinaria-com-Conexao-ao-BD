package clinicaveterinaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class BancoDeDados {
    public static Connection conexaoBD(){
        String url="jdbc:mysql://localhost:3306/veterinario";
        String usuario="root";
        String senha="";
        Connection conexao=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Driver não encontrado: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        try{
           conexao=DriverManager.getConnection(url, usuario, senha);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco de dados: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return conexao;
    }
}
