package org.apache.doris.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class WorksecondsBetween extends UDF {

    public Integer WorksecondsBetween(LocalDateTime dateFrom, LocalDateTime dateTo, String dateExclude, String dateInclude) {
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

        int Workseconds = 0;
        LocalDateTime current = startDate;

        while (!current.isAfter(dateTo)) {
            int dayOfWeek = current.getDayOfWeek().getValue();
            String currentDateStr = current.toLocalDate().toString();

            // 判断是否为工作日或在包含列表中，且不在排除列表中
            boolean isWorkDay = (dayOfWeek >= 1 && dayOfWeek <= 5) ||
                    (dateInclude != null && dateInclude.contains(currentDateStr));
            boolean isExcluded = dateExclude != null && dateExclude.contains(currentDateStr);

            if (isWorkDay && !isExcluded) {
                Workseconds++;
            }

            current = current.plusSeconds(1);
        }

        return Workseconds;
    }
    public Integer evaluate(LocalDateTime dateFrom, LocalDateTime dateTo) {
        String dateExclude = null;
        String dateInclude = null;
        return WorksecondsBetween(dateFrom, dateTo, dateExclude, dateInclude);
    }
    public Integer evaluate(LocalDateTime dateFrom, LocalDateTime dateTo, String dateExclude) {
        String dateInclude = null;
        return WorksecondsBetween(dateFrom, dateTo, dateExclude, dateInclude);
    }
    public Integer evaluate(LocalDateTime dateFrom, LocalDateTime dateTo, String dateExclude, String dateInclude) {
        return WorksecondsBetween(dateFrom, dateTo, dateExclude, dateInclude);
    }
}



