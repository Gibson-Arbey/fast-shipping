package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.DriverJpaMapper;
import co.fastshipping.jpa.repository.DriverJpaRepository;
import co.fastshipping.jpa.repository.LicenseCategoryJpaRepository;
import co.fastshipping.model.driver.Driver;
import co.fastshipping.model.driver.DriverStatus;
import co.fastshipping.model.driver.gateways.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DriverJpaAdapter implements DriverRepository {

    private final DriverJpaRepository driverJpaRepository;
    private final LicenseCategoryJpaRepository licenseCategoryJpaRepository;

    @Override
    @Transactional
    public Driver save(Driver driver) {
        var entity = DriverJpaMapper.toEntity(driver);
        entity.getLicenseCategories().forEach(link -> link.setLicenseCategory(
                licenseCategoryJpaRepository.getReferenceById(link.getLicenseCategory().getId())
        ));

        return DriverJpaMapper.toDomain(driverJpaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Driver> findAllByFilters(
            Long userId,
            DriverStatus status,
            String licenseNumber,
            String licenseCategoryCode
    ) {
        return driverJpaRepository.findAllByFilters(
             userId, status, licenseNumber, licenseCategoryCode
        )
        .stream()
        .map(DriverJpaMapper::toDomain)
        .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Driver findById(Long id) {
        return driverJpaRepository.findById(id)
                .map(DriverJpaMapper::toDomain)
                .orElse(null);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, DriverStatus status) {
        driverJpaRepository.updateStatus(id, status);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return driverJpaRepository.existsByUserId(userId);
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        return driverJpaRepository.existsByLicenseNumberIgnoreCase(licenseNumber);
    }
}
