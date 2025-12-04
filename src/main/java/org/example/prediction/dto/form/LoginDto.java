package org.example.prediction.dto.form;

public class LoginDto {
    private String username;
    private String password;

    // Конструктор по умолчанию для ModelMapper
    public LoginDto() {
    }

    // Основной конструктор
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттеры и сеттеры
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    // Сохраняем методы вида record для совместимости с контроллерами
    public String username() {
        return username;
    }

    public String password() {
        return password;
    }
}