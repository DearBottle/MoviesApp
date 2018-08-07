package com.bottle.moviesapp.adapter;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.bean.MoviesBean;
import com.bottle.moviesapp.utils.FileSizeUtil;
import com.bottle.moviesapp.utils.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by mengbaobao on 2018/7/28.
 */
public class MoviesAdapter extends BaseQuickAdapter<MoviesBean, BaseViewHolder> {
    public MoviesAdapter() {
        super(R.layout.item_movies);
    }

    @Override
    protected void convert(BaseViewHolder helper, MoviesBean item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_time, TimeUtil.format(item.getTime()));
        helper.setText(R.id.tv_size, FileSizeUtil.FormetFileSize(item.getSize()));
    }


}
