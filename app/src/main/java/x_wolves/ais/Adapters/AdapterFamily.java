package x_wolves.ais.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import x_wolves.ais.Models.DataFamily;
import x_wolves.ais.R;


public class AdapterFamily extends RecyclerView.Adapter<AdapterFamily.ViewHolder> {
    Context context;
    List<DataFamily> familyList;

    public AdapterFamily(Context context, List<DataFamily> logsList) {
        this.context = context;
        this.familyList = logsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_family, parent, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(familyList.get(position).getName());
        holder.tvContact.setText(familyList.get(position).getContact());
        holder.tvEmail.setText(familyList.get(position).getEmail());
        holder.tvAddress.setText(familyList.get(position).getAddress());
        holder.tvCNIC.setText(familyList.get(position).getCNIC());
    }

    @Override
    public int getItemCount() {
        return familyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvContact;
        public TextView tvEmail;
        public TextView tvAddress;
        public TextView tvCNIC;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvContact = (TextView) itemView.findViewById(R.id.tvContact);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvCNIC = (TextView) itemView.findViewById(R.id.tvCNIC);
        }
    }
}
