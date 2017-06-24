/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParallelBarracudaFramework1;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author boya
 */
public class DivideAndConquer_BRCD {     
    static class Range_brcd {
        //copy and past formal parameters and return variable declarations
        //declares a constructor whith only formal parameters
    }    
    class Task_brcd implements Runnable {
        Range_brcd[] rangeArray_brcd;
        AtomicInteger cursor_brcd;
        public Task_brcd(Range_brcd[] rangeArray_brcd, AtomicInteger cursor_brcd) {
            this.rangeArray_brcd = rangeArray_brcd;
            this.cursor_brcd = cursor_brcd;
        }
        
        public void run() {
            try {
                while (true) {
                    int i = cursor_brcd.getAndIncrement();
                    //call recursive method with actual parameters pre-contacted with "rangeArray_brcd.get(i)."                    
                    //sequentialQuicksort(rangeArray.get(i).a, rangeArray.get(i).left, rangeArray.get(i).right);
                }
            } catch (Exception e) {
            }
        }
    }
    
     public void divide_brcd(Range_brcd range_brcd, Range_brcd[][] rangeArray_brcd, int step_brcd, int indexOfRange_brcd) {
        //copy and past content of k-way recursive method content and do necessary action
    }
     
     public void merge_brcd(Range_brcd range_brcd, Range_brcd[][] rangeArrayList_brcd, int step_brcd, int indexOfRange_brcd) {
        //copy and past content of k-way recursive method content and do necessary action
    } 
     
    final public void finalDivide_brcd(Range_brcd[] rangeArray_brcd, Range_brcd[][] rangeArrayList_brcd, int numOfTasks_brcd, int k_way_brcd) {
        int numOfSteps_brcd = (int) (log(numOfTasks_brcd) / log(k_way_brcd));
        for (int i = 0; i < numOfSteps_brcd; i++) {
            for (int j = 0; j < pow(k_way_brcd, i); j++) {                
                divide_brcd(rangeArrayList_brcd[i][j], rangeArrayList_brcd, i + 1, k_way_brcd*j-1);
            }
        }        
        rangeArray_brcd = rangeArrayList_brcd[numOfSteps_brcd];
    }  

    final public void setTasks_brcd(Range_brcd[] rangeArray_brcd, Task_brcd[] tasks_brcd, int numOfThreads_brcd, AtomicInteger cursor_brcd) {
        for (int i = 0; i < numOfThreads_brcd; i++) {
            tasks_brcd[i] = new Task_brcd(rangeArray_brcd, cursor_brcd);
        }
    }

    final public void forkJoin_brcd(Task_brcd[] tasks_brcd, Thread[] threads_brcd, int numOfThreads_brcd) {
        for (int i = 0; i < numOfThreads_brcd; i++) {
            threads_brcd[i] = new Thread(tasks_brcd[i]);
            threads_brcd[i].start();
        }
        for (int i = 0; i < numOfThreads_brcd; i++) {
            try {
                threads_brcd[i].join();
            } catch (InterruptedException ex) {
            }
        }
    }

    final public void finalMerge_brcd(Range_brcd[] rangeArray_brcd, Range_brcd[][] rangeArrayList_brcd, int numOfTasks_brcd, int k_way_brcd) {
        int numOfSteps_brcd = (int) (log(numOfTasks_brcd) / log(k_way_brcd));
        for (int i = numOfSteps_brcd; i > 0; i--) {
            for (int j = 0; j < pow(k_way_brcd, i - 1); j++) {                
                merge_brcd(rangeArrayList_brcd[i - 1][j], rangeArrayList_brcd, i, k_way_brcd * j - 1);
            }
        }        
        rangeArray_brcd = rangeArrayList_brcd[0];
    }   

