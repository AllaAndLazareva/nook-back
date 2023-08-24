package by.soykin.nook.nookback;

import by.soykin.nook.nookback.jpa.entities.*;
import by.soykin.nook.nookback.jpa.entities.enums.*;
import by.soykin.nook.nookback.jpa.repository.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class NookBackApplicationTests {

    private Set<String> setUrls = new HashSet<>();
    private List<String> listUrls = new ArrayList<>();

    private static String FOLDER_PATH="C:\\image\\";



    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CostRepository costRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private OperationRepository operationRepository;


    @Autowired
    ImageRepository imageRepository;

    @Test
    void contextLoads() {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\USER\\Desktop\\chromedriver\\chromedriver-win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://r.onliner.by/ak/");
        driver.manage().window().maximize();

        WebDriver.Navigation navigate = driver.navigate();

        WebElement paginationPanel = driver.findElement(By.className("pagination"));
        List<WebElement> pagination__secondary = paginationPanel.findElements(By.className("pagination__secondary"));
        WebElement element = driver.findElement(By.className("classifieds-list"));
        List<WebElement> quantityOfPages = driver.findElements(By.className("pagination-pages__item"));
        List<WebElement> classified = element.findElements(By.className("classified"));
        //Цикл переключения страниц
        for (int i = 0; i < quantityOfPages.size(); i++) {
            List<String> urls = new ArrayList<>();

            //Цикл переключения квартиры на странице
            for (int j = 0; j < classified.size(); j++) {
                WebElement element2 = driver.findElement(By.className("classifieds-list"));
             //   WebElement link1 = wait.until(ExpectedConditions.elementToBeClickable(By.className("classified")));
                List<WebElement> classified2 = element2.findElements(By.className("classified"));
                String href;
                try {

                    href = classified2.get(j).getAttribute("href");
                } catch (org.openqa.selenium.StaleElementReferenceException ex) {

                    WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.className("classified")));
                    href = link.getAttribute("href");
                }

                urls.add(href);
            }
            setUrls.addAll(urls);
            WebElement buttonNext36 = wait
                    .until(ExpectedConditions
                            .elementToBeClickable(By.xpath("//*[@id=\"search-filter-results\"]/div[1]/div/div[3]")));
            buttonNext36.click();

        }
        listUrls.addAll(setUrls);
        System.out.println(listUrls.size());
