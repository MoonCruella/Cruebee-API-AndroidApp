package androidapp.service.Impl;

import androidapp.entity.PromotionEntity;
import androidapp.repository.PromotionRepository;
import androidapp.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<PromotionEntity> findAll() {
        return promotionRepository.findAll();
    }
}
