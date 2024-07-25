package carval.adi.agregadorDeInvestimentos.controller;

import carval.adi.agregadorDeInvestimentos.dto.StockCreateRecordDto;
import carval.adi.agregadorDeInvestimentos.entity.Stock;
import carval.adi.agregadorDeInvestimentos.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {
    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Stock> create(@RequestBody StockCreateRecordDto body)
    {
        var id = service.create(body);
        return ResponseEntity.created(URI.create("/v1/stocks/" + id)).build();
    }
   }