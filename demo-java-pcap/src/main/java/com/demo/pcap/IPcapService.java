package com.demo.pcap;

/**
 * IPcapService
 *
 * @author xiejinjie
 * @date 2023/2/10
 */
public interface IPcapService {

    void startPcap(String nifHost, String pcapFilter);

    void stopPcap();
}
