package sigit.pertama;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.KotaAdapter;
import model.kota_asal.Datum;
import preference.Preference;
import presenter.TravelPresenter;
import presenter.TravelPresenterImp;
import presenter.TravelView;

public class TravelActivity extends AppCompatActivity implements TravelView{

    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minutes;
    private String id_kota_asal;
    private String id_kota_tujuan;
    private String auth;
    private String kota_asal;
    private String kota_tujuan;
    private String tanggal_berangkat;
    private String jam_berangkat;
    private String jml_penumpang;

    private EditText input_kota_asal_travel;
    private EditText input_kota_tujuan_travel;
    private EditText input_tgl_travel;
    private EditText input_jam_travel;
    private EditText input_jml_penumpang;
    private ListView lv;
    KotaAdapter adapter;
    EditText inputSearch;
    TravelPresenter presenter;
    Preference dtpref;
    ProgressDialog loading;
    Button cari_jadwal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        dtpref = new Preference(getApplicationContext());
        auth = dtpref.getUserDetails().get("auth");
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minutes = cal.get(Calendar.MINUTE);
        input_tgl_travel = (EditText)findViewById(R.id.input_tgl_travel);
        input_tgl_travel.setText(day+"/"+month+"/"+year);
        input_tgl_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog();
            }
        });
        input_jam_travel = (EditText)findViewById(R.id.jam_trv_berangkat) ;
        input_jam_travel.setText(hour+":"+minutes);
        input_jam_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog();
            }
        });
        input_kota_asal_travel = (EditText)findViewById(R.id.input_kota_asal_travel);
        input_kota_asal_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityDialog("asal");
            }
        });
        input_kota_tujuan_travel = (EditText)findViewById(R.id.input_kota_tujuan_travel);
        input_kota_tujuan_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityDialog("tujuan");
            }
        });
        cari_jadwal = (Button)findViewById(R.id.btn_cari_jadwal);
        cari_jadwal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                input_tgl_travel = (EditText)findViewById(R.id.input_tgl_travel);
                input_jam_travel = (EditText)findViewById(R.id.jam_trv_berangkat);
                input_jml_penumpang = (EditText)findViewById(R.id.jml_travel_berangkat);
                tanggal_berangkat = input_tgl_travel.getText().toString();
                jam_berangkat = input_jam_travel.getText().toString();
                jml_penumpang = input_jml_penumpang.getText().toString();
//                Toast.makeText(getApplicationContext(),"ini form travel "+kota_asal+"-"+kota_tujuan+"-"+tanggal_berangkat+"-"+jam_berangkat+"-"+jml_penumpang,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),JadwalTravelActivity.class);
                ArrayList<String> var_jadwal = new ArrayList<String>();
                var_jadwal.add(0,kota_asal);
                var_jadwal.add(1,kota_tujuan);
                var_jadwal.add(2,tanggal_berangkat);
                var_jadwal.add(3,jam_berangkat);
                var_jadwal.add(4,jml_penumpang);
                i.putStringArrayListExtra("var_jadwal",var_jadwal);
                startActivity(i);
                finish();
            }
        });
    }

    public void DateDialog(){
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
            {
                input_tgl_travel.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
            }};
        DatePickerDialog dpDialog=new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();
    }

    public void TimeDialog(){
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public  void onTimeSet(TimePicker view, int selectedHour, int selectedMinute){
                input_jam_travel.setText(selectedHour+":"+selectedMinute);
            }
        };
        TimePickerDialog tpDialog = new TimePickerDialog(this,listener,hour,minutes,true);
        tpDialog.show();
    }

    public void CityDialog(String tipe) {
        presenter = new TravelPresenterImp(this);
        if(tipe == "asal"){
            presenter.getKotaAsal(auth);
        }
        else{
            Toast.makeText(getApplicationContext(),auth,Toast.LENGTH_SHORT).show();
            presenter.getKotaTujuan(auth,id_kota_asal);
        }
    }

    @Override
    public void validasiError(String pesan){
        Toast.makeText(getApplicationContext(),pesan,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void tampilLoading() {
        loading = ProgressDialog.show(this, "Mengambil Data","Silakan tunggu..",false,false);
    }
    @Override
    public void tampilKota(List<Datum> kota, final String tipe){
        loading.dismiss();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View post = inflater.inflate(R.layout.auto_kota_travel, null);
        lv = (ListView)post.findViewById(R.id.list_view);
        inputSearch = (EditText)post.findViewById(R.id.cari_kota_travel);
        adapter = new KotaAdapter(this, R.layout.list_kota, kota);;
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TravelActivity.this, "Memilih : "+adapter.getItem(position).getNama()+"-"+adapter.getItem(position).getId(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
                if(tipe == "asal"){
                    id_kota_asal = adapter.getItem(position).getId();
                    kota_asal = adapter.getItem(position).getNama();
                    input_kota_asal_travel = (EditText)findViewById(R.id.input_kota_asal_travel);
                    input_kota_asal_travel.setText(adapter.getItem(position).getNama());
                }
                else{
                    id_kota_tujuan = adapter.getItem(position).getId();
                    kota_tujuan = adapter.getItem(position).getNama();
                    input_kota_tujuan_travel = (EditText)findViewById(R.id.input_kota_tujuan_travel);
                    input_kota_tujuan_travel.setText(adapter.getItem(position).getNama());
                }
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                TravelActivity.this.adapter.filter(cs);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        dialog.setContentView(post);
        dialog.show();
    }

}
