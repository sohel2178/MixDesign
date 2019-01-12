package com.linearbd.mixdesign.ui.documentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linearbd.mixdesign.R;
import com.linearbd.mixdesign.utils.PrebaseActivity;

public class DocumentationActivity extends PrebaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.documentation));
    }
}
