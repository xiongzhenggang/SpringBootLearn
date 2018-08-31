package com.xzg.util;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * <p>Title: ${file_name}</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * @author xiongzhenggang
 * @version 1.0
 * @date ${date}
 */
public class ForeachUtil {

    public static <T> List<T> foreachAddWithReturn(int num, Function<Integer, List<T>> getFunc) {
        List<T> result = new ArrayList<T>();
        for (int i=0; i< num; i++) {

            result.addAll(CatchUtil.tryDo(i, getFunc));
        }
        return result;
    }


    public static <T> Lazy<List<T>> foreachAddReturn(int num, Function<Integer,T> getFunc){
        List<T> result = new ArrayList<>();

        IntStream.of(num).forEach( i->
                result.add(CatchUtil.tryDo(i,getFunc))
        );
        return Lazy.of(()-> result);
    }



    public static <T> void foreachDone(List<T> data, Consumer<T> doFunc) {
        for (T part: data) {
            CatchUtil.tryDo(part, doFunc);
        }
    }

}