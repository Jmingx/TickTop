package club.jming.ticktopserver.controller;

import club.jming.ticktopserver.entity.Msg;
import club.jming.ticktopserver.utils.CPUUtil;
import club.jming.ticktopserver.utils.MemoryUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ServerMsgController {

    @GetMapping("/tickTop/serverMsg")
    public Msg getServerMsg() throws IOException, InterruptedException {
        Msg msg = new Msg();
        msg.setCPUMsg(CPUUtil.getCPUUsage());
        msg.setMemoryMsg(MemoryUtil.getMemoryUsage());
        return msg;
    }
}
