package by.soykin.nook.nookback;

import by.soykin.nook.nookback.jpa.entities.*;
import by.soykin.nook.nookback.jpa.entities.enums.*;
import by.soykin.nook.nookback.jpa.entities.enums.Currency;
import by.soykin.nook.nookback.jpa.repository.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

@SpringBootTest
class NookBackApplicationTests {



//    @Autowired
//    private ItemRepository itemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private NookRepository nookRepository;
    @Autowired
    private CostRepository costRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Test
    void contextLoads() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://r.onliner.by/ak/");
        driver.manage().window().maximize();

        WebDriver.Navigation navigate = driver.navigate();

        WebElement paginationPanel = driver.findElement(By.className("pagination"));
        List<WebElement> pagination__secondary = paginationPanel.findElements(By.className("pagination__secondary"));
        WebElement element = driver.findElement(By.className("classifieds-list"));
        List<WebElement> quantityOfPages=driver.findElements(By.className("pagination-pages__item"));
        List<WebElement> classified = element.findElements(By.className("classified"));
        //Цикл переключения страниц
        for (int i = 1; i < quantityOfPages.size(); i++) {
          List<String> urls = new ArrayList<>();
                //List<WebElement> classified = element.findElements(By.className("classified"));
                //Цикл переключения квартиры на странице
            for (int j = 0; j < classified.size(); j++) {
                WebElement element2 = driver.findElement(By.className("classifieds-list"));
                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement link1 = wait1.until(ExpectedConditions.elementToBeClickable(By.className("classified")));
                List<WebElement> classified2 = element2.findElements(By.className("classified"));
                String href;
                try{
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    href= classified2.get(j).getAttribute("href");}
                catch (org.openqa.selenium.StaleElementReferenceException ex){
                    //List<WebElement> classified3 = element.findElements(By.className("classified"));
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.className("classified")));
                    href= link.getAttribute("href");
                }

                urls.add(href);
            }
try{

            urls.forEach(url -> {
                navigate.to(url);
                WebElement panelWithCost = driver.findElement(By.className("apartment-bar"));
                List<WebElement> span = panelWithCost.findElements(By.tagName("span"));


                WebElement nookDescriptionElement = driver.findElement(By.className("apartment-options"));
                String nookDescription = nookDescriptionElement.getText();

                String quantityRooms = span.get(4).getText();
                String ownerType = span.get(5).getText();


                Nook nook = new Nook();
                nook.setId(UUID.randomUUID().toString());
                setQuantityRooms(nook, quantityRooms);
                nook.setDescription(nookDescription);
                nook.setType(NookType.FLAT);


                WebElement addressElement = driver.findElement(By.cssSelector(".apartment-info__sub-line.apartment-info__sub-line_large"));
                String addressString = addressElement.getText();

                Address address = getAddress(addressString, nook);
                // addressRepository.save(address);
                nook.setAddress(address);
                addressRepository.save(address);
                // nookRepository.save(nook);

                Owner owner = new Owner();
                owner.setId(UUID.randomUUID().toString());
                setOwnerType(owner, ownerType);
                List<Phone> phones = new ArrayList<>();


                WebElement infoPhone = driver.findElement(By.id("apartment-phones"));
                List<WebElement> liInfo = infoPhone.findElements(By.tagName("a"));
                for (WebElement el : liInfo) {
                    String phone = el.getText();
                    Phone phone1 = new Phone();
                    phone1.setNumber(phone);
                    phoneRepository.save(phone1);
                    phones.add(phone1);
                }
                owner.setPhoneNumber(phones);

                WebElement webOwnerName = driver.findElement(By.xpath("//div[@class='apartment-info__sub-line apartment-info__sub-line_extended']"));
                String ownerName = webOwnerName.getText();
                owner.setName(ownerName);
                ownerRepository.save(owner);


                Cost cost = new Cost();

                String costInBy = span.get(0).getText();
                stringToCostByn(cost, costInBy);
                String costInUsd = span.get(2).getText();
                stringToCostUsd(cost, costInUsd);
                costRepository.save(cost);

                Operation operation = new Operation();
                operation.setType(OperationType.RENT);
                try {
                    WebElement descriptionOfOperation = driver.findElement(By.xpath("//div[@class='apartment-info__sub-line apartment-info__sub-line_extended-bottom']"));
                    String stringDescriptionOfOperation = descriptionOfOperation.getText();
                    operation.setDescription(stringDescriptionOfOperation);
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    operation.setDescription(null);
                }

                operation.setNook(nook);
                operation.setOwner(owner);
                operation.setCost(cost);

                operationRepository.save(operation);
            });}
