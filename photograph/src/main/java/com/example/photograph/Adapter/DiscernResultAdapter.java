package com.example.photograph.Adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.photograph.R;
import com.example.photograph.model.GetDiscernResultResponse;

import java.util.List;

/**
 * 识别结果列表适配器
 * @author llw
 */

/**
 * 识别结果列表适配器
 * @author llw
 */
public class DiscernResultAdapter extends BaseQuickAdapter<GetDiscernResultResponse.ResultBean, BaseViewHolder> {
    public DiscernResultAdapter(int layoutResId, @Nullable List<GetDiscernResultResponse.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetDiscernResultResponse.ResultBean item) {
        helper.setText(R.id.tv_keyword,item.getKeyword())
                .setText(R.id.tv_root,item.getRoot())
                .setText(R.id.tv_score,String.valueOf(item.getScore()));
    }
}