package ru.club.sfera.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import ru.club.sfera.R;
import ru.club.sfera.model.Profile;


public class FollowersSpotlightListAdapter extends RecyclerView.Adapter<FollowersSpotlightListAdapter.MyViewHolder> {

    private List<Profile> items;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(View view, Profile obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mOnItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mFullname;
        public ImageView thumbnail;
        public ProgressBar mProgressBar;
        public MaterialRippleLayout mParent;

        public MyViewHolder(View view) {

            super(view);

            mParent = (MaterialRippleLayout) view.findViewById(R.id.parent);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            mFullname = (TextView) view.findViewById(R.id.fullname);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }


    public FollowersSpotlightListAdapter(Context context, List<Profile> items) {

        mContext = context;
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_spotlight_thumbnail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Profile item = items.get(position);

        holder.mFullname.setVisibility(View.GONE);
        holder.thumbnail.setVisibility(View.VISIBLE);
        holder.mProgressBar.setVisibility(View.VISIBLE);

        holder.mFullname.setText(item.getFullname());

        if (item.getLowPhotoUrl() != null && item.getLowPhotoUrl().length() > 0) {

            final ProgressBar progressBar = holder.mProgressBar;
            final ImageView imageView = holder.thumbnail;
            final TextView fullname = holder.mFullname;

            Glide.with(mContext)
                    .load(item.getLowPhotoUrl())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                            fullname.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            imageView.setImageResource(R.drawable.profile_default_photo);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                            progressBar.setVisibility(View.GONE);
                            fullname.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(holder.thumbnail);

        } else {

            holder.mProgressBar.setVisibility(View.GONE);
            holder.thumbnail.setImageResource(R.drawable.profile_default_photo);
            holder.mFullname.setVisibility(View.VISIBLE);
        }

        holder.mParent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (mOnItemClickListener != null) {

                    mOnItemClickListener.onItemClick(view, item, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}