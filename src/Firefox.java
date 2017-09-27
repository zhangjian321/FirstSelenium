import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * Project: FirstSelenium
 * Author: Albert
 * Date: 2017-7-31-0031
 * Description:
 */
public class Firefox {
    public static void main(String[] args) throws InterruptedException{
        String url = "https://www.baidu.com/";
        //指定浏览器启动程序的路径
        System.setProperty("webdriver.firefox.bin", "D:\\software\\Firefox\\firefox.exe");
        //指定驱动的路径
        System.setProperty("webdriver.gecko.driver", "F:\\testing\\selenium-driver\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize(); //放大窗口
        driver.get(url);
        visibilityOfElementLocatedByID(driver,"kw", 10);
        WebElement inputElement = driver.findElement(By.id("kw"));
        inputElement.click();
        inputElement.sendKeys("今日头条");
        driver.findElement(By.id("su")).click();
        Thread.sleep(2000);
        driver.quit();
    }

    //在特定的时间内等待某个元素可见，若在规定的时间期限之前都还没有出现则抛出异常
    public static void visibilityOfElementLocatedByID(WebDriver dr, String ID, int timeout) {
        WebDriverWait wait = new WebDriverWait(dr, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ID)));
    }

    public static void visibilityOfElementLocatedByName(WebDriver dr, String name, int timeout) {
        WebDriverWait wait = new WebDriverWait(dr, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
    }

    public static void waitElementByIdAndInput(WebDriver driver, String text, String ID) {
        WebDriverWait wait = new WebDriverWait(driver, 10, 1);
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id(ID));
            }
        }).sendKeys(text);
        wait.until((ExpectedCondition<WebElement>) dri-> dri.findElement(By.id(ID)));
    }
}
