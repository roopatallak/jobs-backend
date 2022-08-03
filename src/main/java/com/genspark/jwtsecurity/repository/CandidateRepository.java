package com.genspark.jwtsecurity.repository;

import com.genspark.jwtsecurity.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    String STR_QUERY = "select " +
            "  j.title," +
            "  sum(case when c.status = 'SCREEN' then 1 else 0 end) as Screen," +
            "  sum(case when c.status = 'INTERVIEW' then 1 else 0 end) as Interview," +
            "  sum(case when c.status = 'HIRE' then 1 else 0 end) as Hire," +
            "  sum(case when c.status = 'OFFER' then 1 else 0 end) as Offer " +
            " from job j " +
            " LEFT OUTER JOIN candidate c " +
            "  on j.job_id = c.job_id " +
            " group by j.title";

    @Query(value = "select * from candidate c where c.skills like %:skills% order by c.lname", nativeQuery = true)
    List<Candidate> findBySkills(@Param("skills") String skills);

    @Query(value = STR_QUERY, nativeQuery = true)
    String[][] getStatusResults();

}