//Обходим все урлы квартир и скачиваем инфо
        for (int i = 0; i < listUrls.size(); i++) {
            String url = listUrls.get(i);
            WebElement panelWithCost;
            try {
                driver.navigate().to(url);
                panelWithCost = driver.findElement(By.className("apartment-bar"));

            } catch (org.openqa.selenium.NoSuchElementException e) {
                driver.navigate().to(listUrls.get(i + 1));
                i = i + 1;
                panelWithCost = driver.findElement(By.className("apartment-bar"));

            }
            //Images!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            List<Image> imageList = getImages(driver);
            //The end of images
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

            LocalDateTime timeOfAccommodation = getTime(driver);
            nook.setTimeOfAccommodation(timeOfAccommodation);

            setTimeOfEditing(driver, nook);
            if(nook.getTimeOfEditing()==null){
                nook.setTimeOfEditing(nook.getTimeOfAccommodation());
            }
            nook.setImages(imageList);


            WebElement addressElement = driver.findElement(By.cssSelector(".apartment-info__sub-line.apartment-info__sub-line_large"));
            String addressString = addressElement.getText();

            Address address = getAddress(addressString, nook);

            nook.setAddress(address);
            addressRepository.save(address);


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
        }


        driver.quit();

    }

    @NotNull
    private List<Image> getImages(WebDriver driver) {
        List<Image> imageList=new ArrayList<>();
        WebElement images = driver.findElement(By.className("apartment-cover__thumbnails-inner"));
        //images of flat
        List<WebElement> imageElements = images.findElements(By.className("apartment-cover__thumbnail"));
        URL imageURL = null;
        for(WebElement myElement : imageElements) {
            String j = myElement.getAttribute("style");
            String urlOfImage=null;
            Pattern pattern=null;


            if(j.contains(".jpg")){
                pattern=Pattern.compile("https.+jpg");
            }
            else if(j.contains(".jpeg")){
                pattern=Pattern.compile("https.+jpeg");
            }
            else if(j.contains(".png")){
                pattern=Pattern.compile("https.+png");
            }
            else if(j.contains(".gif")){
                pattern=Pattern.compile("https.+gif");
            }
            Matcher matcher = pattern.matcher(j);

            while (matcher.find()){
                urlOfImage = matcher.group();
            }
            try {
                imageURL=new URL(urlOfImage);
                BufferedImage saveImage = ImageIO.read(imageURL);
                String pathName="C:\\image\\"+new Date().getTime();
                String pathNameJPG="C:\\image\\"+new Date().getTime() + ".jpg";
                String pathNameJPEG="C:\\image\\"+new Date().getTime() + ".jpeg";
                String pathNamePNG="C:\\image\\"+new Date().getTime() + ".png";
                String pathNameGIF="C:\\image\\"+new Date().getTime() + ".gif";

                if(j.contains(".jpg")){
                    //download image to the workspace where the project is, save picture as picture.png (can be changed)
                    //ImageIO.write(saveImage, "jpg", new File(pathName));
                    ImageIO.write(saveImage, "jpg", new File(pathNameJPG));
                    Image image=new Image();
                    image.setLocation(pathName);
                    image.setType(ImageType.jpg);
                   // image.setLocation(pathNameJPEG);
                    imageList.add(image);
                    imageRepository.save(image);

                }
                else if(j.contains(".jpeg")){
                    //download image to the workspace where the project is, save picture as picture.png (can be changed)
                    ImageIO.write(saveImage, "jpeg", new File(pathNameJPEG));
                    Image image=new Image();
                    image.setLocation(pathName);
                    image.setType(ImageType.jpeg);
                    imageList.add(image);
                    imageRepository.save(image);
                }
                else if(j.contains(".png")){
                    //download image to the workspace where the project is, save picture as picture.png (can be changed)
                    ImageIO.write(saveImage, "png", new File(pathNamePNG));
                    Image image=new Image();
                    image.setLocation(pathName);
                    image.setType(ImageType.png);
                    imageList.add(image);
                    imageRepository.save(image);
                }
                else if(j.contains(".gif")){
                    //download image to the workspace where the project is, save picture as picture.png (can be changed)
                    ImageIO.write(saveImage, "gif", new File(pathNameGIF));
                    Image image=new Image();
                    image.setLocation(pathName);
                    image.setType(ImageType.gif);
                    imageList.add(image);
                    imageRepository.save(image);
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imageList;
    }

    private void setTimeOfEditing(WebDriver driver, Nook nook) {
        try {
            WebElement timeElement= driver.findElement(By.id("apartment-updated-at"));
            String timeStr=timeElement.getText();
            String[] wordsOfTime = timeStr.split("\\s");
            String number = wordsOfTime[0];
            LocalDateTime timeOfEditing=null;
            long l=Long.parseLong(number);
            if(timeStr.contains("минут")){
                timeOfEditing=LocalDateTime.now().minusMinutes(l);
            }
            else if(timeStr.contains("час")){
                timeOfEditing=LocalDateTime.now().minusHours(l);
            }
            else if(timeStr.contains("дня") | timeStr.contains("дней")| timeStr.contains("день")  ){
                timeOfEditing=LocalDateTime.now().minusDays(l);
            }
            else if(timeStr.contains(" месяц")){
                timeOfEditing=LocalDateTime.now().minusMonths(l);
            }
            nook.setTimeOfEditing(timeOfEditing);
        }
        catch (org.openqa.selenium.NoSuchElementException e){
         nook.setTimeOfEditing(null);
        }
    }

    @Nullable
    private LocalDateTime getTime(WebDriver driver) {
        WebElement timeElement= driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div[2]/div[5]/div[3]/div/div[2]/div[1]"));
        String timeStrOfAccommodation=timeElement.getText();
        String[] wordsOfTime = timeStrOfAccommodation.split("\\s");
        String number = wordsOfTime[1];
        long t=Long.parseLong(number);
        LocalDateTime timeOfAccommodation=null;
        if(timeStrOfAccommodation.contains("минут")){
            timeOfAccommodation=LocalDateTime.now().minusMinutes(t);
        }
        else if(timeStrOfAccommodation.contains("час")){
            timeOfAccommodation=LocalDateTime.now().minusHours(t);
        }
        else if(timeStrOfAccommodation.contains("дня") | timeStrOfAccommodation.contains("дней")| timeStrOfAccommodation.contains("день")  ){
            timeOfAccommodation=LocalDateTime.now().minusDays(t);
        }
        else if(timeStrOfAccommodation.contains(" месяц")){
            timeOfAccommodation=LocalDateTime.now().minusMonths(t);
        }
        return timeOfAccommodation;
    }

    @NotNull
    private Address getAddress(String addressString, Nook nook) {
        Address address;
        Optional<Address> addressInDataBase = addressRepository.findByValue(addressString);
        String addressStringInDataBase = null;
        if (addressInDataBase.isPresent()) {
            Address address1 = addressInDataBase.get();
            addressStringInDataBase = address1.getValue();
        }
        if (!addressString.equals(addressStringInDataBase)) {
            address = new Address();
            address.setId(UUID.randomUUID().toString());
            address.setValue(addressString);
            List<Nook> addressOfNooks = new ArrayList<>();
            addressOfNooks.add(nook);
            address.setNooks(addressOfNooks);
        } else {
            address = addressRepository.findByValue(addressString).orElseThrow();
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





    private static void setOwnerType(Owner owner, String ownerType) {
        switch (ownerType) {
            case "Собственник":
                owner.setOwnerType(OwnerType.OWNER);
                break;
            case "Агентство":
                owner.setOwnerType(OwnerType.REALTOR);
                break;
        }
    }

//    @NotNull
//    private List<Image> uploadImageToFileSystem(WebDriver driver, MultipartFile file) {
//        String filepath=FOLDER_PATH+file.getOriginalFilename();
//        List<Image> imageList=new ArrayList<>();
//        WebElement images = driver.findElement(By.className("apartment-cover__thumbnails-inner"));
//        //images of flat
//        List<WebElement> imageElements = images.findElements(By.className("apartment-cover__thumbnail"));
//        URL imageURL = null;
//        for(WebElement myElement : imageElements) {
//            String j = myElement.getAttribute("style");
//            String urlOfImage=null;
//            Pattern pattern=null;
//
//
//            if(j.contains(".jpg")){
//                pattern=Pattern.compile("https.+jpg");
//            }
//            else if(j.contains(".jpeg")){
//                pattern=Pattern.compile("https.+jpeg");
//            }
//            else if(j.contains(".png")){
//                pattern=Pattern.compile("https.+png");
//            }
//            else if(j.contains(".gif")){
//                pattern=Pattern.compile("https.+gif");
//            }
//            Matcher matcher = pattern.matcher(j);
//
//            while (matcher.find()){
//                urlOfImage = matcher.group();
//            }
//            try {
//                imageURL=new URL(urlOfImage);
//
//                Image image=imageRepository.save(Image.builder()
//                        .name(file.getOriginalFilename())
//                        .type(file.getContentType())
//                        .filepath(filepath).build());
//                BufferedImage saveImage = ImageIO.read(imageURL);
//                String pathNameJPG="C:\\image\\"+new Date().getTime() + ".jpg";
//                String pathNameJPEG="C:\\image\\"+new Date().getTime() + ".jpeg";
//                String pathNamePNG="C:\\image\\"+new Date().getTime() + ".png";
//                String pathNameGIF="C:\\image\\"+new Date().getTime() + ".gif";
//
//                if(j.contains(".jpg")){
//                    //download image to the workspace where the project is, save picture as picture.png (can be changed)
//                    ImageIO.write(saveImage, "jpg", new File(pathNameJPG));
//                    Image image=new Image();
//                    image.setLocation(pathNameJPEG);
//                    imageList.add(image);
//                    imageRepository.save(image);
//
//                }
//                else if(j.contains(".jpeg")){
//                    //download image to the workspace where the project is, save picture as picture.png (can be changed)
//                    ImageIO.write(saveImage, "jpeg", new File(pathNameJPEG));
//                    Image image=new Image();
//                    image.setLocation(pathNameJPEG);
//                    imageList.add(image);
//                    imageRepository.save(image);
//                }
//                else if(j.contains(".png")){
//                    //download image to the workspace where the project is, save picture as picture.png (can be changed)
//                    ImageIO.write(saveImage, "png", new File(pathNamePNG));
//                    Image image=new Image();
//                    image.setLocation(pathNamePNG);
//                    imageList.add(image);
//                    imageRepository.save(image);
//                }
//                else if(j.contains(".gif")){
//                    //download image to the workspace where the project is, save picture as picture.png (can be changed)
//                    ImageIO.write(saveImage, "gif", new File(pathNameGIF));
//                    Image image=new Image();
//                    image.setLocation(pathNameGIF);
//                    imageList.add(image);
//                    imageRepository.save(image);
//                }
//
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return imageList;
//    }
}


