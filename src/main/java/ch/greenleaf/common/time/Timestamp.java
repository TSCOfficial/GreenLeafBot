package ch.greenleaf.common.time;

import org.jetbrains.annotations.NotNull;

public class Timestamp {
	
	// Time in seconds since epoch
	private long epochTime;
	
	// Timestamp variant
	private TimestampType type;

    /**
     * Convert a Unix Epoch Time to a readable Discord Time.
     * <ul>
     *     <h4>Timestamps options</h4>
     *     <li>{@link TimestampType#SHORTDATE}: <code>MM/dd/yyyy</code> -> 01/05/2025</li>
     *     <li>{@link TimestampType#LONGDATE}: <code>MMMM d, yyyy</code> -> January 5, 2025</li>
     *     <li>{@link TimestampType#HOURMINUTE}: <code>hh:mm</code> -> 14:49</li>
     *     <li>{@link TimestampType#HOURMINUTESECOND}: <code>hh:mm:ss</code> -> 14:40:00</li>
     *     <li>{@link TimestampType#MONTHDAYTIME}: <code>MMMM, d, yyyy hh:mm</code> -> January 5, 2025 14:40</li>
     *     <li>{@link TimestampType#DAYMONTHDAYTIME}: <code>DD, MMMM, d, yyyy hh:mm</code> -> Sunday, January 5, 2025 14:40</li>
     *     <li>{@link TimestampType#RELATIVE}: <code>x time ago</code> / <code>in x time</code> -> 5 seconds ago / in 5 seconds</li>
     * </ul>
     *
     * @param unixTime The time as {@link long}
     * @param timestampType The timestamp format as {@link TimestampType}
     * @return The new Discord Time as {@link String}
     */
    public static String convert(long unixTime, @NotNull TimestampType timestampType){
        String discordTime = "";
        switch (timestampType){
            case TimestampType.SHORTDATE -> discordTime = "<t:" + unixTime + ":d>";
            case TimestampType.LONGDATE -> discordTime = "<t:" + unixTime + ":D>";
            case TimestampType.HOURMINUTE -> discordTime = "<t:" + unixTime + ":t>";
            case TimestampType.HOURMINUTESECOND -> discordTime = "<t:" + unixTime + ":T>";
            case TimestampType.MONTHDAYTIME -> discordTime = "<t:" + unixTime + ":f>";
            case TimestampType.DAYMONTHDAYTIME -> discordTime = "<t:" + unixTime + ":F>";
            case TimestampType.RELATIVE -> discordTime = "<t:" + unixTime + ":R>";
        }
        return discordTime;
    }
	
	public String convert() {
		return convert(epochTime, type);
	}
	
	public void setEpochTime(long epochTime) {
		this.epochTime = epochTime;
	}
	
	public void setType(TimestampType type) {
		this.type = type;
	}
}
