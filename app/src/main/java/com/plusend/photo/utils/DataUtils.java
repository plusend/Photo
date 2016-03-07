package com.plusend.photo.utils;

import com.plusend.photo.model.Photo;
import com.plusend.photo.utils.log.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by plusend on 16/3/7.
 */
public class DataUtils {

    private static final String TAG = "DataUtils";

    public static List<Photo> convertFromDb(List<Photo> list) {
        if (list == null)
            return null;
        List<Photo> result = new ArrayList<>();
        int size = list.size();
        int week = 0;
        int flag = 0;
        Date date;
        int j = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        for (int i = 0; i < size; i++) {
            Photo photo = list.get(i);
            date = photo.getDate();
            calendar.setTime(date);
            if (week == calendar.get(Calendar.WEEK_OF_YEAR)) {
                flag++;
                result.add(j++,photo);
            } else {
                week = calendar.get(Calendar.WEEK_OF_YEAR);

//                Photo empty = new Photo();
                switch (flag % 4) {
                    case 1:
                        result.add(j++, null);
                        result.add(j++, null);
                        result.add(j++, null);
                        break;
                    case 2:
                        result.add(j++, null);
                        result.add(j++, null);
                        break;
                    case 3:
                        result.add(j++, null);
                        break;
                    default:
                        break;
                }
                result.add(j++,photo);
                flag = 1;
            }
        }
        Logger.d(TAG, "convertFromDb:" + result);
        return result;
    }
}
