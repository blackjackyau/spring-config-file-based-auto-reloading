package com.blax.springconfig.monitoring;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

// https://examples.javacodegeeks.com/core-java/apache/commons/io-commons/monitor/filealterationmonitor/org-apache-commons-io-monitor-filealterationmonitor-example/
@Component
public class DirectoryMonitor implements InitializingBean {

    private static final long pollingIntervalMs = 5 * 1000;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void afterPropertiesSet() throws Exception {
        FileAlterationObserver fao = new FileAlterationObserver("config/");
        fao.addListener(new DirectoryMonitorListener(eventPublisher));
        final FileAlterationMonitor monitor = new FileAlterationMonitor(pollingIntervalMs);
        monitor.addObserver(fao);
        monitor.start();
    }
}
