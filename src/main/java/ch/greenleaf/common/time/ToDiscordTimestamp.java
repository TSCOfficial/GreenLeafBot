package ch.greenleaf.common.time;

import org.jetbrains.annotations.NotNull;

public class ToDiscordTimestamp {

    /**
     * Convert a Unix Epoch Time to a readable Discord Time.
     * <ul>
     *     <h4>Timestamps options</h4>
     *     <li>SHORTDATE: <code>MM/dd/yyyy</code> -> 01/05/2025</li>
     *     <li>LONGDATE: <code>MMMM d, yyyy</code> -> January 5, 2025</li>
     *     <li>HOURMINUTE: <code>hh:mm</code> -> 14:49</li>
     *     <li>HOURMINUTESECOND: <code>hh:mm:ss</code> -> 14:40:00</li>
     *     <li>MONTHDAYTIME: <code>MMMM, d, yyyy hh:mm</code> -> January 5, 2025 14:40</li>
     *     <li>DAYMONTHDAYTIME: <code>DD, MMMM, d, yyyy hh:mm</code> -> Sunday, January 5, 2025 14:40</li>
     *     <li>TIMEUNTIL: <code>x time ago</code> -> 5 seconds ago (same as TIMESINCE)</li>
     *     <li>TIMESINCE: <code>in x time</code> -> in 5 seconds (same as TIMESINCE)</li>
     * </ul>
     *
     * @param unixTime The time as {@link long}
     * @param timestamps The timestamp format as {@link Timestamps}
     * @return The new Discord Time as {@link String}
     */
    public static String convert(long unixTime, @NotNull Timestamps timestamps){
        String discordTime = "";
        switch (timestamps){
            case Timestamps.SHORTDATE -> discordTime = "<t:" + unixTime + ":d>";
            case Timestamps.LONGDATE -> discordTime = "<t:" + unixTime + ":D>";
            case Timestamps.HOURMINUTE -> discordTime = "<t:" + unixTime + ":t>";
            case Timestamps.HOURMINUTESECOND -> discordTime = "<t:" + unixTime + ":t>";
            case Timestamps.MONTHDAYTIME -> discordTime = "<t:" + unixTime + ":f>";
            case Timestamps.DAYMONTHDAYTIME -> discordTime = "<t:" + unixTime + ":F>";
            case Timestamps.TIMEUNTIL, Timestamps.TIMESINCE -> discordTime = "<t:" + unixTime + ":R>";
        }

        return discordTime;
    }
}
