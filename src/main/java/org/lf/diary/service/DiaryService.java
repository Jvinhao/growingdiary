package org.lf.diary.service;

import org.lf.diary.model.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/18 16:28
 * @Description: TODO
 */
public interface DiaryService {
    /**
     * 保存日记
     * @param diary
     */
    void saveDiary(Diary diary);

    /**
     * 获取指定日记
     * @param id
     * @return
     */
    List<Diary> getDiaryList(Long id);

    /**
     * 查询所有的日记
     * @param  pageable
     * @return
     */
    Page<Diary> list(Pageable pageable);

    /**
     * 更新点赞数量
     * @param likeCount
     * @param id
     */
    void updatePraise(Integer likeCount, Long id);

    /**
     * 统计日记的个数
     * @return
     */
    Long countDiary();

    /**
     * 修改日记状态
     * @param isShow
     * @param id
     */
    void changeState(Integer isShow, Long id);

    /**
     * 删日记
     * @param id
     */
    void delDiary(Long id);
}
