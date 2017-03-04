package com.zyh.android.withthenotes.db;

import java.util.List;

/**
 * Created by zhou on 2016/1/15.
 */
public interface CostDBInterface {

    /**
     * 查询消费
     * @param cost
     */
    void onQueryResult(String cost);

    interface OnDeleteAllCost{
        void complete();
    }

    interface OnAddNewCost{
        void complete(boolean isSuccess);
    }

    interface OnQueryCost{
        void complete(List<CostDBBean> data);
    }

    interface OnQueryCostUseId{
        void complete(CostDBBean data);
    }
}
