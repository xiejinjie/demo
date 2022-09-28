# cors debug code
demo: 使用SpringWeb的RestTemplate，发送CORS跨域请求。

需要设置系统变量才能将origin添加到请求头部
System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
