package sigit.pertama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class JadwalTravelActivity extends AppCompatActivity {
    private String kota_asal;
    private String kota_tujuan;
    private String tanggal_berangkat;
    private String jam_berangkat;
    private String jml_penumpang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_travel);
        kota_asal =  getIntent().getStringArrayListExtra("var_jadwal").get(0);
        kota_tujuan =  getIntent().getStringArrayListExtra("var_jadwal").get(1);
        tanggal_berangkat =  getIntent().getStringArrayListExtra("var_jadwal").get(2);
        jam_berangkat =  getIntent().getStringArrayListExtra("var_jadwal").get(3);
        jml_penumpang =  getIntent().getStringArrayListExtra("var_jadwal").get(4);
//        Toast.makeText(getApplicationContext(),"ini form jadwal "+kota_asal+"-"+kota_tujuan+"-"+tanggal_berangkat+"-"+jam_berangkat+"-"+jml_penumpang,Toast.LENGTH_SHORT).show();
        TextView jdw_kota_asal = (TextView)findViewById(R.id.jdw_kota_asal);
        jdw_kota_asal.setText(kota_asal);
        TextView jdw_kota_tujuan = (TextView)findViewById(R.id.jdw_kota_tujuan);
        jdw_kota_tujuan.setText(kota_tujuan);
        TextView jdw_jml_penumpang = (TextView)findViewById(R.id.jdw_jml_penumpang);
        jdw_jml_penumpang.setText(jml_penumpang);
        TextView jdw_keberangkatan = (TextView)findViewById(R.id.jdw_keberangkatan);
        jdw_keberangkatan.setText(tanggal_berangkat+" "+jam_berangkat);
    }
}
