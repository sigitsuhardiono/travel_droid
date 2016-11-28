package sigit.pertama;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import preference.Preference;

public class ProfilActivity extends AppCompatActivity {
    Preference dtpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        dtpref = new Preference(getApplicationContext());
        new DownloadImageTask((ImageView)findViewById(R.id.profile_image))
                .execute("http://agen.travelmalang.co.id/img/logo-agen/"+dtpref.getUserDetails().get("logo"));
        TextView nama_user = (TextView)findViewById(R.id.user_profile_name);
        nama_user.setText(dtpref.getUserDetails().get("nama"));

        TextView email_user = (TextView)findViewById(R.id.user_profile_email);
        email_user.setText(dtpref.getUserDetails().get("email"));
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
