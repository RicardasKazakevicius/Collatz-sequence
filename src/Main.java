import mpi.*;
/**
 * @author Ričardas Kazakevičius
 */
// 7 užduotis. Class Divider divedes array with n numbers into intervals. 
// Finder class finds number with longest iteration in Collatz search

public class Main {
    static boolean doTest = true;
    static  int cpus;
    public static void main(String[] args) throws InterruptedException {
        int maxThreads = 32; // maximum number of threads in performance test
        int thread[] = {1,2,4,8,12,16};
        int n = 10000; // array size
        long array[] = new long[n];
        double finalTime = 0;
        MPI.Init(args);
        int cpu = MPI.COMM_WORLD.Size();
        int rank = MPI.COMM_WORLD.Rank();
       
        
        int intervalSize = n / cpu;
        long ar[] = new long[intervalSize];
        
        // creating array with numbers from 1 to n
        for (int i = 0; i < n; ++i) {
            array[i] = i + 1;
        }
        
        // storage is for seting number with most iterations
        Storage storage = new Storage();
                 
        Divider divider = new Divider();
        divider.initialize(array, intervalSize);

         // creating threads
        Finder[] finder = new Finder[cpu];
               
        long time0 = System.currentTimeMillis();
        
        if (rank == 0) {
            int tempNum = 0;
            for (int i = 1; i < cpu; ++i) {
                for (int j = 0; j < intervalSize; ++j) {
                    ar[j] = array[tempNum];
                    ++tempNum;
                }
                MPI.COMM_WORLD.Send(ar, 0, ar.length, MPI.LONG, i, 0);  
            }  
        }

        for (int i = 1; i < cpu; ++i) {  
            if (rank == i) {
                MPI.COMM_WORLD.Recv(ar, 0, ar.length, MPI.LONG, MPI.ANY_SOURCE, 0);
                for (int k = 0; k < ar.length; ++k) {   
                    finder[i] = new Finder(i, divider, storage);
                    finder[i].find(ar);
                }
            }
        }
        MPI.Finalize();
       // MPI.Finalize();
       // MPI.COMM_WORLD.Barrier();

        long time1 = System.currentTimeMillis();
        double time = (time1-time0)/1000.;
        System.out.println("Number with most iterations is: " + storage.getNumber());
        System.out.println("time " + time);

        
        /*
        boolean last = false;

        // running multithreading performace test
        while (intervalSize <= n) {
            
            double speedUp = 0; // how many times threads work faster
            double time = 0; // one thread word time
            System.out.println("Threads IntervalSize TimeSec SpeedUp");
            
            //for (int threads = 1; threads <= maxThreads; threads *= 2) {
            for (int threads: thread) {
                 // creating and initializing divider
                Divider divider = new Divider();
                divider.initialize(array, intervalSize);

                long time0 = System.currentTimeMillis();

                storage = new Storage();

                // creating threads
                Finder[] finder = new Finder[cpu];

                for (int i = 0; i < threads; ++i) {
                    finder[i] = new Finder(i, divider, storage);
                    finder[i].start();
                }

                for (int i = 0; i < threads; ++i) {
                    finder[i].join();
                }

                long time1 = System.currentTimeMillis();

                double dtime = (time1-time0)/1000.;

                if (threads == 1) {
                    speedUp = 1;
                    time = dtime;
                }
                else {
                    speedUp = time / dtime;
                }

                System.out.println(threads + "        " + intervalSize + "            "  +
                        dtime + "    " + speedUp);
            }
            System.out.println("");
            intervalSize *= 10;
            if (intervalSize > n && !last) {
                intervalSize = n;
                last = true;
            }
        }
        System.out.println("");
        System.out.println("Number with most iterations is: " + storage.getNumber());        
    }
*/
    }
    
}

               // MPI.COMM_WORLD.Ssend(ar, 0, 1, MPI.LONG, 0, 3);
                     //for (int i = 1; i < size; ++i) {
             //   MPI.COMM_WORLD.Recv(ar, 0, 1, MPI.LONG, MPI.ANY_SOURCE, 3);
           // }