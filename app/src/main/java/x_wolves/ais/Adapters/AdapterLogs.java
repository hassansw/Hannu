package x_wolves.ais.Adapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import x_wolves.ais.Fragments.FragFeed;
import x_wolves.ais.MainActivity;
import x_wolves.ais.Models.DataLogs;
import x_wolves.ais.R;


public class AdapterLogs extends RecyclerView.Adapter<AdapterLogs.ViewHolder> {
    Context context;
    List<DataLogs> logsList;

    public AdapterLogs(Context context, List<DataLogs> logsList) {
        this.context = context;
        this.logsList = logsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_logs, parent, false);
        return new ViewHolder(viewHolder);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvLat.setText( logsList.get(position).getLatitude());
        holder.tvLong.setText( logsList.get(position).getLongitude());
        holder.tvDate.setText(logsList.get(position).getDate());

        holder.tvAddr.setText(logsList.get(position).getAddress());
        holder.tvCity.setText(logsList.get(position).getCity());
        holder.tvState.setText(logsList.get(position).getState());

    }


    @Override
    public int getItemCount() {
        return logsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvLat;
        public TextView tvLong;
        public TextView tvDate;
        public TextView tvAddr;
        public TextView tvCity;
        public TextView tvState;


        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvTime);
            tvLat = (TextView) itemView.findViewById(R.id.tvLatitude);
            tvLong = (TextView) itemView.findViewById(R.id.tvLongitude);
            tvAddr = (TextView) itemView.findViewById(R.id.tvAddr);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            tvState = (TextView) itemView.findViewById(R.id.tvState);
        }
    }
}
