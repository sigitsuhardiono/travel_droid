package preference;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import sigit.pertama.LoginActivity;
import sigit.pertama.MainActivity;

@SuppressLint("CommitPrefEdits")
public class Preference {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // nama sharepreference
    private static final String PREF_NAME = "Sesi";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "id";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_PIMPINAN = "pimpinan";
    public static final String KEY_TELP = "telp";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_AUTH = "auth";
    public static final String KEY_LOGO = "logo";
    public static final String KEY_BY = "by";
    public static final String KEY_REQUEST = "request_hs";
    public static final String KEY_STS = "sts";
    public static final String KEY_DEPOSIT = "deposit";
    public static final String KEY_HAS_DEPOSIT = "has_deposit";


    // Constructor
    public Preference(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String id,String name,String pimpinan,String telp,String email,String username,String password,String auth,String logo,String by,String request_hs,String sts,String deposit,String has_deposit){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_NAMA, name);
        editor.putString(KEY_PIMPINAN, pimpinan);
        editor.putString(KEY_TELP, telp);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_AUTH, auth);
        editor.putString(KEY_LOGO, logo);
        editor.putString(KEY_BY, by);
        editor.putString(KEY_REQUEST, request_hs);
        editor.putString(KEY_STS, sts);
        editor.putString(KEY_DEPOSIT, deposit);
        editor.putString(KEY_HAS_DEPOSIT, has_deposit);
        editor.commit();
    }


    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }

    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_NAMA, pref.getString(KEY_NAMA, null));
        user.put(KEY_PIMPINAN, pref.getString(KEY_PIMPINAN, null));
        user.put(KEY_TELP, pref.getString(KEY_TELP, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_AUTH, pref.getString(KEY_AUTH, null));
        user.put(KEY_LOGO, pref.getString(KEY_LOGO, null));
        user.put(KEY_BY, pref.getString(KEY_BY, null));
        user.put(KEY_REQUEST, pref.getString(KEY_REQUEST, null));
        user.put(KEY_STS, pref.getString(KEY_STS, null));
        user.put(KEY_DEPOSIT, pref.getString(KEY_DEPOSIT, null));
        user.put(KEY_HAS_DEPOSIT, pref.getString(KEY_HAS_DEPOSIT, null));
        return user;
    }


    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}