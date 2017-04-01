package x_wolves.ais.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import x_wolves.ais.Adapters.AdapterFamily;
import x_wolves.ais.Adapters.AdapterLogs;
import x_wolves.ais.MainActivity;
import x_wolves.ais.Models.DataFamily;
import x_wolves.ais.Models.DataLogs;
import x_wolves.ais.R;


public class FragFamily extends Fragment {

    RecyclerView recyclerView;
    AdapterFamily adapterLogs;
    List<DataFamily> dataList;

    public FragFamily() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList = new ArrayList<>();

        if( isOnline() ) {
            loadFromServer(1); // 1 is userid
        }
    }

    private void loadFromServer(final int id) {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://mmssatc.pk/main/test/data_fam.php?user_id="+id).build();
//                Request request = new Request.Builder().url("http://10.0.2.2/Geo/data_fam.php?user_id="+id).build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DataFamily dataLogs = new DataFamily(jsonObject.getString("fam_name"),jsonObject.getString("fam_email"),jsonObject.getString("fam_contact"),jsonObject.getString("fam_address"),jsonObject.getString("fam_cnic"));
                        dataList.add(dataLogs);
                    }
                }
                catch (IOException | JSONException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };

        task.execute(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_family, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvFamily);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterLogs = new AdapterFamily(getActivity(), dataList);
        recyclerView.setAdapter(adapterLogs);

        return view;

    }

    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
