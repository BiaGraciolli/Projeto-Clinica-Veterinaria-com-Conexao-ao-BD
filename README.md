# 🐾 Clínica Veterinária

Sistema de agendamento de consultas para uma clínica veterinária, desenvolvido em **Java** com interface gráfica simples via **JOptionPane** e dados guardados em **MySQL** usando **JDBC**.
A agenda possui 10 horários (exibidos de 1 a 10 e armazenados com índices de 0 a 9).

Menu:
- **Agendar consulta**: escolhe o horário, a espécie (cachorro ou gato), registra os dados do tutor, do animal e os sintomas.
- **Consultar agenda**: lista os 10 horários, indicando quais estão disponíveis e os dados de cada paciente.
- **Editar agendamento/paciente**: altera os dados de uma consulta já existente.
- **Cancelar consulta**: remove o agendamento após confirmação.
- **Validações**: campos obrigatórios, idade inteira e não negativa, horário entre 1 e 10, aviso de horário ocupado e de agenda lotada.

## Arquivos
```
src/clinicaveterinaria/
├── CLINICAVETERINARIA.java   # Classe principal com menu
├── AgendaManager.java        # Acesso a dados (CRUD da agenda via JDBC)
├── BancoDeDados.java         # Conexão com o MySQL
├── Animal.java               # Classe abstrata (nome, idade, tutor, telefone)
├── Cachorro.java             # Herda de Animal (porte e raça)
├── Gato.java                 # Herda de Animal (temperamento)
└── Consulta.java             # Associa paciente, horário e sintomas
```

### Modelo de classes
Classe Animal `(Abstrata)`
- Atributos: nome, idade, nomeDono e telefoneDono.
- Métodos: getters, setters, construtores e emitirSom(). 

Classe Cachorro
- Atributos: O mesmo de Animal + porte e raca.
- Métodos: getters, setters, construtores e emitirSom() retornando "Au Au!".

Classe Gato
- Atributos: O mesmo de Animal + temperamento.
- Métodos: getters, setters, construtores e emitirSom() retornando "Miau!".

Consulta
- Atributos: paciente, horario e sintomas.
- Métodos: getters, setters e construtor.

AgendaManager
- Métodos: verificarDisponibilidade, agendar, editar, excluir e listar

BancoDeDados:
- Método para retornar a conexão com o banco de dados. 

CLINICAVETERINARIA
- Classe principal. 

## POOI
O projeto usa **herança** (`Cachorro` e `Gato` estendem `Animal`), **polimorfismo** (`emitirSom()`) e **abstração** (`Animal` abstrata).

## Tecnologias
- Java (JDK 8 ou superior)
- Swing (`JOptionPane`)
- JDBC
- MySQL e MySQL Connector/J (`com.mysql.cj.jdbc.Driver`)
