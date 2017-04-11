package com.github.vedeshkin;


import java.util.Timer;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {

        DOA doa = new DOA();
        doa.showStatus();
        Workflow main   = new Workflow();
        main.initialize();
        Timer timer = new Timer();
        timer.schedule(main,1000,5000);


    }
}
