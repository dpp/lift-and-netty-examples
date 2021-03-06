package com.something.lift

import org.jboss.netty.channel.group.DefaultChannelGroup
import org.jboss.netty.handler.codec.http.{HttpResponseEncoder, HttpRequestDecoder}
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory
import java.util.concurrent.Executors
import org.jboss.netty.bootstrap.ServerBootstrap
import java.net.InetSocketAddress
import org.jboss.netty.channel.{Channels, ChannelPipeline, ChannelPipelineFactory, ChannelFactory}

/**
 * Created by IntelliJ IDEA.
 * User: jordanrw
 * Date: 2/22/12
 * Time: 10:04 PM
 */

object NothingServer extends App {

  val allChannels = new DefaultChannelGroup("nothing-server")

  private[this] val factory: ChannelFactory = new NioServerSocketChannelFactory(
    Executors.newCachedThreadPool(),
    Executors.newCachedThreadPool()
  )

  private[this] val bootstrap = new ServerBootstrap(factory)

  bootstrap.setPipelineFactory(new ChannelPipelineFactory {
    def getPipeline: ChannelPipeline = Channels.pipeline(
      new HttpRequestDecoder,
      new HttpResponseEncoder,
      new NothingServerHandler
    )
  })

  bootstrap.setOption("child.tcpNoDelay", true)
  bootstrap.setOption("child.keepAlive", true)

  val boundChannel = bootstrap.bind(new InetSocketAddress(8080))
  println("TIME Server Started")

  allChannels.add(boundChannel)

}