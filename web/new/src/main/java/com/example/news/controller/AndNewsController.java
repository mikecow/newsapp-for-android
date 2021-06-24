package com.example.news.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.news.util.Config;
import com.example.news.util.HttpSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Dorado
 * @since 2021-05-14
 */
@RestController
@RequestMapping("/news")
public class AndNewsController {

    // 发送news的http请求
    public String SendNewsRequest(String channel, String start, String num) throws IOException {
        String url = Config.NEWSAPI;
        String param = "channel=" + channel + "&start=" + start + "&num=" + num + "&appkey=" + Config.APPKEY;
        return HttpSender.sendGet(url, param);
    }

    // 发送channels的http请求
    public String SendChannelRequest() throws IOException {
        String url = Config.CHANNELAPI;
        String param = "appkey=" + Config.APPKEY;
        return HttpSender.sendGet(url, param);
    }


    /**
     * 得到新闻list
     * @param channel   新闻种类
     * @param start     起始
     * @param num       数量
     * @return          map(json)
     * @throws IOException  http请求失败
     */
    @RequestMapping("/getnews/{channel}/{start}/{num}")
    public Map<String, Object> getNews(@PathVariable("channel") String channel,
                                       @PathVariable("start") String start,
                                       @PathVariable("num") String num) throws IOException {
        String newsJson =  SendNewsRequest(channel, start, num);
        Map<String, Object> newsMap = JSONObject.parseObject(newsJson);

        if(newsMap.get("msg").equals("ok")){
            return newsMap;
        }
        return null;
    }


    /**
     * 得到标签map
     * @return  Map(json)
     * @throws IOException  http请求发出错误
     */
    @RequestMapping("/getchannel")
    public Map<String, Object> getChannel() throws IOException {
        String channelJson = SendChannelRequest();
        Map<String, Object> channelMap = JSONObject.parseObject(channelJson);

        if(channelMap.get("msg").equals("ok")){
            return channelMap;
        }
        return null;
    }



}

