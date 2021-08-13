package id.carikampus.model;

import com.google.gson.annotations.Expose;

public class UserLogin {

    @Expose
    private int id;

    @Expose
    private String nama;

    @Expose
    private int jenis_kelamin;

    @Expose
    private String email;

    @Expose
    private String username;

    @Expose
    private String password;

    public UserLogin() {

    }

    public UserLogin(int id, String nama, String email,String username, String password) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.username = username;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(int jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
}
