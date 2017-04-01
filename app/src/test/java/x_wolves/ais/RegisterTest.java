package x_wolves.ais;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

public class RegisterTest {

    @Test
    public void signUp() throws Exception {

        String etName = "Hassan";
        String etEmail = "hassan@gmail.com";
        String etPass = "classw";
        String etContact = "03122747998";
        final Integer etSubsId = 12345672;
        String etAddress = "Bufferzone";
        Integer etNumFamily = 5;

        final String urlData = "http://mmssatc.pk/main/test/sign_up.php?name="+etName+"&email="+etEmail+"&pass="+etPass+"&contact="+etContact+"&subsId="+etSubsId+"$address="+etAddress+"&numFam="+etNumFamily;

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(urlData).build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //jsonObject.getString("fam_name");
                        if (jsonObject.getString("status").equals("success")) {
                            assertEquals("Success", jsonObject.getString("status"), "success" );
                        } else if ( jsonObject.getString("status").equals("error") ) {
                            assertEquals("Success", jsonObject.getString("status"), "error" );
                        } else if ( jsonObject.getString("status").equals("exists") ){
                            assertEquals("Success", jsonObject.getString("status"), "exists" );
                        }
                    }
                }
                catch (IOException | JSONException e) {
                }

                return null;
            }


        };
        task.execute();
    }

}