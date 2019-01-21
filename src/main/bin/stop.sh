ps aux|grep java|grep apache-camel-lowspring-demo|grep -v grep|awk '{print $2}'|xargs kill -9
