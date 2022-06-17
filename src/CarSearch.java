import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;


import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CarSearch {
    public static void main(String[] args) throws InterruptedException, IOException {


        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Rahim\\Downloads\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4000));

        driver.get("https://www.cars.com/");


        WebElement actualNU = driver.findElement(By.xpath("//select[@id='make-model-search-stocktype']"));
        Select dropDown1 = new Select(actualNU);
        String selected1 = dropDown1.getFirstSelectedOption().getText();
        Assert.assertEquals(selected1,"New & used cars");

        WebElement actualMake = driver.findElement(By.xpath("//select[@id='makes']"));
        Select dropDown2 = new Select(actualMake);
        String selected2 = dropDown2.getFirstSelectedOption().getText();
        Assert.assertEquals(selected2,"All makes");

        WebElement actualModel = driver.findElement(By.xpath("//select[@id='models']"));
        Select dropDown3 = new Select(actualModel);
        String selected3 = dropDown3.getFirstSelectedOption().getText();
        Assert.assertEquals(selected3,"All models");

        WebElement actualPrice = driver.findElement(By.xpath("//select[@id='make-model-max-price']"));
        Select dropDown4 = new Select(actualPrice);
        String selected4 = dropDown4.getFirstSelectedOption().getText();
        Assert.assertEquals(selected4,"No max price");

        WebElement actualDistance = driver.findElement(By.xpath("//select[@id='make-model-maximum-distance']"));
        Select dropDown5 = new Select(actualDistance);
        String selected5 = dropDown5.getFirstSelectedOption().getText();
        Assert.assertEquals(selected5,"20 miles");
//
//
        String[] expectedNU = {"New & used cars","New & certified cars","New cars","Used cars","Certified cars"};
        List<WebElement> optionsNU = dropDown1.getOptions();

        for(int i =0; i<expectedNU.length;i++) {

                Assert.assertEquals((optionsNU.get(i)).getText(),expectedNU[i]);

        }
        Thread.sleep(2000);
        WebElement newOrUsed = driver.findElement(By.id("make-model-search-stocktype"));
        Select selectCondition = new Select(newOrUsed);
        selectCondition.selectByIndex(0);

        new Select(driver.findElement(By.id("make-model-search-stocktype"))).selectByIndex(0);

        new Select(driver.findElement(By.id("makes"))).selectByVisibleText("Tesla");

        String[] expectedModels = {"All models","Model 3","Model S","Model X","Model Y","Roadster"};
        List<WebElement> optionsModel = dropDown3.getOptions();

        for(int i =0; i<expectedModels.length;i++) {

            Assert.assertEquals((optionsModel.get(i)).getText(),expectedModels[i]);

        }

        new Select(driver.findElement(By.id("models"))).selectByVisibleText("Model S");

        new Select(driver.findElement(By.id("make-model-max-price"))).selectByValue("100000");

        new Select(driver.findElement(By.id("make-model-maximum-distance"))).selectByValue("50");

        driver.findElement(By.id("make-model-zip")).sendKeys("22182");
        driver.findElement(By.xpath("//button[@data-searchtype='make']")).click();

        List<WebElement> numberOfResults = driver.findElements(By.xpath("//a[@class='vehicle-card-link js-gallery-click-link']"));
        int actualNumberOfResults = 20;
        Assert.assertEquals(numberOfResults.size(),actualNumberOfResults); //checking if the correct number of results are present

        new Select(driver.findElement(By.id("sort-dropdown"))).selectByValue("list_price"); //sorting by lowest price
        Thread.sleep(2000);
        Thread.sleep(2000);



        List<Integer> prices = new ArrayList<>();
        List<WebElement> fromLowestToHighest = driver.findElements(By.xpath("//span[@class='primary-price']"));
        for(WebElement price:fromLowestToHighest){
            Integer formatPrice = Integer.valueOf(price.getText().split("\\$")[1].replace(",", ""));
            prices.add(formatPrice);
        }
        //System.out.println(prices.toString());
        Collections.sort(prices);
        for(int i =0; i<prices.size();i++) {

            Assert.assertEquals(Integer.valueOf(fromLowestToHighest.get(i).getText().split("\\$")[1].replace(",", "")),prices.get(i));

        }
        //System.out.println(fromLowestToHighest.toString());

        new Select(driver.findElement(By.id("sort-dropdown"))).selectByValue("mileage_desc"); //sorting by lowest price
        Thread.sleep(2000);
        Thread.sleep(2000);



        List<Integer> mileage = new ArrayList<>();
        List<WebElement> sortMileage = driver.findElements(By.xpath("//div[@class='vehicle-details']//div[@class='mileage']"));
        for(WebElement miles : sortMileage){
            Integer formatMiles = Integer.valueOf(miles.getText().split(" ")[0].replace(",", ""));
            mileage.add(formatMiles);
        }
        //System.out.println(mileage.toString());
        Collections.sort(mileage,Collections.reverseOrder());
        Thread.sleep(2000);

        for(int i =0; i<mileage.size();i++) {

            Assert.assertEquals(Integer.valueOf(sortMileage.get(i).getText().split(" ")[0].replace(",", "")),mileage.get(i));

        }
        //System.out.println(sortMileage.toString());
        Thread.sleep(2000);

        new Select(driver.findElement(By.id("sort-dropdown"))).selectByValue("distance"); //sorting by lowest price
        Thread.sleep(2000);
        Thread.sleep(2000);


        List<Integer> distance = new ArrayList<>();
        List<WebElement> sortDistance = driver.findElements(By.xpath("//div[@data-qa='miles-from-user']"));
        for(WebElement dist : sortDistance){
            String formatDist = dist.getText();
             distance.add(Integer.valueOf(formatDist.split(" ")[0]));

        }

        Thread.sleep(1500);

        //System.out.println(distance.toString());
        Collections.sort(distance);
        //System.out.println(distance.size());
        //System.out.println(sortDistance.toString());
        //System.out.println(sortDistance.size());
        for(int i =0; i<distance.size();i++) {

            String formatDist = sortDistance.get(i).getText();
            Assert.assertEquals(Integer.valueOf(formatDist.split(" ")[0]),distance.get(i));

        }
        //System.out.println(sortDistance.toString());

        new Select(driver.findElement(By.id("sort-dropdown"))).selectByValue("year"); //sorting by lowest price
        Thread.sleep(2000);
        Thread.sleep(2000);

        List<Integer> year = new ArrayList<>();
        List<WebElement> sortYear = driver.findElements(By.xpath("//h2[@class='title']"));

        for(WebElement years : sortYear){
            Integer formatYears = Integer.valueOf(years.getText().split(" ")[0]);
            mileage.add(formatYears);
        }
        //System.out.println(year.toString());
        Collections.sort(year);


        for(int i =0; i<year.size();i++) {

            Assert.assertEquals(Integer.valueOf(sortYear.get(i).getText().split(" ")[0]),year.get(i));

        }
        //System.out.println(sortYear.toString());
        driver.close();

    }
}
