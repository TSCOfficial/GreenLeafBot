package ch.greenleaf.common.timestamp;

public enum TimestampType {
    SHORTDATE, // 01/05/2025
    LONGDATE, // January 5, 2025
    HOURMINUTE, // 14:40
    HOURMINUTESECOND, // 14:40:00
    MONTHDAYTIME, // January 5, 2025 14:40
    DAYMONTHDAYTIME, // Sunday, January 5, 2025 14:40
    RELATIVE, // in 5 seconds / 5 seconds ago
}
