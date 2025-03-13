import java.io.StringReader;

public class InventoryData {
    public static final String CSV_DATA =
            "Date Entered,Stock Label,Brand,Engine Number,Status\n" +
                    "2/1/2023,Old,Honda,142QVTSIUR,On-hand\n" +
                    "2/1/2023,Old,Honda,PZCT1S00XE,Sold\n" +
                    "2/1/2023,Old,Honda,4VBTV8YNM7,Sold\n" +
                    "2/1/2023,Old,Honda,95AN3AWVF4,On-hand\n" +
                    "2/3/2023,Old,Kawasaki,483QHIM661,On-hand\n" +
                    "2/3/2023,Old,Kymco,SPHA17SSEE,On-hand\n" +
                    "2/3/2023,Old,Kymco,0AV7SWGX93,Sold\n" +
                    "2/4/2023,Old,Kymco,QMUB6UYLKL,Sold\n" +
                    "2/4/2023,Old,Honda,V96GMTFFEI,Sold\n" +
                    "2/5/2023,Old,Kawasaki,4J8UA0FMVY,Sold\n" +
                    "2/5/2023,Old,Kawasaki,A8BDL926FA,Sold\n" +
                    "2/5/2023,Old,Kawasaki,X8G5ZZ7A69,Sold\n" +
                    "2/6/2023,Old,Honda,TY5SU0WPDX,On-hand\n" +
                    "2/6/2023,Old,Honda,5Q0EZG7WKB,On-hand\n" +
                    "2/6/2023,Old,Suzuki,9XUOUOJ2XZ,On-hand\n" +
                    "2/6/2023,Old,Kymco,YUL4UTC4FU,On-hand\n" +
                    "2/6/2023,Old,Kymco,2ESQRHAXWG,On-hand\n" +
                    "2/7/2023,Old,Kymco,J8JA99VWZE,Sold\n" +
                    "2/7/2023,Old,Kymco,NS530HOT9H,Sold\n" +
                    "2/7/2023,Old,Suzuki,URIA0XXM05,Sold\n" +
                    "2/7/2023,Old,Yamaha,IDN93SI4KW,Sold\n" +
                    "2/7/2023,Old,Honda,PVAWKD51CE,Sold\n" +
                    "2/7/2023,Old,Honda,K4KHCQAU41,Sold\n" +
                    "2/8/2023,Old,Honda,Z4NY5JGZZT,Sold\n" +
                    "2/8/2023,Old,Honda,IRQACSKUNZ,Sold\n" +
                    "2/8/2023,Old,Yamaha,TMZCTALNDL,Sold\n" +
                    "2/8/2023,Old,Yamaha,DVFUIA0YVB,Sold\n" +
                    "2/8/2023,Old,Kymco,4M793VVAHI,On-hand\n" +
                    "2/8/2023,Old,Suzuki,5N7IQVJ2BA,On-hand\n" +
                    "3/1/2023,New,Suzuki,NO8VW05PU9,On-hand\n" +
                    "3/1/2023,New,Yamaha,NWIP2MQEIN,Sold\n" +
                    "3/1/2023,New,Kawasaki,1HCWCVZSX8,Sold\n" +
                    "3/3/2023,New,Kawasaki,Z46VKPIJBY,Sold\n" +
                    "3/3/2023,New,Kawasaki,LYQVEHJ6IU,Sold\n" +
                    "3/3/2023,New,Yamaha,BVGQQNMATL,Sold\n" +
                    "3/4/2023,New,Kymco,URWMSQZCBU,Sold\n" +
                    "3/4/2023,New,Yamaha,5NGI5UZ8T2,On-hand\n" +
                    "3/5/2023,New,Honda,W2UYM0EIRS,On-hand\n" +
                    "3/5/2023,New,Honda,AITLTSJUK2,On-hand\n" +
                    "3/5/2023,New,Yamaha,45CNYV7IFF,On-hand\n" +
                    "3/6/2023,New,Kymco,MXS36NKV96,Sold\n" +
                    "3/6/2023,New,Kymco,PWM3MJWPYE,Sold\n" +
                    "3/6/2023,New,Kymco,5I80N9HB7W,Sold\n" +
                    "3/6/2023,New,Yamaha,D01JMJL9PG,On-hand\n" +
                    "3/6/2023,New,Suzuki,1R88BOJW8W,On-hand\n" +
                    "3/7/2023,New,Suzuki,LAMH9Y1YD6,On-hand\n" +
                    "3/7/2023,New,Yamaha,02G7NJCRGS,On-hand\n" +
                    "3/7/2023,New,Kawasaki,392XSUBMUW,On-hand";

    /**
     * Returns the CSV data as a StringReader that can be used like a file
     * @return StringReader containing the CSV data
     */
    public static StringReader getCSVAsReader() {
        return new StringReader(CSV_DATA);
    }
}
