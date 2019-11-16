package tr.com.mertkolgu.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView cadText;
    TextView chfText;
    TextView usdText;
    TextView jpyText;
    TextView tryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadText = findViewById(R.id.textViewCAD);
        chfText = findViewById(R.id.textViewCHF);
        usdText = findViewById(R.id.textViewUSD);
        jpyText = findViewById(R.id.textViewJPY);
        tryText = findViewById(R.id.textViewTRY);
    }

    public void getRates(View view) {
        DownloadData downloadData = new DownloadData();

        try {
            String url = "http://data.fixer.io/api/latest?access_key=6b7eb927582c62769d473aea1b788893&format=1";
            downloadData.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                System.out.println("Base" + base);

                String rates = jsonObject.getString("rates");
                System.out.println("Rates" + rates);

                JSONObject jsonObject1 = new JSONObject(rates);

                String CAD = jsonObject1.getString("CAD");
                cadText.setText("CAD : " + CAD);

                String CHF = jsonObject1.getString("CHF");
                chfText.setText("CHF : " + CHF);

                String USD = jsonObject1.getString("USD");
                usdText.setText("USD : " + USD);

                String JPY = jsonObject1.getString("JPY");
                jpyText.setText("JPY : " + JPY);

                String TRY = jsonObject1.getString("TRY");
                tryText.setText("TRY : " + TRY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStream.read();

                while (data > 0) {
                    char character = (char) data;
                    result.append(character);
                    data = inputStreamReader.read();
                }
                return result.toString();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
