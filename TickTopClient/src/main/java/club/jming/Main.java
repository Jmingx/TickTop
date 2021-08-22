package club.jming;

import club.jming.exec.Client;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        Client.exec(16);
    }
}
