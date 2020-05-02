package com.kabanov.scheduler.notification.title;

import java.util.List;
import java.util.Random;

import com.kabanov.scheduler.resource.ResourceReader;

public class NotificationMessageGenerator {
    
    private final ResourceReader resourceReader = new ResourceReader("notification/notification-text.txt");
    private final Random random = new Random();

    public String generate() {
        List<String> resourceRecords = resourceReader.getLines();
        int randomIndex = random.nextInt(resourceRecords.size());    
        return resourceRecords.get(randomIndex);
    }
}
