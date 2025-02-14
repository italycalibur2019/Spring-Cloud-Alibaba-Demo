package com.italycalibur.ciallo.basic.controller;

import com.italycalibur.ciallo.basic.dto.GoodsDTO;
import com.italycalibur.ciallo.basic.service.impl.GoodsService;
import com.italycalibur.ciallo.common.domain.Result;
import com.italycalibur.ciallo.common.models.entity.GoodsPO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 货物控制器
 * @author dhr
 * @date 2025-02-08 13:07:18
 * @version 1.0
 */
@RestController
@RequestMapping("/basic/goods")
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    @GetMapping("/list")
    public Result<List<GoodsPO>> list() {
        return Result.ok(goodsService.list());
    }

    @GetMapping("/get/{id}")
    public Result<GoodsPO> get(@PathVariable("id") Long id) {
        return Result.ok(goodsService.getById(id));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody GoodsDTO params) {
        GoodsPO goods = new GoodsPO();
        goods.setGoodsCode(params.getGoodsCode());
        goods.setGoodsName(params.getGoodsName());
        goodsService.save(goods);
        return Result.ok("添加成功！");
    }

    @PutMapping("/update/{id}")
    public Result<String> update(@PathVariable("id") Long id, @RequestBody GoodsDTO params) {
        GoodsPO goods = goodsService.getById(id);
        if (goods != null) {
            goods.setGoodsName(params.getGoodsName());
            goodsService.updateById(goods);
            return Result.ok("修改成功！");
        }else {
            return Result.ok("修改失败！");
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable("id") Long id) {
        goodsService.removeById(id);
        return Result.ok("删除成功！");
    }
}
