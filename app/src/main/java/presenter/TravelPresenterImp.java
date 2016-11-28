package presenter;

import android.text.TextUtils;

import java.util.List;

import model.kota_asal.Datum;
import model.kota_asal.KotaAsal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import service.ApiInterface;
import service.ApiService;

/**
 * Created by sigit on 11/22/2016.
 */
public class TravelPresenterImp implements TravelPresenter{
    TravelView travelvw;
    public TravelPresenterImp(TravelView travelvw){
        this.travelvw = travelvw;
    }

    @Override
    public void getKotaAsal(String auth){
        if(TextUtils.isEmpty(auth)){
            travelvw.validasiError("auth kosong!");
        }
        else{
            travelvw.tampilLoading();
            ApiInterface apiinterface = ApiService.getClient().create(ApiInterface.class);
            Call<KotaAsal> call = apiinterface.postTravelAsal(auth);
            call.enqueue(new Callback<KotaAsal>() {
                @Override
                public void onResponse(Call<KotaAsal> call, Response<KotaAsal> response) {
                    Integer code = response.body().getMeta().getCode();
                    String pesan = response.body().getMeta().getMessage();
                    if (code == 400) {
                        travelvw.validasiError(pesan);
                    } else {
                        List<Datum> kota_asal = response.body().getData();
                        int i = 0;
                        String dataNama[] = new String[response.body().getData().size()];
                        String dataId[] = new String[response.body().getData().size()];
                        String dataIdKota[] = new String[response.body().getData().size()];
                        String dataIsBandara[] = new String[response.body().getData().size()];
                        for (Datum city : response.body().getData()) {
                            dataNama[i] = city.getNama();
                            dataId[i] = city.getId();
                            dataIdKota[i] = city.getIdKota();
                            dataIsBandara[i] = city.getIsBandara();
                            i++;
                        }
                        travelvw.tampilKota(kota_asal,"asal");
                    }
                }
                @Override
                public void onFailure(Call<KotaAsal> call, Throwable t) {
                    travelvw.validasiError("Gagal mengambil data!");
                }
            });
        }
    }

    @Override
    public void getKotaTujuan(String auth,String id_kota_asal){
        if(TextUtils.isEmpty(auth)){
            travelvw.validasiError("Auth Kosong!");
        }
        else if(TextUtils.isEmpty(id_kota_asal)){
            travelvw.validasiError("Kota asal belum terpilih!");
        }
        else{
            travelvw.tampilLoading();
            ApiInterface apiinterface = ApiService.getClient().create(ApiInterface.class);
            Call<KotaAsal> call = apiinterface.postTravelTujuan(auth,id_kota_asal);
            call.enqueue(new Callback<KotaAsal>() {
                @Override
                public void onResponse(Call<KotaAsal> call, Response<KotaAsal> response) {
                    Integer code = response.body().getMeta().getCode();
                    String pesan = response.body().getMeta().getMessage();
                    if(code == 400){
                        travelvw.validasiError(pesan);
                    }
                    else{
                        List<Datum> data= response.body().getData();
                        travelvw.tampilKota(data,"tujuan");
                    }
                }

                @Override
                public void onFailure(Call<KotaAsal> call, Throwable t) {

                }
            });
        }
    }
}
