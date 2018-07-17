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
 * Created by ali on 2017/05/14.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder>{

    private List<ListItem> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
        void onSecondaryIconClick(int p);

    }

    public void setItemClickCallback (final ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }


    public FriendAdapter(List<ListItem> listData ,Context c){
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }


    @Override
    public FriendAdapter.FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new FriendAdapter.FriendHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendAdapter.FriendHolder holder, int position) {
        ListItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getImageResid());
        holder.update.setText(item.getUpdate());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class FriendHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private ImageView icon;
        private TextView update;
        private View container;

        public FriendHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.lbl_item_text);
            title.setOnClickListener(this);

            icon = (ImageView)itemView.findViewById(R.id.im_item_icon);
            update = (TextView)itemView.findViewById(R.id.lbl_item_update);

            container = itemView.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.cont_item_root){
                itemClickCallback.onItemClick(getAdapterPosition());
                //System.out.println(itemClickCallback.onItemClick(getAdapterPosition()));
            } else{
                itemClickCallback.onSecondaryIconClick(getAdapterPosition());
            }
        }
    }

}
