package concurrent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Kaiming Wan on 2017/1/20.
 */
public class CountFileStreamExample {
    public static void main(String[] args) {
//        File[] roots = File.listRoots();
//        List<File> fileList = Arrays.asList(roots);
        List<File> fileList = Arrays.asList(new File("D:\\我的坚果云\\alluxio"));
        List<File> directories = Collections.synchronizedList(new ArrayList<>(fileList));
        AtomicLong c1 = new AtomicLong();
        AtomicLong c2 = new AtomicLong();
        long startTime = System.nanoTime();
        while (!directories.isEmpty()) {
            List<File> temp = new ArrayList<>(directories);
            directories.clear();
            temp.parallelStream().filter(file -> file!=null).forEach(file->{
                File[] fileArray = file.listFiles();
                if(fileArray!=null){
                    List<File> files = Arrays.asList(fileArray);
                    files.stream().filter(f->f!=null).forEach(f->{
                        c2.getAndIncrement();
                        if (f.isDirectory()) {
                            directories.add(f);
                        }else {
                            c1.addAndGet(f.length());
                        }
                    });
                }
            });
        }

        long endTime = System.nanoTime();
        System.out.println("字节数===========" + c1.get());
        System.out.println("文件数===========" + c2.get());
        System.out.println("时间============= "+ TimeUnit.SECONDS.convert(endTime-startTime,TimeUnit.NANOSECONDS)+"秒");
    }
}
