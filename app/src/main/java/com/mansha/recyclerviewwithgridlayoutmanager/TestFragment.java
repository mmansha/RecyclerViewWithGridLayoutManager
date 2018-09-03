package com.mansha.recyclerviewwithgridlayoutmanager;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment{

    MediaPlayer mp = null;
    Cursor mCursor;
    Context mContext;

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Log.i("TestFragement", "Length " + CardData.cardDataArray.getCardCaption().length);
        mContext = getActivity().getApplicationContext();
        RecyclerView recyclerView = (RecyclerView)inflater.inflate(R.layout.fragment_test, container, false);

        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        mCursor = dbHelper.getAllRows(db);

        CardViewDBAdapter cardViewDBAdapter = new CardViewDBAdapter(getContext(), dbHelper.getAllRows(db));
        recyclerView.setAdapter(cardViewDBAdapter);

//        CardViewAdapter cardviewAdapter = new CardViewAdapter(CardData.cardDataArray.getCardCaption(), CardData.cardDataArray.getCardImageIds());
//        recyclerView.setAdapter(cardviewAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        cardViewDBAdapter.setListenr(new CardViewDBAdapter.Listner() {
            @Override
            public void onClick(int position, int rawSoundfile) {
//              getActivity().startActivity();
                mp = MediaPlayer.create(getView().getContext(), rawSoundfile);
                Log.d("TestFragment", "Audio status  = " +  mp.isPlaying());
                if(!mp.isPlaying()) {
                    Log.d("TestFragment", "Audio not playing will start it");
                    mp.start();
                } else {
                    Log.d("TestFragment", "Audio Playing still");
                }



            }
        });
        return recyclerView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null)
            mp.release();
    }
}
