package com.hihsoft.netty5;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;   

public class NettyChannelMap {  
    private static Map<String,Channel> map=new ConcurrentHashMap<String, Channel>();  
    public static void add(String ip,Channel channel){  
        map.put(ip,channel);  
    }  
    public static Channel get(String ip){  
       return map.get(ip);  
    } 
    
    public static void remove(String ip){   
                 map.remove(ip);  
         
     }
	public static Map<String, Channel> getMap() {
		return map;
	}
	public static void setMap(Map<String, Channel> map) {
		NettyChannelMap.map = map;
	}   
    
   
  
}  
