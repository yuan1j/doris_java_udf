package org.apache.doris.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;


public class WorkdaysBetween extends UDF {

    public Integer WorkdaysBetween(LocalDateTime dateFrom, LocalDateTime dateTo, String dateExclude, String dateInclude) {
        if (dateFrom == null || dateTo == null) {
            return null;
        }

        String fromDateString = dateFrom.toLocalDate().toString();
        LocalDateTime startDate;

        if (dateInclude != null && dateInclude.contains(fromDateString)) {
            startDate = dateFrom;
        } else if (dateFrom.getDayOfWeek().getValue() >= 6) {
            startDate = dateFrom.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .withHour(0).withMinute(0).withSecond(0);
        } else {
            startDate = dateFrom;
        }

        // 计算完整的周数
        long totalDays = ChronoUnit.DAYS.between(startDate, dateTo) + 1;
        long fullWeeks = totalDays / 7;
        int workdays = (int) (fullWeeks * 5); // 每周5个工作日

        // 处理剩余天数
        LocalDateTime current = startDate.plusDays(fullWeeks * 7 );

        // 处理dateExclude中的日期
        if (dateExclude != null) {
            String[] excludedDates = dateExclude.split(",");
            for (String excludedDate : excludedDates) {
                excludedDate = excludedDate.trim();
                LocalDate excludedLocalDate = LocalDate.parse(excludedDate);
                // 检查排除的日期是否在范围内
                if (!excludedLocalDate.isBefore(dateFrom.toLocalDate()) && !excludedLocalDate.isAfter(dateTo.toLocalDate())) {
                    DayOfWeek dayOfWeek = excludedLocalDate.getDayOfWeek();
                    // 如果排除的日期是工作日（周一到周五）
                    if (dayOfWeek.getValue() >= 1 && dayOfWeek.getValue() <= 5) {
                        workdays--; // 从工作日总数中减去这一天
                        // 如果在剩余天数处理范围内
                        if (!excludedLocalDate.isBefore(current.toLocalDate())) {
                            workdays--; // 再多减一天，因为后续计算会将其加上
                        }
                    }
                }
            }
        }

        // 处理dateInclude中的日期
        if (dateInclude != null) {
            String[] excludedDates = dateInclude.split(",");
            for (String excludedDate : excludedDates) {
                excludedDate = excludedDate.trim();
                LocalDate excludedLocalDate = LocalDate.parse(excludedDate);
                // 检查包含的日期是否在范围内
                if (!excludedLocalDate.isBefore(dateFrom.toLocalDate()) && !excludedLocalDate.isAfter(dateTo.toLocalDate())) {
                    DayOfWeek dayOfWeek = excludedLocalDate.getDayOfWeek();
                    // 如果排除的日期是周末（周六到周日）
                    if (dayOfWeek.getValue() >= 6 && dayOfWeek.getValue() <= 7) {
                        workdays++; // 从工作日总数中加上这一天
                        // 如果在剩余天数处理范围内
                        if (!excludedLocalDate.isBefore(current.toLocalDate())) {
                            workdays++; // 再多加上一天，因为后续计算会将其减去
                        }
                    }
                }
            }
        }
        while (!current.isAfter(dateTo)) {
            int dayOfWeek = current.getDayOfWeek().getValue();
            if (dayOfWeek >= 1 && dayOfWeek <= 5) {
                workdays++;
            }
            current = current.plusDays(1);
        }

        return workdays;
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
