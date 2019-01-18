package demo.chenj.processer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HttpProcessor implements Processor {
    private static Logger LOGGER = LoggerFactory.getLogger(HttpProcessor.class);

    /* (non-Javadoc)
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */

    @Override
    public void process(Exchange exchange) throws Exception {
        // 因为很明确消息格式是http的，所以才使用这个类
        // 否则还是建议使用org.apache.camel.Message这个抽象接口
//        HttpMessage message = (HttpMessage)exchange.getIn();
//        InputStream bodyStream =  (InputStream)message.getBody();
//        String inputContext = StringUtil.analysisMessage(bodyStream);
//        bodyStream.close();

        // 存入到exchange的out区域
        Message outMessage = exchange.getOut();
        Map<String, Object> headers = exchange.getIn().getHeaders();
        for(String key:headers.keySet()){
            LOGGER.info("request header [{}:{}]", key, headers.get(key));
        }
        LOGGER.info("request body [{}]", exchange.getIn().getBody());

//        outMessage.setBody("{\"title\": \"The title\", \"content\": \"The content\"}");
        outMessage.setHeaders(exchange.getIn().getHeaders());
        outMessage.setBody(exchange.getIn().getBody());
    }


}