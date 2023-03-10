package com.demo.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ProcessServiceImpl
 *
 * @author xiejinjie
 * @date 2022/12/22
 */
public class ProcessServiceImpl implements IProcessService {
    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    private static final ExecutorService threadPool = Executors.newCachedThreadPool();


    @Override
    public Process exec(String[] cmd) {
        String cmdStr = String.join(" ", cmd);
        logger.info("Execute a command: {}", cmdStr);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            return processBuilder.start();
        } catch (IOException e) {
            logger.error("Command execute failed! cmd = " + cmdStr, e);
            return null;
        }
    }

    @Override
    public boolean execSync(String[] cmd) {
        return execSync(cmd, null);
    }

    @Override
    public boolean execSync(String[] cmd, String directory) {
        String cmdStr = String.join(" ", cmd);
        logger.info("Execute a command: {}", cmdStr);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            if (directory != null && !"".equals(directory)) {
                // 指定命令执行所在目录
                processBuilder.directory(new File(directory));
            }
            Process pro = processBuilder.start();
            try (InputStream in = pro.getInputStream();
                 BufferedReader read = new BufferedReader(new InputStreamReader(in, "gbk"))) {
                String line = null;
                while ((line = read.readLine()) != null) {
                    logger.info(line);
                }
            }
            int code = pro.waitFor();
            logger.info("Command exit with code {}.", code);
            return code == 0;
        } catch (IOException e) {
            logger.error("Command execute failed! cmd = " + cmdStr, e);
        } catch (InterruptedException e) {
            logger.warn("Command interrupted! cmd = " + cmdStr, e);
        }
        return false;
    }

    @Override
    public Future<Boolean> execAsync(String[] cmd) {
        return threadPool.submit(() -> execSync(cmd, null));
    }

    @Override
    public String[] buildCmd(String cmd) {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (osName.contains("windows")) {
            return new String[]{"cmd", "/c", cmd};
        } else {
            return new String[]{"sh", "-c", cmd};
        }
    }
}
