package com.obss.Model.Rep;

import com.obss.Model.Jpa.Advert;
import com.obss.Model.Jpa.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by arnold on 7/14/2017.
 */
public interface ApplicationRepository extends JpaRepository<Application,Integer> {

    List<Application> findByAdvert(Advert advert);
}
