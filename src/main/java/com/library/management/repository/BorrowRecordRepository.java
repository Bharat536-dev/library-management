package com.library.management.repository;

import com.library.management.model.BorrowRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM BorrowRecord br WHERE br.book.id = :bookId")
    void deleteByBookId(@Param("bookId") Long bookId);
}
