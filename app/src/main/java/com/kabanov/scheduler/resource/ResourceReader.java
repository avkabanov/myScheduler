package com.kabanov.scheduler.resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceReader {

    private final List<String> lines = new ArrayList<>(); 
    {
        lines.add("Рыба, так держать! Просроченных дел нет!");
        lines.add("Даже не думай трогать ежа!");
        lines.add("Каждой веснушке раздала по делу и дела закончились...");
        lines.add("А не взять ли мне дело из китовьего шедулера, раз мои закончились?");
        lines.add("Хорошо, что сегодня не надо ничего делать! Хотя нет! Надо зайти в магазин, посмотреть что там по акции!");
        lines.add("Осталось только почесать кита и все дела сделаны");
        lines.add("Сделал дело - плавай смело!");
        lines.add("Я рыба простая: вижу кита - прилипаю к нему");
        lines.add("Ну теперь можно спокойно прилипнуть. До завтра больше дел нет");
        lines.add("Прилипаааалаа.... что делала?? Прилипааааала!");
        lines.add("Рыба все сделала и может есть страчителлу!");
    }

    public ResourceReader(String path) {
    }

    public List<String> getLines() {
        return lines;
    }
}
