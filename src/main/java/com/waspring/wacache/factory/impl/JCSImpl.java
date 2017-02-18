package com.waspring.wacache.factory.impl;

import org.apache.jcs.JCS;
import org.apache.jcs.engine.ElementAttributes;

import com.waspring.wacache.exp.CacheException;
import com.waspring.wacache.factory.manager.JCSManager;

/**
 * <p>
 * <span>JCS是Jakarta的项目Turbine的子项目。它是一个复合式的缓冲工具。可以将对象缓冲到内存、硬盘。具有缓冲对象时间过期设定。
 * 还可以通过JCS构建具有缓冲的分布式构架
 * ，以实现高性能的应用。对于一些需要频繁访问而每访问一次都非常消耗资源的对象，可以临时存放在缓冲区中，这样可以提高服务的性能
 * 。而JCS正是一个很好的缓冲工具。缓冲工具对于读操作远远多于写操作的应用性能提高非常显著。</span>
 * </p>
 * <p>
 * <span>JCS除了简单的将对象缓冲在内存中以外，还具有几个特性，以适应企业级缓冲系统的需要。这些特性包括时间过期、索引式硬盘缓冲、并行式的分布缓冲等。
 * </span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>内存缓冲</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>JCS现在支持两种内存缓冲算法LRU和MRU。通常都是使用LRU算法。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>org.apache.stratum.jcs.engine.memory.lru.LRUMemoryCache</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>使用内存缓冲区需要定义缓冲区大小，当超过缓冲区限制时，会将缓冲内容抛弃掉。如果有配硬盘缓冲，则将挤出来的缓冲内容写入硬盘缓冲区。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>时间过期</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>JCS对于缓冲的对象，可以设定缓冲过期时间，一个对象在缓冲区中停留的时间超过这个时间，就会被认为是“不新鲜”而被放弃。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>索引式硬盘缓冲</span>
 * </p>
 * <p>
 * <span>　　一方面，为了避免缓冲区过大，撑爆虚拟机的内存，另一方面又希望能够缓冲更多的对象，JCS可以将超出缓冲区大小的对象缓存到硬盘上。
 * 配置上也比较方便
 * ，只需要指定缓冲临时文件的存放目录位置。硬盘缓冲将缓冲对象的内容写到文件上，但是将访问索引保存在内存中，因此也能够达到尽可能高的访问效率。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>并行式的分布缓冲(Lateral)</span>
 * </p>
 * <p>
 * <span>　　通常，将对象缓冲在内存中，一方面提高了应用的性能，而另一方面却使得应用不可以分布式发布。因为假设一个应用配置在两台服务器上并行运行，
 * 而两台服务器单独缓冲
 * ，则很容易导致两个缓冲区内容出现版本上的不一致而出错。一个机器上修改了数据，这个动作会影响到本地内存缓冲区和数据库服务器，但是却不会通知到另一台服务器
 * ，导致另一台上缓冲的数据实际上已经无效了。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>　　并行式的分布缓冲就是解决这个问题。可以通过配置，将几台服务器配成一个缓冲组，组内每台服务器上有数据更新，会横向将更新的内容通过TCP/
 * IP协议传输到其他服务器的缓冲层
 * ，这样就可以保证不会出现上述情况。这个的缺点是如果组内的并行的服务器数量增大后，组内的数据传输量将会迅速上升。这种方案适合并行服务器的数量比较少的情况
 * 。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>Client/Server式的缓冲(Remote)</span>
 * </p>
 * <p>
 * <span>　　客户/服务端式的缓冲集群。这种方式支持一个主服务器和最高达到256个客户端。客户端的缓冲层会尝试连接主服务器，如果连接成功，
 * 就会在主服务器上注册。每个客户端有数据更新，就会通知到主服务器，主服务器会将更新通知到除消息来源的客户端以外的所有的客户端。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>　　每个客户端可以配置超过一个服务器，第一个服务器是主服务器，如果与第一个服务器连接失败，客户端会尝试与备用的服务器连接，如果连接成功，
 * 就会通过备用服务器与其他客户端对话
 * ，同时会定期继续尝试与主服务器取得连接。如果备用服务器也连接失败，就会按照配置顺序尝试与下一个备用服务器连接。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>　　这种方式下，更新通知是一种轻量级的，一个机器上的数据更新，不会把整个数据传输出去，而只是通知一个ID，当远程的其他机器收到更新通知后，
 * 就会把对应ID的缓冲对象从本地的内存缓冲区中移除，以保证不会在缓冲区内出现错误数据。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>　　这种构造需要分别配置客户端和服务器，配置比较麻烦。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>&nbsp;</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>配置方法</span>
 * </p>
 * <p>
 * <span>　　JCS的好处之一，就是应用在开发的时候，可以不用去构思底层的缓冲配置构架。同一个应用，只需要修改配置，就可以改变缓冲构架，
 * 不需要修改应用的源代码
 * 。配置方法也比较简单，就是修改配置文件cache.ccf。这个文件放置在WEB-INF/classes目录下。配置格式类似log4j的配置文件格式
 * 。下面介绍一下使用各种缓冲结构的配置方法。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>内存缓冲</span>
 * </p>
 * <p>
 * <span>[plain] view plain copy 在CODE上查看代码片派生到我的代码片</span>
 * </p>
 * <p>
 * <span>#WEB-INF/classes/cache.ccf(以下内容不要换行) &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default= &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.cacheattributes=org.apache.jcs.engine.
 * CompositeCacheAttributes &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.cacheattributes.MaxObjects=1000 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.cacheattributes.MemoryCacheName=org.apache.jcs.engine.
 * memory.lru.LRUMemoryCache &nbsp;</span>
 * </p>
 * <p>
 * <span>　　上面配置了默认缓冲属性。一个应用中，由于对象类型的不同，可能会使用多个缓冲区，每个缓冲区都会有一个名字，
 * 如果在配置文件中没有指明特定的缓冲区的属性
 * ，所有的缓冲区都会根据默认属性来构建。上面的内容，指明缓冲区的大小为存放1000个对象，内存缓冲器使用LRUMemoryCache对象
 * 。可选的还有MRUMemoryCache
 * ，应该可以自定义新的内存缓冲区。1000个缓冲对象这个容量，是指每个缓冲区都缓冲1000个，而不是指所有缓冲区总容量
 * 。以上配置，就可以让应用运行起来。</span>
 * </p>
 * <p>
 * <span>时间过期</span>
 * </p>
 * <p>
 * <span>　　如果需要引入时间过期机制，则需要加上</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>　　jcs.default.cacheattributes.cacheattributes.UseMemoryShrinker=true</
 * span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>　　jcs.default.cacheattributes.cacheattributes.MaxMemoryIdleTimeSeconds=
 * 3600</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>　　jcs.default.cacheattributes.cacheattributes.ShrinkerIntervalSeconds=
 * 60</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>　　这里指明对象超过3600秒则过期，每隔60秒检查一次。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>索引式硬盘缓冲</span>
 * </p>
 * <p>
 * <span>　　索引式硬盘缓冲是辅助缓冲的一种，使用时需要做以下事情</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>[plain] view plain copy 在CODE上查看代码片派生到我的代码片</span>
 * </p>
 * <p>
 * <span>#定义一个硬盘缓冲区产生器(Factory)，取名为DC &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.DC=org.apache.stratum.jcs.auxiliary.disk.indexed.
 * IndexedDiskCacheFactory &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.DC.attributes=org.apache.stratum.jcs.auxiliary.disk.
 * indexed.IndexedDiskCacheAttributes &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.DC.attributes.DiskPath=g:/dev/jakarta-turbine-stratum/raf
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>#这里其实就是指明了缓冲文件存放到那里去。 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>#然后，做以下修改 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default=DC &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>#这样，所有未特别指定属性的缓冲区都会自己使用一个硬盘缓冲区，缓冲文件会以缓冲区的名字来命名。存放在指定的目录下。 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>#横向式的并行缓冲 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>#并行式的配置如下 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.LTCP=org.apache.jcs.auxiliary.lateral.LateralCacheFactory
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.LTCP.attributes=org.apache.jcs.auxiliary.lateral.
 * LateralCacheAttributes &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.LTCP.attributes.TransmissionTypeName=TCP &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.LTCP.attributes.TcpServers=192.168.10.129:1121,192.168.10
 * .222:1121 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.LTCP.attributes.TcpListenerPort=1121 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.LTCP.attributes.PutOnlyMode=false &nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>　　这里的配置是在41,129,221三台机器上实现并行缓冲的。</span>
 * </p>
 * <p>
 * <span>　　大家都在1121端口上监听，同时与另外两台机器连接。如果连接失败，就会等待一个时间后再连接一次，直到连接成功为止。
 * 三台机器中任意一台的缓冲区发生更新，比如put和remove动作，就会把更新传递给另外两台。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>单独指明某个缓冲区的属性</span>
 * </p>
 * <p>
 * <span>　　如果，针对某个缓冲区，比如叫做TestCache1，需要单独配置属性，可以如下配置。</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>[plain] view plain copy 在CODE上查看代码片派生到我的代码片</span>
 * </p>
 * <p>
 * <span>jcs.region.testCache1=DC,LTCP &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.region.testCache1.cacheattributes=org.apache.stratum.jcs.engine.
 * CompositeCacheAttributes &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.region.testCache1.cacheattributes.MaxObjects=1000 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.region.testCache1.cacheattributes.MemoryCacheName=org.apache.
 * stratum.jcs.engine.memory.lru.LRUMemoryCache &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.region.testCache1.cacheattributes.UseMemoryShrinker=true
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.region.testCache1.cacheattributes.MaxMemoryIdleTimeSeconds=3600
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.region.testCache1.cacheattributes.ShrinkerIntervalSeconds=60
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>system.GroupIdCache &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>#这个概念我也不是很清楚。不过JCS文档中指出配置以下内容会比较好。 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.system.groupIdCache=DC &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.system.groupIdCache.cacheattributes=org.apache.stratum.jcs.engine.
 * CompositeCacheAttributes &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.system.groupIdCache.cacheattributes.MaxObjects=10000 &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.system.groupIdCache.cacheattributes.MemoryCacheName=org.apache.
 * stratum.jcs.engine.memory.lru.LRUMemoryCache &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>#这可能是JCS自己的组管理体系上的缓冲区。 &nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>Client/Server式的缓冲(Remote)</span>
 * </p>
 * <p>
 * <span>　　这种构架需要单独配置客户端和服务端，如果要研究，可以查看
 * http://jakarta.apache.org/turbine/jcs/RemoteAuxCache.html</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>&nbsp;</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>&nbsp;</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>这里给出一个之前系统配置的例子：cache.cff配置</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>[plain] view plain copy 在CODE上查看代码片派生到我的代码片</span>
 * </p>
 * <p>
 * <span># DEFAULT CACHE REGION &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span># sets the default aux value for any non configured caches
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default=DC &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.cacheattributes=org.apache.jcs.engine.
 * CompositeCacheAttributes &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.cacheattributes.MaxObjects=1 &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.cacheattributes.MemoryCacheName=org.apache.jcs.engine.
 * memory.lru.LRUMemoryCache &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.elementattributes.IsEternal=true &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.elementattributes.MaxLifeSeconds=360000 &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.elementattributes.IdleTime=1800 &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.elementattributes.IsSpool=true &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.elementattributes.IsRemote=true &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.default.elementattributes.IsLateral=true &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span># CACHE REGIONS AVAILABLE &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span># AUXILIARY CACHES AVAILABLE &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span># Primary Disk Cache -- faster than the rest because of memory key
 * storage &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.DC=org.apache.jcs.auxiliary.disk.indexed.
 * IndexedDiskCacheFactory &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.DC.attributes=org.apache.jcs.auxiliary.disk.indexed.
 * IndexedDiskCacheAttributes &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.DC.attributes.DiskPath=./kpicache &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.DC.attributes.MaxPurgatorySize=100000000 &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.DC.attributes.MaxKeySize=10000000 &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.DC.attributes.OptimizeAtRemoveCount=300000 &nbsp;</span>
 * </p>
 * <p>
 * <span>jcs.auxiliary.DC.attributes.MaxRecycleBinSize=7500 &nbsp;</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>一些方法的操作：</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>[java] view plain copy 在CODE上查看代码片派生到我的代码片</span>
 * </p>
 * <p>
 * <span>package com.zyujie.util; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>import org.apache.jcs.JCS; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>public class JCSManagerDTO { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; private static JCSManagerDTO instance; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; private static int checkedOut = 0; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; public static JCS ObjCache; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; private JCSManagerDTO() { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; try { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ObjCache =
 * JCS.getInstance(&quot;ObjCache&quot;); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; } catch (Exception e) { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; e.printStackTrace();
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; public static JCSManagerDTO getInstance() { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; synchronized (JCSManagerDTO.class) {
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; if (instance == null) {
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; instance = new
 * JCSManagerDTO(); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; synchronized (instance) { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; instance.checkedOut++;
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; return instance; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; public Object getObj(Object key) { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; Object obj = null; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; obj = (Object) ObjCache.get(key);
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; return obj; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; public void storeObject(Object key, Object obj) {
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; try { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; if
 * (!key.equals(&quot;&quot;)) { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; //
 * ObjCache.remove(key); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ObjCache.put(key, obj);
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; } catch (Exception e) { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; public void clearObject(Object key) { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; try { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ObjCache.remove(key);
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; } catch (Exception e) { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; public void clear() { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; try { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ObjCache.clear();
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; } catch (Exception e) { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; public void clearMatchObject(String key) { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; // CacheAccess
 * access1=CacheAccess.getAccess(&quot;DC&quot;); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; // java.util.Map
 * map=access1.get.getMatchingCacheElements(key); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; public static void main(String[] args) { &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; JCSManagerDTO dto =
 * JCSManagerDTO.getInstance(); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; dto.storeObject(&quot;test1&quot;,
 * &quot;111&quot;); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; dto.storeObject(&quot;test2&quot;,
 * &quot;222&quot;); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; dto.storeObject(&quot;test3&quot;,
 * &quot;333&quot;); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; dto.storeObject(&quot;test4&quot;,
 * &quot;444&quot;); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; System.out.println(&quot;test1 is
 * &nbsp;&quot; + dto.getObj(&quot;test1&quot;)); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; System.out.println(&quot;test2 is
 * &nbsp;&quot; + dto.getObj(&quot;test2&quot;)); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; System.out.println(&quot;test3 is
 * &nbsp;&quot; + dto.getObj(&quot;test3&quot;)); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; System.out.println(&quot;test4 is
 * &nbsp;&quot; + dto.getObj(&quot;test4&quot;)); &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; } &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>} &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;</span>
 * </p>
 * <p>
 * <span>依赖jar包</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>jcs-1.3.jar</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>commons-lang-2.3.jar</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>commons-collections-2.1.1.jar</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>concurrent-1.3.4.jar</span>
 * </p>
 * <p>
 * <br/>
 * </p>
 * 
 * @author felly
 * 
 */
public class JCSImpl extends CacheImpl {
	private JCS gca = null;

	public JCSImpl() {
		gca = JCSManager.getInstance().getBaseCache();

	}

	@Override
	public Object getInner(Object key) throws CacheException {
		try {
			return gca.get(key);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public Object setInner(Object key, Object value) throws CacheException {
		try {

			gca.put(key, value);
			return value;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public Object setInner(Object key, Object value, int expire)
			throws CacheException {
		try {
			ElementAttributes ea = new ElementAttributes();
		 ea.setIsEternal(false);
			ea.setMaxLifeSeconds(expire);
			gca.put(key, value, ea);
			return value;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void delInner(Object key) throws CacheException {

		try {
			gca.remove(key);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

}
