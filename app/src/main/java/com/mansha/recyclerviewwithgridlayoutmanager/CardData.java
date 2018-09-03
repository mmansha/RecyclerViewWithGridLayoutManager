package com.mansha.recyclerviewwithgridlayoutmanager;

public class CardData {

    private String[] cardCaption;
    private int[] cardImageIds;

    private static final String[] sampleCardDataCaption = {"Flowers", "Tree"};
    private static final int[] sampleCardImageId = {R.drawable.diavolo, R.drawable.funghi };

    public static final CardData cardDataArray  = new CardData(sampleCardDataCaption, sampleCardImageId);

    public CardData(String[] cardCaption, int[] cardImageIds){
        this.cardCaption = cardCaption;
        this.cardImageIds = cardImageIds;
    }

    public String[] getCardCaption(){
        return cardCaption;
    }

    public int[] getCardImageIds(){
        return cardImageIds;
    }
}
