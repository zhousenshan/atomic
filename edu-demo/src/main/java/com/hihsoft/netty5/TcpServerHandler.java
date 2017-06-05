package com.hihsoft.netty5;

import java.util.Map;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TcpServerHandler extends ChannelHandlerAdapter {
	private static final Logger logger = Logger.getLogger(TcpServerHandler.class.getName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		RequestParam temp = (RequestParam) msg;
		if (msg instanceof RequestParam) {
			RequestParam temp1 = (RequestParam) msg;
			System.out.println("服务端channelRead  收到具体事例:\n" + JSON.toJSONString(temp));
			ctx.writeAndFlush("服务端已经收到，并要返回");
		} else {

		}
		System.out.println("服务端channelRead:\n" + msg);
		logger.info("服务端channelRead:\n" + msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		NettyChannelMap.add(getIPString(ctx), ctx.channel());
		String retUrl = "服务端开始连接channelActive";
		Map<String, Channel> maps = NettyChannelMap.getMap();
		logger.info(retUrl + NettyChannelMap.getMap().keySet());
		logger.info(retUrl + NettyChannelMap.getMap().values());
	}

	

	public void setCliMsg(ChannelHandlerContext ctx) throws Exception {
		NettyChannelMap.get(getIPString(ctx));
		String retUrl = "服务端channelInactive";
		ctx.writeAndFlush(retUrl);
		logger.info(retUrl);
	}
	
	
	
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		NettyChannelMap.remove(getIPString(ctx));
		String retUrl = "服务端channelInactive";
		ctx.writeAndFlush(retUrl);
		logger.info(retUrl);
	}

	public static String getIPString(ChannelHandlerContext ctx) {
		String ipString = "";
		String socketString = ctx.channel().remoteAddress().toString();
		int colonAt = socketString.indexOf(":");
		ipString = socketString.substring(1, colonAt);
		return ipString;
	}

	public static String getRemoteAddress(ChannelHandlerContext ctx) {
		String socketString = "";
		socketString = ctx.channel().remoteAddress().toString();
		return socketString;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		System.out.println("完全结束" );
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// super.exceptionCaught(ctx, cause);
		ctx.close();
	}

}
