package com.lc.car.service;

import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/15
 * @description
 */
public interface ICarCarStatusService {
    List<Integer> getCarlotChartList(String carparkId);

    List<Integer> getCarStaChartList();
}
