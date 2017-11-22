package concurrent;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * 利用多线程统计某个磁盘上所有文件的个数
 * Created by Kaiming Wan on 2017/1/20.
 */
public class CountFileExample {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Long countNums=forkJoinPool.invoke(new CountDirTask(new File("C:\\")));
        long endTime = System.nanoTime();
        System.out.println("磁盘上的文件总数为: " +countNums);
        System.out.println("总计耗时: "+ TimeUnit.SECONDS.convert(endTime-startTime,TimeUnit.NANOSECONDS)+"秒");
    }
}


//统计文件的个数
class CountFileTask extends RecursiveTask<Long>{

    private File rootFile;      //这里的rootFile肯定都是文件了

    public CountFileTask(File rootFile) {
        this.rootFile = rootFile;
    }

    @Override
    protected Long compute() {
        return 1L;
    }
}


//继承RecursiveTask，表示有返回值的任务
class CountDirTask extends RecursiveTask<Long> {
    File rootFile=null;

    public CountDirTask(File rootFile) {
        this.rootFile = rootFile;
    }

    @Override
    protected Long compute() {
        //保存每个任务自己计算得到的count
        Long count=0L;



        //保存要分叉的任务
        List<RecursiveTask<Long>> forks = new LinkedList<>();

        if(rootFile.listFiles()!=null) {

            for (File f : rootFile.listFiles()) {

                if (f.isDirectory()) {
                    CountDirTask dirTask = new CountDirTask(f);
                    forks.add(dirTask);
                    dirTask.fork();
                } else {
                    CountFileTask fileTask = new CountFileTask(f);
                    forks.add(fileTask);
                    fileTask.fork();
                }
            }
        }else{
            return 0L;
        }

        //上面进行完分叉后，然后开始合并结果
        for (RecursiveTask<Long> task : forks) {
            count = count + task.join();
        }
        return count;

    }



}
