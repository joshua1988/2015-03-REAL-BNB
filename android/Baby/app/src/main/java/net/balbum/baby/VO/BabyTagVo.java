package net.balbum.baby.VO;

import java.io.File;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class BabyTagVo {
    public File image;
    public String babyImg;
    public String name;
    public boolean isSelected;
    public Long bId;

    public BabyTagVo(String babyImg, Long bId, boolean isSelected, String name) {
        this.babyImg = babyImg;
        this.bId = bId;
        this.isSelected = isSelected;
        this.name = name;
    }

    public BabyTagVo(File image, String name) {
        this.image = image;
        this.name = name;
    }
}
