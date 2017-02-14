package x_wolves.ais.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.transition.Visibility;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import x_wolves.ais.Adapters.AdapterLogs;
import x_wolves.ais.MainActivity;
import x_wolves.ais.Models.DataLogs;
import x_wolves.ais.R;


public class FragFeed extends Fragment {

    RecyclerView recyclerView;
    AdapterLogs adapterLogs;
    List<DataLogs> dataList;

    public FragFeed() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList = new ArrayList<>();
        loadFromServer(1); // 1 is userid
    }

    private void loadFromServer(final int id) {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://10.0.2.2/Geo/data_logs.php?user_id="+id).build();
//                Request request = new Request.Builder().url("http://mmssatc.pk/main/test/data_logs.php?user_id=1").build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DataLogs dataLogs = new DataLogs(jsonObject.getString("userAcc_latitude"), jsonObject.getString("userAcc_longitude"), jsonObject.getString("userAcc_dateTime"), jsonObject.getString("userAcc_Address"),jsonObject.getString("userAcc_City"), jsonObject.getString("userAcc_State"));
                        dataList.add(dataLogs);
                    }
                }
                catch (IOException | JSONException e) { e.printStackTrace();}

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
        View view = inflater.inflate(R.layout.frag_feed, container, false);
        ((MainActivity) getActivity()).toolbar.setTitle("Maps");
        recyclerView = (RecyclerView) view.findViewById(R.id.rvLogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterLogs = new AdapterLogs(getActivity(), dataList);
        recyclerView.setAdapter(adapterLogs);
//        mProgressFedd = (ProgressBar) getView().findViewById(R.id.pbFeed);

        return view;

    }



    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }



}


