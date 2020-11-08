package com.blax.springconfig.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;

import java.nio.file.*;

// https://www.baeldung.com/java-nio2-watchservice
//@Component
// watchService is not going to work with mounted volume
public class DirectoryWatcher {

    WatchService watchService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public DirectoryWatcher() {
        final String directory = "config/";
        try {
            this.watchService
                    = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(directory);
            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Scheduled(fixedDelay=5000)
    public void start() {
        try {
            System.out.println("poll directory changes");
            WatchKey key = watchService.take();
            Thread.sleep( 50 ); // https://stackoverflow.com/questions/16777869/java-7-watchservice-ignoring-multiple-occurrences-of-the-same-event/25221600#25221600
            boolean modified = false;
            if (key != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println(
                            "Event kind:" + event.kind()
                                    + ". File affected: " + event.context() + ".");
                    modified = true;
                }
                if (modified) {
                    this.eventPublisher.publishEvent(
                            new RefreshEvent(this, "ConfigChanged", "Triggered by Directory Watch"));
                }
                key.reset();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
