package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AppTest {
    public WebDriver driver;
    @Before
    public void setupDriver(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\OEM\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
        driver = new ChromeDriver();
        String url = "https://www.gittigidiyor.com/";
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
    }
    @Test
    public void TestSearch(){

        WebElement openLoginBtn = driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div"));
        openLoginBtn.click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
        driver.navigate().to("https://www.gittigidiyor.com/uye-girisi"); //Gittigidiyorun login yapısı bana biraz farklı geldiği için direk giriş sayfasına yönlendirdim.

        WebElement userName = driver.findElement(By.xpath("//*[@id=\"L-UserNameField\"]"));
        userName.sendKeys("test@gmail.com");

        WebElement password = driver.findElement(By.xpath("//*[@id=\"L-PasswordField\"]"));
        password.sendKeys("123456");

        WebElement Login = driver.findElement(By.xpath("//*[@id=\"gg-login-enter\"]"));
        Login.click();

        //"Bilgisayar" search kısmı
        WebElement searchBox = driver.findElement(By.name("k"));
        searchBox.click();
        searchBox.sendKeys("Bilgisayar");
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

        driver.navigate().to("https://www.gittigidiyor.com/arama/?k=bilgisayar&sf=2"); //Sayfa 2'ye geçmek için olması gereken button, farklı bir attribute olarak tanımlanmış.O yüzden link üzerinden yönlendirdim
        driver.findElement(By.xpath("//*[@id=\"item-info-block-680233923\"]")).click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

        WebElement price= driver.findElement(By.xpath("//*[@id=\"sp-price-lowPrice\"]"));
        String firstPrice= price.getText();

        //Ürünü sepete ekliyor.
        WebElement element = driver.findElement(By.xpath("//*[@id=\"add-to-basket\"]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

        driver.navigate().to("https://www.gittigidiyor.com/sepetim");
        WebElement priceBasket= driver.findElement(By.xpath("//*[@id=\"cart-item-469280355\"]/div[1]/div[5]/div[1]/div[1]/strong[2]"));
        String lastPrice= priceBasket.getText();

        //Sepetteki ürün fiyatı ile sayfadaki fiyatın karşılarştırılması.
        if(firstPrice.equals(lastPrice))
        {
            //Sepetteki ürün adedinin arttırılması.
            WebElement quantityBasket = driver.findElement(By.xpath("//*[@id=\"cart-item-469280355\"]/div[1]/div[4]/div/div[2]/select"));
            quantityBasket.sendKeys("2");
        }
        //Sepetin boşaltılması.
        driver.findElement(By.xpath("//*[@id=\"cart-item-469280355\"]/div[1]/div[3]/div/div[2]/div/a[1]/i")).click();


    }
        @After
        public void quitDriver(){
            driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
            driver.quit();

    }
}
