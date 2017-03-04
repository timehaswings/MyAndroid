package com.zyh.android.withthenotes.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by zhou on 2016/1/15.
 */
public class CostDBColumn implements BaseColumns{

    public static final String AUTHORITIES = "com.zyh.android.withthenotes";

    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITIES+"/"+CostDBColumn.TABLENAME);

    public static final int DB_VERSION = 1;

    public static final String DBNAME = "cost.db";
    public static final String TABLENAME = "cost_table";
    public static final String EVENT_NAME = "event_name";
    public static final String EVENT_COST = "event_cost";
    public static final String EVENT_DESC = "event_desc";
    public static final String EVENT_DATE = "event_date";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
}
