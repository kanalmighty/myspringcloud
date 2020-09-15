package com.zk.cloudalibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.*;
import sun.awt.SunHints;

@RestController
public class FlowLimitController {

    @RequestMapping(value = "/testa", method = RequestMethod.GET)
    public String testA(){
        return "testA";
    }

    @RequestMapping(value = "/testb", method = RequestMethod.GET)
    public String testB(){
        return "testb";
    }

    @RequestMapping(value = "/testkeyA", method = RequestMethod.GET)
    @SentinelResource(value = "testkeyA", blockHandler = "testKeyAHandler")
    public String testKeyA(@RequestParam(value = "p1",required = false) String p1,
                           @RequestParam(value = "p2",required = false) String p2){
        return "testKeyA";
    }

    public String testKeyAHandler(String p1, String p2, BlockException exception){
        return "testKeyAHandler";
    }

//
//    @RequestMapping(value = "/testkeyB/{id}", method = RequestMethod.GET)
//    public String testKeyB(@PathVariable("id") Integer id){
//        return "testKeyB";
//    }


}
