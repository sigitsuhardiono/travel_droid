package presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import model.user.User;
import preference.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ApiInterface;
import service.ApiService;


/**
 * Created by www.scclaptop.com on 11/19/2016.
 */
public class LoginPresenterImp implements LoginPresenter {
    //inisialisasi login view
    LoginView loginvw;
    Preference dtpref;
    Context ctx;
    //kontruktor
    public LoginPresenterImp(LoginView loginvw){
        this.loginvw = loginvw;
    }

    @Override
    public void login(String username, String password,Context context){
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            loginvw.showValidationError();
        }
        else{
            loginvw.beforeLogin();
            ApiInterface apiinterface = ApiService.getClient().create(ApiInterface.class);
            HashMap<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            ctx = context;
            Call<User> call = apiinterface.postLogin(params);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User>call, Response<User> response) {
					Integer code = response.body().getMeta().getCode();
					if(code == 400){
						loginvw.loginError();
					}
					else{
						String data_id = response.body().getData().getId();
                        String data_nama = response.body().getData().getNama();
						String data_pimpinan = response.body().getData().getPimpinan();
						String data_telp = response.body().getData().getTelp1();
						String data_email = response.body().getData().getEmail();
						String data_username = response.body().getData().getUsername();
						String data_password = response.body().getData().getPassword();
						String data_auth = response.body().getData().getAuth();
						String data_logo = response.body().getData().getLogo();
						String data_by = response.body().getData().getBy();
						String data_request = response.body().getData().getRequestHs();
						String data_sts = response.body().getData().getSts();
						String data_deposit = response.body().getData().getDeposit();
						String data_has_deposit = response.body().getData().getHasDeposit();
						dtpref = new Preference(ctx);
                        dtpref.createLoginSession(data_id,data_nama,data_pimpinan,data_telp,data_email,data_username,data_password,data_auth,data_logo,data_by,data_request,data_sts,data_deposit,data_has_deposit);
                        loginvw.loginSucces();

					}
                }

                @Override
                public void onFailure(Call<User>call, Throwable t) {
                    Log.e("tes", "gagal");
                }
            });
        }
    }
}
