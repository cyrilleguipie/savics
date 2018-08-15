package com.savics.savics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.savics.savics.R;
import com.savics.savics.model.User;
import java.util.List;

public class UserListAdapter extends  RecyclerView.Adapter<UserListAdapter.ViewHolder> {

  private List<User> mValues;
  private Context mContext;


  public UserListAdapter(Context context, List<User> items) {

    mContext = context;
    mValues = items;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    public final TextView label_title;
    public final View mView;

    public ViewHolder(View itemView) {
      super(itemView);

      label_title = itemView.findViewById(R.id.label_title);

      mView = itemView;
    }
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);

    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int position) {

    //get object at position
    final User object = mValues.get(position);

    holder.label_title.setText(new StringBuilder(object.toString()));

  }

  @Override public int getItemCount() {
    return mValues.size();
  }


}