    public void compute_brcd(Range_brcd range_brcd, int numThreads_brcd, int numOfTasks_brcd, int k_way_brcd) {
        AtomicInteger cursor_brcd = new AtomicInteger(0);
        Range_brcd[] rangeArray_brcd = new Range_brcd[numOfTasks_brcd];
        int numOfSteps_brcd = (int) (log(numOfTasks_brcd) / log(k_way_brcd));
        Range_brcd[][] rangeArrayList_brcd = new Range_brcd[numOfSteps_brcd + 1][];
        Task_brcd[] tasks_brcd = new Task_brcd[numThreads_brcd];
        Thread[] workerThreads_brcd = new Thread[numThreads_brcd];
        for (int i = 0; i <= numOfSteps_brcd; i++) {
            rangeArrayList_brcd[i] = new Range_brcd[(int) pow(k_way_brcd, i)];            
        }       
        rangeArrayList_brcd[0][0] = range_brcd;
       // finalDivide_brcd(rangeArray_brcd, rangeArrayList_brcd, numOfTasks_brcd, k_way_brcd);
        AtomicInteger count1 = new AtomicInteger(0);        
        parallel_finalDivide_brcd(rangeArray_brcd, rangeArrayList_brcd, numOfTasks_brcd, k_way_brcd,numThreads_brcd,count1);
        rangeArray_brcd = rangeArrayList_brcd[numOfSteps_brcd];
        setTasks_brcd(rangeArray_brcd, tasks_brcd, numThreads_brcd, cursor_brcd);
        forkJoin_brcd(tasks_brcd, workerThreads_brcd, numThreads_brcd);
        //finalMerge_brcd(rangeArray_brcd, rangeArrayList_brcd, numOfTasks_brcd, k_way_brcd);
        parallel_finalMerge_brcd(rangeArray_brcd, rangeArrayList_brcd, numOfTasks_brcd, k_way_brcd,  numThreads_brcd,count1);
    }

    //Start Parallel_Divide    
    public void parallel_finalDivide_brcd(Range_brcd[] rangeArray_brcd, Range_brcd[][] rangeArrayList_brcd, int numOfTasks_brcd, int k_way_brcd, int numThreads,AtomicInteger count1) {
        Integer i = 0;        
        int numOfSteps_brcd = (int) (log(numOfTasks_brcd) / log(k_way_brcd));    
        Phase_DIVIDE phase1 = new Phase_DIVIDE(i,count1);
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(numThreads, phase1);
        Range_DIVIDE[] rangeArray = new Range_DIVIDE[(int) pow(k_way_brcd, numOfSteps_brcd)];
        for (int k = 0; k < (int) pow(k_way_brcd, numOfSteps_brcd); k++) {
            rangeArray[k] = new Range_DIVIDE(0, 0);
        }        
        Task_DIVIDE[] tasks = new Task_DIVIDE[numThreads];
        Thread[] threads = new Thread[numThreads];    
        setTask_DIVIDE(rangeArray, tasks, cyclicBarrier, rangeArrayList_brcd, i, numThreads,numOfSteps_brcd,k_way_brcd,count1);
        forkJoin_DIVIDE(tasks, threads, numThreads);     
        rangeArray_brcd = rangeArrayList_brcd[numOfSteps_brcd];            
    }

    private static class Range_DIVIDE {
        int start;
        int end;
        public Range_DIVIDE(int start0, int end0) {
            start = start0;
            end = end0;
        }
    }
    
    private void setTask_DIVIDE(Range_DIVIDE[] rangeArray, Task_DIVIDE[] tasks, CyclicBarrier cb, Range_brcd[][] rangeArrayList, int j, int Num,int numOfSteps_brcd,int k_way_brcd,AtomicInteger count1) {
        for (int i = 0; i < Num; i++) {
            tasks[i] = new Task_DIVIDE(rangeArray, cb, rangeArrayList, j,numOfSteps_brcd,k_way_brcd,count1);
        }
    }

