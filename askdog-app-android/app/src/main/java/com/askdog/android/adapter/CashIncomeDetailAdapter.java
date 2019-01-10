package com.askdog.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.CashIncomeBean;
import com.askdog.android.model.HistoryBean;
import com.askdog.android.utils.DateUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wyong on 2016/8/24.
 */
public class CashIncomeDetailAdapter extends RecyclerView.Adapter<CashIncomeDetailAdapter.ViewHolder> {

    private final Context mContext;
    private IOnClickListener mListener;

    public void setmItemsData(ArrayList<CashIncomeBean.IncomeResult> mItems) {
        this.mItemsData = mItems;
    }

    public void addItemsData(ArrayList<CashIncomeBean.IncomeResult> mItems){
        mItemsData.addAll(mItems);
        notifyDataSetChanged();
    }
    private ArrayList<CashIncomeBean.IncomeResult> mItemsData;
    public CashIncomeDetailAdapter(Context context, IOnClickListener listener, ArrayList<CashIncomeBean.IncomeResult> items) {
        mContext = context;
        mItemsData = items;
        mListener = listener;
    }

    @Override
    public CashIncomeDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_cash_income_detail_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CashIncomeDetailAdapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                mListener.onItemViewClickListener(pos);
            }
        });
        CashIncomeBean.IncomeResult bean = mItemsData.get(position);
        holder.bindData(bean);
    }

    @Override
    public int getItemCount() {
        return mItemsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cash_income_experience_title)
        TextView title;
        @Bind(R.id.cash_income_time)
        TextView time;
        @Bind(R.id.cash_income_price)
        TextView price;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bindData(CashIncomeBean.IncomeResult value){
            title.setText(value.experience_title);
            time.setText(DateUtils.getTimeState(String.valueOf(value.income_time), ""));
            price.setText(String.valueOf("+" +value.price) + "元");
        }
    }
}
