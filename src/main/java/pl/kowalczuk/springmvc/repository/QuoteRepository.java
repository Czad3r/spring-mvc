package pl.kowalczuk.springmvc.repository;

import org.springframework.stereotype.Component;
import pl.kowalczuk.springmvc.domain.Quote;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuoteRepository {
    List<Quote> quoteList;

    public QuoteRepository() {
        this.quoteList = new ArrayList<>();
        Quote quote = new Quote();
        quote.setAuthor("Andrzej Sapkowski");
        quote.setSource("Wieża jaskółki");
        quote.setContent("Lepiej bez celu iść naprzód niż bez celu stać w miejscu, a z pewnością o niebo lepiej, niż bez celu się cofać...");
        quoteList.add(quote);
        quoteList.add(quote);
        quoteList.add(quote);
        quoteList.add(quote);
        quoteList.add(quote);
        quoteList.add(quote);
    }


    public List<Quote> findAll() {
        return quoteList;
    }
}
