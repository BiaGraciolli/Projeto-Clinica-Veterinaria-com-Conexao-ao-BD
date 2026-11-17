package clinicaveterinaria;

import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CLINICAVETERINARIA {

    public static void main(String[] args) {
        String[] botoes = {"1. Agendar Consulta", "2. Consultar Agenda",
            "3. Editar Agendamento/Paciente", "4. Cancelar Consulta", "5. Sair"};
        int opcao;

        do {
            opcao = JOptionPane.showOptionDialog(null, "CLÍNICA VETERINÁRIA", "Clín. Vet.",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, botoes, botoes[0]);

            try {
                switch (opcao) {
                    case 0:
                        agendar();
                        break;
                    case 1:
                        verAgenda();
                        break;
                    case 2:
                        alterar();
                        break;
                    case 3:
                        cancelar();
                        break;
                    default:
                        break; 
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } while (opcao != 4 && opcao != -1);

        System.out.println("Finalizando o programa...");
    }


    public static void agendar() throws SQLException {
        Consulta[] agenda = AgendaManager.listar();
        int ocupados = 0;
        for (Consulta c : agenda) {
            if (c != null) {
                ocupados++;
            }
        }
        if (ocupados == agenda.length) {
            JOptionPane.showMessageDialog(null, "Agenda lotada!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int horario = lerHorario("Digite o horário (1 a 10):");
        if (horario == -1) {
            return;
        }

        if (!AgendaManager.verificarDisponibilidade(horario)) {
            JOptionPane.showMessageDialog(null, "Esse horário já está ocupado!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Consulta consulta = lerConsulta(horario);
        if (consulta != null) {
            AgendaManager.agendar(consulta);
            JOptionPane.showMessageDialog(null, "Consulta agendada!");
        }
    }

    public static void verAgenda() throws SQLException {
        Consulta[] agenda = AgendaManager.listar();
        String texto = "";

        for (int i = 0; i < agenda.length; i++) {
            texto += "Horário " + (i + 1) + ": ";

            if (agenda[i] == null) {
                texto += "[Disponível]\n";
            } else {
                Animal a = agenda[i].getPaciente();
                String especie = (a instanceof Cachorro) ? "Cachorro" : "Gato";

                texto += a.getNome() + " - " + especie
                        + " - Tutor: " + a.getNomeDono()
                        + " - Tel: " + a.getTelefoneDono()
                        + " - " + a.emitirSom()
                        + " - Sintomas: " + agenda[i].getSintomas()
                        + "\n";
            }
        }

        JOptionPane.showMessageDialog(null, texto, "Agenda",
                JOptionPane.INFORMATION_MESSAGE);
}
    public static void alterar() throws SQLException {
        int horario = lerHorario("Digite o horário que deseja alterar (1 a 10):");
        if (horario == -1) {
            return;
        }

        if (AgendaManager.verificarDisponibilidade(horario)) {
            JOptionPane.showMessageDialog(null, "Não há consulta neste horário!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Consulta consulta = lerConsulta(horario);
        if (consulta != null) {
            AgendaManager.editar(consulta);
            JOptionPane.showMessageDialog(null, "Agendamento alterado!");
        }
    }

    public static void cancelar() throws SQLException {
        int horario = lerHorario("Digite o horário que deseja cancelar (1 a 10):");
        if (horario == -1) {
            return;
        }

        if (AgendaManager.verificarDisponibilidade(horario)) {
            JOptionPane.showMessageDialog(null, "Este horário já está vazio!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Consulta consulta = AgendaManager.listar()[horario];
        int resposta = JOptionPane.showConfirmDialog(null,
                "Deseja cancelar a consulta de " + consulta.getPaciente().getNome() + "?");
        if (resposta == JOptionPane.YES_OPTION) {
            AgendaManager.excluir(horario);
            JOptionPane.showMessageDialog(null, "Consulta cancelada!");
        }
    }


    public static Consulta lerConsulta(int horario) {
        String[] especies = {"Cachorro", "Gato"};
        int especie = JOptionPane.showOptionDialog(null, "Qual a espécie do animal?", "Espécie",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, especies, especies[0]);
        if (especie == -1) {
            return null;
        }

        String nomeDono = lerTexto("Nome do tutor:");
        if (nomeDono == null) {
            return null;
        }

        String telefone = lerTexto("Telefone do tutor:");
        if (telefone == null) {
            return null;
        }

        String nome = lerTexto("Nome do animal:");
        if (nome == null) {
            return null;
        }

        int idade = lerIdade();
        if (idade == -1) {
            return null;
        }

        Animal paciente;
        if (especie == 0) {
            String porte = lerTexto("Porte do cachorro:");
            if (porte == null) {
                return null;
            }
            String raca = lerTexto("Raça do cachorro:");
            if (raca == null) {
                return null;
            }
            paciente = new Cachorro(nome, idade, nomeDono, telefone, porte, raca);
        } else {
            String temperamento = lerTexto("Temperamento do gato:");
            if (temperamento == null) {
                return null;
            }
            paciente = new Gato(nome, idade, nomeDono, telefone, temperamento);
        }

        String sintomas = lerTexto("Sintomas:");
        if (sintomas == null) {
            return null;
        }

        return new Consulta(paciente, horario, sintomas);
    }

    public static String lerTexto(String mensagem) {
        String texto;
        do {
            texto = JOptionPane.showInputDialog(mensagem);
            if (texto == null) {
                return null;
            }
            if (texto.trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Preencha o campo!",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } while (texto.trim().length() == 0);
        return texto.trim();
    }

    public static int lerIdade() {
        while (true) {
            String texto = lerTexto("Idade do animal:");
            if (texto == null) {
                return -1;
            }
            try {
                int idade = Integer.parseInt(texto);
                if (idade >= 0) {
                    return idade;
                }
                JOptionPane.showMessageDialog(null, "A idade não pode ser negativa!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um número inteiro!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static int lerHorario(String mensagem) {
        while (true) {
            String texto = lerTexto(mensagem);
            if (texto == null) {
                return -1;
            }
            try {
                int horario = Integer.parseInt(texto);
                if (horario >= 1 && horario <= 10) {
                    return horario - 1;
                }
                JOptionPane.showMessageDialog(null, "Horário inválido! Digite de 1 a 10.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um número inteiro!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}