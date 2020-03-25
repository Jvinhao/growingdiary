package org.lf.diary.service;

import org.lf.diary.model.DiaryBookVO;
import org.lf.diary.model.DiaryBook;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/15 16:58
 * @Description: TODO
 */
public interface DiaryBookService {
    List<DiaryBook> findAll(Long id);

    void saveDiaryBook(DiaryBook diaryBook);

    Boolean delDiaryBook(Long id);

    List<DiaryBookVO> getAllTitle(Long id);
}
