package com.waspring.wacache.factory.impl;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.opensymphony.oscache.web.filter.ExpiresRefreshPolicy;
import com.waspring.wacache.exp.CacheException;
import com.waspring.wacache.factory.manager.OSCacheManager;

/**
 * <p>
 * OSCache由OpenSymphony设计，它是一种开创性的JSP定制标记应用，提供了在现有JSP页面之内实现快速内存缓冲的功能
 * </p>
 * <p>
 * 缓存任何对象，你可以不受限制的缓存部分jsp页面或HTTP请求，任何java对象都可以缓存。
 * </p>
 * <p>
 * 拥有全面的API--OSCache API给你全面的程序来控制所有的OSCache特性。
 * </p>
 * <p>
 * 永久缓存--缓存能随意的写入硬盘，因此允许昂贵的创建（expensive-to-create）数据来保持缓存，甚至能让应用重启。
 * </p>
 * <p>
 * 支持集群--集群缓存数据能被单个的进行参数配置，不需要修改代码。
 * </p>
 * <p>
 * 缓存记录的过期--你可以有最大限度的控制缓存对象的过期，包括可插入式的刷新策略（如果默认性能不需要时）
 * </p>
 * <p>
 * OSCache的使用：
 * </p>
 * <p>
 * 一，环境的搭建：
 * </p>
 * <p>
 * &nbsp;1,把oscache.jar file放在 /WEB-INF/lib 目录下(Put the oscache.jar file in the
 * /WEB-INF/lib directory)
 * </p>
 * <p>
 * 2,如果commons-logging.jar文件不存在，也要把它放进/WEB-INF/lib 目录下
 * </p>
 * <p>
 * 3,把/etc/oscache.properties放进类路径下
 * </p>
 * <p>
 * 注意：Remember to escape any \ characters in Windows paths - e.g. if you want
 * cache files to Go in c:\cachedir, the cache.path property should be set to
 * c:\ \cachedir.
 * </p>
 * <p>
 * 二，OSCache 处理一个servlet
 * Filter，使你能够很容易的缓存你网站的整个页面，甚至是二进制数据，二进制文件的缓存非常有用当他们被动态产生。
 * </p>
 * <p>
 * 配置Filter：
 * </p>
 * <p>
 * Example1：时间是10minutes scope：Session
 * </p>
 * <p>
 * &lt;filter&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;filter-name&gt;CacheFilter&lt;/filter-name&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp;
 * &lt;filter-class&gt;com.opensymphony.oscache.web.filter.CacheFilter
 * &lt;/filter-class&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;init-param&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &lt;param-name&gt;time&lt;/param-name&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &lt;param-value&gt;600&lt;/param-value&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;/init-param&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;init-param&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &lt;param-name&gt;scope&lt;/param-name&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &lt;param-value&gt;session&lt;/param-value&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;/init-param&gt;
 * </p>
 * <p>
 * &lt;/filter&gt;
 * </p>
 * <p>
 * &lt;filter-mapping&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;filter-name&gt;CacheFilter&lt;/filter-name&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;url-pattern&gt;*.jsp&lt;/url-pattern&gt;
 * </p>
 * <p>
 * &lt;/filter-mapping&gt;
 * </p>
 * <p>
 * Example2：默认缓存的scope是application，时间是一个小时
 * </p>
 * <p>
 * &lt;filter&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;filter-name&gt;CacheFilterStaticContent&lt;/filter-name&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp;
 * &lt;filter-class&gt;com.opensymphony.oscache.web.filter.CacheFilter
 * &lt;/filter-class&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;init-param&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &lt;param-name&gt;expires&lt;/param-name&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &lt;param-value&gt;time&lt;/param-value&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;/init-param&gt;
 * </p>
 * <p>
 * &lt;/filter&gt;
 * </p>
 * <p>
 * &lt;filter-mapping&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;filter-name&gt;CacheFilterStaticContent&lt;/filter-name&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;url-pattern&gt;*.jsp&lt;/url-pattern&gt;
 * </p>
 * <p>
 * &lt;/filter-mapping&gt;
 * </p>
 * <p>
 * 三 标签：
 * </p>
 * <p>
 * &lt;%@ taglib uri=&quot;http://www.opensymphony.com/oscache&quot;
 * prefix=&quot;cache&quot; %&gt;
 * </p>
 * <p>
 * 主要的标签有：
 * </p>
 * <p>
 * · cache- The main caching tag
 * </p>
 * <p>
 * · usecached- A nested tag to force using a cached version.
 * </p>
 * <p>
 * · flush- To flush caches programmatically.
 * </p>
 * <p>
 * · addgroup- It allows a single group name to be dynamically added to a cached
 * block. This tag must be nested inside &lt;cache:cache/&gt;.
 * </p>
 * <p>
 * · addgroups- It allows a comma-delimited list of group names to be
 * dynamically added to a cached block. This tag must be nested inside
 * &lt;cache:cache/&gt;.
 * </p>
 * <p>
 * 1.cache是OSCache主要的标签。根据指定的属性，标签的体将被缓存。以后每次运行标签，它会检查，看看缓存的内容是否过期，如果下列条件成立的话，
 * 标签体的内容将被认为是过时的：
 * </p>
 * <p>
 * &nbsp; &nbsp;1：标签体的内容超过了指定的缓存时间；默认为一小时，指定时以s为单位。
 * </p>
 * <p>
 * &nbsp; &nbsp;2：超多了cron属性指定的日期或时间；
 * </p>
 * <p>
 * &nbsp; &nbsp;3：通过flush标签，清除了指定的scope作用域的缓存。
 * </p>
 * <p>
 * 如果缓存的主体内容是过期的，标签会再次执行和缓存新的主体内容。
 * </p>
 * <p>
 * 属性：
 * </p>
 * <p>
 * ① Key：这应该是为给定的范围内唯一的，因为重复的键映射到相同的缓存条目，因为缓存存放在map集合里面。
 * key的默认值为使用URI转义版本和当前页面的查询字符串(默认值使用的URI转义版本和当前页面的查询字符串)。
 * </p>
 * <p>
 * ② scope：缓存存放的作用域，默认为application。可选的值为session和application。
 * </p>
 * <p>
 * ③ time：指定缓存存放的时间，以秒为单位，默认为3600s，即一个小时。如果为负值表示永远不过期。
 * </p>
 * <p>
 * ④ duration：这是和time属性二选一的。Duration可以通过simple date format指定。
 * </p>
 * <p>
 * ⑤ cron：cron表达式确定缓存的内容什么时候过期，它允许缓存的内容在特殊的日期或时间过期，而不是当缓存的内容达到某个年龄。
 * </p>
 * <p>
 * 介绍cron表达式：cron表达式的语法：
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp;cron表达式由下面5个字段组成：
 * </p>
 * <p>
 * Minute：指定缓存的内容在小时的第几分钟过期，取值范围是0-59；
 * </p>
 * <p>
 * Hour：指定缓存的内容在一天的第几个小时将过期，这是指定使用24小时制，因此取值范围是：0(午夜)-23(上午11点)。
 * </p>
 * <p>
 * DOM：一个月的第几天。这是一个从1到31的数，它表示缓存的内容什么日子过期，如：在每月的10号过期，应该把值设为10。
 * </p>
 * <p>
 * Month：一年的第几月使内容过期，它可以通过数字1-12或者月的英文名字(eg
 * &#39;January&#39;)指定。月的名称对大小写是不敏感的，只考虑前三个字符，其他部分忽略。
 * </p>
 * <p>
 * DOW：一周的第几天使缓存的内容过期，它可以是数字(0-6,0=星期天，6=星期六)或者是英文的星期名称。和月份一样大小写是不敏感的，只考虑前三个字符，
 * 其他部分忽略。
 * </p>
 * <p>
 * 注意：当你不想给某个字段给定特定的值，你可以使用&quot;*&quot;代替。
 * </p>
 * <p>
 * OSCache的也允许你选择性地指定每个字段内的列表，范围和时间间隔（甚至是三者的结合）
 * </p>
 * <p>
 * List：集合中的列表项通过&quot;,&quot;分隔，缓存过期的时间和每个列表项匹配，例如：&quot;0,15,30,45 * * *
 * *&quot;,表示缓存中的内容每隔15分钟过期一次。
 * </p>
 * <p>
 * Ranges：使用&quot;-&quot;指定范围。一个范围将包括所有值从开始到结束值(包括起始值)例如： &quot;* * * Jan-June
 * *&quot;表示一年的前六个月缓存将过期。
 * Intervals：一个间隔通过&quot;/&quot;指定。左边的“/”字符的值表示的出发点或范围值应递增超过
 * ，而右边的值表示间隔的时间长度。例如：&quot;10/20 * * * *&quot; 相当于 &quot;10,30,50 * * *
 * *&quot;, 而&quot;10-45/20 * * * *&quot; 只匹配过去每个小时的10-30分钟，因为50已经超过了范围。
 * </p>
 * <p>
 * ⑥ refresh：布尔值，如为true缓存将被刷新，不管缓存的内容是否过期。使你能决定是否重新缓存内容。
 * </p>
 * <p>
 * ⑦ mode：如果把值设置为&quot;silent&quot;,将防止标签体被写入到输出流。
 * </p>
 * <p>
 * ⑧ groups：用逗号分隔组里面的名称，根据你的需要允许把缓存的条目分组。分组是非常有用的当你的缓存内容依赖应用或数据的其他部分，当这些依赖发生变化，
 * flush相关的组将将导致所有缓存的条目在组中过期。
 * </p>
 * <p>
 * 假设我们有一个类别的动态清单，我们从数据库中拉，我们也偶尔得到更新，通过调用一个WebService存储外币兑换利率。假设，我们也有一些
 * </p>
 * <p>
 * 内容，显示有关类别和当前的汇率值的信息。下面缓存缓存主体内容的例子分配给2个缓存组
 * &quot;currencyData&quot;和&quot;categoryList&quot;。当汇率和类别
 * </p>
 * <p>
 * 列表发生更新，相关的组将被flush因为这些内容(与任何其他与该组相关的内容)被过期了，然后当页面被访问的时候，再次建立缓存。
 * </p>
 * <p>
 * &lt;cache:cache key=&quot;&lt;%= product.getId() %&gt;&quot;
 * time=&quot;-1&quot; group=&quot;currencyData, categories&quot;&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;... display category list ...
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;... display currency information ...
 * </p>
 * <p>
 * &nbsp;&lt;/cache:cache&gt;
 * </p>
 * <p>
 * 2，usecached 这个标签内嵌在一个&lt;cache&gt;标签中，表示&lt;cache&gt;标签是否使用cached version
 * </p>
 * <p>
 * 属性：use：默认值为true，表示使用cached version，这是有用的对于编程式控制缓存。
 * </p>
 * <p>
 * 例如：
 * </p>
 * <p>
 * This is a good example of error tolerance. If an exception occurs, the cached
 * version of this content will be output instead.这是很好的容错例子，当异常发生缓存的内容将不会被输出。
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;cache:cache&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&lt;% try { %&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;... some jsp content ...
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&lt;% } catch (Exception e) { %&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;cache:usecached /&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&lt;% } %&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;/cache:cache&gt;
 * </p>
 * <p>
 * 例如：&lt;cache:cache&gt; &nbsp;
 * </p>
 * <p>
 * &lt;%out.print(new Date()); %&gt;&nbsp;
 * </p>
 * <p>
 * &lt;cache:usecached use=&quot;true&quot;/&gt; &nbsp;
 * </p>
 * <p>
 * &nbsp; &lt;/cache:cache&gt;
 * </p>
 * <p>
 * 第一次访问的时候页面输出：Missing cached content
 * ,如果接着把use改为false则会输出当前时间，当flush时，时间会改变。接着把use改为true
 * ，即使flush，时间也不会改变，它还会使用以前的缓存。
 * </p>
 * <p>
 * 3，flush&nbsp;
 * </p>
 * <p>
 * 这个标签是用来在运行时刷新缓存。这是特别有用，因为它可以将您的网站的管理部分编码，以便管理员可以决定何时刷新缓存。
 * </p>
 * <p>
 * 属性：
 * </p>
 * <p>
 * Scope：这决定哪些范围将被刷新。有效值是“application”，“session”和null。空范围将刷新所有缓存，不论其范围。默认为all。
 * </p>
 * <p>
 * Key：当同时指定了key和scope，表示一个缓存条目被标记为flush。当他下次被访问的时候缓存将被refresh。
 * 当仅指定了key而没有指定scope这是无效的。
 * </p>
 * <p>
 * group ：指定一组将导致组中的所有缓存项被flush，如果仅仅指定group没有指定scope这是无效的。
 * </p>
 * <p>
 * 4，addgroup 必须内嵌在&lt;cache:cache&gt;标签内部，它允许一个组名，动态地添加到缓存的块中，
 * </p>
 * <p>
 * 这将以key为test1的cache块增加到组group1和group2：
 * </p>
 * <p>
 * &nbsp;&lt;cache:cache key=&quot;test1&quot;&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&lt;cache:addgroup group=&quot;group1&quot;
 * /&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;... some jsp content ...
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&lt;cache:addgroup group=&quot;group2&quot;
 * /&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;... some more jsp content ...
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;/cache:cache&gt;
 * </p>
 * <p>
 * 5，addgroups 必须内嵌在&lt;cache:cache&gt;标签内部，
 * </p>
 * <p>
 * This will add the cache block with the key &#39;test1&#39; to groups
 * &#39;group1&#39; and &#39;group2&#39;.
 * </p>
 * <p>
 * &nbsp; &nbsp; &lt;cache:cache key=&quot;test1&quot;&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;... some jsp content ...
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&lt;cache:addgroups
 * groups=&quot;group1,group2&quot; /&gt;
 * </p>
 * <p>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;... some jsp content ...
 * </p>
 * <p>
 * &lt;/cache:cache&gt;
 * </p>
 * <p>
 * 四 属性文件的配置：
 * </p>
 * <p>
 * 介绍仅涵盖了OSCache的配置，使用的oscache.properties文件，下面的属性能够被设置在oscache.properties文件中：
 * </p>
 * <p>
 * Cache.memory：
 * </p>
 * <p>
 * 有效值是真的还是假的，默认值为true。如果你想禁用内存缓存,只需要注释掉或删除此行。
 * </p>
 * <p>
 * 禁用内存和磁盘缓存是可能的，而且相当愚蠢。
 * </p>
 * <p>
 * Cache.capacity：
 * </p>
 * <p>
 * 缓存支持缓存条目的最大数量，默认情况下，容量是无限的。缓存将不会删除任何缓存条目。负的值也将意味着无限容量。
 * </p>
 * <p>
 * Cache.algorithm：
 * </p>
 * <p>
 * 使用默认的缓存算法。请注意，为了使用一种算法，还必须指定缓存的大小，如果未指定缓存的大小，缓存算法将是无限的缓存，而不管这个属性的值，如果你指定了大小，
 * 但没有指定算法，所使用的缓存算法将为com.opensymphony.oscache.base.algorithm.LRUCache。
 * </p>
 * <p>
 * OSCache的目前带有三种算法：
 * </p>
 * <p>
 * com.opensymphony.oscache.base.algorithm.LRUCache
 * --------最近最少使用。这是一个默认值当cache.capacity设置了值。
 * </p>
 * <p>
 * com.opensymphony.oscache.base.algorithm.FIFOCache --------先进先出。
 * </p>
 * <p>
 * com.opensymphony.oscache.base.algorithm.UnlimitedCache---------添加到缓存中的内容，
 * 是绝不会被丢弃的，这是默认值当 cache.capacity属性没有设置值。
 * </p>
 * <p>
 * Cache.blocking：
 * </p>
 * <p>
 * 当一个请求一个过期的缓存条目，它可能被另一个线程正在重建该缓存的过程中。
 * </p>
 * <p>
 * 此设置指定OSCache如何处理后来的“非重建缓存”的线程。
 * </p>
 * <p>
 * 默认行为（cache.blocking =
 * FALSE）是把过期内容给后面的线程，直到缓存条目已更新。这提供最佳的性能(仅仅花费稍微过时的数据服务的成本)。
 * </p>
 * <p>
 * 当阻塞被启用，线程反而会阻塞，直到新的缓存条目准备提供，一旦新的条目放进了缓存中，阻塞的线程将重新启动并给予最新的缓存内容。
 * </p>
 * <p>
 * 请注意，即使阻塞被禁用，当没有过期的数据可提供，线程将被阻塞直到通过线程把建立缓存的数据放进缓存内。
 * </p>
 * <p>
 * Cache.unlimited.disk：
 * </p>
 * <p>
 * 指示磁盘缓存是否应无限制。默认值是false，在这种情况下，磁盘缓存也可以和内存缓存一样通过 cache.capacity属性设置缓存大小
 * </p>
 * <p>
 * Cache.persistence.class：
 * </p>
 * <p>
 * 指定一个类用于持久化缓存条目。这个样的类必须实现PersistenceListener 接口。OSCache
 * 有一个这样的实现提供了一个基于持久化的文件系统
 * 。设置这样的属性给com.opensymphony.oscache.plugins.diskpersistence
 * .HashDiskPersistenceListener类去启用这个实现。你应该可以保存缓存数据使用像JDBC， LDAP.
 * NOTE通过指定你自己的类。这个类的对象的哈希码和toString方法返回的值将被缓存用来生成缓存条目的文件名称
 * 。如果你喜欢可读的文件名称，父DiskPersistenceListener可以被使用，但那将产生一个问题，由于非法的文件系统字符和长长的名字。
 * </p>
 * <p>
 * 注意：HashDiskPersistenceListener和DiskPersistenceListener类需要cache.path的设置，
 * 以便知道在哪里可以保存文件到磁盘。
 * </p>
 * <p>
 * Cache.path：
 * </p>
 * <p>
 * 这指定缓存将存储在磁盘上的哪个目录，该目录将被创建，如果它不存在，但是要记住OSCache
 * 必须要有权限往这个路径写内容。避免不同的缓存共享相同的缓存路径，因为OSCache没有被设计来处理这些问题。
 * </p>
 * <p>
 * 注意：对于windows系统，反斜杠字符“\”需要被转义。(反斜杠\backslash
 * ；斜杠/forwardslash)。例如：cache.path=c:\\myapp\\cache 。
 * </p>
 * <p>
 * cache.persistence.overflow.only (NEW! Since 2.1)：
 * </p>
 * <p>
 * 指示的持久性是否应该只发生一次内存缓存容量已达到。为了向后兼容默认值是false，但是推荐值是true当memory.cache被启用。
 * 这个属性大大改变了缓存的行为，在保存的缓存中将变的不同，那么内存中有什么。
 * </p>
 * <p>
 * <br/>
 * </p>
 * 
 * @author felly
 * 
 */
public class OSCacheImpl extends CacheImpl {
	private GeneralCacheAdministrator gca = null;

	public OSCacheImpl() {
		gca = OSCacheManager.getInstance().getBaseCache();

	}

	@Override
	public Object getInner(Object key) throws CacheException {
		try {
			return gca.getFromCache(String.valueOf(key));
		} catch (NeedsRefreshException e) {
			gca.cancelUpdate(String.valueOf(key));
			return null;
		}

		catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public Object setInner(Object key, Object value) throws CacheException {
		try {

			gca.putInCache(String.valueOf(key), value);
			return value;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public Object setInner(Object key, Object value, int expire)
			throws CacheException {
		try {

			gca.putInCache(String.valueOf(key), value,
					new ExpiresRefreshPolicy(expire));
			return value;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void delInner(Object key) throws CacheException {

		try {
			gca.flushEntry(String.valueOf(key));
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

}
