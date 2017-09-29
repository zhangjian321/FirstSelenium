import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.io.File;

/**
 * Project: FirstSelenium
 * Author: Albert
 * Date: 2017-9-17-0017
 * Description:
 */
public class Chrome {
    private static WebDriver driver;

    //初始化浏览器，最大化窗口
    private void initBrowser(String platformName) {
        String windowsDriverPath = "F:\\testing\\selenium-driver\\chromedriver.exe";
        String macosDriverPath = "/Users/admin/Desktop/备份/driver/chromedriver";
        String linuxDriverPath = ""; //待设置
        switch (platformName) {
            default:
                System.setProperty("webdriver.chrome.driver", linuxDriverPath);
                break;
            case "win":
                System.setProperty("webdriver.chrome.driver", windowsDriverPath);
                break;
            case "mac":
                System.setProperty("webdriver.chrome.driver", macosDriverPath);
                break;
        }
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    //测试百度首页的设置功能
    public void testBaiduPreference() throws InterruptedException {
        driver.get("https://www.baidu.com");
        driver.findElement(By.linkText("设置")).click();
        driver.findElement(By.linkText("搜索设置")).click();
        Thread.sleep(2000);
        WebElement element = driver.findElement(By.id("nr"));
        Select select = new Select(element);
        select.selectByValue("20");
//        select.selectByValue();
//        select.selectByVisibleText();
        System.out.println(select.getFirstSelectedOption().getText());
//        List<WebElement> options = select.getAllSelectedOptions();
//        for (WebElement option : options) {
//            System.out.println(option.getText());
//        }
//        System.out.println("IS multiple? " + select.isMultiple());
        driver.findElement(By.className("prefpanelgo")).click();
        driver.switchTo().alert().accept();
        Thread.sleep(5000);
    }

    //浏览器添加cookie，查看cookie
    public void testCookie() {
        Cookie cookie1 = new Cookie("key1", "value-aaa");
        Cookie cookie2 = new Cookie("key2", "value-bbb");
        driver.manage().addCookie(cookie1);
        driver.manage().addCookie(cookie2);
//        Set<Cookie> cookies = driver.manage().getCookies();
//        System.out.println(cookies);
//        System.out.println(driver.manage().getCookieNamed("key1"));
//        driver.manage().deleteCookieNamed("key2");
//        driver.manage().deleteAllCookies();
    }

    //将界面滚动到定位的元素
    public void testScrollToElement(WebElement element) throws InterruptedException {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView();", element);
        Thread.sleep(2000);
    }

    //截屏
    public void takeScreenShot(String fileName) {
        String currentPath = System.getProperty("user.dir");
        TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(currentPath + "\\" + fileName);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(fileName + "Failed take screenshot.");
        } finally {
            System.out.println(fileName + "Successed take screenshot.");
        }
    }

    //百度搜索框提交关键词
    public void testSearch(String keyWord) throws InterruptedException {
        WebElement searchInput = driver.findElement(By.id("kw"));
        searchInput.sendKeys(keyWord);
        searchInput.submit();
        Thread.sleep(1000);
    }

    //测试对不可见的(visible)元素操作
    public void testUnvisibleElement() throws InterruptedException {
        driver.get("http://www.imooc.com");
        WebDriverWait wait = new WebDriverWait(driver, 5, 1);
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("js-signin-btn"));
            }
        }).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signin")));
        Thread.sleep(1000);
        driver.findElement(By.name("email")).sendKeys("15208395915");
        driver.findElement(By.name("password")).sendKeys("1328738005");
        driver.findElement(By.className("btn-red")).click();
        Thread.sleep(1000);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.id("header-avator"))).perform();
        Thread.sleep(1000);
        String firstHandle = driver.getWindowHandle();
        driver.findElement(By.xpath("//*[@id=\"header-user-card\"]/div/div/div[2]/ul/li[4]/a")).click();
        System.out.println(driver.getWindowHandle());
        Thread.sleep(3000);
//        List<String> list = new ArrayList<>(driver.getWindowHandles());
//        driver.switchTo().window(list.get(1));
        Set<String> handles = driver.getWindowHandles();
        for(String handle : handles) {
            if (!handle.equals(firstHandle)) {
                driver.switchTo().window(handle);
                System.out.println("第二个窗口的handle为：" + handle);
            }
        }
        actions.moveToElement(driver.findElement(By.className("avator-img"))).perform();
        Thread.sleep(1000);
        driver.findElement(By.linkText("更换头像")).click();
        Thread.sleep(1000);
//        driver.findElement(By.linkText("取消")).click();
//        Thread.sleep(3000);
//        driver.findElement(By.id("upload")).sendKeys("F:\\图片\\头像\\1475031075752.jpg");
//        Thread.sleep(2000);
        driver.findElement(By.linkText("确定")).click();
        Thread.sleep(1000);
    }

    public void closeAndQuit() {
        driver.close();
        driver.quit();
    }

    public static void main(String[] args) {
        Chrome chrome = new Chrome();
        //初始化浏览器
        chrome.initBrowser("mac");
        try {
            chrome.testBaiduPreference();
//            chrome.testCookie();
//            chrome.testSearch("Selenium");
//            chrome.testScrollToElement(driver.findElement(By.partialLinkText("亚马逊")));
//              chrome.testUnvisibleElement();
        } catch (InterruptedException e) {
            e.printStackTrace();
            chrome.takeScreenShot("Thread was interrupted.");
            chrome.closeAndQuit();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            chrome.takeScreenShot("This element doesn't found.");
            chrome.closeAndQuit();
        } catch (Exception e) {
            e.printStackTrace();
            chrome.takeScreenShot("Other exceptions.");
            chrome.closeAndQuit();
        } finally {
            System.out.println("This testing is done");
        }
        chrome.closeAndQuit();
    }
}
