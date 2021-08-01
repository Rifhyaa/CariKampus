package id.carikampus.model;

import com.google.gson.annotations.Expose;

public class KampusFavorit {

    @Expose
    private int id;

    @Expose
    private int id_kampus;

    @Expose
    private Kampus kampus;

    @Expose
    private int id_user_login;

    @Expose
    private UserLogin userLogin;

    @Expose
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_kampus() {
        return id_kampus;
    }

    public void setId_kampus(int id_kampus) {
        this.id_kampus = id_kampus;
    }

    public Kampus getKampus() {
        return kampus;
    }

    public void setKampus(Kampus kampus) {
        this.kampus = kampus;
    }

    public int getId_user_login() {
        return id_user_login;
    }

    public void setId_user_login(int id_user_login) {
        this.id_user_login = id_user_login;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
