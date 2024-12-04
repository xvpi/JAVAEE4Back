package com.xvpi.stugraman.strategy;

import com.xvpi.stugraman.entity.Student;

import java.util.List;

// Context 类用于管理不同的策略
public class Context<T> {

    private DisplayStrategy displayStrategy;
    private SortingStrategy sortingStrategy;
    private SearchStrategy searchStrategy;  // 假设存在搜索策略

    // 设置显示策略
    public void setDisplayStrategy(DisplayStrategy displayStrategy) {
        this.displayStrategy = displayStrategy;
    }

    // 设置排序策略
    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    // 设置搜索策略
    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    // 执行显示策略
    public void executeDisplay(T data) {
        if (displayStrategy != null) {
            displayStrategy.display(data);
        } else {
            System.out.println("未设置显示策略！");
        }
    }

    // 执行排序策略
    public List<T> executeSorting(List<T> data) {
        if (sortingStrategy != null) {
            return sortingStrategy.sort(data);
        } else {
            System.out.println("未设置排序策略！");
            return data;
        }
    }

    // 执行搜索策略
    public void executeSearch() {
        if (searchStrategy != null) {
            searchStrategy.search();
        } else {
            System.out.println("未设置搜索策略！");
        }
    }
}
