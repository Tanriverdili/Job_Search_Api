package com.example.api.repository;
import com.example.api.entity.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity,Long> {
    List<JobEntity> findTop5ByTitleContainingIgnoreCase(String keyword);
    @Query("""
SELECT j FROM JobEntity j
WHERE
(:keyword IS NULL OR
 LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
 LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
AND
(:location IS NULL OR j.location = :location)
AND
(:jobType IS NULL OR j.jobType = :jobType)
ORDER BY j.createdAt DESC
""")
    Page<JobEntity> searchJobs(
            @Param("keyword") String keyword,
            @Param("location") String location,
            @Param("jobType") String jobType,
            Pageable pageable
    );
}
