import org.example.config.SpringConfig;
import org.example.dao.BookDao;
import org.example.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppForAnnotation {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
//        BookDao bookDao = (BookDao) ctx.getBean("bookDao");
        BookService bookService = ctx.getBean(BookService.class);
//        System.out.println(bookDao);
//        System.out.println(bookService);
        bookService.save();
    }
}
