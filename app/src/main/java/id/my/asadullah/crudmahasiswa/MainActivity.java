package id.my.asadullah.crudmahasiswa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.my.asadullah.crudmahasiswa.adapter.MahasiswaAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerMahasiswa)
    RecyclerView recyclerMahasiswa;

    Button btnSimpan, btnBatal;
    EditText edtNim, edtNama, edtJurusan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this,"", "Loading", false);

        //TODO 1 -- get data dari service
        ConfigRetrofit.service.lihat().enqueue(new Callback<ResponseGetMahasiswa>() {

            @Override
            public void onFailure(Call<ResponseGetMahasiswa> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Tidak ada koneksi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call<ResponseGetMahasiswa> call, Response<ResponseGetMahasiswa> response) {
                dialog.dismiss();
                //TODO 2 -- ambil datanya
                int status = response.body().getStatus();
                Log.d("Status","status"+response.body().getStatus());
                if (status == 1){
                    List <DataMahasiswa> dataMhs = response.body().getDatanya();
                    MahasiswaAdapter adapter = new MahasiswaAdapter(dataMhs);
                    recyclerMahasiswa.setAdapter(adapter);
                    recyclerMahasiswa.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
            }

        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }

        });
    }

    private void displayDialog() {

        final Dialog dialog = new Dialog (this);
        dialog.setTitle("Input data");
        dialog.setContentView(R.layout.dialog_input);

        edtNim = (EditText)dialog.findViewById(R.id.edtNim);
        edtNama = (EditText)dialog.findViewById(R.id.edtNama);
        edtJurusan = (EditText)dialog.findViewById(R.id.edtJurusan);
        btnBatal = (Button)dialog.findViewById(R.id.btnBatal);
        btnSimpan = (Button)dialog.findViewById(R.id.btnSimpan);

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String paramNim = edtNim.getText().toString();
                String paramNama = edtNama.getText().toString();
                String paramJurusan = edtJurusan.getText().toString();

                ConfigRetrofit.service.tambah(paramNim, paramNama, paramJurusan).enqueue(new Callback<ResponseInsert>() {

                    @Override
                    public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {

                        String pesan = response.body().getPesan();
                        int status = response.body().getStatus();

                        if (status == 1){
                            Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            recreate();
                        }
                        else{
                            Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseInsert> call, Throwable t) {
                        Log.d("Server Error", t.getMessage());
                    }
                });
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnSetting) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}