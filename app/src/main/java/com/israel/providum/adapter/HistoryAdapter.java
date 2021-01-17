package com.israel.providum.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.israel.providum.R;
import com.israel.providum.model.Historydb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context context;
    private List<Historydb> historyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView result;
        public TextView dot;
        public TextView timestamp;
        public ImageView checkBox;

        public MyViewHolder(View view) {
            super(view);
            result = view.findViewById(R.id.result);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
            checkBox = view.findViewById(R.id.check_box);
        }
    }


    public HistoryAdapter(Context context, List<Historydb> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_history, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Historydb note = historyList.get(position);

        holder.result.setText(note.getResult());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(note.getTimestamp()));


    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d YYYY");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }

    public void removeItem(int position) {
        historyList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, historyList.size());
    }
}

