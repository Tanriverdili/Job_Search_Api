package com.example.api.controller;
import com.example.api.entity.JobEntity;
import com.example.api.repository.JobRepository;
import com.example.api.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final JobRepository jobRepository;

    @PostMapping
    public JobEntity createJob(@RequestBody JobEntity jobEntity){
        return jobService.createJob(jobEntity);
    }
    @GetMapping("/suggestions")
    public List<String> suggestJobs(@RequestParam String keyword){
        return jobRepository.findTop5ByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(JobEntity::getTitle)
                .toList();
    }
    @GetMapping
    public Page<JobEntity> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (page<0) page = 0;
        if (size<=0|| size>50 ) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return jobService.searchJobs(keyword, location, jobType, pageable);
    }
}
