package ch.greenleaf.common.time;

public enum Timestamps {
    SHORTDATE, // 01/05/2025
    LONGDATE, // January 5, 2025
    HOURMINUTE, // 14:40
    HOURMINUTESECOND, // 14:40:00
    MONTHDAYTIME, // January 5, 2025 14:40
    DAYMONTHDAYTIME, // Sunday, January 5, 2025 14:40
    TIMEUNTIL, // in 5 seconds (same as TIMESINCE)
    TIMESINCE// 5 Minutes ago (same as TIMEUNTIL)
}
