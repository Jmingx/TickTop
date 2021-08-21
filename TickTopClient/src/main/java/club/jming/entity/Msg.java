package club.jming.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Msg implements Serializable {
    private float CPUMsg;
    private float memoryMsg;
}
