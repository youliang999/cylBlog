package com.cyl.blog.util;

import com.cyl.blog.entity.Tag;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by youliang.cheng on 2018/8/3.
 */
@Data
public class TagVO implements Serializable {
    private static final long serialVersionUID = 8622104523962614304L;
    private Tag tag;
    private int weight=0;
    private int fontSize;

    public void setWeight(int weight) {
        this.weight = weight;
        this.fontSize = caclucate(weight);
    }


    private int caclucate(int weight) {
        int fontSize = 0;
        if(weight < 50) {
            fontSize = 10;
        }
        if(weight < 100 && weight > 50) {
            fontSize = 12;
        }
        if(weight > 100 && weight < 500) {
            fontSize = 14;
        }
        if(weight > 500 && weight < 1000) {
            fontSize = 16;
        }
        if(weight > 1000 && weight < 10000) {
            fontSize = 18;
        }
        if(weight > 10000) {
            fontSize = 20;
        }
        return fontSize;
    }

    public void addWeight(int addWeight) {
        weight += addWeight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TagVO) {
            TagVO v = (TagVO) obj;
            if (v.getTag().getName() == this.getTag().getName()) {
                return true;
            }
            return false;
        }
        return false;
    }

}
