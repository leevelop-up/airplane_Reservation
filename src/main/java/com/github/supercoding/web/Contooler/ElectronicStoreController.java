package com.github.supercoding.web.Contooler;

import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.BuyOrder;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
public class ElectronicStoreController {

    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ElectronicStoreItemService electronicStoreItemService;

    @GetMapping("/items")
    public List<Item> findAllItem() {
        log.info("GET /ites 요청이 들어왔습니다.");
        List<Item> items = electronicStoreItemService.findAllItem();
        log.info("GET /ites 응답"+items);
        return items;
    }
    @ApiOperation(("모든 item 등록"))
    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody) {

        Integer itemId = electronicStoreItemService.saveItem(itemBody);
        return "ID :" + itemId;
    }
    @ApiOperation(("모든 item id로 검색"))
    @GetMapping("/items/{id}")
    public Item findItemByPathId(
            @ApiParam(name="id", value = "item id", example = "1")
            @PathVariable String id) {
       return electronicStoreItemService.findItemById(id);
    }
    @ApiOperation(("모든 item id로 검색(쿼리)"))
    @GetMapping("/items-query")
    public Item findItemByQueryId(
            @ApiParam(name="ids", value = "item ids", example = "[1,2]")
            @RequestParam("id") String id) {
        return electronicStoreItemService.findItemById(id);
    }
    @ApiOperation(("모든 item ids로 검색(쿼리)"))
    @GetMapping("/items-querys")
    public List<Item> findItemByQueryIds(
            @ApiParam(name="id", value = "item id 쿼리문", example = "1")
            @RequestParam("id") List<String> ids) {

        log.info("GET /ites 요청이 들어왔습니다.");
        List<Item> items = electronicStoreItemService.findItemsByIds(ids);
        log.info("GET /ites 응답"+items);
        return items;
    }
    @ApiOperation(("모든 item id로 삭제"))
    @DeleteMapping("/items/{id}")
    public String deleteItemByPathId(
            @ApiParam(name="id", value = "item id삭제", example = "1")
            @PathVariable String id){
        electronicStoreItemService.deleteItem(id);

        return "object with id="+id+"has been deleted";
    }
    @ApiOperation(("모든 item id로 업데이트"))
    @PutMapping("/items/{id}")
    public Item updateItea(@PathVariable String id,@RequestBody ItemBody itemBody){
        return electronicStoreItemService.updateItemEntity(id,itemBody);

    }

    @ApiOperation(("모든 item 검색"))
    @PostMapping("/items/buy")
    public String buyItem(@RequestBody BuyOrder buyOrder){
        Integer orderItemNums = electronicStoreItemService.buyItems(buyOrder);
        return "요청하신 Item 중"+orderItemNums;
    }

    @ApiOperation(("모든 item types 검색(쿼리)"))
    @GetMapping("/items-types")
    public List<Item> findItemByQueryTypes(
            @ApiParam(name="id", value = "item id 쿼리문", example = "[1,2,3]")
            @RequestParam("type") List<String> types) {

        log.info("GET /tems-types 요청이 들어왔습니다.");
        List<Item> items = electronicStoreItemService.findItemsByTypes(types);
        log.info("GET /tems-types 응답"+items);
        return items;
    }

    @ApiOperation(("모든 item ids로 검색(쿼리)"))
    @GetMapping("/items-prices")
    public List<Item> findItemByQueryPrices(
            @RequestParam("max") Integer maxValue) {

        return electronicStoreItemService.findItemsOrderByPrices(maxValue);
    }
    @ApiOperation("pagination지원")
    @GetMapping("/items-page")
    public Page<Item> findItemsPagination(Pageable pageable){
        return electronicStoreItemService.findAllWithPageable(pageable);
    }

    @ApiOperation("pagination지원2")
    @GetMapping("/items-types-page")
    public Page<Item> findItemsPagination(@RequestParam("type") List<String> types, Pageable pageable){
        return electronicStoreItemService.findAllWithPageable(types,pageable);
    }

}

