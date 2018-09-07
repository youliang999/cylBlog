package com.cyl.blog.solr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by youliang.cheng on 2018/8/20.
 */
public class SolrClientServiceImpl {
    private String baseUrl;
    private int connectionTimeout = 5000;
    private int socketTimeout = 5000;
    public static final SolrClientServiceImpl instance = new SolrClientServiceImpl();
    private final ConcurrentHashMap clientMap = new ConcurrentHashMap();
    private  String client = "client";

    private static final String host = "http://127.0.0.1:8080/solr/";
    //private static final String host = "http://127.0.0.1:8888/";
    private static final String core = "blog_index";

    public HttpSolrClient getSolrClient() {

        if(clientMap.get(client) == null) {
            return new HttpSolrClient.Builder(baseUrl)
                    .withConnectionTimeout(connectionTimeout)
                    .withSocketTimeout(socketTimeout).build();
        }
        return (HttpSolrClient)clientMap.get(client);
    }


    private SolrClientServiceImpl() {
        baseUrl = host + core;
        // solr服务的url，tb_item是前面创建的solr core
        //String url = "http://localhost:8080/solr/tb_item";
        // 创建HttpSolrClient
        clientMap.put(client, new HttpSolrClient.Builder(baseUrl)
                .withConnectionTimeout(connectionTimeout)
                .withSocketTimeout(socketTimeout).build());
    }
}
