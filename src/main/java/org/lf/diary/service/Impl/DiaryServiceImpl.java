package org.lf.diary.service.Impl;

import org.hibernate.service.spi.ServiceException;
import org.lf.diary.model.Diary;
import org.lf.diary.model.DiaryBook;
import org.lf.diary.repository.DiaryBookRepository;
import org.lf.diary.repository.DiaryRepository;
import org.lf.diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


/**
 * @Author: Jvinh
 * @DateTime: 2020/1/18 16:29
 * @Description: TODO
 */
@Service
public class DiaryServiceImpl implements DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryBookRepository diaryBookRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveDiary(Diary diary) {
        /**
         * id 为空 则为 插入操作
         * id 不为空 则为保存操作
         */
        if(diary.getId() == null) {
            diary.setCreateTime(new Date());
            diary.setModifyTime(new Date());
            diary.setLikeCount(0);
            diary.setCommentCount(0);
            diaryRepository.save(diary);
        }else {

        }
    }

    @Override
    public List<Diary> getDiaryList(Long id) {

        List<Diary> diaryList = diaryRepository.findAll((root, query, criteriaBuilder) -> {
            /*List<Predicate> predicates = new ArrayList<>();*/
            Predicate predicate = criteriaBuilder.equal(root.get("creator"), id);
            query.where(predicate);
            query.orderBy(criteriaBuilder.desc(root.get("createTime")));
            return null;
        });

        diaryList.forEach(diary -> {
            DiaryBook diaryBook = diaryBookRepository.findById(diary.getParentId()).orElseThrow(() -> new ServiceException("此id不存在"));
            diary.setDiaryBook(diaryBook);
        });
        return diaryList;
    }

    @Override
    /**
     * 有待优化....
     */
    public Page<Diary> list(Pageable pageable) {

        Page<Diary> diaryList = diaryRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("isShow"), "1");
            query.where(predicate);
            query.orderBy(cb.desc(root.get("createTime")));
            return null;
        },pageable);
        return diaryList;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void updatePraise(Integer likeCount, Long id) {
        diaryRepository.updatePraise(likeCount,id);
    }

    @Override
    public Long countDiary() {
        return diaryRepository.count();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void changeState(Integer isShow,Long id) {
        diaryRepository.updateIsShow(isShow,id);
    }


}
