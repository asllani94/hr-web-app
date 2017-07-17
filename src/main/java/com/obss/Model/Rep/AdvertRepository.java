package com.obss.Model.Rep;

import com.obss.Model.Jpa.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by arnold on 7/13/2017.
 */
public interface AdvertRepository extends JpaRepository<Advert,Integer> {


    //Bring all adverts that are active
    public List<Advert> findByAdCodeAndAdStatusIsFalse(int adCode);

    //Bring all adverts that are inactive
    public List<Advert> findByAdCodeAndAdStatusIsTrue(int adCode);

    public void deleteByAdCode(int adCode);
}
