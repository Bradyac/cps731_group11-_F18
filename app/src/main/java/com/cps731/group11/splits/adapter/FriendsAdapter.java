package com.cps731.group11.splits.adapter;

import com.cps731.group11.splits.R;
import com.cps731.group11.splits.model.Friend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private Friend[] friends;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            //imageView = (ImageView) v.findViewById(R.id.friend_picture);
            textView = (TextView) v.findViewById(R.id.friend_name);
        }

        /*public ImageView getImageView() {
            return imageView;
        }*/

        public TextView getTextView() {
            return textView;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public FriendsAdapter(Friend[] dataSet) {
        friends = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_friend, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        /*try {*/
            //viewHolder.getImageView().setImageBitmap(friends[position].convertPicture());
            viewHolder.getTextView().setText(friends[position].getName());
        /*} catch (SQLException e){
            e.printStackTrace();
        }*/
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return friends.length;
    }
}