package com.xzg.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Title: ${file_name}</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * @author xiongzhenggang
 * @version 1.0
 * @date ${date}
 */
public class StreamUtil {


    public static <T,R> List<R> map(List<T> data, Function<T, R> mapFunc) {
        // stream replace foreach
        return data.stream().map(mapFunc).collect(Collectors.toList());

    }

}