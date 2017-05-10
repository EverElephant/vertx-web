package com.elp.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class ServerVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);
		//创建route并指定handler
		Route route = router.route();
		route.handler(this::hello);
		//上两行的简写并指定另一个handler world()
		router.route().handler(this::world);
		//rest匹配，执行可能阻塞的handler
		router.route("/query").blockingHandler(this::query);
		
		//以下为完整写法的route以及各种route
//		router.route("/query").handler(new Handler<RoutingContext>() {		
//			@Override
//			public void handle(RoutingContext routingContext) {
//				hello(routingContext);
//			}
//		});
//		
//		router.post().handler(this::hello);
//		router.post("/submit").handler(new Handler<RoutingContext>() {
//			
//			@Override
//			public void handle(RoutingContext event) {
//				// TODO Auto-generated method stub
//			}
//		});
		
		vertx.createHttpServer().requestHandler(router::accept).listen(8081);
	}

	public void hello(RoutingContext routingContext) {
		System.out.println("hello");
		vertx.eventBus().send("world", "hello", reply -> {
			if (reply.succeeded()) {
				System.out.println("Received and reply is: " + reply.result().body());			
			}
		});
		//执行下一个匹配的route
		routingContext.next();
	}
	public void world(RoutingContext routingContext) {
		routingContext.response().end("hello world");
	}
	public void query(RoutingContext routingContext) {
		 // Call some blocking API that takes a significant amount of time to return
		String result = "query sccessed";
		routingContext.response().end(result);
	}

}
