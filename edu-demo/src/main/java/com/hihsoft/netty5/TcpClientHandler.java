package com.hihsoft.netty5;



import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author lh
 *
 */
public class TcpClientHandler extends ChannelHandlerAdapter {

	private static final Logger logger = Logger.getLogger(TcpClientHandler.class.getName());
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	String retUrl = "客户端channelActive发送数据";
		
		RequestParam temp=new RequestParam();
		temp.setType("1");
		temp.setData("中国人");
		//ctx.writeAndFlush(temp);
		ctx.writeAndFlush(retUrl);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body = (String)msg;
		System.out.println("客户端channelRead读取服务端传递过来数据 :\n"+body);
		logger.info("客户端读channelRead取服务端传递过来数据 :\n"+body);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.warning("unexpected exception from downstream:"+ cause.getMessage());
		ctx.close();
	}
	
}
