package id.carikampus.model;

import com.google.gson.annotations.Expose;

public class ProdiFavorit {

    @Expose
    private int id;

    @Expose
    private int id_prodi;

    @Expose
    private Prodi prodi;

    @Expose
    private int id_user_login;

    @Expose
    private UserLogin userLogin;

    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_prodi() {
        return id_prodi;
    }

    public void setId_prodi(int id_prodi) {
        this.id_prodi = id_prodi;
    }

    public Prodi getProdi() {
        return prodi;
    }

    public void setProdi(Prodi prodi) {
        this.prodi = prodi;
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
