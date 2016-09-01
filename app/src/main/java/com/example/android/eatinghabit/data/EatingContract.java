package com.example.android.eatinghabit.data;

import android.provider.BaseColumns;

/**
 * Created by OWNER on 8/31/2016.
 */
public final class EatingContract {

    private EatingContract(){

    }

    public static final class EatingEntry implements BaseColumns{

        public static final String TABLE_NAME = "eating";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_EATING_MENU = "menu";

        public static final String COLUMN_EATING_DATE = "date";

        public static final String COLUMN_EATING_TIME = "time";

        public static final String COLUMN_EATING_CALROIES = "calories";


    }
}
