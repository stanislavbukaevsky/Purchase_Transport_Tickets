package com.github.stanislavbukaevsky.purchasetransporttickets.service.impl;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.CarrierRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.CarrierResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.CarrierMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Carrier;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.CarrierRepository;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.CarrierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для перевозчика в приложении.
 * Реализует интерфейс {@link CarrierService}
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CarrierServiceImpl implements CarrierService {
    private final CarrierRepository carrierRepository;
    private final CarrierMapper carrierMapper;

    /**
     * Реализация метода для добавления новых перевозчиков в приложении.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param carrierRequestDto объект DTO с запросом от пользователя
     * @return Возвращает DTO с информацией о добавленном перевозчике
     */
    @Override
    public CarrierResponseDto addCarrier(@Valid CarrierRequestDto carrierRequestDto) {
        Carrier carrier = carrierMapper.toCarrierModel(carrierRequestDto);

        Carrier result = carrierRepository.save(carrier);
        log.info(ADD_CARRIER_MESSAGE_LOGGER_SERVICE, carrierRequestDto);
        return carrierMapper.toCarrierResponseDto(result);
    }

    /**
     * Реализация метода для изменения информации о перевозчике в приложении.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param carrierRequestDto объект DTO с запросом от пользователя
     * @param id                уникальный идентификатор перевозчика
     * @return Возвращает DTO с информацией об измененном перевозчике
     */
    @Override
    public CarrierResponseDto updateCarrier(@Valid CarrierRequestDto carrierRequestDto, @Positive Long id) {
        Carrier carrier = carrierRepository.findCarrierById(id);
        carrier.setCompanyName(carrierRequestDto.getCompanyName());
        carrier.setPhoneNumber(carrierRequestDto.getPhoneNumber());

        Carrier result = carrierRepository.update(carrier);
        log.info(UPDATE_CARRIER_MESSAGE_LOGGER_SERVICE, carrierRequestDto, id);
        return carrierMapper.toCarrierResponseDto(result);
    }

    /**
     * Реализация метода для удаления перевозчика из базы данных.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param id уникальный идентификатор перевозчика
     */
    @Override
    public void deleteCarrier(@Positive Long id) {
        Carrier carrier = carrierRepository.findCarrierById(id);
        carrierRepository.deleteById(carrier.getId());
        log.info(DELETE_CARRIER_MESSAGE_LOGGER_SERVICE, id);
    }
}
