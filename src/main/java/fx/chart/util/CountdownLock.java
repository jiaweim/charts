package fx.chart.util;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


public class CountdownLock<T> {
    private ScheduledExecutorService service;
    private final Duration timeout;
    private final AtomicBoolean running;
    private final Command cmd;
    private final T param;
    private final AtomicLong secondsToGo;

    public CountdownLock(final Command cmd, final T param, final Duration timeout) {
        if (null == cmd || null == timeout) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        this.service = Executors.newSingleThreadScheduledExecutor();
        this.cmd = cmd;
        this.param = param;
        this.timeout = timeout;
        this.running = new AtomicBoolean(false);
        this.secondsToGo = new AtomicLong(timeout.getSeconds());
    }


    public void start() {
        if (isRunning()) {
            return;
        }

        Runnable runnable = () -> {
            secondsToGo.decrementAndGet();
            if (secondsToGo.get() < 0) {
                cmd.execute(param);
                stop();
            }
        };
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
        running.set(true);
    }

    public void stop() {
        if (!isRunning()) {
            return;
        }

        service.shutdown();
        running.set(false);
        service = Executors.newScheduledThreadPool(1);
        secondsToGo.set(timeout.getSeconds());
    }

    public boolean isRunning() {return running.get();}

    public long getSecondsToGo() {return secondsToGo.get() + 1;}


    // ******************** Inner Classes *************************************
    @FunctionalInterface
    public interface Command<T> {
        void execute(T value);
    }
}
