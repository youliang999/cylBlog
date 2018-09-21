<@page isBackend=true>
<div class="col-sm-9 col-md-10 bdiv">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li>缓存监控</li>
    </ol>
    <div class="row" style="padding-top: 15px; ">
        <div class="col-sm-6 col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading"><span class="icon glyphicon glyphicon-certificate"></span>redis信息</div>
                <div class="panel-body">
                    <ul class="list-unstyled ul-group">
                    <#if redisInfos??>
                        <#list redisInfos as redis>
                            <li>${redis.key!}: ${redis.value!}</li>
                        </#list>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-6" style="float: right">
            <div class="panel panel-default">
                <div class="panel-heading"><span class="icon glyphicon glyphicon-certificate"></span>server : 一般 Redis 服务器信息，包含以下域：</div>
                <div class="panel-body">
                    <ul class="list-unstyled ul-group">
                        <li>redis_version : Redis 服务器版本</li>
                        <li>redis_git_sha1 : Git SHA1</li>
                        <li>redis_git_dirty : Git dirty flag</li>
                        <li>os : Redis 服务器的宿主操作系统</li>
                        <li>arch_bits : 架构（32 或 64 位）</li>
                        <li>multiplexing_api : Redis 所使用的事件处理机制</li>
                        <li>gcc_version : 编译 Redis 时所使用的 GCC 版本</li>
                        <li>process_id : 服务器进程的 PID</li>
                        <li>run_id : Redis 服务器的随机标识符（用于 Sentinel 和集群）</li>
                        <li>tcp_port : TCP/IP 监听端口</li>
                        <li>uptime_in_seconds : 自 Redis 服务器启动以来，经过的秒数</li>
                        <li>uptime_in_days : 自 Redis 服务器启动以来，经过的天数</li>
                        <li>lru_clock : 以分钟为单位进行自增的时钟，用于 LRU 管理</li>
                        <li>clients : 已连接客户端信息，包含以下域：</li>
                        <li>connected_clients : 已连接客户端的数量（不包括通过从属服务器连接的客户端）</li>
                        <li>client_longest_output_list : 当前连接的客户端当中，最长的输出列表</li>
                        <li>client_longest_input_buf : 当前连接的客户端当中，最大输入缓存</li>
                        <li>blocked_clients : 正在等待阻塞命令（BLPOP、BRPOP、BRPOPLPUSH）的客户端的数量</li>
                        <li>memory : 内存信息，包含以下域：</li>
                        <li>used_memory : 由 Redis 分配器分配的内存总量，以字节（byte）为单位</li>
                        <li>used_memory_human : 以人类可读的格式返回 Redis 分配的内存总量</li>
                        <li>used_memory_rss : 从操作系统的角度，返回 Redis 已分配的内存总量（俗称常驻集大小）。这个值和 top 、 ps 等命令的输出一致。</li>
                        <li>used_memory_peak : Redis 的内存消耗峰值（以字节为单位）</li>
                        <li>used_memory_peak_human : 以人类可读的格式返回 Redis 的内存消耗峰值</li>
                        <li>used_memory_lua : Lua 引擎所使用的内存大小（以字节为单位）</li>
                        <li>mem_fragmentation_ratio : used_memory_rss 和 used_memory 之间的比率</li>
                        <li>mem_allocator : 在编译时指定的， Redis 所使用的内存分配器。可以是 libc 、 jemalloc 或者 tcmalloc 。 </li>
                        <li>在理想情况下， used_memory_rss 的值应该只比 used_memory 稍微高一点儿。 </li>
                        <li>当 rss > used ，且两者的值相差较大时，表示存在（内部或外部的）内存碎片。 </li>
                        <li>内存碎片的比率可以通过 mem_fragmentation_ratio 的值看出。 </li>
                        <li>当 used > rss 时，表示 Redis 的部分内存被操作系统换出到交换空间了，在这种情况下，操作可能会产生明显的延迟。 </li>
                        <li>当 Redis 释放内存时，分配器可能会，也可能不会，将内存返还给操作系统。 </li>
                        <li>如果 Redis 释放了内存，却没有将内存返还给操作系统，那么 used_memory 的值可能和操作系统显示的 Redis 内存占用并不一致。 </li>
                        <li>查看 used_memory_peak 的值可以验证这种情况是否发生。</li>
                        <li>persistence : RDB 和 AOF 的相关信息</li>
                        <li>stats : 一般统计信息</li>
                        <li>replication : 主/从复制信息</li>
                        <li>cpu : CPU 计算量统计信息</li>
                        <li>commandstats : Redis 命令统计信息</li>
                        <li>cluster : Redis 集群信息</li>
                        <li>keyspace : 数据库相关的统计信息</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</@page>