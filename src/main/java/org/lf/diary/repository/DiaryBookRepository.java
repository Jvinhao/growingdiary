package org.lf.diary.repository;

import org.lf.diary.model.DiaryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/15 16:52
 * @Description: TODO
 */
@Repository
public interface DiaryBookRepository extends JpaRepository<DiaryBook,Long>, JpaSpecificationExecutor<DiaryBook> {

//   Page<DiaryBook> findAllDiaryBook(Pageable pageable,Long id);

    List<DiaryBook> findAllByCreator(Long id);

}
