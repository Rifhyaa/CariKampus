package id.carikampus.model;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class Komentar {

    @Expose
    private int id;

    @Expose
    private Integer id_kampus;

    @Expose
    private Kampus kampus;

    @Expose
    private Integer id_user_login;

    @Expose
    private UserLogin userLogin;

    @Expose
    private String komentar;

    @Expose
    private int edited = 0;

    @Expose
    private Date created;

    public Komentar(int id, Integer id_kampus, Integer id_user_login, String komentar) {
        this.id = id;
        this.id_kampus = id_kampus;
        this.id_user_login = id_user_login;
        this.komentar = komentar;
        this.edited = 0;
        this.created = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId_kampus() {
        return id_kampus;
    }

    public void setId_kampus(Integer id_kampus) {
        this.id_kampus = id_kampus;
    }

    public Kampus getKampus() {
        return kampus;
    }

    public void setKampus(Kampus kampus) {
        this.kampus = kampus;
    }

    public Integer getId_user_login() {
        return id_user_login;
    }

    public void setId_user_login(Integer id_user_login) {
        this.id_user_login = id_user_login;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public int getEdited() {
        return edited;
    }

    public void setEdited(int edited) {
        this.edited = edited;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