    private void forkJoin_DIVIDE(Task_DIVIDE[] tasks, Thread[] threads, int Num) {
        for (int i = 0; i < Num; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }
        for (int i = 0; i < Num; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class Task_DIVIDE implements Runnable {
        Range_DIVIDE[] rangeArray;
        Range_brcd[][] rangeArrayList;
        int i;
        private CyclicBarrier cb;
        int numOfSteps_brcd;
        int k_way_brcd;
        AtomicInteger count1;
        public Task_DIVIDE(Range_DIVIDE[] rangeArray0, CyclicBarrier cb0, Range_brcd[][] rangeArrayList_brcd, int i0,int numOfSteps_brcd,int k_way_brcd,AtomicInteger count1) {
            rangeArray = rangeArray0;
            cb = cb0;
            rangeArrayList = rangeArrayList_brcd;
            i = i0;
            this.numOfSteps_brcd=numOfSteps_brcd;
            this.k_way_brcd=k_way_brcd;
            this.count1=count1;
        }

        @Override
        public void run() {            
            while (Phase_DIVIDE.phaseStage < numOfSteps_brcd) {
                int i = Phase_DIVIDE.phaseStage;                
                int j = count1.getAndIncrement();                
                if (j < pow(k_way_brcd, i)) {                
                    divide_brcd(rangeArrayList[i][j], rangeArrayList, i + 1, k_way_brcd*j-1);
                } else {
                    try {
                        cb.await();
                    } catch (InterruptedException ex) {
                    } catch (BrokenBarrierException ex) {
                    }
                }                
            }           
        }
    }

    public static class Phase_DIVIDE implements Runnable {
        static Integer phaseStage;
        static Integer indexArray;
        AtomicInteger count1;
        public Phase_DIVIDE(Integer phaseStage0,AtomicInteger count1) {
            phaseStage = phaseStage0;
            this.count1=count1;            
        }
        @Override
        public void run() {            
            phaseStage++;            
            count1.set(0);
        }
    }
    //End Parallel Divide    
    
   //Start Parallel Merge     
    final public void parallel_finalMerge_brcd(Range_brcd[] rangeArray_brcd, Range_brcd[][] rangeArrayList_brcd, int numOfTasks_brcd, int k_way_brcd, int numThreads,AtomicInteger count1) {          
        int numOfSteps_brcd = (int) (log(numOfTasks_brcd) / log(k_way_brcd));  
        Integer i = numOfSteps_brcd;        
        Phase_MERGE phase1 = new Phase_MERGE(i,count1);
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(numThreads, phase1);
        Range_MERGE[] rangeArray = new Range_MERGE[(int) pow(k_way_brcd, numOfSteps_brcd)];
        for (int k = 0; k < (int) pow(k_way_brcd, numOfSteps_brcd); k++) {
            rangeArray[k] = new Range_MERGE(0, 0);
        }        
        Task_MERGE[] tasks = new Task_MERGE[numThreads];
        Thread[] threads = new Thread[numThreads];        
        setTask_MERGE(rangeArray, tasks, cyclicBarrier, rangeArrayList_brcd, i, numThreads,k_way_brcd,count1);
        forkJoin_MERGE(tasks, threads, numThreads);       
        rangeArray_brcd = rangeArrayList_brcd[0];            
    }

    private static class Range_MERGE {
        int start;
        int end;
        public Range_MERGE(int start0, int end0) {
            start = start0;
            end = end0;
        }
    }   

    private void setTask_MERGE(Range_MERGE[] rangeArray, Task_MERGE[] tasks, CyclicBarrier cb, Range_brcd[][] rangeArrayList, int j, int Num,int k_way_brcd,AtomicInteger count1) {
        for (int i = 0; i < Num; i++) {
            tasks[i] = new Task_MERGE(rangeArray, cb, rangeArrayList, j,k_way_brcd,count1);
        }
    }

    private void forkJoin_MERGE(Task_MERGE[] tasks, Thread[] threads, int Num) {
        for (int i = 0; i < Num; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }
        for (int i = 0; i < Num; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class Task_MERGE implements Runnable {
        Range_MERGE[] rangeArray;
        Range_brcd[][] rangeArrayList_brcd;
        int i;
        private CyclicBarrier cb;
        int k_way_brcd;
        AtomicInteger count1;
        public Task_MERGE(Range_MERGE[] rangeArray0, CyclicBarrier cb0, Range_brcd[][] rangeArrayList_brcd, int i0,int k_way_brcd,AtomicInteger count1) {
            rangeArray = rangeArray0;
            cb = cb0;
            this.rangeArrayList_brcd = rangeArrayList_brcd;
            i = i0;
            this.k_way_brcd=k_way_brcd;
            this.count1=count1;
        }

        @Override
        public void run() {            
            while (Phase_MERGE.phaseStage >0) {
                int i = Phase_MERGE.phaseStage;                
                int j = count1.getAndIncrement();                
                if (j < pow(k_way_brcd, i-1)) {               
                    merge_brcd(rangeArrayList_brcd[i - 1][j], rangeArrayList_brcd, i, k_way_brcd * j - 1);                    
                } else {
                    try {
                        cb.await();
                    } catch (InterruptedException ex) {
                    } catch (BrokenBarrierException ex) {
                    }
                }                
            }           
        }
    }

    public static class Phase_MERGE implements Runnable {
        static Integer phaseStage;
        static Integer indexArray;
        AtomicInteger count1;
        public Phase_MERGE(Integer phaseStage0,AtomicInteger count1) {
            phaseStage = phaseStage0;
            this.count1=count1;            
        }
        @Override
        public void run() {            
            phaseStage--;            
            count1.set(0);
        }
    }    
    //End Parallel Merge
}
