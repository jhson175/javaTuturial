package com.cjhv.mvno.drools.util;


import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class Util
{
    public Util()
    {
    }

    /**
     * 반올림 또는 내림 하는 메소드 Parameter : 입력값, 소수점 이하 자리수, 올림내림 구분 round_type :
     * java.math.BigDecimal.ROUND_CEILING java.math.BigDecimal.ROUND_DOWN
     * java.math.BigDecimal.ROUND_FLOOR java.math.BigDecimal.ROUND_HALF_DOWN
     * java.math.BigDecimal.ROUND_HALF_EVEN java.math.BigDecimal.ROUND_HALF_UP
     * java.math.BigDecimal.ROUND_UP
     */
    public static float round( float f, int len, int round_type )
    {
        float retval = 0F;
        try
        {
            retval = ( new java.math.BigDecimal( f ).setScale( len, round_type ) ).floatValue();
        }
        catch ( NumberFormatException nfe )
        {
        }
        return retval;
    }

    /**
     * 분모가 0이면 ArithmeticException이 발생하는 것을 방지하기 위한 메소드 Parameter : 분자, 분모
     */
    public static float division( float son, float mother )
    {
        float retval = 0F;
        if ( mother == 0 )
        {
            retval = son;
        }
        else
        {
            retval = ( son / mother );
            retval = round( retval, 2, java.math.BigDecimal.ROUND_HALF_UP );
        }
        return retval;
    }

    /**
     * 8859_1 -> KSC5601
     */
    public static String toEN( String ko )
    {
        String new_str = null;
        try
        {
            if ( ko != null )
            {
                new_str = new String( ko.getBytes( "KSC5601" ), "8859_1" );
            }
        }
        catch ( UnsupportedEncodingException e )
        {
        }
        return new_str;
    }

    /**
     * KSC5601 -> 8859_1
     */
    public static String toKSC( String en )
    {
        String new_str = null;
        try
        {
            if ( en != null )
            {
                new_str = new String( en.getBytes( "8859_1" ), "KSC5601" );
            }
        }
        catch ( UnsupportedEncodingException e )
        {
        }
        return new_str;
    }

    /**
     * KSC5601 -> 8859_1 return : null일 경우, ""을 return
     */
    public static String toKSC2( String en )
    {
        return en;
        /*
         * String new_str = null; try { if(en != null){ new_str = new String (en.getBytes("8859_1"),
         * "KSC5601"); } else { new_str = ""; } } catch (UnsupportedEncodingException e) {} return
         * new_str;
         */
    }

    /**
     * 8859 -> UTF-8 return : null일 경우, ""을 return
     */
    public static String toEucKr( String en )
    {
        return en;
        /*
         * String new_str = null; try { if(en != null){ new_str = new String (en.getBytes("8859_1"),
         * "UTF-8"); } else { new_str = ""; } } catch (UnsupportedEncodingException e) {} return
         * new_str;
         */
    }

    /**
     * space -> &nbsp;
     */
    public static String spaceToNBSP( String source )
    {
        StringBuffer sb = new StringBuffer( source );
        StringBuffer result = new StringBuffer();
        String ch = null;
        for ( int i = 0; i < source.length(); i++ )
        {
            if ( Character.isSpaceChar( sb.charAt( i ) ) )
                ch = "&nbsp;";
            else
                ch = String.valueOf( sb.charAt( i ) );
            result.append( ch );
        }
        return result.toString();
    }

    /*************************************************************************************
     * 'yyyy-MM-dd 형태를 yyyymmdd형의 int로 반환 inserted by jungeun, Kim
     ************************************************************************************/
    public static int dateFormat2( String date_s )
    {
        date_s = date_s.substring( 0, 4 ) + date_s.substring( 5, 7 ) + date_s.substring( 8, 10 );
        int intDate = Integer.parseInt( date_s );
        return intDate;
    }

    /**
     * 현재날짜를 지정된 포맷으로 만들어 리턴
     */
    public static String dateFormat( String format )
    {
        String date = null;
        try
        {
            TimeZone tz = new SimpleTimeZone( 9 * 60 * 60 * 1000, "KST" );
            TimeZone.setDefault( tz );
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat( format );
            date = sdf.format( d );
        }
        catch ( Exception kkkk )
        {
        }
        return date;
    }

    /**
     * 현재날짜를 YYYY-MM-DD 형식으로 만들어 리턴
     */
    public static String getDate()
    {
        String ch = null;
        try
        {
            TimeZone tz = new SimpleTimeZone( 9 * 60 * 60 * 1000, "KST" );
            TimeZone.setDefault( tz );
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy'-'MM'-'dd" );
            ch = sdf.format( d );
        }
        catch ( Exception dfdf )
        {
        }
        return ch;
    }

    /**
     * 입력받은 Date 오브젝트를 YYYY-MM-DD 형식의 String 으로 만들어 리턴
     */
    public static String dateToString( Date d )
    {
        String ch = null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy'-'MM'-'dd" );
            ch = sdf.format( d );
        }
        catch ( Exception dfdf )
        {
        }
        return ch;
    }

    /**
     * 입력받은 Date 오브젝트를 특정한 포멧 형식의 String 으로 만들어 리턴
     */
    public static String dateToString( Date d, String format )
    {
        String ch = null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat( format );
            ch = sdf.format( d );
        }
        catch ( Exception dfdf )
        {
        }
        return ch;
    }

    /**
     * 입력받은 String오브젝트를 특정한 포멧 형식의 Date 형으로 만들어 리턴 예) stringToDate("2001-06-01", "yyyy-'-'MM'-'dd")
     */
    public static java.util.Date stringToDate( String d, String format )
    {
        java.util.Date ch = null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat( format );
            ch = sdf.parse( d );
        }
        catch ( Exception dfdf )
        {
        }
        return ch;
    }

    public static java.util.Date dateFormat( Date d, String format )
    {
        String str = dateToString( d, format );
        return stringToDate( str, format );
    }

    public static String stringToDateString( String d, String format )
    {
        if ( d == null || d.length() < 6 )
            return "";
        if ( d.length() < 7 )
        {
            d = d.substring( 0, 4 ) + "-" + d.substring( 4, 6 ) + "-" + d.substring( 6 );
        }
        return dateToString( stringToDate( d, "yyyy'-'MM'-'dd" ), format );
    }

    /**
     * 현재날짜를 YYYY-MM-DD HH:MM:SS 형식으로 만들어 리턴
     */
    public static String getTime()
    {
        String ch = null;
        try
        {
            TimeZone tz = new SimpleTimeZone( 9 * 60 * 60 * 1000, "KST" );
            TimeZone.setDefault( tz );
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy'-'MM'-'dd'-'HH'-'mm'-'ss" );
            ch = sdf.format( d );
        }
        catch ( Exception dfdf )
        {
        }
        return ch;
    }

    /**
     * 현재 날짜를 타입에 따라 년, 월,일 만을 리턴
     */
    public static String getDate( int type )
    {
        String ch = getDate();
        String format = null;
        switch ( type )
        {
            case 1:
                format = ch.substring( 0, 4 );
                break;
            case 2:
                format = ch.substring( 5, 7 );
                break;
            case 4:
                format = ch.substring( 0, 4 ) + ch.substring( 5, 7 ) + ch.substring( 8, 10 );
                break;
            default:
                format = ch.substring( 8, 10 );
                break;
        }
        return format;
    }

    /**
     * Encode URL -> Decode URL : jdk 1.x 버전에서는 java.net.URLDecoder 클래스를 지원하지 않기 때문에 jdk1.x 에서 URL
     * 디코딩시 사용.
     */
    public static String decodeURL( String s )
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream( s.length() );
        for ( int i = 0; i < s.length(); i++ )
        {
            int c = (int) s.charAt( i );
            if ( c == '+' )
                out.write( ' ' );
            else if ( c == '%' )
            {
                int c1 = Character.digit( s.charAt( ++i ), 16 );
                int c2 = Character.digit( s.charAt( ++i ), 16 );
                out.write( (char) ( c1 * 16 + c2 ) );
            }
            else
                out.write( c );
        }
        return out.toString();
    }

    /**
     * 스트링을 int로 변환. NumberFormatException, NullPointerException 을 검사하기 위해, Exception 발생시 0 리턴
     */
    public static int parseInt( String str )
    {
        int parseInt = 0;
        try
        {
            parseInt = Integer.parseInt( str );
        }
        catch ( Exception nf )
        {
        }
        return parseInt;
    }

    /**
     * 스트링을 int로 변환. NumberFormatException, NullPointerException 을 검사하기 위해, Exception 발생시 0 리턴
     */
    public static float parseFloat( String str )
    {
        float parseFloat = 0.0f;
        try
        {
            parseFloat = Float.parseFloat( str );
        }
        catch ( Exception nf )
        {
        }
        return parseFloat;
    }

    /**
     * 스트링을 int로 변환. NumberFormatException, NullPointerException 을 검사하기 위해, Exception 발생시 0 리턴
     */
    public static long parseLong( String str )
    {
        long parseLong = 0L;
        try
        {
            parseLong = Long.parseLong( str );
        }
        catch ( Exception nf )
        {
        }
        return parseLong;
    }

    /**
     * 스트링을 int로 변환. NumberFormatException, NullPointerException을 검사하기 위해, Exception 발생시 default
     * value 리턴
     */
    public static int parseInt( String str, int def )
    {
        int parseInt = 0;
        try
        {
            parseInt = Integer.parseInt( str );
        }
        catch ( Exception nf )
        {
            parseInt = def;
        }
        return parseInt;
    }

    /**
     * 파라미터의 값이 "" 일때 null 을 리턴하게끔 하는 메소드 URL에서 파라미터를 받을때 name 값이 존재하면 "" 이 넘어올 수 있기 때문에 null 값을
     * 검사할때 사용.
     */
    public static String checkNull( String key )
    {
        String value = key;
        if ( key == null || key.equals( "" ) )
            value = null;
        return value;
    }

    /**
     * 날짜형을 년, 월, 일로 나누어 리턴하는 메소드
     */
    public static String parseDate( String date, int type )
    {
        String parse = "";
        if ( date != null && date.length() == 8 )
        {
            switch ( type )
            {
                case 1: // 년
                    parse = date.substring( 0, 4 );
                    break;
                case 2: // 월
                    parse = date.substring( 4, 6 );
                    break;
                default: // 일
                    parse = date.substring( 6, 8 );
                    break;
            }
        }
        return parse;
    }

    public static String parseMMDD( String date, String delim )
    {
        String parse = null;
        if ( date != null && date.length() == 8 )
        {
            parse = date.substring( 4, 6 ) + delim + date.substring( 6, 8 );
        }
        return parse;
    }

    public static String parseDate( String date, String delim )
    {
        String parse = null;
        if ( date != null && date.length() == 8 )
        {
            parse = date.substring( 0, 4 ) + delim + date.substring( 4, 6 ) + delim + date.substring( 6, 8 );
        }
        return parse;
    }

    // YYYY-MM-DD 또는 YYYY/MM/DD인 경우 YYYY년MM월DD일로 반환
    public static String parseKorDate( String tdate, int type )
    {
        String parse = null;
        if ( tdate != null && tdate.length() == 10 )
        {
            switch ( type )
            {
                case 1: // 년 월 일
                    parse = Util.parseInt( tdate.substring( 0, 4 ) ) + "년 " + Util.parseInt( tdate.substring( 5, 7 ) )
                        + "월 " + Util.parseInt( tdate.substring( 7, 9 ) ) + "일";
                    break;
                case 2: // 월 일
                    parse = Util.parseInt( tdate.substring( 5, 7 ) ) + "월 " + Util.parseInt( tdate.substring( 8, 10 ) )
                        + "일";
                    break;
                default: // 일
                    parse = Util.parseInt( tdate.substring( 8, 10 ) ) + "일";
                    break;
            }
        }
        return parse;
    }

    /**
     * &nbsp -> ""
     */
    public static String nbspToSpace( String nbsp )
    {
        String value = "";
        if ( nbsp != null && !nbsp.trim().equals( "&nbsp;" ) )
        {
            value = nbsp;
        }
        return value;
    }

    /**
     * null -> "" 로 변환하는 메소드 : 데이터 수정시 데이터 베이스로 부터 읽은 값이 null이면 수정 폼에 null이 들어가므로 이값을 변환하는 메소드 : 데이터
     * 수정시 null 값을 수정 폼에 setting 할때 사용하면 유용한 메소드.
     */
    public static String nullToString( String str )
    {
        String value = str;
        if ( str == null )
        {
            value = "";
        }
        return value;
    }

    /**
     * null or "" --> "&nbsp;" : HTML에서 테이블의 셀에 "" 이 들어가면 테이블이 깨지므로(netscape) 공백문자(&nbsp;)로 대치하는 메소드
     */
    public static String nullToNbsp( String str )
    {
        String value = str;
        if ( str == null || str.equals( "" ) )
        {
            value = "&nbsp;";
        }
        return value;
    }

    /**
     * Object 형을 String 으로 변환 : Object 가 null 일때 NullpointerException 을 검사하기 위해서 사용. : ResultSet
     * 으로부터 getObject()로 값을 가져왔을경우 String으로 변환할때 사용하면 유용한 메소드.
     * 
     * @author : 심재진
     * @E-mail : sim11@miraenet.com
     */
    public static String toString( Object obj )
    {
        String str = "";
        if ( obj != null )
            str = obj.toString();
        return str;
    }

    /**
     * 전체 데이터수로 마지막페이지를 계산해오기 위한 Method. : 게시판 목록 같은 경우 몇 페이지 까지 있는지 계산할때 사용하면 유용한 메소드
     */
    public static int getPageCount( int token, int allPage )
    {
        int lastPage = (int) ( ( ( allPage - 1 ) / token ) + 1 );
        return lastPage;
    }

    /**
     * 데이터의 번호를 주기위해 번호를 계산해오는 Method : 게시판 목록 같은 경우 페이지별 데이터의 번호를 계산해 주는 메소드.
     */
    public static int getDataNum( int token, int page, int allPage )
    {
        if ( allPage <= token )
        {
            return allPage;
        }
        int num = allPage - ( token * page ) + token;
        return num;
    }

    /**
     * 문자열의 값이 null 이거나 ""이면 default 값을 리턴하는 메소드
     */
    public static String getString( String line, String def )
    {
        if ( line == null || line.equals( "" ) )
            return def;
        return line;
    }

    /**
     * 현재일이 특정 기간에 속해있는지 검사하는 메소드 : argument : 시작일(yyyy-mm-dd), 종료일(yyyy-mm-dd)
     */
    public static boolean betweenDate( String first, String second )
    {
        boolean flag = false;
        java.util.Date start = null;
        java.util.Date end = null;
        java.util.Date current = null;
        DateFormat df = DateFormat.getDateInstance( DateFormat.MEDIUM, Locale.KOREA );
        try
        {
            start = df.parse( first );
            end = df.parse( second );
            current = df.parse( getDate() );
        }
        catch ( Exception pe )
        {
            return false;
        }
        if ( ( start.before( current ) && end.after( current ) ) || start.equals( current ) || end.equals( current ) )
            flag = true;
        return flag;
    }

    public static String reqTime()
    {
        String ch = null;
        try
        {
            TimeZone tz = new SimpleTimeZone( 9 * 60 * 60 * 1000, "KST" );
            TimeZone.setDefault( tz );
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy'-'MM'-'dd' 'HH" );
            ch = sdf.format( d );
        }
        catch ( Exception dfdf )
        {
        }
        return ch;
    }

    /*************************************************************************************
     * * 현재일이 종료일 이후에 있는지 검사**
     ************************************************************************************/
    public static boolean afterDate( String second )
    {
        boolean flag = false;
        java.util.Date end = null;
        java.util.Date current = null;
        DateFormat df = DateFormat.getDateInstance( DateFormat.MEDIUM, Locale.KOREA );
        try
        {
            end = df.parse( second );
            current = df.parse( Util.reqTime() );
        }
        catch ( Exception pe )
        {
            System.err.println( "afterDate 에러입니다." + pe.getMessage() );
            return false;
        }
        if ( end.before( current ) || end.equals( current ) )
            flag = true;
        return flag;
    }

    /*************************************************************************************
     * * 현재일이 시작일 이전에 있는지 검사**
     ************************************************************************************/
    public static boolean beforeDate( String first )
    {
        boolean flag = false;
        java.util.Date start = null;
        java.util.Date current = null;
        DateFormat df = DateFormat.getDateInstance( DateFormat.MEDIUM, Locale.KOREA );
        try
        {
            start = df.parse( first );
            current = df.parse( Util.reqTime() );
        }
        catch ( Exception pe )
        {
            System.err.println( "beforeDate 에러입니다." + pe.getMessage() );
        }
        if ( start.after( current ) || start.equals( current ) )
            flag = true;
        return flag;
    }

    /**
     * 가격등의 값을 3자리 마다 comma(,)로 분리하여 리턴
     */
    public static String parseDecimal( double unFormat )
    {
        DecimalFormat df = new DecimalFormat( "###,###.##" );
        String format = df.format( unFormat );
        return format;
    }

    /**
     * 가격등의 값을 3자리 마다 comma(,)로 분리하여 리턴
     */
    public static String parseDecimal( double unFormat, String foramt )
    {
        DecimalFormat df = new DecimalFormat( foramt );
        String format = df.format( unFormat );
        return format;
    }

    /**
     * Object 의 복제하여 주는 메소드 일반적으로 java.lang.Object.clone() 메소드를 사용하여 Object를 복제하면 Object내에 있는
     * Primitive type을 제외한 Object field들은 복제가 되는 것이 아니라 같은 Object의 reference를 갖게 된다. 그러나 이 Method를
     * 사용하면 각 field의 동일한 Object를 새로 복제(clone)하여 준다. java.lang.reflect API 를 사용하였음.
     */
    public static Object clone( Object object )
    {
        Class c = object.getClass();
        Object newObject = null;
        try
        {
            newObject = c.newInstance();
        }
        catch ( Exception e )
        {
            return null;
        }
        java.lang.reflect.Field[] field = c.getFields();
        for ( int i = 0; i < field.length; i++ )
        {
            try
            {
                Object f = field[i].get( object );
                field[i].set( newObject, f );
            }
            catch ( Exception e )
            {
            }
        }
        return newObject;
    }

    /**
     * 디버깅시 Servlet 에서는 PrintWriter 를 넣어서 쉽게 디버깅을 할 수 있었지만 JSP 에서 처럼 PrintWriter가 없을때 디버깅을 쉽게 하기 위하여
     * 메세지를 문자열로 만들어 리턴하게 하였음.
     */
    public static String getStackTrace( Throwable e )
    {
        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
        java.io.PrintWriter writer = new java.io.PrintWriter( bos );
        e.printStackTrace( writer );
        writer.flush();
        return bos.toString();
    }

    /**
     * 특정 문자열을 다른 문자열로 대체하는 메소드
     */
    public static String replace( String line, String oldString, String newString )
    {
        if ( line == null )
            return line;
        int index = 0;
        while ( ( index = line.indexOf( oldString, index ) ) >= 0 )
        {
            line = line.substring( 0, index ) + newString + line.substring( index + oldString.length() );
            index += newString.length();
        }
        return line;
    }



    /**
     * 메일에 대한 익명 처리
     */
    public static String MailPaste( String usermail )
    {
        String rtnValue = "";
        int eint = usermail.indexOf( "@" );
        String hiden = "";
        if ( eint > 3 )
        {
            for ( int i = 3; i < eint; i++ )
            {
                hiden = hiden + "*";
            }
            rtnValue = usermail.substring( 0, 3 ) + hiden + usermail.substring( eint, usermail.length() );
        }
        else
        {
            rtnValue = usermail;
        }
        return rtnValue;
    }

    /**
     * 문자열을 substring할때 문자열 길이를 넘어설 경우 "" 를리턴하는 메소드
     */
    public static String substring( String str, int start, int end )
    {
        String val = null;
        try
        {
            val = str.substring( start, end );
        }
        catch ( Exception e )
        {
            return "";
        }
        return val;
    }

    /**
     * ','로 분리되어 있는 문자열을 분리하여 Return List에서 일괄 삭제시 ID값을 일괄로 받아와서 Parsing... written by Blue.
     */
    public static String[] getItemArray( String src )
    {
        String[] retVal = null;
        if ( src.length() == 0 )
            return null;
        int nitem = 1;
        for ( int i = 0; i < src.length(); i++ )
            if ( src.charAt( i ) == ',' )
                nitem++;
        retVal = new String[nitem];
        int ep = 0;
        int sp = 0;
        for ( int i = 0; i < nitem; i++ )
        {
            ep = src.indexOf( ",", sp );
            if ( ep == -1 )
                ep = src.length();
            retVal[i] = new String( src.substring( sp, ep ) );
            sp = ep + 1;
        }
        return retVal;
    }

    public static String[] getItemArray( String src, char parser )
    {
        String[] retVal = null;
        if ( src.length() == 0 )
            return null;
        int nitem = 1;
        for ( int i = 0; i < src.length(); i++ )
            if ( src.charAt( i ) == parser )
                nitem++;
        retVal = new String[nitem];
        int ep = 0;
        int sp = 0;
        for ( int i = 0; i < nitem; i++ )
        {
            ep = src.indexOf( parser, sp );
            if ( ep == -1 )
                ep = src.length();
            retVal[i] = new String( src.substring( sp, ep ) );
            sp = ep + 1;
        }
        return retVal;
    }

    public static String[] getItemArray( String src, String parser )
    {
        char sparser = parser.charAt( 0 );
        String[] retVal = null;
        if ( src.length() == 0 )
            return null;
        int nitem = 1;
        for ( int i = 0; i < src.length(); i++ )
            if ( src.charAt( i ) == sparser )
                nitem++;
        retVal = new String[nitem];
        int ep = 0;
        int sp = 0;
        for ( int i = 0; i < nitem; i++ )
        {
            ep = src.indexOf( parser, sp );
            if ( ep == -1 )
                ep = src.length();
            retVal[i] = new String( src.substring( sp, ep ) );
            sp = ep + 1;
        }
        return retVal;
    }

    /**
     * 특정 날짜를 'YYYY/MM/DD' 형식으로 return
     */
    public static String returnDate( String date )
    {
        if ( date == null )
        {
            return "";
        }
        else if ( date.length() < 8 )
        {
            return date;
        }
        String year = date.substring( 0, 4 );
        String month = date.substring( 4, 6 );
        String day = date.substring( 6, 8 );
        return year + "/" + month + "/" + day;
    }

    /**
     * 윤년 check Method... : 올해가 윤년인지를 check하여 booelan으로 return;
     */
    public static boolean checkEmbolism( int year )
    {
        int remain = 0;
        int remain_1 = 0;
        int remain_2 = 0;
        remain = year % 4;
        remain_1 = year % 100;
        remain_2 = year % 400;
        // the ramain is 0 when year is divided by 4;
        if ( remain == 0 )
        {
            // the ramain is 0 when year is divided by 100;
            if ( remain_1 == 0 )
            {
                // the remain is 0 when year is divided by 400;
                if ( remain_2 == 0 )
                    return true;
                else
                    return false;
            }
            else
                return true;
        }
        return false;
    }

    /**
     * 각 월의 마지막 일을 return 해당 월의 마지막일을 return. 윤년 check후 해당 일을 return
     */
    public static int getMonthDate( int year, int month )
    {
        int[] dateMonth = new int[12];
        dateMonth[0] = 31;
        dateMonth[1] = 28;
        dateMonth[2] = 31;
        dateMonth[3] = 30;
        dateMonth[4] = 31;
        dateMonth[5] = 30;
        dateMonth[6] = 31;
        dateMonth[7] = 31;
        dateMonth[8] = 30;
        dateMonth[9] = 31;
        dateMonth[10] = 30;
        dateMonth[11] = 31;
        if ( Util.checkEmbolism( year ) )
            dateMonth[1] = 29;
        return dateMonth[month - 1];
    }

    /**
     * 한 자리 숫자에 앞에 '0'을 붙여 String으로 return하는 메소드 : argument : str, int
     */
    public static String addZero( String str )
    {
        return ( Integer.toString( Integer.parseInt( str ) + 100 ) ).substring( 1, 3 );
    }

    public static String addZero2( int num )
    {
        return ( Integer.toString( num + 100 ) ).substring( 1, 3 );
    }

    /**
     * 특정 디렉토리의 파일 삭제 : argument : 파일위치 + 파일명
     */
    public static void DeleteFile( String path )
    {
        File f = new File( path );
        f.delete();
    }

    /**
     * 현재 년/월/일
     */
    public static String[] GetTodayString()
    {
        java.util.SimpleTimeZone kst = new java.util.SimpleTimeZone( 9 * 60 * 60 * 1000, "KST" );
        java.util.Calendar cal = java.util.Calendar.getInstance( kst );
        String[] today = new String[3];
        int year = cal.get( Calendar.YEAR );
        today[0] = Integer.toString( year );
        if ( ( cal.get( Calendar.MONTH ) + 1 ) < 10 )
            today[1] = "0" + ( cal.get( Calendar.MONTH ) + 1 );
        else
            today[1] += ( cal.get( Calendar.MONTH ) + 1 );
        if ( cal.get( Calendar.DAY_OF_MONTH ) < 10 )
            today[2] = "0" + cal.get( Calendar.DAY_OF_MONTH );
        else
            today[2] += cal.get( Calendar.DAY_OF_MONTH );
        return today;
    }

    public static int[] GetTodayInt()
    {
        java.util.SimpleTimeZone kst = new java.util.SimpleTimeZone( 9 * 60 * 60 * 1000, "KST" );
        java.util.Calendar cal = java.util.Calendar.getInstance( kst );
        int[] today = new int[3];
        today[0] = cal.get( Calendar.YEAR );
        today[1] = cal.get( Calendar.MONTH ) + 1;
        today[2] = cal.get( Calendar.DAY_OF_MONTH );
        return today;
    }

    /*
     * Date : yyyymmdd -> yyyy-mm-dd
     */
    public static String makeDateString( String date )
    {
        if ( date.length() > 9 )
            return date;
        return date.substring( 0, 4 ) + "-" + date.substring( 4, 6 ) + "-" + date.substring( 6 );
    }

    /*
     * Date : yyyymmdd -> yyyy-mm-dd
     */
    public static String makeDateString2( String date )
    {
        return date.substring( 0, 4 ) + "." + date.substring( 5, 7 ) + "." + date.substring( 8, 10 );
    }

    /*
     * Date : yyyy-mm-dd -> yyyymmdd
     */
    public static int makeDateInt( String date )
    {
        return Integer.parseInt( date.substring( 0, 4 ) + date.substring( 5, 7 ) + date.substring( 8 ) );
    }

    /**
     * <pre>
     *  text를 format에 맞추어 출력한다.
     *  getFormatedText(&quot;0193372412&quot;,&quot;???-???-????&quot;) ---&gt;&gt; 019-337-2412로 출력
     * </pre>
     * 
     * @param String text
     * @param String format
     * @return String
     */
    public static String getFormatedText( String text, String format )
    {
        String rtn;
        int start, i, j, len;
        int tCount, fCount;
        tCount = text.length();
        fCount = format.length();
        rtn = "";
        if ( text.equals( "" ) )
            return rtn;
        if ( text.equals( "&nbsp;" ) )
            return "&nbsp;";
        // text가 01252412 이고 format 이 ????-???? 이면 0125-2412로 출력
        // text에서 -를 제거한다.
        for ( i = 0; i < tCount; ++i )
        {
            if ( !text.substring( i, i + 1 ).equals( "-" ) )
                rtn = rtn + text.substring( i, i + 1 );
        }
        text = rtn;
        tCount = text.length();
        // 포멧에서 ?의 count
        len = 0;
        for ( j = 0; j < fCount; ++j )
        {
            if ( format.substring( j, j + 1 ).equals( "?" ) )
                ++len;
        }
        // text의 길이가 len보다 작으면 앞에 0를 붙인다.
        if ( tCount < len )
        {
            for ( i = 0; i < ( len - tCount ); ++i )
            {
                text = '0' + text;
            }
            tCount = len;
        }
        rtn = "";
        start = 0;
        for ( i = 0; i < tCount; ++i )
        {
            for ( j = start; j < fCount; ++j )
            {
                if ( format.substring( j, j + 1 ).equals( "?" ) )
                {
                    rtn = rtn + text.substring( i, i + 1 );
                    start = start + 1;
                    break;
                }
                else
                {
                    rtn = rtn + format.substring( j, j + 1 );
                    start = start + 1;
                }
            }
        }
        return rtn + format.substring( start );
    }

    public static String lectureDate( String sdate, String edate )
    {
        String retval = null;
        boolean s = sdate.equals( "00000000" );
        boolean e = edate.equals( "00000000" );
        if ( s )
        {
            if ( e )
            {
                retval = "제한없음";
            }
            else
            {
                retval = edate.substring( 4, 6 ) + "/" + edate.substring( 6 ) + " 까지";
            }
        }
        else
        {
            if ( e )
            {
                retval = sdate.substring( 4, 6 ) + "/" + sdate.substring( 6 ) + " 부터";
            }
            else
            {
                retval = sdate.substring( 4, 6 ) + "/" + sdate.substring( 6 ) + " ~ " + edate.substring( 4, 6 ) + "/"
                    + edate.substring( 6 );
            }
        }
        return retval;
    }

    public static void MoveFiles( String[] filename, String src, String des )
    {
        // 파일을 이동한다
        if ( filename != null && filename.length > 0 )
        {
            for ( int i = 0; i < filename.length; i++ )
            {
                File sf = new File( src + Util.toKSC( filename[i] ) );
                File desDir = new File( des );
                if ( !desDir.exists() )
                {
                    desDir.mkdirs();
                }
                File df = new File( des + Util.toKSC( filename[i] ) );
                sf.renameTo( df );
            }
        }
    }

    public static String sNull( String str )
    {
        if ( str == null )
        {
            return "";
        }
        else
        {
            return str;
        }
    }

    /*
     * str,str,str 이러한 형식으로 묶인것을 풀어서 쿼리에서 사용할 수 있도록 한다.
     */
    public static String InnerQuery( String maskStr, String cond1, String cond2, String sourceStr, String delim )
    {
        StringBuffer innerQuery = new StringBuffer();
        StringTokenizer st = new StringTokenizer( sourceStr, delim );
        while ( st.hasMoreTokens() )
        {
            innerQuery.append( maskStr );
            if ( cond1.equals( "like" ) )
            {
                innerQuery.append( " like '" );
                innerQuery.append( st.nextToken() );
                innerQuery.append( "%' " );
            }
            else
            {
                innerQuery.append( " = '" );
                innerQuery.append( st.nextToken() );
                innerQuery.append( "' " );
            }
            innerQuery.append( cond2 );
            innerQuery.append( " " );
        }
        innerQuery.delete( innerQuery.length() - cond2.length() - 1, innerQuery.length() );
        return innerQuery.toString();
    }

    // 두개의 날짜 받아서 날짜 차이 계산.."20031212,20031214"
    public static int getDiff_day( String firstday, String lastday )
    {
        int mon[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int year = 0;
        int month = 0;
        int day = 0;
        int Year = 0;
        int Month = 0;
        int Day = 0;
        int yetDay = 0;
        if ( firstday.length() > 8 || firstday.length() < 8 || lastday.length() < 8 || lastday.length() > 8 )
            return 99999;
        year = Integer.parseInt( firstday.substring( 0, 4 ) );
        month = Integer.parseInt( firstday.substring( 4, 6 ) );
        day = Integer.parseInt( firstday.substring( 6, 8 ) );
        Year = Integer.parseInt( lastday.substring( 0, 4 ) );
        Month = Integer.parseInt( lastday.substring( 4, 6 ) );
        Day = Integer.parseInt( lastday.substring( 6, 8 ) );
        // 년도가 같을때 날짜 계산
        if ( year == Year )
        {
            for ( int i = month; i < Month; i++ )
            {
                // 윤달구하는 소스
                if ( ( ( year % 400 == 0 ) || ( ( year % 100 != 0 ) && ( year % 4 == 0 ) ) ) && i == 2 )
                    yetDay++;
                yetDay += mon[i];
            }
        }
        // 년도가 차이날 경우 날짜 계산
        else
        {
            for ( int i = year; i <= Year; i++ )
            {
                if ( i == year )
                {
                    for ( int j = month + 1; j <= 12; j++ )
                    {
                        if ( ( ( year % 400 == 0 ) || ( ( year % 100 != 0 ) && ( year % 4 == 0 ) ) ) && j == 2 )
                            yetDay++;
                        yetDay += mon[j];
                    }
                }
                else if ( i == Year )
                {
                    for ( int j = 1; j <= Month; j++ )
                    {
                        if ( ( ( year % 400 == 0 ) || ( ( year % 100 != 0 ) && ( year % 4 == 0 ) ) ) && j == 2 )
                            yetDay++;
                        yetDay += mon[j];
                    }
                }
                else
                {
                    for ( int j = 1; j <= 12; j++ )
                    {
                        if ( ( ( year % 400 == 0 ) || ( ( year % 100 != 0 ) && ( year % 4 == 0 ) ) ) && j == 2 )
                            yetDay++;
                        yetDay += mon[j];
                    }
                }
            }
        }
        yetDay += ( Day - day );
        // return Month;
        // 남은 날짜를 반환
        return yetDay;
    }

 
    public static String getListAtt( List list, String searchStr )
    {
        String selectValue = "";
        Map insertMap = null;
        Iterator it = list.iterator();
        while ( it.hasNext() )
        {
            insertMap = (Map) it.next();
            selectValue = (String) insertMap.get( searchStr );
        }
        return selectValue;
    }



    /**
     * @Taskunit Null 체크 -> 변환
     * @param String
     * @return String
     */
    public static String sNull( String strVal, String strValChg )
    {
        if ( strVal == null )
        {
            return strValChg;
        }
        else
        {
            return strVal;
        }
    }

    /**
     * @Taskunit Null 체크 -> 변환
     * @param String
     * @return String
     */
    public static String formatNull( String strVal, String strValChg )
    {
        if ( strVal == null )
        {
            return strValChg;
        }
        else if ( strVal.trim().equals( "" ) )
        {
            return strValChg;
        }
        else
        {
            return strVal;
        }
    }

    /**
     * @Taskunit 문자열 자르기(byte)
     * @param String
     * @param int length
     * @return String
     */
    public static String cutString( String s, int cutlen )
    {
        if ( s == null )
            return null;
        byte[] ab = s.getBytes();
        int i;
        int slen;
        int cnt;
        slen = ab.length;
        if ( slen <= cutlen )
            return s;
        cnt = 0;
        for ( i = 0; i < cutlen; i++ )
        {
            if ( ( ( (int) ab[i] ) & 0xff ) > 0x80 )
            {
                cnt++;
            }
        }
        // 한글 깨짐 현상 방지 (영문일 경우 -1 만큼 자름)
        if ( ( cnt % 2 ) == 1 )
        {
            i--;
        }
        return new String( ab, 0, i );
    }

    /**
     * @Taskunit 문자열 자르기(byte) (시작지점, 길이)
     * @param String
     * @param int idx (시작지점)
     * @param int length (길이)
     * @return String
     */
    public static String cutString( String s, int idx, int cutlen )
    {
        if ( s == null )
            return null;
        byte[] ab = s.getBytes();
        int i;
        int slen;
        int cnt;
        slen = ab.length;
        if ( slen <= cutlen )
            return s;
        cnt = 0;
        for ( i = 0; i < cutlen; i++ )
        {
            if ( ( ( (int) ab[i] ) & 0xff ) > 0x80 )
            {
                cnt++;
            }
        }
        // 한글 깨짐 현상 방지 (영문일 경우 -1 만큼 자름)
        /*
         * if( (cnt % 2) == 1 ) { i--; }
         */
        return new String( ab, idx, i );
    }

    

    /**
     * @Taskunit 문자열 자르기(byte) (시작지점, 길이)
     * @param String
     * @param int idx (시작지점)
     * @param int length (길이)
     * @return String
     */
    public static String cutByteString( byte[] ab, int idx, int cutlen )
    {
        if ( ab == null )
            return null;
        int i;
        int slen;
        int cnt;
        slen = ab.length;
        if ( slen <= cutlen )
            return new String( ab );
        cnt = 0;
        for ( i = 0; i < cutlen; i++ )
        {
            if ( ( ( (int) ab[i] ) & 0xff ) > 0x80 )
            {
                cnt++;
            }
        }
        // 한글 깨짐 현상 방지 (영문일 경우 -1 만큼 자름)
        /*
         * if( (cnt % 2) == 1 ) { i--; }
         */
        return new String( ab, idx, i );
    }

    /**
     * @Taskunit 문자열 byte 길이 구하기
     * @param String
     * @param int length
     * @return String
     */
    public static int byteLength( String strVal )
    {
        if ( strVal == null )
            return 0;
        // 문자열 byte 길이 구하기
        byte[] bt = strVal.getBytes();
        int btLen = bt.length;
        return btLen;
    }

    /**
     * @Taskunit strin 길이(byte) 검사
     * @param String : 값
     * @param String : 부등호(=, ==, <, <=, >=, <>, !=)
     * @param int : 길이
     * @return boolean
     */
    public static boolean isFormatLength( String strVal, String sign, int len )
    {
        // 문자열 byte 길이 구하기
        int byteLen = Util.byteLength( strVal );
        boolean rtn = false;
        // System.out.println("**** byteLen("+ len +")= " + byteLen + "||strVal="+strVal +
        // "||");
        if ( "=".equals( sign ) || "==".equals( sign ) )
        {
            if ( byteLen == len )
                rtn = true;
        }
        else if ( "<".equals( sign ) )
        {
            if ( byteLen < len )
                rtn = true;
        }
        else if ( ">".equals( sign ) )
        {
            if ( byteLen > len )
                rtn = true;
        }
        else if ( "<=".equals( sign ) )
        {
            if ( byteLen <= len )
                rtn = true;
        }
        else if ( ">=".equals( sign ) )
        {
            if ( byteLen >= len )
                rtn = true;
        }
        else if ( "<>".equals( sign ) || "!=".equals( sign ) )
        {
            if ( byteLen != len )
                rtn = true;
        }
        return rtn;
    }

    /**
     * 정수형검사 . NumberFormatException, NullPointerException 을 검사하기 위해, Exception 발생시 false 리턴
     */
    public static boolean isParseInt( String str )
    {
        boolean rtn = false;
        try
        {
            Integer.parseInt( str );
            rtn = true;
        }
        catch ( Exception nf )
        {
            rtn = false;
        }
        return rtn;
    }

    /**
     * 정수형검사. NumberFormatException, NullPointerException 을 검사하기 위해, Exception 발생시 false 리턴
     */
    public static boolean isParseLong( String str )
    {
        boolean rtn = false;
        try
        {
            Long.parseLong( str );
            rtn = true;
        }
        catch ( Exception nf )
        {
            rtn = false;
        }
        return rtn;
    }

    /**
     * Map 에서 Object를 String 변환
     */
    public static String objectToString( Object o )
    {
        if ( o == null )
            return null;
        String str = null;
        try
        {
            str = o.toString();
        }
        catch ( Exception dfdf )
        {
        }
        return str;
    }
    
    
    public static String getSerialString (long keyNum) {
    	
    	int len = String.valueOf(keyNum).length();
    	
    	if (len > 6) {
    		return String.valueOf(keyNum).substring(len-6, len);
    	} else {
    		return String.format("%06d", keyNum);
    		//return String.format("%1$" + (6-len+1) + "0", keyNum);  

    	}
    }
    
    
}
