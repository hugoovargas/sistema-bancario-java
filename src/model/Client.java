package model;

public class Client {
    private static int idCounter = 1;
    private final int id;
    private String name;
    private final String cpf;
    private String email;


    public Client(String name, String cpf, String email) {
        this.id = idCounter++;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCpf() {
        return cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
