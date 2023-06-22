package com.bolotov.service;

import com.bolotov.entity.KitItem;

import java.util.List;

public interface KitItemService {
    List<KitItem> setSimpleKit();

    List<KitItem> setExtendedKit();
}
