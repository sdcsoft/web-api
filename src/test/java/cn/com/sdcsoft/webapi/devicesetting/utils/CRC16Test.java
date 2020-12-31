package cn.com.sdcsoft.webapi.devicesetting.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class CRC16Test {

    @Test
    public void crc16(){
        int[] datas = {
                0x01,
                0x03,
                0x05,
                0x80,
                0x00,
                0x0C
        };
        int crc = CRC16.crc16(datas);
        String cmd = String.format("%04X#",crc);
        System.out.println(cmd);
    }
}