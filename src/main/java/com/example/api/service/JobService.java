package com.example.api.service;
import com.example.api.entity.JobEntity;
import com.example.api.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public JobEntity createJob(JobEntity jobEntity) {
        return jobRepository.save(jobEntity);

    }
    public List<JobEntity> getAllJobs() {
        return jobRepository.findAll();
    }

    public Page<JobEntity> searchJobs(String keyword, String location, String jobType, Pageable pageable) {
        if (keyword != null && keyword.isBlank()) {
            keyword = null;
        }
        if (location != null && location.isBlank()) {
            location = null;
        }
        if (jobType != null && jobType.isBlank()) {
            jobType = null;
        }
        return jobRepository.searchJobs(keyword, location, jobType, pageable);
    }
}



