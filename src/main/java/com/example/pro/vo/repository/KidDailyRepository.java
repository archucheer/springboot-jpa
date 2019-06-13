package com.example.pro.vo.repository;

import com.example.pro.vo.model.KidDailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: ccz
 * @Date: 2019/5/9 17:12
 * @Description:
 */
@Repository
public interface KidDailyRepository extends JpaRepository<KidDailyInfo, String> {


}
