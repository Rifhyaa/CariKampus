package id.carikampus.data.model;

import com.google.gson.annotations.Expose;

public class Kampus {

    @Expose
    private Integer id;

    @Expose
    private String nama_kampus;

    @Expose
    private String singkatan;

    @Expose
    private String akreditasi;

    @Expose
    private Integer ranking;

    @Expose
    private Integer total_prodi;

    @Expose
    private Integer total_dosen;

    @Expose
    private Integer total_mahasiswa;

    @Expose
    private Integer biaya_administrasi;

    @Expose
    private Integer biaya_semester_minimal;

    @Expose
    private Integer biaya_semester_maksimal;

    @Expose
    private String alamat;

    @Expose
    private String telepon;

    @Expose
    private String website;

    @Expose
    private String email;

    @Expose
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama_kampus() {
        return nama_kampus;
    }

    public void setNama_kampus(String nama_kampus) {
        this.nama_kampus = nama_kampus;
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

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getTotal_prodi() {
        return total_prodi;
    }

    public void setTotal_prodi(Integer total_prodi) {
        this.total_prodi = total_prodi;
    }

    public Integer getTotal_dosen() {
        return total_dosen;
    }

    public void setTotal_dosen(Integer total_dosen) {
        this.total_dosen = total_dosen;
    }

    public Integer getTotal_mahasiswa() {
        return total_mahasiswa;
    }

    public void setTotal_mahasiswa(Integer total_mahasiswa) {
        this.total_mahasiswa = total_mahasiswa;
    }

    public Integer getBiaya_administrasi() {
        return biaya_administrasi;
    }

    public void setBiaya_administrasi(Integer biaya_administrasi) {
        this.biaya_administrasi = biaya_administrasi;
    }

    public Integer getBiaya_semester_minimal() {
        return biaya_semester_minimal;
    }

    public void setBiaya_semester_minimal(Integer biaya_semester_minimal) {
        this.biaya_semester_minimal = biaya_semester_minimal;
    }

    public Integer getBiaya_semester_maksimal() {
        return biaya_semester_maksimal;
    }

    public void setBiaya_semester_maksimal(Integer biaya_semester_maksimal) {
        this.biaya_semester_maksimal = biaya_semester_maksimal;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
