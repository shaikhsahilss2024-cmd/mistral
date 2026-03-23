import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class TestLogin {

    @Test
    public void test() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String apiKey = System.getenv("MISTRAL_API_KEY");
        System.out.println(apiKey);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        SelfHealingDriver sh = new SelfHealingDriver(driver);

        Thread.sleep(4000);

        // ❌ Wrong locator
        sh.find(By.xpath("//input[@name='wrongUsername']")).sendKeys("Admin");

        sh.find(By.xpath("//input[@name='password']")).sendKeys("admin123");

        sh.find(By.xpath("//button[@type='submit']")).click();

        Thread.sleep(5000);

        driver.quit();
    }

}

//setx MISTRAL_API_KEY "8pYiosqeqyNq6XHrr1XEPUYVhvX1u7v0"
//echo $env:MISTRAL_API_KEY