package net.balbum.baby.VO;

import java.util.List;

/**
 * Created by hyes on 2015. 11. 17..
 */
public class CardFormVo {
    public Long cId;
    public String token;
    public List<Long> babies;
    public String content;
    public String modifiedDate;


    public CardFormVo(String content) {
        this.content = content;
    }

    public CardFormVo(Long cId, String token, List<Long> babies, String content, String modifiedDate) {
        this.cId = cId;
        this.token = token;
        this.babies = babies;
        this.content = content;
        this.modifiedDate = modifiedDate;
    }

    public CardFormVo(List<Long> babies, String modifiedDate, String content) {
        this.babies = babies;
        this.modifiedDate = modifiedDate;
        this.content = content;
    }
}
