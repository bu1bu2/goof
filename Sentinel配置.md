# redis sentinel配置

------

&nbsp;&nbsp;Redis-Sentinel是官方推荐的高可用解决方案，当redis在做master-slave的高可用方案时，假如master宕机了，redis本身（以及其很多客户端）都没有实现自动进行主备切换，而redis-sentinel本身也是独立运行的进程，可以部署在其他与redis集群可通讯的机器中监控redis集群。**哨兵的作用**:

> * 1、监控redis进行状态，包括master和slave 
> * 2、当master down机，能自动将slave切换成master

![哨兵机制][1]
------



## 1.准备工作
**1.1 redis.conf配置**
解压redis,复制三分redis.conf到三个不同的目录redis-6379,redis-6380,redis-6381,分别修改redis.conf中的如下配置:

- [x] port 6379
- [x] daemonize yes
- [x] pidfile "/var/run/redis_6379.pid"
- [x] slaveof masterip masterport

其中slaveof写在配置中则为永久生效,亦可以使用命令 `slaveof 127.0.0.1 6379`,只不过重启后则失效.
以上文件对应的端口号需要分别更改,为master的主机则不需要修改salveof 配置项.


----------


**1.2 sentinel.conf配置**
由于上面三个redis实例采取的一主二从的模式,且为了简单,我只配置了一个监控master的sentinel.其实哨兵之间也是可以集群的,如下图:
![哨兵集群][2]

配置如下:

> - [x] sentinel monitor mymaster 127.0.0.1 6379 1


其中mymaster表示要监控的主库的名字，可以自己定义,后两个参数表示主库的IP地址和端口号。最后的1表示最低通过票数.<br/>
配置哨兵监控一个系统时，只需要配置其监控主库即可，哨兵会自动发现所有复制该主库的从库.


启动哨兵:

    ./redis-sentinel ../sentinel.conf
查看角色信息:

    127.0.0.1:6380>info replication
至此结束,关于哨兵的详情见参考链接

[参考链接][1]


  [1]: http://blog.csdn.net/gqtcgq/article/details/50273431
