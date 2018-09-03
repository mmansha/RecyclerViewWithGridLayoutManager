package com.mansha.recyclerviewwithgridlayoutmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewDBAdapter extends RecyclerView.Adapter<CardViewDBAdapter.ViewHolder>{

     interface Listner{
        void onClick(int position, int rawsound);
    };

    private DatabaseHelper dbHelperClass;
    private SQLiteDatabase db;
    private Context mContext;
    private Cursor mCursor;
    private Listner listner;

    public CardViewDBAdapter(Context context, Cursor cursor){
        dbHelperClass = new DatabaseHelper(context);
        this.db = dbHelperClass.getWritableDatabase();
        mContext = context;
        mCursor = cursor;

    }

    public void setListenr(Listner listener){
        this.listner = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }



    @Override
    public int getItemCount() {
        int rowCount = this.dbHelperClass.getRowCount(this.db);
        Log.d("CardViewDBAdapter", "Row count = " + rowCount);
        return rowCount;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap bitmap;
        if(!mCursor.moveToPosition(position)){
            return;
        }
        Log.d("CardViewDBAdapter", "Position = " + position);
        String mCaptionName = mCursor.getString(2);
        String mCaptionImagePath = mCursor.getString(3);

        String mCaptionSoundPath  = mCursor.getString(4);
        Log.d("CardViewDBAdapter", "Image Path in DB: " + mCaptionImagePath);
        CardView cardView = holder.cardView;
        if ( mCaptionImagePath != null ){
            mCaptionImagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/dadAppData/" + mCaptionImagePath;
            Log.d("CardViewDBAdaper", "Path: " + mCaptionImagePath);
//            bitmap = BitmapFactory.decodeFile(mCaptionImagePath);

            ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
//            Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), R.drawable.restaurant);
//            imageView.setImageDrawable(drawable);
            imageView.setImageBitmap(BitmapFactory.decodeFile(mCaptionImagePath));
            imageView.setContentDescription(mCaptionName);
        }


        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(mCaptionName);

    }
}
