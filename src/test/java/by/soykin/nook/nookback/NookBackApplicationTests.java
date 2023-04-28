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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
class NookBackApplicationTests {

    private List<String> urls = new ArrayList<>();

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

        WebDriver.Navigation navigate = driver.navigate();
        WebElement element = driver.findElement(By.className("classifieds-list"));
        WebElement paginationPanel = driver.findElement(By.className("pagination"));
        List<WebElement> pagination__secondary = paginationPanel.findElements(By.className("pagination__secondary"));
        List<WebElement> classified = element.findElements(By.className("classified"));
        for (WebElement webElement : classified) {
            String href = webElement.getAttribute("href");
            urls.add(href);
        }
        urls.forEach(url -> {
            navigate.to(url);
            WebElement panelWithCost = driver.findElement(By.className("apartment-bar"));
            List<WebElement> span = panelWithCost.findElements(By.tagName("span"));


            WebElement nookDescriptionElement=driver.findElement(By.className("apartment-options"));
            String nookDescription=nookDescriptionElement.getText();

            String quantityRooms = span.get(4).getText();
            String ownerType = span.get(5).getText();







            Nook nook = new Nook();
            nook.setId(UUID.randomUUID().toString());
            setQuantityRooms(nook, quantityRooms);
            nook.setDescription(nookDescription);
            nook.setType(NookType.FLAT);


            WebElement addressElement= driver.findElement(By.cssSelector(".apartment-info__sub-line.apartment-info__sub-line_large"));
            String addressString=addressElement.getText();

            Address address = getAddress(addressString, nook);
          // addressRepository.save(address);
            nook.setAddress(address);
            addressRepository.save(address);
           // nookRepository.save(nook);

            Owner owner = new Owner();
            owner.setId(UUID.randomUUID().toString());
            setOwnerType(owner, ownerType);
            List<Phone> phones=new ArrayList<>();


            WebElement infoPhone=driver.findElement(By.id("apartment-phones"));
            List<WebElement> liInfo=infoPhone.findElements(By.tagName("a"));
            for (WebElement el: liInfo) {
                String phone=el.getText();
                Phone phone1=new Phone();
                phone1.setNumber(phone);
                phoneRepository.save(phone1);
                phones.add(phone1);
            }
           owner.setPhoneNumber(phones);

            WebElement webOwnerName= driver.findElement(By.xpath("//div[@class='apartment-info__sub-line apartment-info__sub-line_extended']"));
            String ownerName=webOwnerName.getText();
            owner.setName(ownerName);
            ownerRepository.save(owner);


            Cost cost=new Cost();

            String costInBy = span.get(0).getText();
            stringToCostByn(cost, costInBy);
            String costInUsd = span.get(2).getText();
            stringToCostUsd(cost, costInUsd);
            costRepository.save(cost);

            Operation operation=new Operation();
            operation.setType(OperationType.RENT);
            try {
                WebElement descriptionOfOperation=driver.findElement(By.xpath("//div[@class='apartment-info__sub-line apartment-info__sub-line_extended-bottom']"));
                String stringDescriptionOfOperation=descriptionOfOperation.getText();
                operation.setDescription(stringDescriptionOfOperation);
            }
            catch (org.openqa.selenium.NoSuchElementException e){
                operation.setDescription(null);
            }

            operation.setNook(nook);
            operation.setOwner(owner);
            operation.setCost(cost);

            operationRepository.save(operation);


//
//            String quantityRooms = span.get(4).getText();
//            System.out.println(quantityRooms);
//            Nook nook = new Nook();
//            nook.setId(UUID.randomUUID().toString());
//            setQuantityRooms(nook, quantityRooms);
//           // nookRepository.save(nook);
//
//
//            Owner owner = new Owner();
//            String ownerType = span.get(5).getText();
//            System.out.println(ownerType);
//            owner.setId(UUID.randomUUID().toString());
//            setOwnerType(owner, ownerType);
//            //ownerRepository.save(owner);
//
//
//
//            System.out.println(costInBy);
//            System.out.println(costInUsd);
//            System.out.println(quantityRooms);
//            System.out.println(owner);
//            WebElement description = driver.findElement(By.className("apartment-info"));
//            List<String> existItems = description.findElements(By.className("apartment-options__item")).stream().map(WebElement::getText).toList();
//            String descriptionText = description.findElement(By.className("apartment-info__sub-line_extended-bottom")).getText();
//            String fullAddress = description.findElement(By.className("apartment-info__sub-line_large")).getText();
//
//
//            Operation operation = new Operation();
//            operation.setId(UUID.randomUUID().toString());
//            operation.setDescription(descriptionText);
//          //  operation.setNook(nook);
//          //  operation.setOwner(owner);
//            List<Cost> costs=new ArrayList<>();
//            costs.add(costBYN);
//            costs.add(costUSD);
//           // operation.setCosts(costs);
//            operation.setType(OperationType.RENT);
//            operationRepository.save(operation);
//
//            System.out.println(fullAddress);
//            Address address = new Address();
//            address.setId(fullAddress);
//
//            existItems.forEach(s -> {
//                Item item = new Item();
//                item.setValue(s);
//                itemRepository.save(item);
//            });


        });
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