package pl.sztukakodu.bookaro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookaroApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(BookaroApplication.class, args);
        //    System.out.println(context); // ustawic brakpointa i wywolac w evaluate context.getBean(SchoolCatalogRepository.class) aby znalesc nazwe beanow i uzyc tej nazwy w konstrutorze CatalogService z @Qualifier("schoolCatalogRepository")


    }

   /* @Bean
    CatalogRepository catalogRepository() {
        Random random = new Random();
        if (random.nextBoolean()) {
            return new SchoolCatalogRepository();

        } else {
            return new BestsellerCatalogRepository();
        }

    }*/


    /*@Bean
    String query() {
        return "Pan";
    }*/

}
