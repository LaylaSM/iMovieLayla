package intermediate.iak.laylasm.imovie;

/**
 * Created by Layla Siti Mardhiyah on 16/08/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerAdapterTrailer extends RecyclerView.Adapter<RecyclerHolderTrailer> {
    public List<ObjectTrailer.DataTrailer> itemList;
    public Context context;

    public RecyclerAdapterTrailer(Context context, List<ObjectTrailer.DataTrailer> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerHolderTrailer onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, null);
        RecyclerHolderTrailer rcv = new RecyclerHolderTrailer(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerHolderTrailer holder, int position) {
        // ObjectTrailer.DataTrailer item = itemList.get(position);
        holder.txtName.setText(itemList.get(position).name);
        holder.txtType.setText(itemList.get(position).type);
        holder.key = itemList.get(position).key;
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

}