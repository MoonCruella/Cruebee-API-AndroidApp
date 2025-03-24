package androidapp.service;

import androidapp.entity.PromotionEntity;
import androidapp.repository.PromotionRepository;

import java.util.List;

public interface PromotionService {
    public List<PromotionEntity> findAll();
}