catch (org.openqa.selenium.NoSuchElementException e){
    driver.navigate().forward();
}
            driver.get("https://r.onliner.by/ak/");
            //WebElement button= driver.findElement(By.className("pagination-dropdown"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.className("pagination-dropdown")));
            link.click();
            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement link2 = wait2.until(ExpectedConditions.elementToBeClickable(By.className("pagination-pages__item")));
            List<WebElement> quantityOfPages1=driver.findElements(By.className("pagination-pages__item"));
            quantityOfPages1.get(i).click();

        }

        driver.quit();

    }

    @NotNull
    private Address getAddress(String addressString, Nook nook) {
        Address address;
        Optional<Address> addressInDataBase=addressRepository.findByValue(addressString);
      String addressStringInDataBase=null;
        if(addressInDataBase.isPresent()){
          Address address1=addressInDataBase.get();
          addressStringInDataBase=address1.getValue();
      }
        if(!addressString.equals(addressStringInDataBase)) {
            address = new Address();
            address.setId(UUID.randomUUID().toString());
            address.setValue(addressString);
            List<Nook> addressOfNooks=new ArrayList<>();
            addressOfNooks.add(nook);
            address.setNooks(addressOfNooks);
        }
        else {
           address=addressRepository.findByValue(addressString).orElseThrow();
           address.getNooks().add(nook);

        }
        return address;
    }

        private static void stringToCostByn(Cost costBYN, String costInByn) {

        String[] words = costInByn.split("\\s");
        String number = words[0];
        BigDecimal Byn = new BigDecimal(number.replace(",", "."));
        costBYN.setCostInBYN(Byn);
    }

    private static void stringToCostUsd(Cost costUSD, String costInUsd) {
        String[] words = costInUsd.split("\\s");
        String number = words[0];
        BigDecimal Usd = new BigDecimal(number);
        costUSD.setCostInUSD(Usd);
            }

    private static void setQuantityRooms(Nook nook, String quantityRooms) {
        switch (quantityRooms) {
            case "1-комнатная квартира":
                nook.setQuantityRooms(Room.ONE);
                break;
            case "2-комнатная квартира":
                nook.setQuantityRooms(Room.TWO);
                break;
            case "3-комнатная квартира":
                nook.setQuantityRooms(Room.THREE);
                break;
            case "4-комнатная квартира":
                nook.setQuantityRooms(Room.FOUR);
                break;
            case "5-комнатная квартира":
                nook.setQuantityRooms(Room.FIVE);
                break;
            case "6-комнатная квартира":
                nook.setQuantityRooms(Room.SIX);
                break;
            case "7-комнатная квартира":
                nook.setQuantityRooms(Room.SEVEN);
                break;
            case "8-комнатная квартира":
                nook.setQuantityRooms(Room.EIGHT);
                break;
            case "9-комнатная квартира":
                nook.setQuantityRooms(Room.NINE);
                break;
            case "10-комнатная квартира":
                nook.setQuantityRooms(Room.TEN);
                break;
            case "Комната":
                nook.setQuantityRooms(Room.ROOM);
                break;
        }

    }

    public static void setNooksToAddress(Address address, Nook nook){

    }

    private static void setOwnerType(Owner owner, String ownerType){
        switch (ownerType){
            case "Собственник": owner.setOwnerType(OwnerType.OWNER);
            break;
            case "Агентство": owner.setOwnerType(OwnerType.REALTOR);
            break;
        }
    }


}