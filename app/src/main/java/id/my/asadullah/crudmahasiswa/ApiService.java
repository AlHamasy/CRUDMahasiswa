package id.my.asadullah.crudmahasiswa;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by asadullah on 1/12/18.
 */

interface ApiService {

    @GET("getMahasiswa")
    Call<ResponseGetMahasiswa> lihat ();

    @FormUrlEncoded
    @POST("insertMahasiswa")
    Call <ResponseInsert> tambah (@Field("nim") String nim,
                                  @Field("name") String nama,
                                  @Field("majors") String jurusan);

    @FormUrlEncoded
    @POST("updateMahasiswa")
    Call <ResponseInsert> update (@Field("id") String id,
                                  @Field("nim") String nim,
                                  @Field("name") String nama,
                                  @Field("majors") String jurusan);

    @FormUrlEncoded
    @POST("deleteMahasiswa")
    Call<ResponseInsert> hapus (@Field("id") String id);
}