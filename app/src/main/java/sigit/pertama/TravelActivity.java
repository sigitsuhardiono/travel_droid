package sigit.pertama;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import adapter.KotaAdapter;
import me.anwarshahriar.calligrapher.Calligrapher;
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
    private String id_kota_asal;
    private String id_kota_tujuan;
    private EditText input_kota_asal_travel;
    private EditText input_kota_tujuan_travel;
    private EditText input_tgl_travel;
    private ListView lv;
    KotaAdapter adapter;
    EditText inputSearch;
    TravelPresenter presenter;
    Preference dtpref;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/OpenSans-Regular.ttf", true);

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        input_tgl_travel = (EditText)findViewById(R.id.input_tgl_travel);
        input_tgl_travel.setText(day+"/"+month+"/"+year);
        input_tgl_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog();
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

    public void CityDialog(String tipe) {
        dtpref = new Preference(getApplicationContext());
        String auth = dtpref.getUserDetails().get("auth");
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
    public void tampilKota(List<Datum> kota_asal, final String tipe){
        loading.dismiss();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View post = inflater.inflate(R.layout.auto_kota_travel, null);
        lv = (ListView)post.findViewById(R.id.list_view);
        inputSearch = (EditText)post.findViewById(R.id.cari_kota_travel);
        adapter = new KotaAdapter(this, R.layout.list_kota, kota_asal);;
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TravelActivity.this, "Memilih : "+adapter.getItem(position).getNama()+"-"+adapter.getItem(position).getId(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
                if(tipe == "asal"){
                    id_kota_asal = adapter.getItem(position).getId();
                    input_kota_asal_travel = (EditText)findViewById(R.id.input_kota_asal_travel);
                    input_kota_asal_travel.setText(adapter.getItem(position).getNama());
                }
                else{
                    id_kota_tujuan = adapter.getItem(position).getId();
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
