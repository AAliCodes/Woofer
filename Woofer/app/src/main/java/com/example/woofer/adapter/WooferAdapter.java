package com.example.woofer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.woofer.R;
import com.example.woofer.model.ListItem;

import java.util.List;

/**
 * Created by ali on 2017/05/13.
 */
public class WooferAdapter extends RecyclerView.Adapter<WooferAdapter.WooferHolder> {



    private List<ListItem> listData;
    private LayoutInflater inflater;


    public WooferAdapter(List<ListItem> listData ,Context c){
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }


    @Override
    public WooferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new WooferHolder(view);
    }

    @Override
    public void onBindViewHolder(WooferHolder holder, int position) {
        ListItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getImageResid());
        holder.update.setText(item.getUpdate());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class WooferHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private TextView update;
        private View container;

        public WooferHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.lbl_item_text);
            icon = (ImageView)itemView.findViewById(R.id.im_item_icon);
            update = (TextView)itemView.findViewById(R.id.lbl_item_update);

            container = itemView.findViewById(R.id.cont_item_root);

        }
    }
}
