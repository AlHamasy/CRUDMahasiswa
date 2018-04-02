package id.my.asadullah.crudmahasiswa;

/**
 * Created by asadullah on 1/12/18.
 */

public class DataMahasiswa {

    // todo -- data ini diambil dari hasil json getMahasiswa
    String mahasiswa_id;
    String mahasiswa_nim;
    String mahasiswa_nama;
    String mahasiswa_jurusan;
    String mahasiswa_foto;

    public String getMahasiswa_id() {
        return mahasiswa_id;
    }

    public String getMahasiswa_nim() {
        return mahasiswa_nim;
    }

    public String getMahasiswa_nama() {
        return mahasiswa_nama;
    }

    public String getMahasiswa_jurusan() {
        return mahasiswa_jurusan;
    }

    public String getMahasiswa_foto() {
        return mahasiswa_foto;
    }
}
