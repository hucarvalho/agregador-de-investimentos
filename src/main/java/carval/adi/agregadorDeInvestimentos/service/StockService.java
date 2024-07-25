package carval.adi.agregadorDeInvestimentos.service;

import carval.adi.agregadorDeInvestimentos.dto.StockCreateRecordDto;
import carval.adi.agregadorDeInvestimentos.dto.UserCreateRecordDto;
import carval.adi.agregadorDeInvestimentos.entity.Stock;
import carval.adi.agregadorDeInvestimentos.entity.User;
import carval.adi.agregadorDeInvestimentos.repository.StockRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public String create(StockCreateRecordDto body) {
        Stock stock = new Stock(
                body.id(),
                body.description()
        );
        var stockCreated = stockRepository.save(stock);

        return stockCreated.getId();
    }
}
