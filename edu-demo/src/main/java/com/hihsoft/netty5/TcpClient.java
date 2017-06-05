package com.hihsoft.netty5;



import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class TcpClient {
	
	public void connect(int port,String host)throws Exception{
		
		//配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		
		try{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
						pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
						pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
						pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
						//添加POJO对象解码器 禁止缓存类加载器
						pipeline.addLast(new ObjectDecoder(1024,ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
						
						/*pipeline.addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver
								(this.getClass().getClassLoader()))); */
						//设置发送消息编码器
						pipeline.addLast(new ObjectEncoder());
						pipeline.addLast("handler", new TcpClientHandler());				
					};
				});
			
			//发起异步连接操作
			ChannelFuture f = b.connect(host,port).sync();
			//等待客户端链路关闭
			f.channel().closeFuture().sync();
		}finally{
			//退出，释放资源
			group.shutdownGracefully();
		}
		
	}
	
	public static void main(String[] args)throws Exception {
		int port = 8083;
		new TcpClient().connect(port, "127.0.0.1");		
	}
}
