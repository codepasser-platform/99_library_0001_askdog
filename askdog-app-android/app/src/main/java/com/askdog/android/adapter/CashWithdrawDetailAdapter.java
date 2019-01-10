package com.askdog.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.CashWithdrawBean;
import com.askdog.android.utils.DateUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wyong on 2016/8/24.
 */
public class CashWithdrawDetailAdapter extends RecyclerView.Adapter<CashWithdrawDetailAdapter.ViewHolder> {

    private final Context mContext;
    private IOnClickListener mListener;
    private ArrayList<CashWithdrawBean.WithdrawResult> mItemsData;

    public CashWithdrawDetailAdapter(Context context, IOnClickListener listener, ArrayList<CashWithdrawBean.WithdrawResult> items) {
        mContext = context;
        mItemsData = items;
        mListener = listener;
    }

    public void setmItemsData(ArrayList<CashWithdrawBean.WithdrawResult> mItems) {
        this.mItemsData = mItems;
    }

    public void addItemsData(ArrayList<CashWithdrawBean.WithdrawResult> mItems) {
        mItemsData.addAll(mItems);
        notifyDataSetChanged();
    }

    @Override
    public CashWithdrawDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_cash_withdraw_record_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CashWithdrawDetailAdapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                mListener.onItemViewClickListener(pos);
            }
        });
        CashWithdrawBean.WithdrawResult bean = mItemsData.get(position);
        holder.bindData(bean);
    }

    @Override
    public int getItemCount() {
        return mItemsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cash_withdrawal_to)
        TextView to;
        @Bind(R.id.cash_withdrawal_time)
        TextView time;
        @Bind(R.id.cash_withdrawal_amount)
        TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CashWithdrawBean.WithdrawResult value) {
            to.setText(value.withdrawal_to);
            time.setText(DateUtils.getTimeState(String.valueOf(value.withdrawal_time), ""));
            amount.setText(String.valueOf(value.withdrawal_amount));
        }
    }
}
