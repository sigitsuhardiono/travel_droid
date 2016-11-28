package sigit.pertama;
import preference.Preference;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import presenter.LoginPresenter;
import presenter.LoginView;
import presenter.LoginPresenterImp;

public class LoginActivity extends AppCompatActivity implements LoginView {
    LoginPresenter presenter;
    Preference dtpref;
    Button login;
    EditText user,pass;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        dtpref = new Preference(getApplicationContext());
        Toast.makeText(getApplicationContext(),
                "User Login Status: " + dtpref.isLoggedIn(), Toast.LENGTH_LONG)
                .show();
        login   = (Button)findViewById(R.id.btn_login);
        user    = (EditText)findViewById(R.id.input_user);
        pass    = (EditText)findViewById(R.id.input_pass);
        presenter = new LoginPresenterImp(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login(user.getText().toString(), pass.getText().toString(),getApplicationContext());
            }
        });
    }

    // fungsi yang dipanggil saat validasi error
    @Override
    public void showValidationError() {
        Toast.makeText(this, "Please enter valid username and password!", Toast.LENGTH_SHORT).show();
    }


    // fungsi yang dipanggil saat login error
    @Override
    public void loginError() {
        Toast.makeText(this, "User tidak ditemukan!", Toast.LENGTH_SHORT).show();
        loading.dismiss();
    }

    // fungsi yang dipanggil saat validasi error
    @Override
    public void loginSucces() {
        Intent main = new Intent(this,MainActivity.class);
        startActivity(main);
        finish();
        loading.dismiss();
    }

    // fungsi yang dipanggil sebelum login
    @Override
    public void beforeLogin() {
        loading = ProgressDialog.show(this, "Mengambil Data","Silakan tunggu..",false,false);
    }

}
