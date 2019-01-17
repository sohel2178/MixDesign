package com.linearbd.mixdesign.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.database.Query;
import com.linearbd.mixdesign.R;
import com.linearbd.mixdesign.helper.AggregateSize;
import com.linearbd.mixdesign.helper.ConcreteGrade;
import com.linearbd.mixdesign.helper.DryBulkVolByUnitVol;
import com.linearbd.mixdesign.helper.Exposure;
import com.linearbd.mixdesign.helper.HimsWorthConstant;
import com.linearbd.mixdesign.helper.StandardDeviation;
import com.linearbd.mixdesign.helper.WaterAndAirContent;
import com.linearbd.mixdesign.helper.WaterCementFromCompressiveStrength;
import com.linearbd.mixdesign.holder.MixDesignHolder;
import com.linearbd.mixdesign.model.Data;
import com.linearbd.mixdesign.ui.designForm.DesignFormFragment;
import com.linearbd.mixdesign.ui.documentation.DocumentationActivity;
import com.linearbd.mixdesign.ui.login.LoginActivity;
import com.linearbd.mixdesign.ui.mixDesign.MixDesignActivity;
import com.linearbd.mixdesign.utils.AdUtil;
import com.linearbd.mixdesign.utils.BaseActivity;
import com.linearbd.mixdesign.utils.Constant;

public class MainActivity extends BaseActivity implements View.OnClickListener,MainContract.View{

    private Button btnNew,btnDocumentation;

    private MainPresenter mPresenter;

    private int action;

    FirebaseRecyclerAdapter<Data,MixDesignHolder> adapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupToolbar();
        getSupportActionBar().setTitle(getString(R.string.app_name));

        new AdUtil(this);

        mPresenter = new MainPresenter(this);

        mPresenter.checkCurrentUser();

        getmInterstitialAd().setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                getmInterstitialAd().loadAd(new AdRequest.Builder().build());
                mPresenter.buttonClick(action);
            }
        });

        //initView();

        /*Log.d("hhhhhhh",HimsWorthConstant.getValue(2)+"");
        Log.d("hhhhhhh",WaterCementFromCompressiveStrength.getWaterCementRatio(36.5,1)+"");
        Log.d("hhhhhhh",Exposure.getWaterCementRatio(Exposure.MODERATE,Exposure.REINFORCED_CONCRETE)+"");
        Log.d("hhhhhhh",WaterAndAirContent.getWaterContent("20 mm",0,"30-50 mm")+"");
        Log.d("hhhhhhh", StandardDeviation.getStandardDeviation(ConcreteGrade.M30)+"");
        Log.d("hhhhhhh", DryBulkVolByUnitVol.getBulkVolPerUnitVol(AggregateSize.S_20,1.8)+"");*/


    }

    @Override
    public void initView() {
        btnNew = findViewById(R.id.new_design);
        btnDocumentation = findViewById(R.id.documentation);
        btnNew.setOnClickListener(this);
        btnDocumentation.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mPresenter.initAdapter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_design:
                mPresenter.requestForAd(1);
                break;

            case R.id.documentation:
                mPresenter.requestForAd(2);
                break;
        }
    }


    @Override
    public void showForm(){
        DesignFormFragment designFormFragment = new DesignFormFragment();
        designFormFragment.setCancelable(false);
        Bundle bundle = new Bundle();
        //bundle.putSerializable(Constant.DATA,tran);
        designFormFragment.setArguments(bundle);

        designFormFragment.show(getSupportFragmentManager(),"TTTT");
    }

    @Override
    public void showDocumentation() {
        Intent intent = new Intent(getApplicationContext(), DocumentationActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAd(int action) {
        this.action = action;

        if(getmInterstitialAd().isLoaded()){
            showAd();
        }else{
            mPresenter.buttonClick(action);
        }

    }

    @Override
    public void initAdapter(Query query) {
        adapter = new FirebaseRecyclerAdapter<Data, MixDesignHolder>(Data.class,R.layout.item_mix_design,MixDesignHolder.class,query) {
            @Override
            protected void populateViewHolder(final MixDesignHolder viewHolder, final Data model, int position) {
                viewHolder.bind(model);

                viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       /* Intent intent = new Intent(getApplicationContext(), MixDesignActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.DATA,model);
                        intent.putExtras(bundle);
                        startActivity(intent);*/

                    }
                });

                viewHolder.getmFoldingCell().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewHolder.getmFoldingCell().toggle(false);
                    }
                });
            }
        };

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void startLoginActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

}
