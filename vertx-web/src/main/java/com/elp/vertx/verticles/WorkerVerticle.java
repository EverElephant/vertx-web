package com.elp.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class WorkerVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {
		
		EventBus eb = vertx.eventBus();
		eb.consumer("world", rm -> {
			System.out.println("receive:"+rm.body());
			vertx.executeBlocking(future -> {
				
			  // Call some blocking API that takes a significant amount of time to return
			  String result = "haha";
			  
			  future.complete(result);
			}, res -> {
				rm.reply(res.result());
			});
		});
	}
}
