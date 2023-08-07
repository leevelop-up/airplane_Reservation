package com.github.supercoding.web.Contooler.sample;

import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.Item;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("api/sample")
@RequiredArgsConstructor
@Slf4j
public class Chapter109Controller {
    private final ElectronicStoreItemService electronicStoreItemService;

    @ApiOperation("가성비 싼 거부터 검색")
    @GetMapping("/items-prices")
    public List<Item> findItemsByPricing(
            HttpServletRequest httpServletRequest
            //@RequestParam("max") Integer maxPrice
    ){
        Integer maxPrice = Integer.valueOf(httpServletRequest.getParameter("max"));
//        log.info("GET /items-prices 요청이 들어왔습니다.");
        List<Item> items = electronicStoreItemService.findItemsOrderByPrices(maxPrice);
//        log.info("GET /items-prices 응답: " + items);
        return items;
    }

}
