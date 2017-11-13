package com.example.xxxloli.zshmerchant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.xxxloli.zshmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComplainActivity extends AppCompatActivity {
    @BindView(R.id.functionLL) LinearLayout functionLL;
    @BindView(R.id.distributionLL) LinearLayout distributionLL;
    @BindView(R.id.suggestLL) LinearLayout suggestLL;
    @BindView(R.id.feedbackLL) LinearLayout feedbackLL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.back_rl, R.id.functionLL, R.id.distributionLL, R.id.suggestLL, R.id.feedbackLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.functionLL:
                Intent function = new Intent(this,ExceptionActivity.class);
                //function.putExtra(getString(R.string.title),R.string.terraceFunctionException);
                //function.putExtra(getString(R.string.txceptionType),R.string.functionException);
                startActivity(function);
                break;
            case R.id.distributionLL:
                Intent distribution = new Intent(this,ExceptionActivity.class);
                distribution.putExtra(getString(R.string.title), R.string.distributionIssue);
                distribution.putExtra(getString(R.string.txceptionType), R.string.distributionProblem);
                startActivity(distribution);
                break;
            case R.id.suggestLL:
                Intent suggest = new Intent(this,ExceptionActivity.class);
                suggest.putExtra(getString(R.string.title), R.string.productSuggest);
                suggest.putExtra(getString(R.string.txceptionType), R.string.productSuggestion);
                startActivity(suggest);
                break;
            case R.id.feedbackLL:
                startActivity(new Intent(this,FeedbackRecordActivity.class));
                break;
        }
    }

}
