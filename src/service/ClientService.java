package service;

import model.Client;
import Exception.*;

import java.util.HashMap;
import java.util.Map;

public class ClientService {
    private final Map<String, Client> clientMap;


    public ClientService() {
        this.clientMap = new HashMap<>();
    }


    public void addClient(String name, String cpf, String email) throws InvalidCpfException, CpfAlreadyExistsException {
        if (!isCpfValid(cpf)) throw new InvalidCpfException("CPF Inválido");

        if (clientMap.containsKey(cpf)) {
            throw new CpfAlreadyExistsException("CPF JÁ CADASTRADO");
        }
        Client client = new Client(name, cpf, email);
        clientMap.put(cpf, client);
    }


    public Client get(String cpf) throws ClientNotFoundException {
        Client client = clientMap.get(cpf);

        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado");
        }

        return client;
    }


    private boolean isCpfValid(String cpf) {
        if (cpf == null || cpf.isBlank()) return false;

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) return false;

        // Bloqueia CPFs com todos os números iguais
        if (cpf.matches("(\\d)\\1{10}")) return false;

        int sum = 0;

        // 1º dígito
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }

        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) firstDigit = 0;

        if (firstDigit != (cpf.charAt(9) - '0')) return false;

        // 2º dígito
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }

        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) secondDigit = 0;

        return secondDigit == (cpf.charAt(10) - '0');
    }
}
