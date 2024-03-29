线程池会根据corePoolSize和maximumPoolSize动态调整线程池的大小。当提交一个新的任务时，会根据当前工作的线程的数量和核任务队列的数量进行不同处理：

1. 线程数<核心线程数；则创建新的线程处理任务。注意，线程没有任务执行也会创建新线程。
2. 核心线程数<=工作线程数<最大线程数，且任务队列未满；则将任务添加至任务队列。
3. 核心线程数<=工作线程数<最大线程数，且任务队列已满；则创建新的线程处理任务。
4. 工作线程数=最大线程数,且任务队列已满；拒绝任务。

将核心线程数和最大线程数设置为相同的值，就得到了固定容量的线程池，等同于Executors.newFixedThreadPool(int)。将最大线程数设置为Integer.MAX_VALUE，线程池可以容纳任意数量的并发任务。
核心线程默认只会在新任务提交时初始化，想要提前初始化，可以使用prestartCoreThread()或prestartAllCoreThreads()启动一个或多个核心线程。

这是一个的示例程序，创建了一个核心线程为1，最大线程为2，任务队列为1的线程池，依次提交了4个任务，每个任务直接休眠10s。