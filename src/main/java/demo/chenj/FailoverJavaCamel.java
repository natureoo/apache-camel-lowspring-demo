package demo.chenj;

import demo.chenj.processer.ExceptionProcessor;
import demo.chenj.processer.HttpProcessor;
import demo.chenj.processer.OtherProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FailoverJavaCamel extends RouteBuilder {

    private static Logger LOGGER = LoggerFactory.getLogger(FailoverJavaCamel.class);

    private CamelContext context;

    private static List<String> sendUriList;

    public FailoverJavaCamel(CamelContext context){
        this.context = context;
    }

    public static void main(String[] args) throws Exception {

//        InputStream inStream = FailoverJavaCamel.class.getClassLoader().getResourceAsStream("demp.properties");
//        Properties prop = new Properties();
//        prop.load(inStream);
//        String sendUris = prop.getProperty("send.uris");
        String sendUris = System.getProperty("sendUris");
        LOGGER.info("send.uris:"+sendUris);
        sendUriList = new ArrayList<>();
        for(String uri:sendUris.split(",")) {
            if(!uri.contains("?"))
                uri += "?bridgeEndpoint=true";
            sendUriList.add(uri);
        }

        // 这是camel上下文对象，整个路由的驱动全靠它了。
        CamelContext camelContext = new DefaultCamelContext();
        // 启动route
        camelContext.start();
        // 将我们编排的一个完整消息路由过程，加入到上下文中
        camelContext.addRoutes(new FailoverJavaCamel(camelContext));

        // 通用没有具体业务意义的代码，只是为了保证主线程不退出
        synchronized (FailoverJavaCamel.class) {
            FailoverJavaCamel.class.wait();
        }
    }

    @Override
    public void configure() throws Exception {
//        List<String> list = new ArrayList<String>();
//        list.add("http4://localhost:8081/camel/post?bridgeEndpoint=true");
//        list.add("http4://localhost:8082/camel/post?bridgeEndpoint=true");
//        errorHandler(defaultErrorHandler().maximumRedeliveries(3));
//        errorHandler(deadLetterChannel("log:deadLetterChannel?showExchangeId=true") .maximumRedeliveries(2).redeliveryDelay(1000).onExceptionOccurred(new ExceptionProcessor()));
//
//        onException(IOException.class)
//                .continued(true);

//        errorHandler(defaultErrorHandler().maximumRedeliveries(1));
//        errorHandler(deadLetterChannel("log:deadLetterChannel?showExchangeId=true").maximumRedeliveries(4).onExceptionOccurred(new ExceptionProcessor()));
        onException(IOException.class)
                .maximumRedeliveries(1)
                .onRedelivery(new ExceptionProcessor());
        from("stream:in?promptMessage=Enter post json body : ")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json;charset=utf-8"))
                .process(new HttpProcessor())

                //failover(int maximumFailoverAttempts, boolean inheritErrorHandler, boolean roundRobin, boolean sticky, Class... exceptions
                .loadBalance().failover()

//                .to("http://localhost:8081/camel/post?bridgeEndpoint=true").
//                to("http://localhost:8082/camel/post?bridgeEndpoint=true")
                .to((String[])sendUriList.toArray(new String[0]))
                .end()
                 .process(new OtherProcessor());
    }










}