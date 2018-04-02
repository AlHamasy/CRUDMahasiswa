package id.my.asadullah.crudmahasiswa;

import java.util.List;

/**
 * Created by asadullah on 1/12/18.
 */

class ResponseGetMahasiswa {

    String pesan;
    int status;
    List <DataMahasiswa> datanya;

    public String getPesan() {
        return pesan;
    }

    public int getStatus() {
        return status;
    }

    public List<DataMahasiswa> getDatanya() {
        return datanya;
    }

}