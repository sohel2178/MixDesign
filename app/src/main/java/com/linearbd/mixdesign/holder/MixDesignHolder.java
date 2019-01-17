package com.linearbd.mixdesign.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.linearbd.mixdesign.R;
import com.linearbd.mixdesign.model.Data;
import com.ramotion.foldingcell.FoldingCell;

public class MixDesignHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    FoldingCell mFoldingCell;

    public MixDesignHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.title);
        mFoldingCell = itemView.findViewById(R.id.folding_cell);
    }

    public void bind(Data data){
        tvTitle.setText(data.getTitle());
    }

    public View getItemView(){
        return this.itemView;
    }

    public FoldingCell getmFoldingCell() {
        return mFoldingCell;
    }
}
