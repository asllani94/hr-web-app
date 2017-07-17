package com.obss.Model.Repositories;

import com.obss.Model.Entities.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by arnold on 7/13/2017.
 */
public interface AdvertRepository extends JpaRepository<Advert,Integer> {


    public List<Advert> findByAdStatus(boolean status);

    public void deleteByAdCode(int adCode);
}
