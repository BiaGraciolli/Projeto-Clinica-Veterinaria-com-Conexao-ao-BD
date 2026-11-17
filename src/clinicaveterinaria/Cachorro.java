package clinicaveterinaria;

class Cachorro extends Animal{
    private String porte;
    private String raca;

    public Cachorro(){

    }

    public Cachorro(String nome, int idade, String nomeDono, String telefoneDono, String porte, String raca) {
        super(nome, idade, nomeDono, telefoneDono);
        this.porte = porte;
        this.raca = raca;
    }

    public String getPorte(){ 
        return porte;
    }
    public void setPorte(String porte){ 
        this.porte = porte;
    }
    public String getRaca(){ 
        return raca; 
    }
    public void setRaca(String raca){ 
        this.raca = raca;
    }

    @Override
    public String emitirSom(){
        return "Au Au!";
    }
}
