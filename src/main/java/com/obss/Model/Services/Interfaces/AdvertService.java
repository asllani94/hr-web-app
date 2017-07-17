package com.obss.Model.Services.Interfaces;

import com.obss.Model.Entities.Advert;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arnold on 7/17/2017.
 */
@Service
public interface AdvertService {

    public List<Advert> loadAllActiveAdverts();

    public List<Advert> loadAllInactiveAdverts();

    public Advert loadAdvertByAdCode(int adCode);

    public void createOrSaveAdvert(Advert advert);

    public void deleteAdvert(Advert advert);

}
