package pl.sztukakodu.bookaro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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


    /*@Bean  tak nie wstrzykujemy typow prostych -> lepiej przez properties
    String query() {
        return "Pan";
    }*/

}
