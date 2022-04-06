import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SampleTest {

    protected WebDriver driver;
    private Logger logger = LogManager.getLogger(SampleTest.class);
    Duration duration;

    @Before
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("Драйвер поднят");
        duration = Duration.ofSeconds(10);
        //Set implicit wait:
//wait for WebElement
        driver.manage().timeouts().implicitlyWait(duration);

//wait for loading page
        driver.manage().timeouts().pageLoadTimeout(duration);

//wait for an asynchronous script to finish execution
        driver.manage().timeouts().setScriptTimeout(duration);
    }

    @Test
    public void testYandex() throws InterruptedException {
        driver.get("https://market.yandex.ru/");
        logger.info("Открыт яндекс маркет");
        WebElement catalogButton = driver.findElement(By.xpath("//button[@id='catalogPopupButton']"));
        catalogButton.click();
        logger.info("Открыт каталог");
        WebElement smartphones = driver.findElement(By.xpath("//a[@class = 'egKyN _1mqvV _3kgUl' and contains(text(),'Смартфоны')]"));
        ExpectedConditions.visibilityOf(smartphones);
        smartphones.click();
        logger.info("Открыта вкладка смартфоны");
        WebElement searchAll = driver.findElement(By.xpath("//fieldset[@data-autotest-id='7893318']//button[contains(text(),'Показать всё')]"));
        ExpectedConditions.visibilityOf(searchAll);
        logger.info("Показать всё");
        searchAll.click();
        WebElement searchPhone = driver.findElement(By.xpath("//input[@name='Поле поиска']"));
        searchPhone.sendKeys("Xiaomi");
        logger.info("В поиск введен xiaomi");
        WebElement xiaomi = driver.findElement(By.xpath("//input[@name = 'Производитель Xiaomi']//ancestor::label"));
        ExpectedConditions.visibilityOf(xiaomi);
        xiaomi.click();
        logger.info("Выбран xiaomi");
        searchPhone.clear();
        searchPhone.sendKeys("realme");
        logger.info("В поиск введен realme");
        WebElement realme = driver.findElement(By.xpath("//input[@name = 'Производитель realme']//ancestor::label"));
        ExpectedConditions.visibilityOf(realme);
        realme.click();
        logger.info("Выбран realme");
        WebElement dbprice = driver.findElement(By.xpath("//button[@data-autotest-id='dprice']"));
        ExpectedConditions.visibilityOf(dbprice);
        dbprice.click();
        logger.info("Сортировка по цене");
        WebElement phoneXiaomi = driver.findElement(By.xpath("//a[contains(@title,'Xiaomi')]/parent::h3/parent::div/parent::div/preceding-sibling::div/div[@role='button']"));
        clickOnInvisibleElement(phoneXiaomi);
        WebElement addForCompare = driver.findElement(By.xpath("//div[@class='_3rFvf' and contains(text(), 'добавлен')]"));
        ExpectedConditions.visibilityOf(addForCompare);
        logger.info("Телефон Xiaomi добавлен в сравнение");
        WebElement phoneRealme = driver.findElement(By.xpath("//a[contains(@title,'realme')]/parent::h3/parent::div/parent::div/preceding-sibling::div/div[@role='button']"));
        clickOnInvisibleElement(phoneRealme);
        ExpectedConditions.visibilityOf(addForCompare);
        logger.info("Телефон realme добавлен в сравнение");
        WebElement compareButton = driver.findElement(By.xpath("//a[@class='_20WYq _1XpIt hycgz']"));
        ExpectedConditions.visibilityOf(compareButton);
        clickOnInvisibleElement(compareButton);
        logger.info("Открыт раздел сравнение");
        List<WebElement> comparePhones = driver.findElements(By.xpath("//div[@class='Lwwoc _2VGDF']"));
        if (comparePhones.size() != 2) {
            logger.info("Количество телефонов в разделе сравнение = " + comparePhones.size());
            driver.quit();
        }
        logger.info("Количество телефонов в разделе сравнение = " + comparePhones.size());
        WebElement buttonAllCharacteristics = driver.findElement(By.xpath("//button[@class='_1KpjX _1_bHO']"));
        buttonAllCharacteristics.click();
        logger.info("Нажата кнопка все характеристики");
        WebElement osCharacteristic = driver.findElement(By.xpath("//button[@class='_1KpjX _3eHi2' and contains(text(), 'Общие характеристики')]/parent::div/following-sibling::div/div[contains(text(), 'Операционная система')]"));
        ExpectedConditions.visibilityOf(osCharacteristic);
        logger.info("Операционная система появилась в характеристиках");
        WebElement buttonDifferentCharacterstics = driver.findElement(By.xpath("//button[@class='_1KpjX _1_bHO']"));
        buttonDifferentCharacterstics.click();
        logger.info("Нажата кнопка различающиеся характеристики");
        ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@class='_1KpjX _3eHi2' and contains(text(), 'Общие характеристики')]/parent::div/following-sibling::div/div[contains(text(), 'Операционная система')]"));
        logger.info("Операционная система исчезла из характеристик");
    }

    @After
    public void setDown(){
        if (driver != null){
            driver.quit();
        }
        logger.info("Драйвер закрыт");
    }

    public void clickOnInvisibleElement(WebElement element) {

        String script = "var object = arguments[0];"
                + "var theEvent = document.createEvent(\"MouseEvent\");"
                + "theEvent.initMouseEvent(\"click\", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
                + "object.dispatchEvent(theEvent);"
                ;

        ((JavascriptExecutor)driver).executeScript(script, element);
    }
}
