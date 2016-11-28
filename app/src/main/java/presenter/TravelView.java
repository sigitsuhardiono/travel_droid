package presenter;


import java.util.ArrayList;
import java.util.List;

import model.kota_asal.Datum;

/**
 * Created by www.scclaptop.com on 11/22/2016.
 */
public interface TravelView {
    void tampilLoading();
    void tampilKota(List<Datum> kota_asal,String tipe);
    void validasiError(String pesan);
}
