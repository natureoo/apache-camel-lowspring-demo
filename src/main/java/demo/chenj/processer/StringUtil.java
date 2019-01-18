package demo.chenj.processer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static  void main(String[] args){
        List<String> list = new ArrayList<String>();
        list.add("http://localhost:8081/camel/post?bridgeEndpoint=true");
        list.add("http://localhost:8082/camel/post?bridgeEndpoint=true");
        String[] arrays = list.toArray(new String[0]);
        System.out.println(arrays);
    }

    public static String analysisMessage(InputStream bodyStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] contextBytes = new byte[4096];
        int realLen;
        while((realLen = bodyStream.read(contextBytes , 0 ,4096)) != -1) {
            outStream.write(contextBytes, 0, realLen);
        }

        // 返回从Stream中读取的字串
        try {
            return new String(outStream.toByteArray() , "UTF-8");
        } finally {
            outStream.close();
        }
    }

}
