package id.my.asadullah.crudmahasiswa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateHapusActivity extends AppCompatActivity {

    // todo -- inisialisasi awal
    @BindView(R.id.edtNim)
    EditText edtNim;
    @BindView(R.id.edtNama)
    EditText edtNama;
    @BindView(R.id.edtJurusan)
    EditText edtJurusan;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hapus);
        ButterKnife.bind(this);

        //todo -- terima data intent
        id = getIntent().getStringExtra("id");
        String nim = getIntent().getStringExtra("nim");
        String nama = getIntent().getStringExtra("nama");
        String jurusan = getIntent().getStringExtra("jurusan");

        //todo - tampilkan data di edit text
        edtNim.setText(nim);
        edtNama.setText(nama);
        edtJurusan.setText(jurusan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick({R.id.btnUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnUpdate:
                updateData();
                break;
        }
    }

    private void updateData() {
        AlertDialog alert = new AlertDialog.Builder(UpdateHapusActivity.this).create();
        alert.setTitle("Konfirmasi");
        alert.setMessage("Anda yakin ingin merubah data ini ?");
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String upnim = edtNim.getText().toString();
                String upnama = edtNama.getText().toString();
                String upjurusan = edtJurusan.getText().toString();

                ConfigRetrofit.service.update(id, upnim, upnama, upjurusan).enqueue(new Callback<ResponseInsert>() {
                    @Override
                    public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                        String pesan = response.body().getPesan();
                        int status = response.body().getStatus();

                        if (status == 1){
                            Toast.makeText(UpdateHapusActivity.this, pesan, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(UpdateHapusActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseInsert> call, Throwable t) {

                    }
                });
                dialogInterface.dismiss();
                startActivity(new Intent(UpdateHapusActivity.this, MainActivity.class));
                finishActivity(1);
                //recreate();

            }
        });

        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.show();

    }

    //todo -- buat menu muncul di toolbar, panggil layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hapus, menu);
        return true;
    }

    //todo -- aksi dari menu yang dibuat, panggil id
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int idMenu = item.getItemId();

        if (idMenu == R.id.btnHapus) {

            AlertDialog alert = new AlertDialog.Builder(UpdateHapusActivity.this).create();
            alert.setTitle("Konfirmasi");
            alert.setMessage("Anda yakin ingin menghapus data ini ?");
            alert.setButton(AlertDialog.BUTTON_POSITIVE, "Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    ConfigRetrofit.service.hapus(id).enqueue(new Callback<ResponseInsert>() {
                        @Override
                        public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                            String pesan = response.body().getPesan();
                            int status = response.body().getStatus();

                            if (status == 1 ){
                                Toast.makeText(UpdateHapusActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdateHapusActivity.this, MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(UpdateHapusActivity.this, pesan, Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseInsert> call, Throwable t) {

                        }
                    });
                    dialogInterface.dismiss();
                    //finishActivity(2);
                }
            });
            alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            alert.show();

        }
        return super.onOptionsItemSelected(item);
    }

}