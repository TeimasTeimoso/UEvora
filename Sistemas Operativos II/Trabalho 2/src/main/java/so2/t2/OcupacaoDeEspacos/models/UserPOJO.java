package so2.t2.OcupacaoDeEspacos.models;

import javax.validation.constraints.NotEmpty;

public class UserPOJO {

    @NotEmpty(message = "O campo \"nome de utilizador\" não pode ser vazio!")
    String userName;
    @NotEmpty(message = "O campo \"password\" não pode ser vazio!")
    String password;

    public UserPOJO() {}

    public UserPOJO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    public void setUserName(String username) {
        this.userName = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }
}