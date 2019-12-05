import java.util.Random;
import java.util.concurrent.Semaphore;

class PidManager {

    private static final int minPid = 300;
    private static final int maxPid = 5000;
    private static int[] pids;

    public static int allocate_map() {
        pids = new int[maxPid - minPid];
        for (int i = 0; i < pids.length; i++) {
            pids[i] = 0;
        }
        if (pids != null) return 1;
        else return -1;
    }

    public static int allocate_pid() {
        int pidNumber = 0;
        for (int i = 0; i < pids.length; i++) {
            if (pids[i] == 0) {
                pids[i] = 1;
                pidNumber = i + minPid;
                break;
            }
        }
        if (pidNumber == 0) return -1;
        return pidNumber;
    }

    public static void release_pid(int pid) {
        if (pid < minPid || pid > maxPid)
            System.out.println("This Pid is not available for use");
        int pidIndex = pid - minPid;
        if (pids[pidIndex] == 0)
            System.out.println("This Pid already released and available to be assigned");
        pids[pidIndex] = 0;
        System.out.println("Pid: " + pid + " is released");
    }
}

class PidSleepThread extends Thread {

    private int threadNumber;
    private int pidNumber = PidManager.allocate_pid();

    private long x = 5000L;
    private long y = 20000L;
    Random r = new Random();
    private long sleepTime = x + ((long) (r.nextDouble() * (y - x)));

    public PidSleepThread(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        System.out.println("Allocating a Pid: " + pidNumber);
        try {
            Thread.sleep(sleepTime);
            System.out.println("Releasing Pid: " + pidNumber + " after " + sleepTime + " milliseconds ");
            PidManager.release_pid(pidNumber);
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }

}

public class mutexLock {

    public static Semaphore sem = new Semaphore(1);

    public static void acquire() {
        if (sem.availablePermits() == 1) {
            try {
                sem.acquire();
                System.out.println("Successfully acquired a lock for Pids.");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        } else System.out.println("There are 0 permits available");
    }

    public static void release() {
        sem.release();
        System.out.println("Successfully released lock for Pids.");
        System.out.println(sem.availablePermits() + " permit(s) are available.");
    }

    public static void main(String[] args) {

        PidManager pM = new PidManager();
        pM.allocate_map();

        mutexLock mutex = new mutexLock();

        for (int i = 0; i < 5; i++) {

            PidSleepThread thread = new PidSleepThread(i);
            mutex.acquire();
            thread.start();
            try {
                thread.join(10);
            } catch (InterruptedException e) {
                e.getMessage();
            }
            if(i==4) mutex.release();
        }
    }
}