package com.linearbd.mixdesign.ui.mixDesign;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linearbd.mixdesign.R;
import com.linearbd.mixdesign.model.Data;
import com.linearbd.mixdesign.ui.mixDesign.calculation.CalculationFragment;
import com.linearbd.mixdesign.ui.mixDesign.info.InfoFragment;
import com.linearbd.mixdesign.utils.AdUtil;
import com.linearbd.mixdesign.utils.Constant;
import com.linearbd.mixdesign.utils.PrebaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MixDesignActivity extends PrebaseActivity {

    private Data data;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_design);

        data = (Data) getIntent().getSerializableExtra(Constant.DATA);

        setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(data!=null){
            getSupportActionBar().setTitle(data.getTitle());
        }

        new AdUtil(this);

        initView();
    }

    private void initView() {

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        if(adapter==null){
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
        }
        adapter.addFragment(new InfoFragment(), "Info");
        adapter.addFragment(new CalculationFragment(), "Calculations");
        //adapter.addFragment(new ProgressFragment(), "PROGRESS");
        //adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public Data getData() {
        return data;
    }
}
