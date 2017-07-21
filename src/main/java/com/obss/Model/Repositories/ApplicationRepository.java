package com.obss.Model.Repositories;

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.Advert;
import com.obss.Model.Entities.Application;
import com.obss.Model.Entities.Extras.ApplicationId;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by arnold on 7/21/2017.
 */
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Modifying
    @Query("UPDATE Application app set app.status=:status where app.pk.account.accountId=:accountId and app.pk.advert.adCode=:adCode")
    public void updateApplicationStatus(@Param("accountId") int accountId, @Param("adCode") int adCode, @Param("status") int status);

    @Modifying
    @Query(value = "insert into application (account_id,ad_code,status) VALUES (:accountId,:adCode,:status)", nativeQuery = true)
    public void insertNewApplication(@Param("accountId") int accountId, @Param("adCode") int adCode, @Param("status") int status);

}
