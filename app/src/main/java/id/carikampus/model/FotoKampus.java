package id.carikampus.model;

import com.google.gson.annotations.Expose;

public class FotoKampus {

    @Expose
    private Integer id;

    @Expose
    private Kampus kampus;

    @Expose
    private String foto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Kampus getKampus() {
        return kampus;
    }

    public void setKampus(Kampus kampus) {
        this.kampus = kampus;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
