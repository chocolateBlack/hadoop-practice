package cn.edu.buaa.practice.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by BurningIce.
 */
public class StringUtilsTest {

    @Test
    public void testApply() throws Exception {
        Assert.assertEquals("/abcabc/*/*.xml", StringUtils.normalizeUri("/abcabc/01239/1234abcdefABCDEF.xml"));
        Assert.assertEquals("/*/*/*.xml", StringUtils.normalizeUri("/abcabcd/01239/1234abcdefABCDEF.xml"));

        Assert.assertEquals("/*/01239f/*.xml", StringUtils.normalizeUri("/ABCDEFF/01239f/1234abcdefABCDEF.xml"));
        Assert.assertEquals("/*/01239f/1234ad.xml", StringUtils.normalizeUri("/ABCDEFF/01239f/1234ad.xml"));

        Assert.assertEquals("/*/01239f/1234ad.xml", StringUtils.normalizeUri("/ABCDEFghijklmnopqrstfdsafdsafdsafdsafdsafdafda/01239f/1234ad.xml"));
        Assert.assertEquals("/ABCDEFghijklmnopqrs/01239f/1234ad.xml", StringUtils.normalizeUri("/ABCDEFghijklmnopqrs/01239f/1234ad.xml"));

        Assert.assertEquals("/ABCDEFghijklmnopqrs/*", StringUtils.normalizeUri("/ABCDEFghijklmnopqrs/01239*;+,-_/1234ad.xml"));

        Assert.assertEquals("/*.jpg@502_502h_80q", StringUtils.normalizeUri("/TB2tLZecXXXXXc4XXXXXXXXXXXX_!!405312179.jpg@502_502h_80q"));
        Assert.assertEquals("/upload/show/*/*/*.jpg-show.half", StringUtils.normalizeUri("/upload/show/201507/16/f721df380c65fc46cfd52a3405ed3172.jpg-show.half"));
        Assert.assertEquals("/appIn/widgetStartup/postData/*", StringUtils.normalizeUri("/appIn/widgetStartup/postData/ff929301339a8357027600c6cf591a19"));
        Assert.assertEquals("/custom/*/*/*/", StringUtils.normalizeUri("/custom/3760/5583313127/0_14_683_491_0.2518/"));
        Assert.assertEquals("/custom/*/*/*/", StringUtils.normalizeUri("/custom/3760/5583313127/0.2518/"));
        Assert.assertEquals(StringUtils.normalizeUri("/wp-content/uploads/2013/06/wtmart.png"), "/wp-content/uploads/*/*/wtmart.png");

    }
}