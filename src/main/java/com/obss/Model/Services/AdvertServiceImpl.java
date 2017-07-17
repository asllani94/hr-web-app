package com.obss.Model.Services;

import com.obss.Model.Entities.Advert;
import com.obss.Model.Repositories.AdvertRepository;
import com.obss.Model.Services.Interfaces.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arnold on 7/17/2017.
 */
@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertRepository advertRepository;

    @Override
    public List<Advert> loadAllActiveAdverts() {
        return advertRepository.findByAdStatus(false);
    }

    @Override
    public List<Advert> loadAllInactiveAdverts() {
        return advertRepository.findByAdStatus(true);
    }

    @Override
    public Advert loadAdvertByAdCode(int adCode) {
        return advertRepository.findOne(adCode);
    }

    @Override
    public void createOrSaveAdvert(Advert advert) {
        advertRepository.save(advert);
    }

    @Override
    public void deleteAdvert(Advert advert) {
        advertRepository.delete(advert);
    }
}
