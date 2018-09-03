package com.mansha.recyclerviewwithgridlayoutmanager;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    interface Listner{
        void onClick(int position, int rawsound);
    };

    private String[] captions;
    private int[] imageIds;
    private Listner listner;
    private MediaPlayer mp = null;



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    public CardViewAdapter(String[] caption, int[] imageIds){
        this.captions = caption;
        this.imageIds = imageIds;
    }

    @Override
    public int getItemCount(){
        return CardData.cardDataArray.getCardCaption().length;
    }

    public void setListenr(Listner listener){
        this.listner = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mp = MediaPlayer.create(view.getContext(), R.raw.ring);
//                mp.start();
//                //mp.release();
                listner.onClick(position, R.raw.ring);

            }
        });

    }


}
