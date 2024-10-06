package Praktikum.Courier;

public class CourierRegister {
    private String login;
    private String password;

    public CourierRegister(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierRegister from (CourierData courierInfo) {
        return new CourierRegister(courierInfo.getLogin(), courierInfo.getPassword());
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
