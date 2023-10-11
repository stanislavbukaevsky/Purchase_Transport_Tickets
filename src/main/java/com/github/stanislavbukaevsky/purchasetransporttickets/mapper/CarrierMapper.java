package com.github.stanislavbukaevsky.purchasetransporttickets.mapper;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.CarrierRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.CarrierResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Carrier;
import org.mapstruct.Mapper;

/**
 * Маппер-интерфейс, который преобразует информацию о перевозчике в DTO
 */
@Mapper
public interface CarrierMapper {
    /**
     * Этот метод преобразует полученные поля из DTO в модель, для получения информации о перевозчике
     *
     * @param carrierRequestDto DTO запроса с информацией о перевозчике
     * @return Возвращает сформированную модель с информацией о перевозчике
     */
    Carrier toCarrierModel(CarrierRequestDto carrierRequestDto);

    /**
     * Этот метод преобразует полученные поля из модели в DTO, для получения информации о перевозчике
     *
     * @param carrier модель с информацией о перевозчике
     * @return Возвращает сформированную DTO с ответом о перевозчике
     */
    CarrierResponseDto toCarrierResponseDto(Carrier carrier);
}
