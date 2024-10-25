- Docker启动Nacos
```shell
git clone https://github.com/nacos-group/nacos-docker.git
cd nacos-docker
docker-compose -f example/standalone-derby.yaml up
```
- 登陆Nacos
http://127.0.0.1:8848/nacos
nacos/nacos

- Nacos添加配置logback.yml(配置格式YAML)
```yaml
logging:
  config: classpath:logback-nacos.xml
  console:
    enabled: true
```