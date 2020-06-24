package com.kabanov.scheduler.notification.title;

import com.kabanov.scheduler.resource.ResourceReader;
import java.util.List;
import java.util.Random;

public class NotificationMessageGenerator {
    
    private final ResourceReader resourceReader = new ResourceReader();
    private final Random random = new Random();

    public String generate() {
        List<String> resourceRecords = resourceReader.getLines();
        int randomIndex = random.nextInt(resourceRecords.size());    
        return resourceRecords.get(randomIndex);
    }
}
