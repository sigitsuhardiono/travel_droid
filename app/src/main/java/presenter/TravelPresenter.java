package presenter;

/**
 * Created by sigit on 11/22/2016.
 */
public interface TravelPresenter {
    void getKotaAsal(String auth);
    void getKotaTujuan(String auth,String id_kota_asal);
}
