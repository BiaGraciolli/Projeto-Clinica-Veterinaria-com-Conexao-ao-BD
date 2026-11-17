create database veterinario;
use veterinario; 

create table Consulta(
idConsulta INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
horarioIndex INT NOT NULL, 
nomeAnimal VARCHAR(100) NOT NULL,
idadeAnimal INT NOT NULL,
nomeDono VARCHAR(100) NOT NULL,
telefoneDono VARCHAR(13) NOT NULL, 
especie VARCHAR(8) NOT NULL, 
porte VARCHAR(50),
raca VARCHAR(50),
temperamento VARCHAR(50),
sintomas VARCHAR(225)
);

