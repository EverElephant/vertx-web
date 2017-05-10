package com.elp;


import com.elp.vertx.verticles.ServerVerticle;
import com.elp.vertx.verticles.WorkerVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * 三种防止阻塞的方法：
 * 1.会阻塞进程的内容用vertx.executeBlocking包裹会单独起worker线程启动
 * 2.把阻塞进程的内容单独写一个Verticle，通过eventbus进行信息传递（主进程在发送）
 * 3.route指定blockingHandler：例子“router.route("/query").blockingHandler(this::query)”
 * 
 * 一个Vertx实例可以创建多个httpSever，一个server只能设置一个handler，并且只能监听一个端口，同时一个端口只能被一个server监听
 * 
 * 启动的时候deployVerticle执行后，各个Verticle执行start方法（router创建并设置handler,eventbus创建并注册地址等等，详见verticle代码）
 * 
 * @author hys
 *
 */
public class VertxApplication {

	public static void main(String[] args) {
		VertxOptions vo = new VertxOptions();
		
		  vo.setEventLoopPoolSize(16);
		  Vertx vertx = Vertx.vertx(vo);
		 

		  DeploymentOptions options = new DeploymentOptions();
		  options.setInstances(100);
		
		vertx.deployVerticle(ServerVerticle.class.getName(),options);
		vertx.deployVerticle(WorkerVerticle.class.getName(),options);
		

	}

}
