/*
 * Copyright (c) 2012 - 2012 CJ Hellovision, Inc.
 * All right reserved.
 *
 * * This software is the confidential and proprietary information of CJ Hellovision
 * , Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * * you entered into with CJ Hellovision.
 *
 */
package com.cjhv.mvno.drools.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class StringUtils.
 * 
 * @author JungmokKim, Commoninfra
 */
public class StringUtils
{

    /**
     * 널이거나 빈 문자열을 원하는 스트링으로 변환한다 <BR>
     * 단, 좌우 공백이 있는 문자열은 trim 한다 <br>
     * .
     * 
     * @param org the org
     * @param converted the converted
     * @return 치환된 문자열
     */
    public static String nullToStr( String org, String converted )
    {
        if ( org == null || org.trim().length() == 0 )
            return converted;
        else
            return org.trim();
    }

    /**
     * Null to str.
     * 
     * @param org the org
     * @return the string
     */
    public static String nullToStr( String org )
    {
        return nullToStr( org, "" );
    }

    /**
     * Removes the ascii a1.
     * 
     * @param source the source
     * @return the string
     */
    public static String RemoveAsciiA1( String source )
    {
        char[] targetAscii = new char[2];
        targetAscii[0] = (char) 0xA1;
        targetAscii[1] = (char) 0xA1;

        return source.replaceAll( new String( targetAscii ), " " );
    }

    /**
     * 특정 문자열을 다른 문자열로 대체하는 메소드 : 문자열 검색시 검색어에 색깔을 넣거나 ... 테그를 HTML 문자로 바꾸는데 사용하면 유용할거 같음.
     * 
     * @param line the line
     * @param oldString the old string
     * @param newString the new string
     * @return the string
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
     * Left pad.
     * 
     * @param str : 원본문자열
     * @param len : 총길이
     * @param pad : 채워질 문자
     * @return the string
     */
    public static String leftPad( String str, int len, String pad )
    {
        String result = str;
        if ( null == result )
            result = "";
        int templen = len - result.getBytes().length;

        for ( int i = 0; i < templen; i++ )
            result = pad + result;

        return result;
    }

    /**
     * Right pad.
     * 
     * @param str : 원본문자열
     * @param len : 총길이
     * @param pad : 채워질 문자
     * @return the string
     */
    public static String rightPad( String str, int len, char pad )
    {
        String result = str;
        int templen = len - result.getBytes().length;

        for ( int i = 0; i < templen; i++ )
            result = result + pad;

        return result;
    }

    /**
     * 입력받은 String을 원하는 길이만큼 원하는 문자로 오른쪽을 채워주는 함수.
     * 
     * @param str the str
     * @param len the len
     * @param pad the pad
     * @return 지정된 문자로 채워진 String
     */
    public static String rightPad( String str, int len, String pad )
    {
        String result = str;
        int templen = len - result.getBytes().length;

        for ( int i = 0; i < templen; i++ )
            result = result + pad;

        return result;
    }

    /**
     * To list.
     * 
     * @param key the key
     * @param value the value
     * @return the list
     */
    public static List toList( String key, String value )
    {
        List list = new ArrayList();
        Map map = new HashMap();
        map.put( key, value );
        list.add( map );

        return list;
    }

    /**
     * 오라클 Clob 데이터를 String 형태로 변환 후 리턴
     * @param clob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String clobToString(Clob clob) throws SQLException, IOException{
        if(clob == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        String str = "";
        BufferedReader br = new BufferedReader(clob.getCharacterStream());
        
        while((str = br.readLine()) != null){
            sb.append(str);
        }
        
        br.close();
        return sb.toString();
    }
}
