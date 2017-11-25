package com.lavekush.telstra.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lavekush.telstra.R;
import com.lavekush.telstra.fragment.ItemFragment.OnListFragmentInteractionListener;
import com.lavekush.telstra.vo.RowItem;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final List<RowItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ItemRecyclerViewAdapter(List<RowItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.mTitle.setText(mValues.get(position).getTitle());
        viewHolder.mDesc.setText(mValues.get(position).getDescription());
        Picasso.with(viewHolder.mIcon.getContext())
                .load(mValues.get(position).getImageHref()).into(viewHolder.mIcon);

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(mValues.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mIcon;
        public final TextView mTitle;
        public final TextView mDesc;
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = view.findViewById(R.id.text_title);
            mDesc = view.findViewById(R.id.text_desc);
            mIcon = view.findViewById(R.id.img_icon);
        }
    }
}
