package club.jming;

import club.jming.exec.Client;
import java.util.concurrent.BrokenBarrierException;

public class Main {


    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        Client.exec(16);
    }
}
