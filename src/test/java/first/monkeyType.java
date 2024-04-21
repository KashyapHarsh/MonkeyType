package first;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class monkeyType {
    public static void main(String[] args) throws InterruptedException {
        // creating the chrome driver
        WebDriver driver = new ChromeDriver();

        // maximaizing the window
        driver.manage().window().maximize();

        // navigating to url
        driver.get("https://monkeytype.com/");

        // waiting for the cookie popup to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(
            driver.findElement(By.xpath("//button[@class='rejectAll']"))));
        Actions actions = new Actions(driver);
    
        // clicking the cookie popup
        driver.findElement(By.xpath("//button[@class='rejectAll']")).click();

        Thread.sleep(5000);

        // initiate the time 
        Instant start = Instant.now();
        // Selecting the time for the typing test
        String timer = driver.findElement(By.xpath("//div[@timeconfig='30']")).getText();
        int time = Integer.valueOf(timer);


        while(Duration.between(start, Instant.now()).toSeconds()<time-1){
            // finding all the word element to get the text
            List<WebElement> wordList = driver.findElements(By.className("word"));
            int activeIdx = 0;
            // Here we are finding where the active index is since we are finding the text every time the loop runs
            // so to avoid the same words adding to list we are finding the active class name of the word list
            for(int i =0;i<wordList.size();i++){
                if(wordList.get(i).getAttribute("class").contains("active")){
                    activeIdx = i;
                    break;
                }
            }
            // Here we are removing the the part of the wordlist which is already typed
            wordList = wordList.subList(activeIdx, wordList.size()-1);
            //Here we are geting the text from the word element
            for(WebElement word: wordList){
                String letters = "";
                letters += word.getText();
                letters += " ";
                // pressing the key
                actions.sendKeys(letters).perform();;
                
            }
        }
    }
    
}
