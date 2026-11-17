package clinicaveterinaria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgendaManager {

    public static boolean verificarDisponibilidade(int indice) throws SQLException {
        String sql = "SELECT 1 FROM Consulta WHERE horarioIndex = ?";
        try (Connection conexao = BancoDeDados.conexaoBD();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, indice);
            try (ResultSet resultado = stmt.executeQuery()) {
                return !resultado.next();
            }
        }
    }

    public static void agendar(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO Consulta (horarioIndex, nomeAnimal, idadeAnimal, nomeDono, "
                + "telefoneDono, especie, porte, raca, temperamento, sintomas) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = BancoDeDados.conexaoBD();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            Animal animal = consulta.getPaciente();
            stmt.setInt(1, consulta.getHorario());
            stmt.setString(2, animal.getNome());
            stmt.setInt(3, animal.getIdade());
            stmt.setString(4, animal.getNomeDono());
            stmt.setString(5, animal.getTelefoneDono());
            preencherEspecie(stmt, animal, 6);        
            stmt.setString(10, consulta.getSintomas());
            stmt.executeUpdate();
        }
    }

    public static void editar(Consulta consulta) throws SQLException {
        String sql = "UPDATE Consulta SET nomeAnimal = ?, idadeAnimal = ?, nomeDono = ?, "
                + "telefoneDono = ?, especie = ?, porte = ?, raca = ?, temperamento = ?, "
                + "sintomas = ? WHERE horarioIndex = ?";
        try (Connection conexao = BancoDeDados.conexaoBD();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            Animal animal = consulta.getPaciente();
            stmt.setString(1, animal.getNome());
            stmt.setInt(2, animal.getIdade());
            stmt.setString(3, animal.getNomeDono());
            stmt.setString(4, animal.getTelefoneDono());
            preencherEspecie(stmt, animal, 5);         
            stmt.setString(9, consulta.getSintomas());
            stmt.setInt(10, consulta.getHorario());
            stmt.executeUpdate();
        }
    }

    public static void excluir(int horario) throws SQLException {
        String sql = "DELETE FROM Consulta WHERE horarioIndex = ?";
        try (Connection conexao = BancoDeDados.conexaoBD();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, horario);
            stmt.executeUpdate();
        }
    }

    public static Consulta[] listar() throws SQLException {
        Consulta[] agenda = new Consulta[10];
        String sql = "SELECT * FROM Consulta";
        try (Connection conexao = BancoDeDados.conexaoBD();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                int horario = resultado.getInt("horarioIndex");
                String nome = resultado.getString("nomeAnimal");
                int idade = resultado.getInt("idadeAnimal");
                String nomeDono = resultado.getString("nomeDono");
                String telefoneDono = resultado.getString("telefoneDono");
                String especie = resultado.getString("especie");
                String sintomas = resultado.getString("sintomas");

                Animal paciente;
                if ("Cachorro".equals(especie)) {
                    paciente = new Cachorro(nome, idade, nomeDono, telefoneDono,
                            resultado.getString("porte"), resultado.getString("raca"));
                } else {
                    paciente = new Gato(nome, idade, nomeDono, telefoneDono,
                            resultado.getString("temperamento"));
                }

                if (horario >= 0 && horario < agenda.length) {
                    agenda[horario] = new Consulta(paciente, horario, sintomas);
                }
            }
        }
        return agenda;
    }

    private static void preencherEspecie(PreparedStatement stmt, Animal animal, int inicio)
            throws SQLException {
        if (animal instanceof Cachorro) {
            Cachorro c = (Cachorro) animal;
            stmt.setString(inicio, "Cachorro");
            stmt.setString(inicio + 1, c.getPorte());
            stmt.setString(inicio + 2, c.getRaca());
            stmt.setString(inicio + 3, "");
        } else {
            Gato g = (Gato) animal;
            stmt.setString(inicio, "Gato");
            stmt.setString(inicio + 1, "");
            stmt.setString(inicio + 2, "");
            stmt.setString(inicio + 3, g.getTemperamento());
        }
    }
}