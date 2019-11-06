package brishko.com.spring5webapp.bootstrap;

import brishko.com.spring5webapp.model.Author;
import brishko.com.spring5webapp.model.Book;
import brishko.com.spring5webapp.model.Publisher;
import brishko.com.spring5webapp.repositories.AuthorRepository;
import brishko.com.spring5webapp.repositories.BookRepository;
import brishko.com.spring5webapp.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

// Application listener, on started, on ready... onfinished
// ContextRefreshedEvent - Event raised when an {@code ApplicationContext} gets initialized or refreshed.
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    private void initData() {
        Author eric = new Author("Eric", "Evans");
        Publisher publisher = new Publisher("Publisher 1", "Address 1");
        Book ddd = new Book("Domain Driven Design", "1234", publisher);
        // only need to add the author to the book since the join happens there
        ddd.getAuthors().add(eric);

        authorRepository.save(eric);
        publisherRepository.save(publisher);
        bookRepository.save(ddd);

        Author rod = new Author("Rod", "Johnson");
        Publisher publisher2 = new Publisher("Publisher 2", "Address 2");
        Book noEJB = new Book("J2EE Dev without EJB", "54321", publisher2);
        noEJB.getAuthors().add(rod);

        publisherRepository.save(publisher2);
        authorRepository.save(rod);
        bookRepository.save(noEJB);
    }
}
