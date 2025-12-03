package org.apache.doris.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;


public class WorkhoursBetween extends UDF {
    public Integer evaluate(LocalDateTime dateFrom, LocalDateTime dateTo, String dateExclude, String dateInclude) {
        if (dateFrom == null || dateTo == null) {
            return null;
        }
        if (dateFrom.isAfter(dateTo)) {
            return -1;
        }

        LocalDateTime startDate;

        // 调整开始日期为工作日
        if (dateFrom.getDayOfWeek().getValue() >= 6) {
            startDate = dateFrom.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .withHour(0).withMinute(0).withSecond(0);
        } else {
            startDate = dateFrom;
        }
        // 1. 计算完整周的工作小时数
        long totalDays = ChronoUnit.DAYS.between(startDate, dateTo) + 1;
        long fullWeeks = totalDays / 7;
        long weekHours = fullWeeks * 120; // 每周5个工作日，每个工作日24小时

        // 2. 再加上剩余小时数
        long restHours = 0;
        // 将开始日期加上完整周数
        LocalDateTime current = startDate.plusDays(fullWeeks * 7);
        // 因为前面处理了，如果开始日期是周末，则处理成了周一的0点，所以开始日期只有可能为周一到周五
        // 这样就只有3种可能
        if(dateTo.isAfter(current)){
            if (current.getDayOfWeek().getValue() <= dateTo.getDayOfWeek().getValue() && dateTo.getDayOfWeek().getValue() <= 5) {
                // 结束日期与开始日期同周，且结束日期为周一到周五; 此时剩余小时数为两者的差
                restHours += ChronoUnit.HOURS.between(current, dateTo) + 1;
            } else if (current.getDayOfWeek().getValue() <= dateTo.getDayOfWeek().getValue() && dateTo.getDayOfWeek().getValue() > 5) {
                // 结束日期与开始日期同周，且结束日期为周六或周日; 此时剩余小时数为开始日期到周六凌晨0点的小时数
                restHours += ChronoUnit.HOURS.between(current,
                        dateTo.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY)).withHour(0).withMinute(0).withSecond(0));
            } else {
                // 结束日期为开始日期的下一周，且结束日期为周一到周五; 此时剩余小时数为两者的差，再减去48小时
                restHours += ChronoUnit.HOURS.between(current, dateTo) - 47;
            }
        }

        // 处理特殊情况
        String dateFromString = dateFrom.toLocalDate().toString();
        String dateToString = dateTo.toLocalDate().toString();

        // 3. 处理dateExclude的特殊情况，如果dateExclude不为空，则减去dateExclude的所含的小时数
        long excludeHours = 0;
        if (dateExclude != null) {
            String[] excludedDates = dateExclude.split(",");
            LocalDate excludedLocalDate = null;
            for (String excludedDate : excludedDates) {
                try {
                    excludedLocalDate = LocalDate.parse(excludedDate.trim());
                } catch (Exception e) {
                    // 处理日期解析异常
                    System.err.println("无法解析排除日期: " + dateExclude);
                    continue;
                }
                if (excludedLocalDate.getDayOfWeek().getValue() > 5) {
                    // 如果是周末，则无需任何处理
                    continue;
                } else if (dateFromString.equals(excludedDate)) {
                    // 如果是和开始日期同一天，则剔除min(开始日期到第二天凌晨0点的小时数,开始日期到结束日期的小时数)
                    excludeHours += Math.min(ChronoUnit.HOURS.between(dateFrom,dateFrom.toLocalDate().plusDays(1).atStartOfDay())
                    ,ChronoUnit.HOURS.between(dateFrom,dateTo) + 1);
                } else if (dateToString.equals(excludedDate)) {
                    // 如果是和结束日期同一天，则剔除min(结束日期凌晨0点到结束日期的小时数,开始日期到结束日期的小时数)
                    excludeHours += Math.min(ChronoUnit.HOURS.between(dateTo.toLocalDate().atStartOfDay(),dateTo) + 1
                    ,ChronoUnit.HOURS.between(dateFrom,dateTo) + 1);
                } else {
                    // 其它情况，为24小时
                    excludeHours += 24;
                }
            }
        }

        // 4. 处理dateInclude的特殊情况，如果dateInclude不为空，则加上dateInclude的所含的小时数
        long includeHours = 0;
        if (dateInclude != null) {
            String[] includeDates = dateInclude.split(",");
            LocalDate includeLocalDate = null;
            for (String includeDate : includeDates) {
                try {
                    includeLocalDate = LocalDate.parse(includeDate.trim());
                } catch (Exception e) {
                    // 处理日期解析异常
                    System.err.println("无法解析包含日期: " + dateInclude);
                    continue;
                }
                if (includeLocalDate.getDayOfWeek().getValue() <= 5) {
                    // 如果是周末，则无需任何处理
                    continue;
                } else if (dateFromString.equals(includeDate)) {
                    // 如果是和开始日期同一天，则加上min(开始日期到第二天凌晨0点的小时数,开始日期到结束日期的小时数)
                    includeHours += Math.min(ChronoUnit.HOURS.between(dateFrom,dateFrom.toLocalDate().plusDays(1).atStartOfDay())
                    ,ChronoUnit.HOURS.between(dateFrom,dateTo) + 1);
                } else if (dateToString.equals(includeDate)) {
                    // 如果是和结束日期同一天，则加上min(结束日期凌晨0点到结束日期的小时数,开始日期到结束日期的小时数)
                    includeHours += Math.min(ChronoUnit.HOURS.between(dateTo.toLocalDate().atStartOfDay(),dateTo) + 1
                            ,ChronoUnit.HOURS.between(dateFrom,dateTo) + 1);
                } else {
                    // 其它情况，为24小时
                    includeHours += 24;
                }
            }
        }

        // 返回结果
        System.out.println("weekHours, restHours, excludeHours, includeHours: " + weekHours + ", " + restHours+ ", " + excludeHours+ ", " + includeHours);
        long workhours =  weekHours + restHours - excludeHours + includeHours;
        return (int)workhours;
    }
}
