package org.apache.doris.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class WorkdaysBetween extends UDF {

    public Integer WorkdaysBetween(LocalDateTime dateFrom, LocalDateTime dateTo, String dateExclude, String dateInclude) {
        if (dateFrom == null || dateTo == null) {
            return null;
        }

        String fromDateString = dateFrom.toLocalDate().toString();
        // 处理开始日期
        LocalDateTime startDate;
        if (dateInclude != null && dateInclude.contains(fromDateString)) {
            startDate = dateFrom;
        } else if (dateFrom.getDayOfWeek().getValue() >= 6) {
            // 周六(6)或周日(7)，转为下周一00:00:00
            startDate = dateFrom.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .withHour(0).withMinute(0).withSecond(0);
        } else {
            startDate = dateFrom;
        }

        int Workdays = 0;
        LocalDateTime current = startDate;

        while (!current.isAfter(dateTo)) {
            int dayOfWeek = current.getDayOfWeek().getValue();
            String currentDateStr = current.toLocalDate().toString();

            // 判断是否为工作日或在包含列表中，且不在排除列表中
            boolean isWorkDay = (dayOfWeek >= 1 && dayOfWeek <= 5) ||
                    (dateInclude != null && dateInclude.contains(currentDateStr));
            boolean isExcluded = dateExclude != null && dateExclude.contains(currentDateStr);

            if (isWorkDay && !isExcluded) {
                Workdays++;
            }

            current = current.plusDays(1);
        }

        return Workdays;
    }
    public Integer evaluate(LocalDateTime dateFrom, LocalDateTime dateTo) {
        String dateExclude = null;
        String dateInclude = null;
        return WorkdaysBetween(dateFrom, dateTo, dateExclude, dateInclude);
    }
    public Integer evaluate(LocalDateTime dateFrom, LocalDateTime dateTo, String dateExclude) {
        String dateInclude = null;
        return WorkdaysBetween(dateFrom, dateTo, dateExclude, dateInclude);
    }
    public Integer evaluate(LocalDateTime dateFrom, LocalDateTime dateTo, String dateExclude, String dateInclude) {
        return WorkdaysBetween(dateFrom, dateTo, dateExclude, dateInclude);
    }
}



