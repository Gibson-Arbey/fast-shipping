package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.entity.LicenseCategoryJpaEntity;
import co.fastshipping.jpa.mapper.LicenseCategoryJpaMapper;
import co.fastshipping.jpa.repository.LicenseCategoryJpaRepository;
import co.fastshipping.model.licensecategory.LicenseCategory;
import co.fastshipping.model.licensecategory.gateways.LicenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LicenseCategoryJpaAdapter implements LicenseCategoryRepository {

    private final LicenseCategoryJpaRepository licenseCategoryJpaRepository;

    @Override
    @Transactional
    public LicenseCategory save(LicenseCategory licenseCategory) {
        return LicenseCategoryJpaMapper.toDomain(
                licenseCategoryJpaRepository.save(LicenseCategoryJpaMapper.toEntity(licenseCategory))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseCategory> findAll() {
        return licenseCategoryJpaRepository.findAllByOrderByIdAsc()
                .stream()
                .map(LicenseCategoryJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LicenseCategory findByCode(String code) {
        return licenseCategoryJpaRepository.findByCodeIgnoreCase(code)
                .map(LicenseCategoryJpaMapper::toDomain)
                .orElse(null);
    }

    @Override
    @Transactional
    public void delete(LicenseCategory licenseCategory) {
        LicenseCategoryJpaEntity entity = licenseCategoryJpaRepository.findById(licenseCategory.getId())
                .orElse(null);
        if (entity != null) {
            licenseCategoryJpaRepository.delete(entity);
        }
    }
}
