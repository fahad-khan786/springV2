package CoreJava;

import java.util.concurrent.CompletableFuture;

public class FutureTasks {
    public static void main(String[] args) {
        //CompletableFuture it runs in background or asynchronously without blocking main thread
        //it has multiple methods like supplyAsync (return some value) and thenApply(transform the result)
        // and thenAccept(consume the result no return)
      CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
          try {
              Thread.sleep(2000);
              System.out.println("task has completed");
          }catch (Exception e){
              e.printStackTrace();
          }
      });

      System.out.println("main thread is going to run"); //it will run without waiting to CompletableFuture thread

      future.join();//if background task is not done then it will wait till completion

      System.out.println("all task has done....");
    }
}
