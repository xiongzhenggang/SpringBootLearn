package com.xzg.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: ${file_name}</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * @author xiongzhenggang
 * @version 1.0
 * @date ${date}
 */
public class TaskUtil {

    private TaskUtil() {}

    public static List<String> divide(int totalSize, int persize) {
        List<String> parts = new ArrayList<String>();
        if (totalSize <= 0 || persize <= 0) {
            return parts;
        }

        if (persize >= totalSize) {
            parts.add("0:" + totalSize);
            return parts;
        }

        int num = totalSize / persize + (totalSize % persize == 0 ? 0 : 1);

        for (int i=0; i<num; i++) {
            int start = persize*i;
            int end = persize*i+persize;
            if (end > totalSize) {
                end = totalSize;
            }
            parts.add(start + ":" + end);
        }
        return parts;
    }

    public static <T> List<T> getSubList(List<T> allKeys, String part) {
        int start = Integer.parseInt(part.split(":")[0]);
        int end = Integer.parseInt(part.split(":")[1]);
        if (end > allKeys.size()) {
            end = allKeys.size();
        }
        return allKeys.subList(start, end);
    }

}