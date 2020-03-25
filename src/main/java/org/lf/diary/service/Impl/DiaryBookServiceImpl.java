package org.lf.diary.service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.lf.diary.model.DiaryBookVO;
import org.lf.diary.model.DiaryBook;
import org.lf.diary.repository.DiaryBookRepository;
import org.lf.diary.repository.DiaryRepository;
import org.lf.diary.service.DiaryBookService;
import org.lf.diary.utils.ImageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/15 16:58
 * @Description: TODO
 */
@Service
public class DiaryBookServiceImpl implements DiaryBookService {

    @Autowired
    private DiaryBookRepository diaryBookRepository;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private DiaryRepository diaryRepository;

    @Override
    public List<DiaryBook> findAll(Long id) {
        return diaryBookRepository.findAllByCreator(id);
    }

    @Override
    public void saveDiaryBook(DiaryBook diaryBook) {
        /**
         * id 为空则执行插入操作
         * id 不为空则执行保存操作 即更新操作
         */
        if(diaryBook.getId() == null) {
            diaryBook.setCreateTime(new Date());
            diaryBook.setModifyTime(diaryBook.getCreateTime());
            diaryBookRepository.save(diaryBook);
        }else if(diaryBook.getId() != null){
            diaryBook.setModifyTime(new Date());
            Optional<DiaryBook> optionalDiaryBook = diaryBookRepository.findById(diaryBook.getId());
            if(optionalDiaryBook.isPresent()) {
                DiaryBook book = optionalDiaryBook.get();
                diaryBook.setCreateTime(book.getCreateTime());
                if(StringUtils.equals("",diaryBook.getCover())) {
                    diaryBook.setCover(book.getCover());
                }
                /*//删除原有的封面
                if(!StringUtils.equals("",book.getCover())) {
                    imageUtils.removeImage(book.getCover());
                }*/
                diaryBookRepository.save(diaryBook);
            }

        }

    }

    @Override
    public Boolean delDiaryBook(Long id) {

        /**
         * 先判断id是否存在
         */
        Optional<DiaryBook> diaryBookOptional = diaryBookRepository.findById(id);
        if (diaryBookOptional.isPresent()) {
            DiaryBook diaryBook = diaryBookOptional.get();
            //删除与此日记本有关的所有日记
            diaryRepository.deleteAllByParentId(id);
            String cover = diaryBook.getCover();
            try {
                if(!StringUtils.equals("",cover)) {
                    imageUtils.removeImage(cover);
                }
            } catch (IOException | MyException e) {
                e.printStackTrace();
            }
            diaryBookRepository.deleteById(id);
            return true;
        } else {

            return false;
        }
    }

    @Override
    public List<DiaryBookVO> getAllTitle(Long id) {
        List<DiaryBook> diaryBooks = diaryBookRepository.findAllByCreator(id);
        List<DiaryBookVO> diaryBookVOList = new ArrayList<>();
        diaryBooks.forEach(diaryBook -> {
            DiaryBookVO diaryBookVO = new DiaryBookVO();
            BeanUtils.copyProperties(diaryBook,diaryBookVO);
            diaryBookVOList.add(diaryBookVO);
        });


        return diaryBookVOList;
    }
}
