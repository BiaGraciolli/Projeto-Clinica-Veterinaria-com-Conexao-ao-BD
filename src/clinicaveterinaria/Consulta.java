package clinicaveterinaria;

class Consulta{
    private Animal paciente;
    private int horario;
    private String sintomas;

    public Consulta(){

    }

    public Consulta(Animal paciente, int horario, String sintomas) {
        this.paciente = paciente;
        this.horario = horario;
        this.sintomas = sintomas;
    }

    public Animal getPaciente(){ 
        return paciente; 
    }

    public void setPaciente(Animal paciente){ 
        this.paciente = paciente;
    }

    public int getHorario(){ 
        return horario;
    }

    public void setHorario(int horario){
        this.horario = horario;
    }

    public String getSintomas(){ 
        return sintomas; 
    }

    public void setSintomas(String sintomas){ 
        this.sintomas = sintomas;
    }
}