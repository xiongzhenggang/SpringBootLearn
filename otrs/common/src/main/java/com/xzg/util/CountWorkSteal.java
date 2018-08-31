package com.xzg.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * <p>Title: ${file_name}</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * @author xiongzhenggang
 * @version 1.0
 * @date ${date}
 */
public class CountWorkSteal  extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 10;
    private int start;
    private int end;

    public CountWorkSteal(int start, int end)
    {
        super();
        this.start = start;
        this.end = end;
    }
    @Override
    protected Integer compute() {
        int sum = 0;
        boolean isCompute = (end - start) < THRESHOLD;
        if(isCompute){
            for(int i=start;i<end;i++){
                sum+=i;
            }
        }else{
            int middle = (end - start)/2;
            CountWorkSteal leftTask = new CountWorkSteal(start,middle);
            CountWorkSteal rightTask = new CountWorkSteal(middle+1,end);
            leftTask.fork();
            rightTask.fork();
            sum = leftTask.join() + rightTask.join();
        }
        return sum;
    }

}
class TestWork{
    public static void main(String[] args){

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountWorkSteal task = new CountWorkSteal(1,100);
        Future<Integer> result = forkJoinPool.submit(task);
        try
        {
            System.out.println(result.get());
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }

        if(task.isCompletedAbnormally()){
            System.out.println(task.getException());
        }
    }
}