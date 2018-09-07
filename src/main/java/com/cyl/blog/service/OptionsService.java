package com.cyl.blog.service;

import com.cyl.blog.backend.vo.GeneralOption;

public interface OptionsService extends BlogBaseService{

    String getOptionValue(String name);

    String getOptionValueForUpdate(String name);

    void updateOptionValue(String name, String value);

    GeneralOption getGeneralOption();
}
