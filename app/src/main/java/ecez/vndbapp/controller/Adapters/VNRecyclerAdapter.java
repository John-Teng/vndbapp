package ecez.vndbapp.controller.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

import ecez.vndbapp.model.NovelData;

/**
 * Created by johnteng on 2017-09-23.
 */


public abstract class VNRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<NovelData> listData;
    protected LayoutInflater inflater;
    protected Context context;

    abstract public void setData (List<NovelData> newData);

    public VNRecyclerAdapter(List<NovelData> listData, Context context) {
        this.listData = listData;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

}
