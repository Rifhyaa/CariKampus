package id.carikampus.model;

import com.google.gson.annotations.Expose;

public class Prodi {

    @Expose
    private int id;

    @Expose
    private Kampus kampus;

    @Expose
    private String nama_prodi;

    @Expose
    private String singkatan;

    @Expose
    private String akreditasi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kampus getKampus() {
        return kampus;
    }

    public void setKampus(Kampus kampus) {
        this.kampus = kampus;
    }

    public String getNama_prodi() {
        return nama_prodi;
    }

    public void setNama_prodi(String nama_prodi) {
        this.nama_prodi = nama_prodi;
    }

    public String getSingkatan() {
        return singkatan;
    }

    public void setSingkatan(String singkatan) {
        this.singkatan = singkatan;
    }

    public String getAkreditasi() {
        return akreditasi;
    }

    public void setAkreditasi(String akreditasi) {
        this.akreditasi = akreditasi;
    }
}
