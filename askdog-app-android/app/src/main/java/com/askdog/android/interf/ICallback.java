package com.askdog.android.interf;

import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Administrator on 2016/8/6.
 */
public interface
ICallback {
    /**
     * 赞或者踩回调接口 1 表示赞
     *                  0 表示踩
     * @param type
     */
    public void HeaderBtnClickListener(int type);
    /**
     *发表评论
     * @param node 节点，评论内容直接在上面添加
     * @param content 评论内容
     * @param holeder 根据该内容上传服务器
     */
    public void OnRealeaseClick(final TreeNode node,String content,Object holeder);//点击发布按钮事件

    /**
     *
     * @param holder 节点内容
     * @param type    哪一种holder，便于强制类型转换
     */
    public void OnItemClick(Object holder,int type);

    /**
     * 启动activity页面，0 个人频道，其它待定
     * @param type
     */
    public void OnItemClickListerner(int type);

    /**
     * 展开二级菜单
     * @param node
     */
    public void expandSecondList(TreeNode node,boolean isExpand,String id);

    /**
     * 推荐加载更多
     */
    public void onLoadMoreClick();

    /**
     *
     * @param node 父节点
     * @param id   回复的ID
     */
    public void onAnswerCommend(TreeNode node,String id,String reply_comment_id);
}
