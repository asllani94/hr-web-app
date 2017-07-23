package com.obss.Model.Repositories;

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.Advert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by arnold on 7/13/2017.
 */
public interface AdvertRepository extends JpaRepository<Advert,Integer> {


    public List<Advert> findByAdStatus(boolean status);

    public void deleteByAdCode(int adCode);

    @Query("select count(a) from Advert a")
    int countAllAdvert();

    Page<Advert> findAll(Pageable pageable);
}
