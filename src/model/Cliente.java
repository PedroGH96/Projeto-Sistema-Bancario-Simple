package model;

public class Cliente {
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome(){
        return nome;
    }

    public String getCpf(){
        return cpf;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (objeto == null || getClass() != objeto.getClass()) return false;
        Cliente cliente = (Cliente) objeto;
        return cpf.equals(cliente.cpf);
    }
    @Override
    public String toString() {
        return "Cliente{nome='" + nome + "', cpf='" + cpf + "'}";
    }
}
