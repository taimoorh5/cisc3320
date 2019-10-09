//Taimoor Hafeez
//CISC 3320 Operating Systems
//Assignment 2

import java.util.Random;

public class PidSleepThread extends Thread {                      //creating a class that extends the Thread class

  private int threadNumber;                                       //this variable keeps track of the number of threads
  private int pidNumber = PidManager.allocate_pid();              //this variable creates a pid number using the method from previous assignment. Pid number changes as you use the variable

  private long x = 5000L;                                         //this variable is set to a minimum time the thread can sleep which is 5000 milliseconds
  private long y = 20000L;                                        //this variable is set to a maximum time the thread can sleep which is 5000 milliseconds
  Random r = new Random();                                        //creates a random variable to use in the next step to get a random sleep time
  private long sleepTime = x+((long)(r.nextDouble()*(y-x)));      //generates a random long from 5000 to 20000 milliseconds which represents the time a thread can sleep
 
  private PidSleepThread(int threadNumber) {                      //I created a constructor so I can keep track of the the thread number
    this.threadNumber = threadNumber;
  }

  @Override
  public void run() {                                           //this method will carry out the action of the thread when the thread starts
    System.out.println("Thread number: " + threadNumber);       //prints out the thread number that is in use
    System.out.println("Allocating a Pid: " + pidNumber);       //allocates a pid to the thread
    try {
        Thread.sleep(sleepTime);                                //thread sleeps for a random amount of time anywhere from 5000 milliseconds to 20000 milliseconds
        System.out.println("Releasing Pid: " + pidNumber + " after " + sleepTime + " milliseconds ");   //lets you know which pid is about to released as well as how long the thread has slept
        PidManager.release_pid(pidNumber);                      //pid number is released
    }
    catch (InterruptedException e) {                            //throws an exception if the thread is interrupted
      e.getMessage();
    }
  }
  
  public static void main(String [] args) {

    PidManager.allocate_map();                    //method from last assignment; creates an array of size 4700 and assigns a value of 0 to every index meaning every pid is ready to assigned
    
    for(int i = 0; i < 25; i++) {                 //creates 25 different threads that will perform the actions in run()
      PidSleepThread thread = new PidSleepThread(i);  //creates a thread object using constructor above
      thread.start();                               //starts all threads; Each thread is assigned a pid number, sleeps for a random amount of time from 5000 to 20000 milliseconds and then releases pid after sleep time
      try {
        thread.join(10);                      //thread is stopped for 10 milliseconds
      }
      catch (InterruptedException e) {              //throws exception if the thread is interrupted
        e.getMessage();
      }
    }
  }
}