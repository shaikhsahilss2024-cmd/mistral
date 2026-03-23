import org.openqa.selenium.*;

public class SelfHealingDriver {

    WebDriver driver;

    public SelfHealingDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement find(By locator) {

        try {
            return driver.findElement(locator);

        } catch (Exception e) {

            System.out.println("❌ Locator failed: " + locator);

            String dom = driver.getPageSource();

            String newXpath = MistralUtil.getXPath(dom, locator.toString());

            System.out.println("🤖 Mistral XPath: " + newXpath);

            return driver.findElement(By.xpath(newXpath));
        }
    }
}