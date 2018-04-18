package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class TimeSpan implements Cloneable {
    /**
     * Fields use to date format DD/MM/YYYY
     */
    private int years = 0;
    private int month = 0;
    private int days  = 0;

    /**
     * Fields use to date time format DD/MM/YYYY HH:MM:SS
     */
    private int totalDays = 0;
    private int hours     = 0;
    private int minites   = 0;
    private int secondes  = 0;

    private TimeSpan(int years, int month, int days, int hours, int minites, int secondes) {
        setYears(years);
        setMonth(month);
        setDays(days);
        setHours(hours);
        setMinites(minites);
        setSecondes(secondes);
    }

    public TimeSpan(int days, int hours, int minites, int secondes) {
        this.totalDays = days;
        this.hours = hours;
        this.minites = minites;
        this.secondes = secondes;
    }

    public TimeSpan(int years, int month, int days) {
        setYears(years);
        setMonth(month);
        setDays(days);
    }

    public static String now() {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        return simpleFormat.format(new Date());
    }

    /**
     * return un format de la date sous forme de bigInt*/
    public static String timesTemp() {
        return  new SimpleDateFormat("dd/MM/YYYY HH:mm:ss:SSS")
                .format(new Date()).replace(":", "")
                .replace(" ", "")
                .replace("/", "");
    }

    public static TimeSpan parse(String date) {
        DateFormatException dateFormat = new DateFormatException("'" + date + "' is not a datetime or time", new Throwable(date));
        TimeSpan            timeSpan;
        String[]            dateTime   = date.split(" ");
        String[]            sList      = new String[2];

        String spliter = "";
        if (date.contains("/"))
            spliter = "/";
        if (date.contains("-"))
            spliter = "-";

        int inc = 0;

        for (String s : dateTime) {
            if (!s.equals("")) {
                if (inc == 2) break;
                sList[inc] = s;
                inc++;
            }
        }
        if (date.contains(":") || !spliter.equals("")) {
            if (inc == 2) {
                if (sList[0].contains(spliter) && sList[1].contains(":")) {
                    String[] dateSpliter = sList[0].split(spliter);
                    String[] timeSpliter = sList[1].split(":");

                    if (dateSpliter.length != 3)
                        throw dateFormat;
                    else if (timeSpliter.length <= 1)
                        throw dateFormat;
                    else {
                        int val1 = parseInt(dateSpliter[0]);
                        int val2 = parseInt(dateSpliter[1]);
                        int val3 = parseInt(dateSpliter[2]);

                        int val4 = parseInt(timeSpliter[0]);
                        int val5 = parseInt(timeSpliter[1]);

                        if (dateSpliter[0].length() == 4) {
                            timeSpan = new TimeSpan(val1, val2, val3, val4, val5, 0);
                        } else {
                            timeSpan = new TimeSpan(val3, val2, val1, val4, val5, 0);
                        }

                        if (timeSpliter.length == 3) {
                            timeSpan.setSecondes(parseInt(timeSpliter[2]));
                        }
                        return timeSpan;
                    }
                } else throw dateFormat;
            } else if (inc == 1) {
                if (!spliter.equals("")) {
                    if (sList[0].contains(spliter)) {
                        String[] dateSpliter = sList[0].split(spliter);
                        if (dateSpliter.length != 3) throw dateFormat;
                        int val1 = parseInt(dateSpliter[0]);
                        int val2 = parseInt(dateSpliter[1]);
                        int val3 = parseInt(dateSpliter[2]);

                        if (dateSpliter[0].length() == 4) {
                            timeSpan = new TimeSpan(val1, val2, val3, 0, 0, 0);
                        } else {
                            timeSpan = new TimeSpan(val3, val2, val1, 0, 0, 0);
                        }
                        return timeSpan;
                    }
                    throw dateFormat;
                } else if (sList[0].contains(":")) {
                    String[] timeSpliter = sList[0].split(":");
                    if (timeSpliter.length <= 1) {
                        throw dateFormat;
                    }
                    int val4 = parseInt(timeSpliter[0]);
                    int val5 = parseInt(timeSpliter[1]);
                    timeSpan = new TimeSpan(0, val4, val5, 0);
                    if (timeSpliter.length == 3) {
                        timeSpan.setSecondes(parseInt(timeSpliter[2]));
                    }
                    return timeSpan;
                } else throw dateFormat;
            }
        }
        throw dateFormat;
    }


    public TimeSpan difference(TimeSpan timeSpan) {
        double  allDays  = 1;
        boolean negative = false;

        TimeSpan timeSpan1 = this;
        TimeSpan timeSpan2 = timeSpan;

        if ((timeSpan1.years < timeSpan2.years) || (timeSpan1.month < timeSpan2.month) || (timeSpan1.days < timeSpan2.days)) {
            timeSpan1 = timeSpan;
            timeSpan2 = this;
            negative = true;
        }

        int month = timeSpan2.month;
        int days  = timeSpan2.days;

        for (int a = timeSpan2.years; a <= timeSpan1.years; a++) {
            for (int i = month; i <= 12; i++) {
                for (int j = days; j <= 31; j++) {
                    if (a != timeSpan1.years || i != timeSpan1.month || j != timeSpan1.days) {
                        allDays++;
                        if (i == 4 || i == 6 || i == 9 || i == 11) if(j == 30) break;
                        if (i == 2) if(a % 4 == 0 && j == 29) break;
                        else if(a % 4 != 0 && j == 28) break;
                    } else {
                        if (timeSpan1.years == timeSpan1.years && timeSpan1.month == timeSpan2.month && timeSpan1.days == timeSpan2.days)
                            allDays = 0;
                        double difference = timeSpan1.totalSecondes() - timeSpan2.totalSecondes();
                        int    totalDays  = (int) ((difference / (24 * 3600)) + allDays);
                        int    reste      = (int) (difference % (24 * 3600));
                        int hours = reste / 3600;
                        reste = reste % 3600;
                        int minites = reste / 60;
                        reste = reste % 60;
                        int secondes = reste;
                        if (negative)
                            return new TimeSpan(-totalDays, -hours, -minites, -secondes);
                        return new TimeSpan(totalDays, hours, minites, secondes);
                    }
                }
                days = 1;
            }
            month = 1;
        }
        throw new RuntimeException("Nous n'avons pas la difference entre ces deux TimeSpan");
    }

    public String toString() {
        String sMonth    = String.valueOf(month);
        String sDays     = String.valueOf(days);
        String sHours    = String.valueOf(hours);
        String sMinites  = String.valueOf(minites);
        String sSecondes = String.valueOf(secondes);

        if (-10 < month && month < 10) sMonth = "0" + month;
        if (-10 < days && days < 10) sDays = "0" + days;
        if (-10 < hours && hours < 10) sHours = "0" + hours;
        if (-10 < minites && minites < 10) sMinites = "0" + minites;
        if (-10 < secondes && secondes < 10) sSecondes = "0" + secondes;

        if (years == 0 && month == 0 && days == 0) return String.format("%s:%s:%s", sHours, sMinites, sSecondes);
        return String.format("%s/%s/%d %s:%s:%s", sDays, sMonth, years, sHours, sMinites, sSecondes);
    }

    private void setYears(int years) {
        if (years < 1957 || 9999 < years) throw new DateFormatException("Years out of bounds. Max : 9999");
        this.years = years;
    }

    private void setMonth(int month) {
        if (month < 1 || 12 < month) throw new DateFormatException("Month out of bounds. Max : 12");
        this.month = month;
    }

    private void setDays(int days) {
        int max = 31;
        if (month == 4 || month == 6 || month == 9 || month == 11) max = 30;
        else if (month == 2) if(years % 4 == 0) max = 29;
        else max = 28;
        if (days < 1 || max < days) throw new DateFormatException("Days out of bounds. Max : " + max);
        this.days = days;
    }

    private void setHours(int hours) {
        if (hours < 0 || 23 < hours) throw new DateFormatException("Hours out of bounds. Max : 23");
        this.hours = hours;
    }

    private void setMinites(int minites) {
        if (minites < 0 || 59 < minites) throw new DateFormatException("Minites out of bounds. Max : 59");
        this.minites = minites;
    }

    private void setSecondes(int secondes) {
        if (secondes < 0 || 59 < secondes) throw new DateFormatException("Secondes out of bounds. Max : 59");
        this.secondes = secondes;
    }

    public double totalDays() {
        return totalDays + hours / 24 + minites / (24 * 60) + secondes / (24 * 3600);
    }

    public double totalHours() {
        return 24 * totalDays + hours + minites / 60 + secondes / 3600;
    }

    public double totalMinites() {
        return secondes / 60 + minites + 60 * hours + 60 * 24 * totalDays;
    }

    public double totalSecondes() {
        return secondes + 60 * minites + 3600 * hours + 3600 * 24 * totalDays;
    }

    public TimeSpan clone() {
        return this;
    }
}
