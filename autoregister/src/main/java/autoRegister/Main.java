package autoRegister;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.List;

public class Main {
    public static void main(String[] args){
        WebDriver driver = new ChromeDriver();

        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            driver.get("https://reservation.frontdesksuite.ca/rcfs/richcraftkanata");
            WebElement firstButton = driver.findElement(By.xpath("/html/body/div/main/div[2]/div/div[5]/div[1]/a/div"));
            //change to whichever sport, above is swim^
            //WebElement firstButton = driver.findElement(By.xpath("/html/body/div/main/div[2]/div/div[11]/div[1]/a"));
            //this is for badminton doubles - adult^
            firstButton.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"reservationCount\"]")));

            WebElement howMany = driver.findElement(By.xpath("//*[@id=\"reservationCount\"]"));
            howMany.clear();
            //howMany is the same
            howMany.sendKeys("2");
            WebElement howManyConfirm = driver.findElement(By.xpath("//*[@id=\"submit-btn\"]/span"));
            howManyConfirm.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mainForm\"]/div[2]/div[1]/ul/li[1]/a")));

            //WebElement time = driver.findElement(By.xpath("//*[@id=\"mainForm\"]/div[2]/div[1]/ul/li[1]/a"));
            //time.click();

            boolean availableFound = false;

            List<WebElement> days = driver.findElements(By.className("date"));
            for (WebElement day : days) {
                // Check if the day is available
                if (day.getAttribute("class").contains("not-available")) {
                    // This day is unavailable, continue to next
                    continue;
                } else {
                    // This day is available, click it
                    day.click();
                    availableFound = true;
                    System.out.println("Clicked on: " + day.getText());
                    break; // Exit the loop after clicking one available day
                }
            }

            if (!availableFound) {
                System.out.println("Both days are unavailable. Ending driver.");
                driver.quit(); // Close the driver if both are unavailable
            }

            List<WebElement> timeButtons = driver.findElements(By.cssSelector(".date.one-queue .mdc-button"));

            boolean availableTimeFound = false;

            // Loop through the buttons to find an available time
            for (WebElement time : timeButtons) {
                // Check if the button's onclick attribute is not "return false;"
                String onclickAttribute = time.getAttribute("onclick");
                if (onclickAttribute != null && !onclickAttribute.contains("return false;")) {
                    // Click the button if it's available
                    time.click();
                    System.out.println("Selected time: " + time.getAttribute("aria-label"));
                    availableTimeFound = true;
                    break; // Exit the loop after clicking the first available time
                }
            }

            if (!availableTimeFound) {
                System.out.println("No available time slots found.");
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"telephone\"]")));
            WebElement phone = driver.findElement(By.xpath("//*[@id=\"telephone\"]"));
            WebElement email = driver.findElement(By.xpath("//*[@id=\"email\"]"));
            WebElement name = driver.findElement(By.xpath("//*[@id=\"field2021\"]"));
            WebElement confirm = driver.findElement(By.xpath("//*[@id=\"submit-btn\"]/span"));
            phone.sendKeys("6139815685");
            email.sendKeys("m.jzhou05@gmail.com");
            name.sendKeys("Mason Zhou");
            confirm.click();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            driver.quit();
        }

    }

}
