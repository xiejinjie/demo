package com.demo.runtime;

import java.util.Locale;

public class RuntimeDemo {

    public static void main(String[] args) {
        IProcessService processService = new ProcessServiceImpl();

        String[] cmd = null;
        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (osName.contains("windows")) {
            cmd = processService.buildCmd("rundll32 url.dll,FileProtocolHandler https://www.baidu.com");
        } else if (osName.contains("mac")) {
            cmd = new String[]{"open", "https://www.baidu.com"};
        } else {
            return;
        }

        processService.exec(cmd);
    }
}
